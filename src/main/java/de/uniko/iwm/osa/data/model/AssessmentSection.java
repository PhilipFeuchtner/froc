package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

import de.uniko.iwm.osa.data.assessmentItem.AssessmentItemType01;
import de.uniko.iwm.osa.data.assessmentItem.Item;

public class AssessmentSection {
	
	private String title;
	private String rubricBlock;
	private List<Item> assessmentItems = new ArrayList<Item>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRubricBlock() {
		return rubricBlock;
	}
	public void setRubricBlock(String rubricBlock) {
		this.rubricBlock = rubricBlock;
	}
	public List<Item> getAssessmentItems() {
		return assessmentItems;
	}
	public void setAssessmentItems(List<Item> assessmentItems) {
		this.assessmentItems = assessmentItems;
	}
	
	public void addAssessmentItem(AssessmentItemType01 item) {
		assessmentItems.add(item);
	}
}
