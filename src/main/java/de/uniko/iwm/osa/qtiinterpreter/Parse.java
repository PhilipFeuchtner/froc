package de.uniko.iwm.osa.qtiinterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.sf.saxon.s9api.Axis;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.WhitespaceStrippingPolicy;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmSequenceIterator;
import net.sf.saxon.s9api.XdmValue;

import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type001;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type002;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type003;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type008;
import de.uniko.iwm.osa.data.model.Cy_PageItem;
import de.uniko.iwm.osa.data.model.Cy_QuestItem;
import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.model.OsaItem;
import de.uniko.iwm.osa.utils.HtmlFilter;

/**
 * @author user main class
 */
public class Parse {

	static Logger log = Logger.getLogger(Parse.class.getName());

	final String MANIFEST_NAME = "imsmanifest.xml";

	//
	// imscp assessment
	//
	/**
	 * xpath expressions imsmanifest
	 */
	// final String QUERY_MANIFEST_RESOURCE =
	// "/imscp:manifest/imscp:resources/imscp:resource[@type='imsqti_item_xmlv2p1']";
	final String QUERY_MANIFEST_ASSESSMENT = "/imscp:manifest/imscp:resources"
			+ "/imscp:resource[@type='imsqti_assessment_xmlv2p1']";
	final String QUERY_MANIFEST_ITEM = "/imscp:manifest"
			+ "/imscp:resources/imscp:resource[@type='imsqti_item_xmlv2p1']";
	final String QUERY_MANIFEST_DESCRIPTION = "/imscp:manifest//imscp:resources/imscp:resource"
			+ "/imsmd:metadata/imsmd:lom/imsmd:general/imsmd:description/imsmd:langstring";

	// imsmd:metadata/imsmd:lom/imsmd:general/imsmd:keyword/imsmd:langstring
	final String QUERY_MANIFEST_CY_QUESTIONTYPE = "imsmd:metadata/imsmd:lom/imsmd:general"
			+ "//imsmd:keyword/imsmd:langstring/text()";

	//
	// imsqti
	// assessment -> assessmentItemRef
	//

	/**
	 * xpath imsqti entries
	 */
	final String QUERY_IMSQTI_ASSESSMENTTEST = "imsqti:assessmentTest";
	final String QUERY_IMSQTI_TESTPART = "imsqti:testPart";
	final String QUERY_IMSQTI_ASSESSMENTSECTION = "imsqti:assessmentSection";
	// final String QUERY_ASSESSMENTSECTION_RUBRIC =
	// "imsqti:rubricBlock/child::node()";
	final String QUERY_ASSESSMENTSECTION_RUBRIC = "imsqti:rubricBlock";
	final String PART_RUBRIC = "imsqti:rubricBlock/child::node()";

	final String QUERY_TITLE_ATTRIBUTE = "@title";
	final String QUERY_IMSQTI_ASSESSMENTITEMREF = "imsqti:assessmentItemRef/@href";

	//
	// imsqti
	// assessmentItem
	//

	/**
	 * xpath-queries imsqti
	 */
	final String PART_ITEM_BODY = "/imsqti:assessmentItem/imsqti:itemBody";
	final String PART_ASS_TITLE = "/imsqti:assessmentItem/@title";
	final String PART_ASS_IDENTIFIER = "/imsqti:assessmentItem/@identifier";

	final String PART_CORRECT_RESP = "/imsqti:assessmentItem/imsqti:responseDeclaration/imsqti:correctResponse/imsqti:value";

	final String IMAGE_TAG = "//imsqti:img";
	final String IMAGE_PREFIX = "<img";

	final String IQ_QUERY_TASK = PART_ITEM_BODY + "/p/text()";
	final String IQ_QUERY_QUESTION = PART_ITEM_BODY + "/p/imsqti:img/@src";
	final String IQ_QUERY_CHOICES = PART_ITEM_BODY
			+ "/imsqti:choiceInteraction"
			+ "//imsqti:simpleChoice/imsqti:img/@src";

