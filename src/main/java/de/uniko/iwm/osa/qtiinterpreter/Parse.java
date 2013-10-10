package de.uniko.iwm.osa.qtiinterpreter;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import net.sf.saxon.s9api.Axis;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmSequenceIterator;
import net.sf.saxon.s9api.XdmValue;

import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type001;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type002;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type003;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type008;
import de.uniko.iwm.osa.data.model.Cy_QuestionWrapper;
import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.data.model.osaitem.OsaItem;

/**
 * @author user main class
 */
public class Parse {

	static Logger log = Logger.getLogger(Parse.class.getName());

	final String MANIFEST_NAME = "imsmanifest.xml";

	private final String QUERY_ASSESSMENTSECTION_RUBRIC = "imsqti:rubricBlock";
	private final String PART_RUBRIC = "imsqti:rubricBlock/child::node()";
	private final String QUERY_TITLE_ATTRIBUTE = "@title";
	private final String QUERY_IMSQTI_ASSESSMENTITEMREF = "imsqti:assessmentItemRef/@href";

	//
	// imsqti
	// assessmentItem
	//

	/**
	 * xpath-queries imsqti
	 */
	private final String PART_ITEM_BODY = "/imsqti:assessmentItem/imsqti:itemBody";
	private final String PART_ASS_TITLE = "/imsqti:assessmentItem/@title";
	private final String PART_ASS_IDENTIFIER = "/imsqti:assessmentItem/@identifier";

	private final String PART_CORRECT_RESP = "/imsqti:assessmentItem/imsqti:responseDeclaration/imsqti:correctResponse/imsqti:value";

	private final String IMAGE_TAG = "//imsqti:img";
	private final String IMAGE_PREFIX = "<img";

	private final String IQ_QUERY_TASK = PART_ITEM_BODY + "/p/text()";
	private final String IQ_QUERY_QUESTION = PART_ITEM_BODY
			+ "/p/descendant::imsqti:img/@src";
	// "/p/imsqti:img/@src"
	// + "|" + PART_ITEM_BODY + "/xsi:p/imsqti:img/@src";

	private final String IQ_QUERY_CHOICES = PART_ITEM_BODY
			+ "/imsqti:choiceInteraction"
			+ "/imsqti:simpleChoice/imsqti:img/@src";

	private final String PART_HTML = "//imsqti:itemBody/child::node()";
	// final String PART_HTML = "//imsqti:itemBody";

	Pattern PATTERN_IMAGE_SRC = Pattern.compile("src=\"media");

	private String base;
	private String cy_image_base;
	private String qti_media_folder;

	// private String image_base;

	/* --- generated values --- */

	private List<Cy_QuestionWrapper> generated_pages = new ArrayList<Cy_QuestionWrapper>();

	// private OsaPage osaPage = null;
	private HashMap<String, ManifestItem> identifier2questionType;
	private HashMap<String, Integer> questionType2CyquestQuestionType;

	private int count = 0;
	private int pageCount = 0;

	private OsaItem oi = null;

