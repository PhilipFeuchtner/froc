package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import net.sf.saxon.s9api.XdmNode;

import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

public class AssessmentItem_Type008 implements AssessmentItem {
	
	static Logger log = Logger.getLogger(AssessmentItem_Type008.class.getName());

	ItemConigurator ic = null;
	
	String identifier = "Cyquest-8";
	String cyquest_question_type = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */
	
	public AssessmentItem_Type008(ItemConigurator ic) {
		log.info("Assessment item type 008 created");
		
		this.ic = ic;
	}
	
	@Override
	public boolean init(String identifier, String cyquest_question_type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(XdmNode assecssmentItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setSequenceValues(int count, int cy_position) {
		// TODO Auto-generated method stub
		return false;
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
	public OsaDbQuestitems getOsaDbQuestItem() {
		return new OsaDbQuestitems();
	}
}
