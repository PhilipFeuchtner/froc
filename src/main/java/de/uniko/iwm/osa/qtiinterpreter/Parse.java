package de.uniko.iwm.osa.qtiinterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import de.uniko.iwm.osa.data.model.AssessmentItem;
import de.uniko.iwm.osa.data.model.AssessmentItem.AssessmentItemType;
import de.uniko.iwm.osa.data.model.AssessmentSection;
import de.uniko.iwm.osa.data.model.AssessmentTest;
import de.uniko.iwm.osa.data.model.TestPart;

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

public class Parse {

	final String MANIFEST_NAME = "imsmanifest.xml";

	//
	// imscp assessment
	//
	// final String QUERY_MANIFEST_RESOURCE =
	// "/imscp:manifest/imscp:resources/imscp:resource[@type='imsqti_item_xmlv2p1']";
	final String QUERY_MANIFEST_ASSESSMENT = "/imscp:manifest/imscp:resources/imscp:resource[@type='imsqti_assessment_xmlv2p1']";

	//
	// imsqti
	// assessment -> assessmentItemRef
	//

	final String QUERY_IMSQTI_ASSESSMENTTEST = "imsqti:assessmentTest";
	final String QUERY_IMSQTI_TESTPART = "imsqti:testPart";
	final String QUERY_IMSQTI_ASSESSMENTSECTION = "imsqti:assessmentSection";
	// final String QUERY_ASSESSMENTSECTION_RUBRIC =
	// "imsqti:rubricBlock/child::node()";
	final String QUERY_ASSESSMENTSECTION_RUBRIC = "imsqti:rubricBlock";
	final String PART_RUBRIC = "imsqti:rubricBlock/child::node()";

	final String QUERY_IMSQTI_ASSESSMENTITEMREF = "imsqti:assessmentItemRef/@href";

	//
	// imsqti
	// assessmentItem
	//

	final String PART_ITEM_BODY = "/imsqti:assessmentItem/imsqti:itemBody";
	final String PART_ASS_TITLE = "/imsqti:assessmentItem/@title";

	final String PART_CORRECT_RESP = "/imsqti:assessmentItem/imsqti:responseDeclaration/imsqti:correctResponse/imsqti:value";

	final String IMAGE_TAG = "//imsqti:img";
	final String IMAGE_PREFIX = "<img";

	final String PART_HTML = "//imsqti:itemBody/child::node()";
	// final String PART_HTML = "//imsqti:itemBody";

	Pattern PATTERN_IMAGE_SRC = Pattern.compile("src=\"media");

	Processor proc;
	XPathCompiler xpath;
	DocumentBuilder builder;

	// private final String sep = System.getProperty("file.separator");

	private String base;
	private String image_base;

	private int count = 0;

	public Parse(String base, String image_base) {
		this.base = base;
		this.image_base = image_base;

		proc = new Processor(false);
		xpath = proc.newXPathCompiler();

		xpath.declareNamespace("imscp",
				"http://www.imsglobal.org/xsd/imscp_v1p1");
		xpath.declareNamespace("imsmd",
				"http://www.imsglobal.org/xsd/imsmd_v1p2");
		xpath.declareNamespace("imsqti",
				"http://www.imsglobal.org/xsd/imsqti_v2p1");

		builder = proc.newDocumentBuilder();
		builder.setLineNumbering(true);
		builder.setWhitespaceStrippingPolicy(WhitespaceStrippingPolicy.ALL);

	}

