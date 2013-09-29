package de.uniko.iwm.osa.qtiinterpreter;

import java.util.List;

import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmNode;

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
	}

	private String queryQuesttype() {
		List<String> questtypes = queryToStringList(QUERY_MANIFEST_CY_QUESTIONTYPE);

		for (String item : questtypes) {
			if (item.startsWith("qt"))
				return item;
		}

		return null;
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
		return "MI [" + questTypeString + "][" + questTitle + "]";
	}
}
