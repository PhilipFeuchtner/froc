package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import net.sf.saxon.s9api.XdmNode;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

public class AssessmentItem_Type002 implements AssessmentItem {
	static Logger log = Logger
			.getLogger(AssessmentItem_Type002.class.getName());

	int identifier = 2;
	// String cyquest_question_type = null;
	// String title;

	int itemPerPage = 8;

	final String MAGIC_INTERESSEN_TYPEVALUES = "a:1:{s:8:\"scaledir\";s:2:\"up\";}";

	// OsaDbQuests quest;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */

	public AssessmentItem_Type002(PagesQuestitemsQuestsMisc pqiq, ItemConigurator ic) {
		pqiq.setQi_questtype(identifier);
		pqiq.setM_itemPerPage(itemPerPage);
		
		// buildShowdesc(pqiq, ic);
		buildTypevalues(pqiq, ic);
	}

	private void buildTypevalues(PagesQuestitemsQuestsMisc pqiq, ItemConigurator ic) {
		pqiq.setQ_typevalues(MAGIC_INTERESSEN_TYPEVALUES);
	}

	// public AssessmentItem_Type002(OsaDbQuests quest, ItemConigurator ic,
	// String title) {
	// log.info("Assessment item type 002 created");
	//
	// this.quest = quest;
	// this.ic = ic;
	// this.title = title;
	//
	// quest.setTypevalues(MAGIC_INTERESSEN_TYPEVALUES);
	// }

//	@Override
//	public int getIdentifier() {
//		return identifier;
//	}

//	@Override
//	public String getCqt() {
//		return cyquest_question_type;
//	}

	// @Override
	// public OsaDbQuests getOsaDbQuest() {
	// return quest;
	// }

	@Override
	public int getItemPerPage() {
		return itemPerPage;
	}

	// @Override
	// public void setTitle(String text) {
	// title = text;
	// }
	//
	// @Override
	// public String getTitle() {
	// return title;
	// }
}
