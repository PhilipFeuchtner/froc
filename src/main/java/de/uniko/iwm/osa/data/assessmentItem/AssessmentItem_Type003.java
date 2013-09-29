package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.QuestConfigurer;

public class AssessmentItem_Type003 implements AssessmentItem {

	static Logger log = Logger
			.getLogger(AssessmentItem_Type003.class.getName());

	int identifier = 3;
	// String cyquest_question_type = null;
	
	int itemPerPage = 1;
	
	// a:2:{i:0;i:1;i:1;i:0;}
	final String MAGIC_TYPEVALUES = "a:2:{i:0;i:1;i:1;i:0;}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */

	public AssessmentItem_Type003(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		pqiq.setQi_questtype(identifier);
		pqiq.setM_itemPerPage(itemPerPage);
		
		buildShowdesc(pqiq, qc);
		buildTypevalues(pqiq, qc);
	}

	private void buildShowdesc(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		pqiq.setQ_showdesc("please implement me");
		
	}

	private void buildTypevalues(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		pqiq.setQ_typevalues(MAGIC_TYPEVALUES);
	}

//	@Override
//	public int getIdentifier() {
//		return identifier;
//	}

//	@Override
//	public String getCqt() {
//		return cyquest_question_type;
//	}

//	@Override
//	public OsaDbQuests getOsaDbQuest() {
//		return quest;
//	}

	@Override
	public int getItemPerPage() {
		return itemPerPage;
	}
	
//	@Override
//	public void setTitle(String text) {
//		title = text;	
//	}
//
//	@Override
//	public String getTitle() {
//		return title;
//	}
	
	// ----------------------------------------------------------------------------
	
//	private void setupItem(ItemConigurator ic) {
//		quest.setTypevalues(MAGIC_TYPEVALUES);
//		quest.setShowdesc(ic.queryShowdescr());
//	}
}
