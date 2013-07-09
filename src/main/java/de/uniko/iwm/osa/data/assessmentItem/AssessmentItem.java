package de.uniko.iwm.osa.data.assessmentItem;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import net.sf.saxon.s9api.XdmNode;

public interface AssessmentItem {
	static enum CYQUEST_QUESTION_TYPE {
		QESTION, EXTRASEITE
	};

	public String getIdentifier();

	public String getCqt();

	public OsaDbQuests getOsaDbQuest();
}
