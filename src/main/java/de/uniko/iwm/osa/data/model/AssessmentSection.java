package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class AssessmentSection {
	
	private String title;
	private String rubicBlock;
	private List<AssessmentItem> assessmentItems = new ArrayList<AssessmentItem>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRubicBlock() {
		return rubicBlock;
	}
	public void setRubicBlock(String rubicBlock) {
		this.rubicBlock = rubicBlock;
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
