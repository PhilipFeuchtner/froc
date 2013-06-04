package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import net.sf.saxon.s9api.XdmNode;

import de.uniko.iwm.osa.data.service.OsaDbQuestsService;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

public class AssessmentItem_Type002 implements AssessmentItem {
	static Logger log = Logger.getLogger(AssessmentItem_Type002.class.getName());
	

	ItemConigurator ic = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */
	
	public AssessmentItem_Type002(ItemConigurator ic) {
		log.info("Assessment item type 002 created");
		
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
	public int toOsaDbQuests(OsaDbQuestsService questsService) {
		// TODO Auto-generated method stub
		return 0;
	}
}