	final String PART_HTML = "//imsqti:itemBody/child::node()";
	// final String PART_HTML = "//imsqti:itemBody";

	Pattern PATTERN_IMAGE_SRC = Pattern.compile("src=\"media");

	final String MAGIC_INTERESSEN_TYPEVALUES = "a:1:{s:8:\"scaledir\";s:2:\"up\";}";

	Processor proc;
	XPathCompiler xpath;
	DocumentBuilder builder;

	// private final String sep = System.getProperty("file.separator");

	private String base;
	private String cy_image_base;
	
	// private String image_base;

	/* --- generated values --- */

	private List<Cy_PageItem> generated_pages = new ArrayList<Cy_PageItem>();

	// private OsaPage osaPage = null;
	private HashMap<String, String> identifier2questionType;
	private HashMap<String, Integer> questionType2CyquestQuestionType;

	private int count = 0;

	private String MD5PREFIX = "MD5PREFIX %s %d";
	private int MD5Counter = 0;

	private int pageCount = 0;

	private String pagesid;

	private OsaItem oi = null;

	public Parse(String base, String cy_image_base,
			HashMap<String, Integer> questionType2CyquestQuestionType,
			String pagesid, OsaItem oi) {
		
		this.base = base;
		this.cy_image_base = cy_image_base;
		this.pagesid = pagesid;
		this.questionType2CyquestQuestionType = questionType2CyquestQuestionType;
		this.oi = oi;
		
		// this.image_base = image_base;
		identifier2questionType = new HashMap<String, String>();

		proc = new Processor(false);
		xpath = proc.newXPathCompiler();

		xpath.declareNamespace("imscp",
				"http://www.imsglobal.org/xsd/imscp_v1p1");
		xpath.declareNamespace("imsmd",
				"http://www.imsglobal.org/xsd/imsmd_v1p2p2");
		xpath.declareNamespace("imsqti",
				"http://www.imsglobal.org/xsd/imsqti_v2p1");

		builder = proc.newDocumentBuilder();
		builder.setLineNumbering(true);
		builder.setWhitespaceStrippingPolicy(WhitespaceStrippingPolicy.ALL);

	}

	/**
	 * entry point parses qti-file and produces a list of generated pages
	 * 
	 * @param filename
	 *            file to parse
	 * @return true/false
	 * @throws FileNotFoundException
	 */
	public boolean handleManifest(String filename) throws FileNotFoundException {
		// osaPage = new OsaPage();

		XPathSelector selector;

		try {
			XdmNode manifestDoc = builder.build(new File(base, filename));

			// //
			// // query description
			// //
			// selector = xpath.compile(QUERY_MANIFEST_DESCRIPTION)
			// .load();
			// selector.setContextItem(manifestDoc);
			// XdmValue descs = selector.evaluate();
			//
			// log.info("ASS STRING search");
			//
			// for (XdmItem item : descs) {
			// XdmNode resNode = (XdmNode) item;
			//
			// String text = resNode.getStringValue();
			// System.out.println("ASS STRING:" + text);
			// }

			//
			// collect metadata
			//

			handle_IMSItem(manifestDoc);

			//
			// query assessmentTest
			//

			selector = xpath.compile(QUERY_MANIFEST_ASSESSMENT).load();
			selector.setContextItem(manifestDoc);
			XdmValue children = selector.evaluate();

			for (XdmItem child : children) {
				XdmNode resNode = (XdmNode) child;

				String href = resNode.getAttributeValue(new QName("href"));
				if (handle_AssessmentFile(href)) {
					return true;
				}
			}

		} catch (SaxonApiException | UnsupportedEncodingException
				| NoSuchAlgorithmException e) {
			oi.addErrorEntry(e.getMessage());
			e.printStackTrace();

			return false;
		}

		return false;
	}

