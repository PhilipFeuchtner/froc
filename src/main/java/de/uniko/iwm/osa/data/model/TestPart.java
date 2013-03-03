package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class TestPart {
	List <AssessmentSection> testParts = new ArrayList<AssessmentSection>();

	public List<AssessmentSection> getTestParts() {
		return testParts;
	}

	public void setTestParts(List<AssessmentSection> testParts) {
		this.testParts = testParts;
	}
	
	public void addAssessmentSection(AssessmentSection item) {
		testParts.add(item);
	}
}
