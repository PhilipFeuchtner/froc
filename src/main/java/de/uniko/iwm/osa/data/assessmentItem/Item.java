package de.uniko.iwm.osa.data.assessmentItem;

abstract public class Item {
	
	public enum ItemType {
		INTERESSEN, EXTRASEITE
	}

	abstract public Integer getId();
	abstract public void setId(Integer id);
	
	abstract public Integer getQuestid();
	abstract public void setQuestid(Integer questid);
	
	abstract public ItemType getAssessmentType();
}
