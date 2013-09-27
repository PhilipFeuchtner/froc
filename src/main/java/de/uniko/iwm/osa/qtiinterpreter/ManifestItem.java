package de.uniko.iwm.osa.qtiinterpreter;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;

public class ManifestItem extends ItemConfigurer {

	final String QUERY_MANIFEST_CY_QUESTIONTYPE = "imsmd:metadata/imsmd:lom/imsmd:general"
			+ "//imsmd:keyword/imsmd:langstring/text()";

	final String QUERY_MANIFEST_CY_TITLE = "imsmd:metadata/imsmd:lom/imsmd:general"
			+ "//imsmd:title/imsmd:langstring/text()";

	String questTypeString = "";
	String questTitle = "";
	String pageDescr = "--- missing ---";

	XPathSelector selector;

	public ManifestItem(XdmNode node) {
		super(node);
		
		questTypeString = queryQuesttype();
		questTitle = queryTitle();

		// System.err.println("MI ["+ questTitle + "][" + questTypeString +
		// "]");
	}

	// --------------------------------------------------------------

	private String queryTitle() {
		return queryToString(QUERY_MANIFEST_CY_TITLE);

//		try {
//			selector = xpath.compile(QUERY_MANIFEST_CY_TITLE).load();
//
//			selector.setContextItem(node);
//			XdmValue titles = selector.evaluate();
//
//			for (XdmItem child : titles) {
//				XdmNode resNode = (XdmNode) child;
//
//				questTitle = questTitle + resNode.getStringValue();
//			}
//		} catch (SaxonApiException e) {
//			e.printStackTrace();
//		}

	}

	private String queryQuesttype() {
		return queryToString(QUERY_MANIFEST_CY_QUESTIONTYPE);
		
//		try {
//			XPathSelector selector = xpath.compile(
//					QUERY_MANIFEST_CY_QUESTIONTYPE).load();
//			selector.setContextItem(node);
//			XdmValue children = selector.evaluate();
//
//			for (XdmItem child : children) {
//				XdmNode resNode = (XdmNode) child;
//
//				String result = resNode.getStringValue();
//				if (result.startsWith("qt")) {
//					questTypeString = result;
//					return;
//				}
//			}
//		} catch (SaxonApiException e) {
//			e.printStackTrace();
//		}
	}

	// --------------------------------------------------------------

	public String getQuestTypeString() {
		return questTypeString;
	}

	public String getQuestTitle() {
		return questTitle;
	}

	public String getPageDescr() {
		return pageDescr;
	}

	public void setPageDescr(String pageDescr) {
		this.pageDescr = pageDescr;
	}
	
	// --------------------------------------------------------------

	@Override
	public String toString() {
		return "MI ["+questTypeString+"]["+questTitle+"]";
	}
}
