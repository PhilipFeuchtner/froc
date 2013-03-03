package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class AssessmentTest {
	private List<TestPart> testParts = new ArrayList<TestPart>();

	public List<TestPart> getTestParts() {
		return testParts;
	}

	public void setTestParts(List<TestPart> testParts) {
		this.testParts = testParts;
	}
	
	public void addTestPart(TestPart item) {
		testParts.add(item);
	}
}
