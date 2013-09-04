package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import net.sf.saxon.s9api.XdmNode;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

public class AssessmentItem_Type003 implements AssessmentItem {

	static Logger log = Logger
			.getLogger(AssessmentItem_Type003.class.getName());

	ItemConigurator ic = null;

	int identifier = 3;
	String cyquest_question_type = null;
	String title;
	
	int itemPerPage = 1;
	
	OsaDbQuests quest;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */

	public AssessmentItem_Type003(OsaDbQuests quest, ItemConigurator ic, String title) {
		log.info("Assessment item type 003 created");

		this.quest = quest;
		this.ic = ic;
		this.title = title;
	}

	@Override
	public int getIdentifier() {
		return identifier;
	}

	@Override
	public String getCqt() {
		return cyquest_question_type;
	}

	@Override
	public OsaDbQuests getOsaDbQuest() {
		return quest;
	}
	
	@Override
	public int getItemPerPage() {
		return itemPerPage;
	}
	
	@Override
	public void setTitle(String text) {
		title = text;	
	}

	@Override
	public String getTitle() {
		return title;
	}
}