	private boolean handle_AssessmentFile(String href)
			throws FileNotFoundException, SaxonApiException,
			UnsupportedEncodingException, NoSuchAlgorithmException {

		count = 0;

		XdmNode item = builder.build(new File(base, href));

		XPathSelector selector = xpath.compile(QUERY_IMSQTI_ASSESSMENTTEST)
				.load();
		selector.setContextItem(item);
		XdmValue children = selector.evaluate();

		for (XdmItem child : children) {
			//
			// tests
			//
			log.info("AssessmentTest");

			if (handle_AssessmentTest(child))
				return true;
		}

		return false;
	}

	private boolean handle_AssessmentTest(XdmItem item)
			throws FileNotFoundException, SaxonApiException,
			UnsupportedEncodingException, NoSuchAlgorithmException {

		XPathSelector selector = xpath.compile(QUERY_IMSQTI_TESTPART).load();
		selector.setContextItem(item);
		XdmValue children = selector.evaluate();

		for (XdmItem child : children) {
			//
			// testParts
			//
			log.info("TestPart");

			handle_TestPart(child);
		}

		return true;
	}

	private void handle_TestPart(XdmItem item) throws FileNotFoundException,
			SaxonApiException, UnsupportedEncodingException,
			NoSuchAlgorithmException {

		int cy_questid = 0;

		XPathSelector selector = xpath.compile(QUERY_IMSQTI_ASSESSMENTSECTION)
				.load();
		selector.setContextItem(item);
		XdmValue children = selector.evaluate();

		for (XdmItem child : children) {
			cy_questid++;

			//
			// assessment section
			//
			log.info("AssessmentSection");

			handle_AssessmentSection(child, cy_questid);
		}
	}

	private void handle_AssessmentSection(XdmItem item, int cy_questid)
			throws FileNotFoundException, SaxonApiException,
			UnsupportedEncodingException, NoSuchAlgorithmException {

		ItemConigurator ic = new ItemConigurator((XdmNode) item);

		int cy_position = 0;

		OsaDbPages cy_db_page = new OsaDbPages();
		OsaDbQuestitems cy_db_quest = new OsaDbQuestitems();

		Cy_PageItem cy_page = new Cy_PageItem(cy_db_page);
		Cy_QuestItem cy_quest = new Cy_QuestItem(cy_db_quest);

		// pagesid ----------------------------------------------

		cy_db_page.setPid(pagesid + pageCount++);

		// md5 --------------------------------------------------

		{
			MD5Counter++;
			String md5Text = String.format(MD5PREFIX, MD5Counter,
					System.currentTimeMillis());

			final MessageDigest messageDigest = MessageDigest
					.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(md5Text.getBytes(Charset.forName("UTF8")));
			final String result = new String(Hex.encodeHex(messageDigest
					.digest()));
			log.info("md5 [" + md5Text + "] " + result);

			// --------------------------------------------------

			cy_db_page.setMd5key(result);
		}

		// ------------------------------------------------------

		//
		// title
		//

		{
			String text = ic.queryToString(QUERY_TITLE_ATTRIBUTE);
			cy_db_quest.setQuesthead(text);
		}

		//
		// set rubricBlock
		//

		{
			String text = ic.cleanHtmlContent(QUERY_ASSESSMENTSECTION_RUBRIC,
					PART_RUBRIC);
			cy_db_quest.setQuestdesc(text);
		}

		//
		// find refs
		//

		List<String> hrefs = ic
				.queryToStringList(QUERY_IMSQTI_ASSESSMENTITEMREF);

		//
		// go on
		//

		for (String href : hrefs) {
			count++;
			cy_position++;

			log.info(String.format("ref file: %s", href));
			log.info(String.format(
					"count [%2d], questid [%2d], position [%2d]", count,
					cy_questid, cy_position));

			cy_db_quest.setQuestsubhead(String.format("Aufgabe %d von %d",
					cy_questid, hrefs.size()));

			AssessmentItem ai = handle_imsqti_item_xmlv2p1(href, cy_questid,
					cy_position, cy_db_page, cy_db_quest);

			if (ai != null) {
				log.info("IT: " + ai);

				cy_quest.addQuest(ai.getOsaDbQuest());
			}
		}

		// save results -----------------------------------------

		cy_page.addQuest(cy_quest);
		generated_pages.add(cy_page);

		// ------------------------------------------------------
	}

