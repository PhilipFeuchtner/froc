package de.uniko.iwm.osa.data.assessmentItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.QuestConfigurer;

public class AssessmentItem_Type003 extends AssessmentItem {

	int identifier = 3;
	int itemPerPage = 1;
	
	final String MAGIC_TYPEVALUES = "a:2:{i:0;i:1;i:1;i:0;}";
	final String MAGIC_SHOWDESC = "a:2:{i:0;s:11:\"G&uuml;ltig\";i:1;s:13:\"Ung&uuml;ltig\";}";
	final String MAGIC_QUESTPARAM = "";

	public AssessmentItem_Type003() {}
	
	public boolean setup(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		pqiq.setQi_questtype(identifier);
		pqiq.setQi_questparam(MAGIC_QUESTPARAM);
		
		String qText = qc.queryQuestionText();
		pqiq.setQi_questdesc(cleanMediaUrl(qText, qc));
		
		pqiq.setM_itemPerPage(itemPerPage);
		
		pqiq.setQ_showdesc(MAGIC_SHOWDESC);
		
		qc.queryCorrectResponsePosition();
		pqiq.setQ_typevalues(MAGIC_TYPEVALUES);
		
		return true;
	}
}
