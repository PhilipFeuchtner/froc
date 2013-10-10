package de.uniko.iwm.osa.data.assessmentItem;

import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.QuestConfigurer;

public class AssessmentItem_Type002 extends AssessmentItem {
	int identifier = 2;
	int itemPerPage = 8;

	final String MAGIC_TYPEVALUES = "a:2:{i:0;i:1;i:1;i:0;}";
	final String MAGIC_QUESTPARAM = "a:1:{s:8:\"scaledir\";s:2:\"up\";}";

	public AssessmentItem_Type002() {
	}

	public boolean setup(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		pqiq.setQi_questtype(identifier);
		pqiq.setQi_questparam(MAGIC_QUESTPARAM);

		pqiq.setQ_typevalues(MAGIC_TYPEVALUES);

		pqiq.setM_itemPerPage(itemPerPage);

		return true;
	}
}
