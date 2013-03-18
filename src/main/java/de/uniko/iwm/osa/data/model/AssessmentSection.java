package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class AssessmentSection {
	
	private String title;
	private String rubricBlock;
	private List<AssessmentItem> assessmentItems = new ArrayList<AssessmentItem>();
	
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
	public List<AssessmentItem> getAssessmentItems() {
		return assessmentItems;
	}
	public void setAssessmentItems(List<AssessmentItem> assessmentItems) {
		this.assessmentItems = assessmentItems;
	}
	
	public void addAssessmentItem(AssessmentItem item) {
		assessmentItems.add(item);
	}
}
