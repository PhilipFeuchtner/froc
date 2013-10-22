package de.uniko.iwm.osa.data.assessmentItem;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.QuestConfigurer;

public class AssessmentItem_Type003 extends AssessmentItem {

	int identifier = 3;
	int itemPerPage = 1;
	
	// final String MAGIC_TYPEVALUES = "a:2:{i:0;i:1;i:1;i:0;}";
	// final String MAGIC_SHOWDESC = "a:2:{i:0;s:11:\"G&uuml;ltig\";i:1;s:13:\"Ung&uuml;ltig\";}";
	final String MAGIC_QUESTPARAM = "";

	public AssessmentItem_Type003() {}
	
	public boolean setup(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		pqiq.setQi_questtype(identifier);
		pqiq.setQi_questparam(MAGIC_QUESTPARAM);
		
		String qText = qc.queryQuestionText();
		pqiq.setQi_questdesc(cleanMediaUrl(qText, qc));
		
		pqiq.setM_itemPerPage(itemPerPage);
		
		// pqiq.setQ_showdesc(MAGIC_SHOWDESC);
		// pqiq.setQ_typevalues(MAGIC_TYPEVALUES);
		
		setTypevaluesAndShowdesc(pqiq, qc);
		
		return true;
	}

	private void setTypevaluesAndShowdesc(PagesQuestitemsQuestsMisc pqiq,
			QuestConfigurer qc) {
		
		String template = "i:%d;s:%d:\"%s\";";
		
		int correct = qc.queryCorrectResponsePosition();
		List<String> responses = qc.queryResponseItemTextList();
		
		String tv;
		String sd;
		
		if (responses.isEmpty()) {
			tv = "a:1:{i:0;i:1;}";
			sd = "a:1:{i:0;s:11:\"G&uuml;ltig\";}";
		} else {
			int count = 0;
		
			tv = "a:" + responses.size() + ":{";
			sd = "a:" + responses.size() + ":{";
			
			for (String item : responses) {
				//
				// build correct response
				//
				
				if (count == correct) {
					tv = tv + "i:" + count + ";i:1;";
				} else {
					tv = tv + "i:" + count + ";i:0;";
				}
				
				//
				// build descr
				//
				
				sd = sd + String.format(template, count, item.length(), item);
				
				// next item
				count++;
			}
			
			tv = tv + "}";
			sd = sd + "}";
		}
		
		System.err.println(sd);
		System.err.println(tv);
		
		pqiq.setQ_showdesc(sd);
		pqiq.setQ_typevalues(tv);
	}
}
