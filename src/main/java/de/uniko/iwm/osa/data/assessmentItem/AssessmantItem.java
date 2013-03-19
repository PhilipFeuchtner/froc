package de.uniko.iwm.osa.data.assessmentItem;

import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

public interface AssessmantItem {
	
	public enum AssessmentItemType {
		INTERESSEN
	}

	public abstract int getId();

	public abstract void setId(int id);

	public abstract int getQuestid();

	public abstract void setQuestid(int questid);

	public abstract int getPosition();

	public abstract void setPosition(int position);

	public abstract String getShownum();

	public abstract void setShownum(String shownum);

	public abstract String getShowdesc();

	public abstract void setShowdesc(String showdesc);

	public abstract String getTypevalues();

	public abstract void setTypevalues(String typevalues);
	
	public AssessmentItemType getAssessmentType();
	
	/* -------------------------- */

	public abstract int toOsaDbQuests(OsaDbQuestsService questsService);

	public abstract String toString();

}