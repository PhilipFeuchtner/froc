package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import net.sf.saxon.s9api.XdmNode;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

public class AssessmentItem_Type001 implements AssessmentItem {

	static Logger log = Logger
			.getLogger(AssessmentItem_Type001.class.getName());

	int identifier = 1;
	// String cyquest_question_type = null;
	// String title;

	int itemPerPage = 8;

	final String MAGIC_TYPEVALUES = "a:5:{i:0;i:1;i:1;i:2;i:2;i:3;i:3;i:4;i:4;i:5;}";


	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */

	public AssessmentItem_Type001(PagesQuestitemsQuestsMisc pqiq, ItemConigurator ic) {
		pqiq.setQi_questtype(identifier);
		pqiq.setM_itemPerPage(itemPerPage);
		
		// buildShowdesc(pqiq, ic);
		buildTypevalues(pqiq, ic);
	}

	private void buildTypevalues(PagesQuestitemsQuestsMisc pqiq, ItemConigurator ic) {
		pqiq.setQ_typevalues(MAGIC_TYPEVALUES);
	}

	// public AssessmentItem_Type001(OsaDbQuests quest, ItemConigurator ic,
	// String title) {
	// log.info("Assessment item type 001 created");
	//
	// this.ic = ic;
	// this.quest = quest;
	// this.title = title;
	//
	// quest.setTypevalues(MAGIC_TYPEVALUES);
	// }

	@Override
	public String toString() {

		// return String.format("%s-%s-%s [%s][%s][%s]", quest.getId(),
		// quest.getQuestid(), quest.getPosition(), quest.getShownum(),
		// quest.getShowdesc(), quest.getTypevalues());
		return "item 01";
	}

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
