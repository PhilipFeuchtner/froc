package de.uniko.iwm.osa.data.assessmentItem;

public class Item {
	
	public enum ItemType {
		INTERESSEN, EXTRASEITE
	}

	Integer id = null;
	private ItemType assessmentType = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	// dummy implementations
	public Integer getQuestid() {
		return null;
	}
	
	public ItemType getAssessmentType() {
		return assessmentType;
	}

	public void setQuestid(Integer questid) {
	}
}