	public AssessmentTest handleManifest(String filename) throws FileNotFoundException {
		AssessmentTest assessmentTest = null;

		try {
			XdmNode manifestDoc = builder.build(new File(base, filename));

			XPathSelector selector = xpath.compile(QUERY_MANIFEST_ASSESSMENT)
					.load();
			selector.setContextItem(manifestDoc);

			// Evaluate the expression.
			XdmValue children = selector.evaluate();

			for (XdmItem item : children) {
				XdmNode resNode = (XdmNode) item;

				String id = resNode.getAttributeValue(new QName("href"));
				assessmentTest = handle_AssessmentFile(id);
			}
		} catch (SaxonApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return assessmentTest;
	}

	private AssessmentTest handle_AssessmentFile(String href)
			throws FileNotFoundException {

		AssessmentTest assessmentTest = null;
		count = 0;

		try {
			XdmNode item = builder.build(new File(base, href));

			XPathSelector selector = xpath.compile(QUERY_IMSQTI_ASSESSMENTTEST)
					.load();
			selector.setContextItem(item);
			XdmValue children = selector.evaluate();

			for (XdmItem child : children) {
				//
				// tests
				//
				System.out.println("AssessmentTest");

				assessmentTest = handle_AssessmentTest(child);
			}

		} catch (SaxonApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return assessmentTest;
	}

	private AssessmentTest handle_AssessmentTest(XdmItem item)
			throws FileNotFoundException, SaxonApiException {
		AssessmentTest assessmentTest = new AssessmentTest();

		XPathSelector selector = xpath.compile(QUERY_IMSQTI_TESTPART).load();
		selector.setContextItem(item);
		XdmValue children = selector.evaluate();

		for (XdmItem child : children) {
			//
			// testParts
			//
			System.out.println("TestPart");

			TestPart testPart = handle_TestPart(child);
			assessmentTest.addTestPart(testPart);
		}

		return assessmentTest;
	}

	private TestPart handle_TestPart(XdmItem item)
			throws FileNotFoundException, SaxonApiException {
		TestPart testPart = new TestPart();
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
			System.out.println("AssessmentSection");

			AssessmentSection assessmentSection = handle_AssessmentSection(
					child, cy_questid);
			testPart.addAssessmentSection(assessmentSection);
		}

		return testPart;
	}

	private AssessmentSection handle_AssessmentSection(XdmItem item,
			int cy_questid) throws FileNotFoundException, SaxonApiException {
		AssessmentSection assessmentSection = new AssessmentSection();
		int cy_position = 0;

		//
		// title
		//

		XPathSelector selector = xpath.compile("@title").load();
		selector.setContextItem(item);
		XdmValue titles = selector.evaluate();

		//
		// set title
		//
		for (XdmItem att_titles : titles) {
			String text = att_titles.getStringValue();
			assessmentSection.setTitle(text);

			System.out.println(String.format("Att title: (%s)", text));
		}

		//
		// set rubricBlock
		//

		String text = cleanHtmlContent(selector, (XdmNode) item,
				QUERY_ASSESSMENTSECTION_RUBRIC, PART_RUBRIC);
		assessmentSection.setRubicBlock(text);

		//
		// find refs
		//

		selector = xpath.compile(QUERY_IMSQTI_ASSESSMENTITEMREF).load();
		selector.setContextItem(item);
		XdmValue children_item3 = selector.evaluate();

		//
		// go on
		//

		for (XdmItem refs : children_item3) {
			count++;
			cy_position++;

			System.out.println(String.format("ref file: %s",
					refs.getStringValue()));
			System.out.println(String.format(
					"count [%2d], questid [%2d], position [%2d]", count,
					cy_questid, cy_position));

			AssessmentItem it = handle_imsqti_item_xmlv2p1(
					refs.getStringValue(), cy_questid, cy_position);

			if (it != null) {
				it.setShownum("" + count);
				it.setAssessmentType(AssessmentItemType.INTERESSEN);

				assessmentSection.addAssessmentItem(it);
				System.out.println("IT: " + it);
			}
		}

		return assessmentSection;
	}

	private AssessmentItem handle_imsqti_item_xmlv2p1(String href,
			int cy_questid, int cy_position) throws FileNotFoundException {

		AssessmentItem question = new AssessmentItem();

		XdmNode document;
		try {
			document = builder.build(new File(base, href));

			XPathSelector selector;

			//
			// find title
			//

			selector = xpath.compile(PART_ASS_TITLE).load();
			selector.setContextItem(document);

			// Evaluate the expression.
			XdmValue children_tiltes = selector.evaluate();

			for (XdmItem item : children_tiltes) {
				System.out.println(String.format("TITLE  : (%s)",
						item.getStringValue()));
			}

			//
			// find correct responses
			//

			selector = xpath.compile(PART_CORRECT_RESP).load();
			selector.setContextItem(document);

			// Evaluate the expression.
			XdmValue children_correct_responses = selector.evaluate();

			for (XdmItem item : children_correct_responses) {
				System.out.println(String.format("CORRECT: (%s)",
						item.getStringValue()));
			}

			//
			// parse content
			//

			String text = cleanHtmlContent(selector, document, PART_ITEM_BODY,
					PART_HTML);
			question.setShowdesc(text);

			question.setId(count);
			question.setPosition(cy_position);
			question.setQuestid(cy_questid);

		} catch (SaxonApiException e) {
			question = null;

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return question;
	}

	// -------------------------------- helper ------------ //

	public String cleanHtmlContent(XPathSelector selector, XdmNode doc,
			String XPATH_NODE, String XPATH_CHILD) throws SaxonApiException {
		String result = "";

		selector = xpath.compile(XPATH_NODE).load();
		selector.setContextItem(doc);

		// Evaluate the expression.
		XdmValue children = selector.evaluate();

		for (XdmItem item : children) {
			// System.out.println("------>" + item.toString());

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
		System.out.println(String.format("HTML   : (%s)", result));

		return result;
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
}