	private AssessmentItem handle_imsqti_item_xmlv2p1(String href,
			int cy_questid, int cy_position, OsaDbPages cy_page,
			OsaDbQuestitems cy_questitem) throws FileNotFoundException,
			SaxonApiException {

		OsaDbQuests cy_quest = new OsaDbQuests();
		ItemConigurator ic = new ItemConigurator(href, cy_page);

		cy_quest.setPosition(cy_position);
		cy_quest.setShownum(String.format("%d", count));
		cy_quest.setShowdesc(ic.queryShowdescr());

		AssessmentItem question = null;

		String questionType = identifier2questionType.get(ic.queryIdentifier());

		if (questionType != null
				&& questionType2CyquestQuestionType.containsKey(questionType)) {
			int cyType = questionType2CyquestQuestionType.get(questionType);

			//
			// set cyquest-question-type
			//
			cy_questitem.setQuesttype(cyType);

			switch (cyType) {
			case 1:
				return new AssessmentItem_Type001(cy_quest, ic);
			case 2:
				return new AssessmentItem_Type002(cy_quest, ic);
			case 3:
				return new AssessmentItem_Type003(cy_quest, ic);
			case 8:
				return new AssessmentItem_Type008(cy_quest, ic);

			default:
				oi.addErrorEntry("QuestionType not implemented: "
						+ questionType);
				log.error("QuestionType not implemented: " + questionType);
			}

		} else {
			oi.addErrorEntry("QuestionType not defined: "
					+ ic.queryIdentifier() + " " + questionType);
			log.error("QuestionType not defined: " + ic.queryIdentifier() + " "
					+ questionType);
		}

		return question;
	}

	// -------------------------------- helper ------------ //

	/**
	 * Helper method to get the first child of an element having a given name.
	 * If there is no child with the given name it returns null.
	 */
	public static XdmNode getChild(XdmNode parent, String childName) {

		XdmSequenceIterator iter = parent.axisIterator(Axis.CHILD, new QName(
				childName));

		if (iter.hasNext()) {
			return (XdmNode) iter.next();
		} else {
			return null;
		}
	}

	// ------------------------------------------------------------ //
	// --- imsmanifest
	// --- handle imsqti:imsassessmetitem
	// --- and lommd:metadata

	private void handle_IMSItem(XdmItem item) throws FileNotFoundException,
			SaxonApiException {

		XPathSelector selector = xpath.compile(QUERY_MANIFEST_ITEM).load();
		selector.setContextItem(item);
		XdmValue children = selector.evaluate();

		for (XdmItem child : children) {
			XdmNode resNode = (XdmNode) child;

			String href = resNode.getAttributeValue(new QName("href"));
			String identifier = resNode.getAttributeValue(new QName(
					"identifier"));
			//
			// item nodes
			//
			log.info("TestPart " + href + "/" + identifier);

			//
			// parse metadata
			//
			handle_IMSMetadata(child);
		}
	}

	private void handle_IMSMetadata(XdmItem item) throws FileNotFoundException,
			SaxonApiException {

		String identifier = ((XdmNode) item).getAttributeValue(new QName(
				"identifier"));

		XPathSelector selector = xpath.compile(QUERY_MANIFEST_CY_QUESTIONTYPE)
				.load();
		selector.setContextItem(item);
		XdmValue children = selector.evaluate();

		for (XdmItem child : children) {
			XdmNode resNode = (XdmNode) child;
			//
			// text nodes
			//
			String questionType = resNode.getStringValue();
			if (questionType.startsWith("qt")) {
				identifier2questionType.put(identifier, questionType);
				log.info("Cyquest Question Type: " + questionType);
				return;
			} else {
				oi.addErrorEntry("Unknown Cyquest Question Type: "
						+ questionType);
				log.error("Unknown Cyquest Question Type: " + questionType);
			}
		}
	}

