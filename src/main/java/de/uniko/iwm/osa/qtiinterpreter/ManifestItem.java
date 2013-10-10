package de.uniko.iwm.osa.qtiinterpreter;

import java.util.List;

import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type001;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type002;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type003;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type008;

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

	AssessmentItem ai = null;

	XPathSelector selector;

	public ManifestItem(XdmNode node) {
		super(node);

		questTypeString = queryQuesttype();
		questTitle = queryTitle();
		
		switch (questTypeString) {
		case "qt1":
			ai = new AssessmentItem_Type001();
			break;

		case "qt2":
			ai = new AssessmentItem_Type002();
			break;

		case "qt3":
			ai = new AssessmentItem_Type003();
			break;

		case "qt8":
			ai = new AssessmentItem_Type008();
			break;

		default:
			//oi.addErrorEntry("QuestionType not implemented: "
			//		+ questionType);
			log.error("QuestionType not implemented: " + questTypeString);
		}

		// System.err.println("MI ["+ questTitle + "][" + questTypeString +
		// "]");
	}

	// --------------------------------------------------------------

	private String queryTitle() {
		return queryToString(QUERY_MANIFEST_CY_TITLE);
	}

	public String queryQuesttype() {
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

	public AssessmentItem getAi() {
		return ai;
	}

	public void setAi(AssessmentItem ai) {
		this.ai = ai;
	}
	
	// --------------------------------------------------------------

	@Override
	public String toString() {
		return "MI [" + questTypeString + "][" + questTitle + "]";
	}
}
