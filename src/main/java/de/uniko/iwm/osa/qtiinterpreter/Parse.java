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

import de.uniko.iwm.osa.questsitemTree.AssessmentItem;

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

	Pattern PATTERN_IMAGE_SRC = Pattern.compile("src=\"media");

	Processor proc;
	XPathCompiler xpath;
	DocumentBuilder builder;

	// private final String sep = System.getProperty("file.separator");

	private String base;
	private String image_base;

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

	public List<String> handleManifest(String filename) {
		List<String> res = null;

		try {

			XdmNode manifestDoc = builder.build(new File(base, filename));

			XPathSelector selector = xpath.compile(QUERY_MANIFEST_ASSESSMENT)
					.load();
			selector.setContextItem(manifestDoc);

			// Evaluate the expression.
			XdmValue children = selector.evaluate();

			// result value
			res = new ArrayList<String>();

			for (XdmItem item : children) {
				XdmNode resNode = (XdmNode) item;

				String id = resNode.getAttributeValue(new QName("href"));
				res.add(id);
			}

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			res = null;
		}

		return res;
	}

	public List<AssessmentItem> handle_assessmentTest(String href) throws FileNotFoundException {
		 List<AssessmentItem> assessmentItemList = new ArrayList<AssessmentItem>();
		
		XdmNode assessmentTest;
		try {
			assessmentTest = builder.build(new File(base, href));

			XPathSelector selector;

			//
			// tests
			//

			selector = xpath.compile(QUERY_IMSQTI_ASSESSMENTTEST).load();
			selector.setContextItem(assessmentTest);

			// Evaluate the expression.
			XdmValue children_tests = selector.evaluate();

			for (XdmItem item1 : children_tests) {

				//
				// testParts
				//

				selector = xpath.compile(QUERY_IMSQTI_TESTPART).load();
				selector.setContextItem(item1);
				XdmValue children_item1 = selector.evaluate();

				for (XdmItem item2 : children_item1) {
					int count = 0;
					int cy_questid = 0;

					//
					// assessment section
					//

					selector = xpath.compile(QUERY_IMSQTI_ASSESSMENTSECTION)
							.load();
					selector.setContextItem(item2);
					XdmValue children_item2 = selector.evaluate();

					for (XdmItem item3 : children_item2) {
						int cy_position = 0;
						cy_questid++;

						//
						// title
						//

						selector = xpath.compile("@title").load();
						selector.setContextItem(item3);
						XdmValue titles = selector.evaluate();

						//
						// set title
						//
						for (XdmItem att_titles : titles) {
							System.out.println(String.format("Att title: (%s)",
									att_titles.getStringValue()));
						}

						//
						// find refs
						//

						selector = xpath
								.compile(QUERY_IMSQTI_ASSESSMENTITEMREF).load();
						selector.setContextItem(item3);
						XdmValue children_item3 = selector.evaluate();

						//
						// go on
						//

						for (XdmItem refs : children_item3) {
							count++;
							cy_position++;

							System.out.println(String.format("ref file: %s",
									refs.getStringValue()));
							System.out
									.println(String
											.format("count [%2d], questid [%2d], position [%2d]",
													count, cy_questid,
													cy_position));

							AssessmentItem it = handle_imsqti_item_xmlv2p1(refs.getStringValue(),
									count, cy_questid, cy_position);
							
							if (it != null) assessmentItemList.add(it);
						}
					}
				}
			}
		} catch (SaxonApiException e) {
			assessmentItemList = null;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return assessmentItemList;
	}

	public AssessmentItem handle_imsqti_item_xmlv2p1(String href, int count,
			int cy_questid, int cy_position) throws FileNotFoundException {

		AssessmentItem question = new AssessmentItem();

		// InputStream xmlStream = new FileInputStream( new File(base, href));
		// StreamResult transformed = doXsltTransform(xmlStream, xsltStream);

		// handle_imsqti_text(href);
		// handle_imsqti_choiceInteraction(href);

		XdmNode manifestDoc;
		try {
			manifestDoc = builder.build(new File(base, href));

			XPathSelector selector;

			//
			// find title
			//

			selector = xpath.compile(PART_ASS_TITLE).load();
			selector.setContextItem(manifestDoc);

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
			selector.setContextItem(manifestDoc);

			// Evaluate the expression.
			XdmValue children_correct_responses = selector.evaluate();

			for (XdmItem item : children_correct_responses) {
				System.out.println(String.format("CORRECT: (%s)",
						item.getStringValue()));
			}

			//
			// parse content
			//

			selector = xpath.compile(PART_ITEM_BODY).load();
			selector.setContextItem(manifestDoc);

			// Evaluate the expression.
			XdmValue children = selector.evaluate();

			for (XdmItem item : children) {

				HtmlFilter hf = new HtmlFilter();

				String fragment = hf.parseText(item.toString());
				XdmNode htmlDoc = builder.build(new StreamSource(
						new java.io.StringReader(fragment)));

				selector = xpath.compile(PART_HTML).load();
				selector.setContextItem(htmlDoc);

				// Evaluate the expression.
				XdmValue htmlNodes = selector.evaluate();

				StringBuilder html = new StringBuilder();
				for (XdmItem htmlNode : htmlNodes) {
					html.append(htmlNode.toString());
				}

				String text = html.toString().trim();

				System.out.println(String.format("HTML   : (%s)", text));

				question.setShowdesc(text);
			}
			
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

	//
	// xslt
	//

	public StreamResult doXsltTransform(InputStream xmlSource,
			InputStream xsltStream) {

		Source xsltSource = new StreamSource(xsltStream);

		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// StreamResult result = new StreamResult(baos);

		StreamResult result = new StreamResult(new StringWriter());

		try {
			TransformerFactory tFactory = TransformerFactory.newInstance(
					"net.sf.saxon.TransformerFactoryImpl", null);
			Transformer transformer = tFactory.newTransformer(xsltSource);
			transformer.transform(new StreamSource(xmlSource), result);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		String xmlString = result.getWriter().toString();
		System.out.println(xmlString);

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

	//
	// helper
	//

	public String modifyImageUrl(XdmItem v) {
		String text = v.toString();
		if (text.startsWith(IMAGE_PREFIX)) {
			Matcher m = PATTERN_IMAGE_SRC.matcher(text);
			return m.replaceFirst("src=\"" + image_base);
		}
		return text;
	}

}