	public Parse(String base, String cy_image_base, String oss_media_folder,
			HashMap<String, Integer> questionType2CyquestQuestionType,
			OsaItem oi) {

		this.base = base;
		this.cy_image_base = cy_image_base;
		this.qti_media_folder = oss_media_folder;
		this.questionType2CyquestQuestionType = questionType2CyquestQuestionType;
		this.oi = oi;

		// this.image_base = image_base;
		identifier2questionType = new HashMap<String, ManifestItem>();

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

		// XPathSelector selector;

		try {
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

			AssessmentConfigurer ac = new AssessmentConfigurer(base, filename);
			handle_IMSItem(ac);
			XdmValue children = ac.queryAssessment();

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

		AssessmentConfigurer ac = new AssessmentConfigurer(base, href);
		XdmValue children = ac.queryAssessmentTest();

		for (XdmItem child : children) {
			//
			// tests
			//
			log.info("AssessmentTest");

			if (handle_AssessmentTest((XdmNode) child))
				return true;
		}

		return false;
	}

	private boolean handle_AssessmentTest(XdmNode node)
			throws FileNotFoundException, SaxonApiException,
			UnsupportedEncodingException, NoSuchAlgorithmException {

		AssessmentConfigurer ac = new AssessmentConfigurer(node);
		XdmValue children = ac.queryTestpart();

		for (XdmItem child : children) {
			//
			// testParts
			//
			log.info("TestPart");
			handle_TestPart((XdmNode) child);
		}

		return true;
	}

	private void handle_TestPart(XdmNode node) throws FileNotFoundException,
			SaxonApiException, UnsupportedEncodingException,
			NoSuchAlgorithmException {

		int cy_questid = 0;

		AssessmentConfigurer ac = new AssessmentConfigurer(node);
		XdmValue children = ac.queryAssessmentSection();

		for (XdmItem child : children) {
			cy_questid++;

			//
			// assessment section
			//
			log.info("AssessmentSection");

			handle_AssessmentSection((XdmNode) child, cy_questid);
		}
	}

	private void handle_AssessmentSection(XdmNode node, int cy_questid)
			throws FileNotFoundException, SaxonApiException,
			UnsupportedEncodingException {

		PageQuestitemConfigurer pq_config = new PageQuestitemConfigurer(node);
		Cy_QuestionWrapper qw = null;

		int cy_position = 0;

		//
		// find refs
		//

		List<String> hrefs = pq_config.queryAssessmentItemref();

		//
		// go on
		//

		for (String href : hrefs) {
			count++;
			cy_position++;

			QuestConfigurer q_config = new QuestConfigurer(base, href);
			PagesQuestitemsQuestsMisc pqiqm = getPageAndQuest(pq_config,
					q_config, cy_position);

			log.info(String.format("ref file: %s", href));
			log.info(String.format(
					"count [%2d], questid [%2d], position [%2d]", count,
					cy_questid, cy_position));

			handle_imsqti_item_xmlv2p1(q_config, pqiqm);

			// getPageAndQuest(pqiqm, pq_config, quest_config, pageCount);

			// pqiqm.setQi_questsubhead(String.format("Aufgabe %d von %d",cy_position,
			// hrefs.size()));

			if ((cy_position % pqiqm.getM_itemPerPage() == 0) || qw == null) {

				qw = new Cy_QuestionWrapper(pqiqm);
				generated_pages.add(qw);
			}

			log.info("IT: " + pqiqm);
			qw.addQuest(pqiqm.getQuests());
		}

	}

	private boolean handle_imsqti_item_xmlv2p1(QuestConfigurer qc,
			PagesQuestitemsQuestsMisc pqiqm) throws FileNotFoundException,
			SaxonApiException {

		ManifestItem manifestItem = identifier2questionType.get(qc
				.queryIdentifier());

		pqiqm.setP_name(manifestItem.getQuestTitle());

		manifestItem.getAi().setup(pqiqm, qc);

		return true;
	}

	// -------------------------------- helper ------------ //

	private PagesQuestitemsQuestsMisc getPageAndQuest(
			PageQuestitemConfigurer pq_config, QuestConfigurer q_config,
			int pageCount) {

		PagesQuestitemsQuestsMisc pqiqm = new PagesQuestitemsQuestsMisc();

		// quest.setQuestdesc(ic.queryQuestDescription());

		// quest shosdesrc
		// pqiqm.setQi_questhead(pq_config.queryQuestTitle());
		String title = q_config.queryTitle();
		pqiqm.setQi_questhead(title.isEmpty() ? pqiqm.getQi_questhead() : title);
		pqiqm.setQi_questsubhead("Aufgabe");
		pqiqm.setQi_questdesc(pq_config.queryRubric());

		// pqiqm.setQi_questdesc(pages_config.queryIQTask());

		// page pagesid

		String pagesIDText = String.format("X%04d", count);
		pqiqm.setP_pid(pagesIDText);

		// cy_db_quest.setQuestsubhead(String.format("Aufgabe %d von %d",
		// cy_questid, hrefs.size()));
		// quest.setPosition(cy_position);
		pqiqm.setQ_shownum(String.format("%d", count));
		pqiqm.setQ_showdesc(q_config.queryQuestionText());

		return pqiqm;
	}

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

	private void handle_IMSItem(AssessmentConfigurer ac)
			throws FileNotFoundException {
		// try {
		// XPathSelector selector = xpath.compile(QUERY_MANIFEST_ITEM).load();
		// selector.setContextItem(item);
		// XdmValue children = selector.evaluate();

		XdmValue children = ac.queryItem();

		for (XdmItem child : children) {
			XdmNode resNode = (XdmNode) child;

			String href = resNode.getAttributeValue(new QName("href"));
			String identifier = resNode.getAttributeValue(new QName(
					"identifier"));

			ManifestItem manifestItem = new ManifestItem(resNode);
			identifier2questionType.put(identifier, manifestItem);

			//
			// item nodes
			//
			log.info("TestPart " + href + "/" + identifier);
			log.info("         " + manifestItem);
		}
		// } catch (SaxonApiException e) {
		// e.printStackTrace();
		// }
	}

	// getter & setter

	public List<Cy_QuestionWrapper> getGenerated_pages() {
		return generated_pages;
	}

	public void setGenerated_pages(List<Cy_QuestionWrapper> generated_pages) {
		this.generated_pages = generated_pages;
	}
}
