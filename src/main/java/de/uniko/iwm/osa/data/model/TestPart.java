package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class TestPart {
	List <AssessmentSection> assessmentSections = new ArrayList<AssessmentSection>();

	public List<AssessmentSection> getAssessmentSections() {
		return assessmentSections;
	}

	public void setTestParts(List<AssessmentSection> assessmentSection) {
		this.assessmentSections = assessmentSection;
	}
	
	public void addAssessmentSection(AssessmentSection item) {
		assessmentSections.add(item);
	}
}