	/* --- helper --- */

	/* --- getter & setter --- */

	// ----------------------------------------------------------------------

	public class ItemConigurator {

		XdmNode node;

		ItemConigurator(XdmNode node) {
			this.node = node;
		}

		ItemConigurator(String href, OsaDbPages cy_page)
				throws SaxonApiException {
			XdmNode document = builder.build(new File(base, href));

			this.node = document;
		}

		String queryToString(String query) throws SaxonApiException {
			return StringUtils.join(queryToStringList(query), "");
		}

		List<String> queryToStringList(String query) throws SaxonApiException {
			List<String> result = new ArrayList<String>();

			XPathSelector selector = xpath.compile(query).load();
			selector.setContextItem(node);

			// Evaluate the expression.
			XdmValue children_titles = selector.evaluate();

			for (XdmItem item : children_titles) {
				result.add(item.getStringValue());
				log.info(String.format("Found: (%s)", item.getStringValue()));
			}
			return result;
		}

		public String cleanHtmlContent(String XPATH_NODE, String XPATH_CHILD)
				throws SaxonApiException {
			String result = "";

			XPathSelector selector = xpath.compile(XPATH_NODE).load();
			selector.setContextItem(node);

			// Evaluate the expression.
			XdmValue children = selector.evaluate();

			for (XdmItem item : children) {
				// log.info("------>" + item.toString());

				HtmlFilter hf = new HtmlFilter();
				String fragment = hf.parseText(item.toString());

				XdmNode htmlDoc = builder.build(new StreamSource(
						new java.io.StringReader(fragment)));

				selector = xpath.compile(XPATH_CHILD).load();
				selector.setContextItem(htmlDoc);

				// Evaluate the expression.
				XdmValue htmlNodes = selector.evaluate();

				StringBuilder html = new StringBuilder();
				for (XdmItem htmlNode : htmlNodes) {
					html.append(htmlNode.toString());
				}

				result += html.toString().trim();
			}
			log.info(String.format("HTML   : (%s)", result));

			return result;
		}

		// ----------------------------------------------------- //

		public String queryIdentifier() {
			try {
				return queryToString(PART_ASS_IDENTIFIER);
			} catch (SaxonApiException e) {
				oi.addErrorEntry(e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		public String queryTitle() {
			try {
				return queryToString(PART_ASS_TITLE);
			} catch (SaxonApiException e) {
				oi.addErrorEntry(e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		public List<String> queryCorrectResp() {
			try {
				return queryToStringList(PART_CORRECT_RESP);
			} catch (SaxonApiException e) {
				oi.addErrorEntry(e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		public String queryShowdescr() {
			try {
				return cleanHtmlContent(PART_ITEM_BODY, PART_HTML);
			} catch (SaxonApiException e) {
				oi.addErrorEntry(e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		// type 8

		public String queryIQTask() {
			try {
				return queryToString(IQ_QUERY_TASK);
			} catch (SaxonApiException e) {
				oi.addErrorEntry(e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		public String queryIQQuestion() {
			try {
				return queryToString(IQ_QUERY_QUESTION);
			} catch (SaxonApiException e) {
				e.printStackTrace();
				oi.addErrorEntry(e.getMessage());
			}

			return null;
		}

		public List<String> queryIQChoices() {
			try {
				return queryToStringList(IQ_QUERY_CHOICES);
			} catch (SaxonApiException e) {
				e.printStackTrace();
				oi.addErrorEntry(e.getMessage());
			}

			return null;
		}
		
		public String getCy_image_base() {
			return cy_image_base;
		}
	}

	// getter & setter

	public List<Cy_PageItem> getGenerated_pages() {
		return generated_pages;
	}

	public void setGenerated_pages(List<Cy_PageItem> generated_pages) {
		this.generated_pages = generated_pages;
	}
}
