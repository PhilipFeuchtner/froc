package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import net.sf.saxon.s9api.XdmNode;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.PageQuestitemConfigurer;
import de.uniko.iwm.osa.qtiinterpreter.QuestConfigurer;

public class AssessmentItem_Type002 implements AssessmentItem {
	static Logger log = Logger
			.getLogger(AssessmentItem_Type002.class.getName());

	int identifier = 2;
	// String cyquest_question_type = null;
	// String title;

	int itemPerPage = 8;

	final String MAGIC_TYPEVALUES = "a:2:{i:0;i:1;i:1;i:0;}";
	final String MAGIC_QUESTPARAM = "a:1:{s:8:\"scaledir\";s:2:\"up\";}";
	

	// OsaDbQuests quest;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */

	public AssessmentItem_Type002(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		pqiq.setQi_questtype(identifier);
		pqiq.setQi_questparam(MAGIC_QUESTPARAM);
		
		pqiq.setQ_typevalues(MAGIC_TYPEVALUES);
		
		pqiq.setM_itemPerPage(itemPerPage);
	}

	@Override
	public int getItemPerPage() {
		return itemPerPage;
	}
}
