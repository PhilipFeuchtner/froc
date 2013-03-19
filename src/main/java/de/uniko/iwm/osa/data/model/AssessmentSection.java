package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

import de.uniko.iwm.osa.data.assessmentItem.AssessmentItemType01;

public class AssessmentSection {
	
	private String title;
	private String rubricBlock;
	private List<AssessmentItemType01> assessmentItems = new ArrayList<AssessmentItemType01>();
	
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
	public List<AssessmentItemType01> getAssessmentItems() {
		return assessmentItems;
	}
	public void setAssessmentItems(List<AssessmentItemType01> assessmentItems) {
		this.assessmentItems = assessmentItems;
	}
	
	public void addAssessmentItem(AssessmentItemType01 item) {
		assessmentItems.add(item);
	}
}
