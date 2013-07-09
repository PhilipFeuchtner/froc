package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import net.sf.saxon.s9api.XdmNode;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

public class AssessmentItem_Type008 implements AssessmentItem {

	static Logger log = Logger
			.getLogger(AssessmentItem_Type008.class.getName());

	ItemConigurator ic = null;

	String identifier = "Cyquest-8";
	String cyquest_question_type = null;
	
	OsaDbQuests quest;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */

	public AssessmentItem_Type008(OsaDbQuests quest, ItemConigurator ic) {
		log.info("Assessment item type 008 created");

		this.quest = quest;
		this.ic = ic;
	}

	@Override
	public String getIdentifier() {
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
}
