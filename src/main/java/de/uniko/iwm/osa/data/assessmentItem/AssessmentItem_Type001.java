package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import net.sf.saxon.s9api.XdmNode;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

public class AssessmentItem_Type001 implements AssessmentItem {

	static Logger log = Logger
			.getLogger(AssessmentItem_Type001.class.getName());

	String identifier = "Cyquest-1";
	String cyquest_question_type = null;

	final String MAGIC_INTERESSEN_TYPEVALUES = "a:5:{i:0;i:1;i:1;i:2;i:2;i:3;i:3;i:4;i:4;i:5;}";

	OsaDbQuests quest;

	ItemConigurator ic = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */

	public AssessmentItem_Type001(OsaDbQuests quest, ItemConigurator ic) {
		log.info("Assessment item type 001 created");

		this.ic = ic;
		this.quest = quest;

		quest.setTypevalues(MAGIC_INTERESSEN_TYPEVALUES);

	}

	@Override
	public String toString() {

		return String.format("%s-%s-%s [%s][%s][%s]", quest.getId(),
				quest.getQuestid(), quest.getPosition(), quest.getShownum(),
				quest.getShowdesc(), quest.getTypevalues());
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
