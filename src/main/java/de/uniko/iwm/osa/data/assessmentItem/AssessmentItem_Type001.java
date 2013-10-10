package de.uniko.iwm.osa.data.assessmentItem;

import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.QuestConfigurer;

public class AssessmentItem_Type001 extends AssessmentItem {

	int identifier = 1;
	int itemPerPage = 8;

	final String MAGIC_TYPEVALUES = "a:5:{i:0;i:1;i:1;i:2;i:2;i:3;i:3;i:4;i:4;i:5;}";

	public AssessmentItem_Type001() {
	}

	public boolean setup(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		pqiq.setQi_questtype(identifier);
		pqiq.setM_itemPerPage(itemPerPage);

		// buildShowdesc(pqiq, ic);
		buildTypevalues(pqiq, qc);

		return true;
	}

	private void buildTypevalues(PagesQuestitemsQuestsMisc pqiq,
			QuestConfigurer qc) {
		pqiq.setQ_typevalues(MAGIC_TYPEVALUES);
	}

	@Override
	public String toString() {

		// return String.format("%s-%s-%s [%s][%s][%s]", quest.getId(),
		// quest.getQuestid(), quest.getPosition(), quest.getShownum(),
		// quest.getShowdesc(), quest.getTypevalues());
		return "item 01";
	}
}
