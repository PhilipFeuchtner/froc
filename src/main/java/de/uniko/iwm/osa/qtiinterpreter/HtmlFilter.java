package de.uniko.iwm.osa.qtiinterpreter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import org.xml.sax.SAXException;

public class HtmlFilter {

	List<String> skipList;
	List<String> skipIfEmptyList;
	List<String> qtiInlineElementsList;
	List<String> htmlImageElementsList;

	static Document doc = null;

	public enum Kind {
		keep, skip, skipIfEmpty, qti, image
	};

	public HtmlFilter() {
		skipList = new ArrayList<String>();
		skipList.add("span");
		skipList.add("font");

		skipIfEmptyList = new ArrayList<String>();
		skipIfEmptyList.add("div");

		qtiInlineElementsList = new ArrayList<String>();
		qtiInlineElementsList.add("positionObjectStage");
		qtiInlineElementsList.add("customInteraction");
		qtiInlineElementsList.add("drawingInteraction");
		qtiInlineElementsList.add("gapMatchInteraction");
		qtiInlineElementsList.add("matchInteraction");
		qtiInlineElementsList.add("graphicGapMatchInteraction");
		qtiInlineElementsList.add("hotspotInteraction");
		qtiInlineElementsList.add("graphicOrderInteraction");
		qtiInlineElementsList.add("selectPointInteraction");
		qtiInlineElementsList.add("graphicAssociateInteraction");
		qtiInlineElementsList.add("sliderInteraction");
		qtiInlineElementsList.add("choiceInteraction");
		qtiInlineElementsList.add("mediaInteraction");
		qtiInlineElementsList.add("hottextInteraction");
		qtiInlineElementsList.add("orderInteraction");
		qtiInlineElementsList.add("extendedTextInteraction");
		qtiInlineElementsList.add("uploadInteraction");
		qtiInlineElementsList.add("associateInteraction");
		qtiInlineElementsList.add("feedbackBlock");
		qtiInlineElementsList.add("templateBlock");
		qtiInlineElementsList.add("infoControl");
		// <xs:element ref="m:math" minOccurs="1" maxOccurs="1"/>
		// <xs:element ref="xi:include" minOccurs="1" maxOccurs="1"/>

		htmlImageElementsList = new ArrayList<String>();
		htmlImageElementsList.add("img");
	}

	public static void main(String args[]) {

		String TEST = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<itemBody>"
				+ "<span xmlns=\"\" style=\"line-height: 115%; font-family: &quot;Times New Roman&quot;,&quot;serif&quot;; font-size: 12pt\">"
				+ "Elemente zu einer <b>geschmackvollen <span>Gesamtheit</span></b> arrangieren </span>"
				+ "<choiceInteraction responseIdentifier=\"RESPONSE_26290011\" shuffle=\"false\" maxChoices=\"0\"><simpleChoice identifier=\"choice_1838764326\">1</simpleChoice><simpleChoice identifier=\"choice_231326100\">2</simpleChoice><simpleChoice identifier=\"choice_2072240260\">3</simpleChoice><simpleChoice identifier=\"choice_1671796474\">4</simpleChoice><simpleChoice identifier=\"choice_1556773229\">5</simpleChoice></choiceInteraction></itemBody>";

		HtmlFilter hf = new HtmlFilter();
		hf.parseText(TEST);
		System.out.println(hf.printXmlDocument(doc));
	}

	public String parseText(String text) {
		InputStream is = new ByteArrayInputStream(text.getBytes());

		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		dfactory.setNamespaceAware(true);

		DocumentBuilder docBuilder;
		try {
			docBuilder = dfactory.newDocumentBuilder();
			doc = docBuilder.parse(is);

			while (doIt(doc.getDocumentElement()))
				;

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return printXmlDocument(doc);
	}

	public boolean doIt(Node inspect) {

		if (cleanUp(inspect)) {
			return true;
		} else {
			NodeList children = inspect.getChildNodes();
			boolean changed = false;

			for (int i = 0; i < children.getLength(); i++) {
				if (doIt(children.item(i))) {
					changed = true;
					break;
				}
			}

			return changed;
		}
	}

	public boolean cleanUp(Node inspect) {
		boolean someThingChanged;
		Node parent = inspect.getParentNode();

		if (inspect.getNodeType() == Node.ELEMENT_NODE) {
			switch (filter(inspect.getNodeName())) {

			case keep:
				someThingChanged = false;
				break;

			case skipIfEmpty:
				if (inspect.hasAttributes()) {
					someThingChanged = false;
					break;
				}

			case skip:
				Node mark = inspect;

				while (inspect.hasChildNodes()) {
					Node child = inspect.getLastChild();

					parent.insertBefore(child, mark);
					mark = child;
				}

				parent.removeChild(inspect);

				someThingChanged = true;
				break;

			case qti:
				System.out.println("qti node: " + inspect.getNodeName());

				parent.removeChild(inspect);
				someThingChanged = true;
				break;

			default:
				System.out.println("Error: [missing node] "
						+ inspect.getNodeName());

				someThingChanged = false;
			}
		} else {
			someThingChanged = false;
		}

		return someThingChanged;
	}

	private Kind filter(String what) {

		for (String test : skipList) {
			if (test.equalsIgnoreCase(what)) {
				return Kind.skip;
			}
		}

		for (String test : skipIfEmptyList) {
			if (test.equalsIgnoreCase(what)) {
				return Kind.skipIfEmpty;
			}
		}

		for (String test : qtiInlineElementsList) {
			if (test.equalsIgnoreCase(what)) {
				return Kind.qti;
			}
		}

		for (String test : htmlImageElementsList) {
			if (test.equalsIgnoreCase(what)) {
				return Kind.image;
			}
		}

		return Kind.keep;
	}

	public String printXmlDocument(Document document) {
		DOMImplementationLS domImplementationLS = (DOMImplementationLS) document
				.getImplementation();
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		String string = lsSerializer.writeToString(document);
		System.out.println("-> " + string);
		return string;
	}
}
