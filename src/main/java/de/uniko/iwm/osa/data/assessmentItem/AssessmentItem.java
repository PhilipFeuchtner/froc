package de.uniko.iwm.osa.data.assessmentItem;

import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;
import net.sf.saxon.s9api.XdmNode;

public interface AssessmentItem {
	static enum CYQUEST_QUESTION_TYPE {QESTION, EXTRASEITE};
	
	public boolean init(String identifier, String cyquest_question_type);
	public boolean create(XdmNode assecssmentItem);
	public boolean setSequenceValues(int count, int cy_position);
	// public int toOsaDbQuests(OsaDbQuestsService questsService);
	
	public String getIdentifier();
	public String getCqt();
	
	public OsaDbQuestitems getOsaDbQuestItem();
}
