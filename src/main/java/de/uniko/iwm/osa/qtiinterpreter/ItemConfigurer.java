package de.uniko.iwm.osa.qtiinterpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.WhitespaceStrippingPolicy;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import de.uniko.iwm.osa.utils.HtmlFilter;

public class ItemConfigurer {
	static Logger log = Logger.getLogger(ItemConfigurer.class);

	XdmNode node;

	static XPathCompiler xpath;
	static DocumentBuilder builder;

	static {
		Processor proc = new Processor(false);
		xpath = proc.newXPathCompiler();

		xpath.declareNamespace("xsi",
				"http://www.w3.org/2001/XMLSchema-instance");

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

	public ItemConfigurer(XdmNode node) {
		this.node = node;
	}

	public ItemConfigurer(String base, String href) {

		try {
			XdmNode document = builder.build(new File(base, href));
			this.node = document;
		} catch (SaxonApiException e) {
			e.printStackTrace();
		}

	}

	XdmValue queryToNodelist(String query) {
		try {
			XPathSelector selector = xpath.compile(query).load();
			selector.setContextItem(node);
			return selector.evaluate();
		} catch (SaxonApiException e) {
			e.printStackTrace();
			return null;
		}
	}

	XdmValue queryToNodelist(XdmNode node, String query) {
		try {

			XPathSelector selector = xpath.compile(query).load();
			selector.setContextItem(node);
			return selector.evaluate();
		} catch (SaxonApiException e) {
			e.printStackTrace();
			return null;
		}
	}

	String queryToString(String query) {
		return StringUtils.join(queryToStringList(query), "");
	}

	List<String> queryToStringList(String query) {
		try {
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
		} catch (SaxonApiException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String cleanHtmlContent(String XPATH_NODE, String XPATH_CHILD) {

		String result = "";

		try {
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
		} catch (SaxonApiException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public XdmNode getNode() {
		return node;
	}

	public void setNode(XdmNode node) {
		this.node = node;
	}
	
	// --------------------------------------------------------------
			
	public String getCy_image_base() {
		return "media/images";
	}

	public String getQti_media_folder() {
		return "media";
	}

}
