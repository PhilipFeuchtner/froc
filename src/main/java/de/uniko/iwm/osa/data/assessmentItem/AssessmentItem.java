package de.uniko.iwm.osa.data.assessmentItem;

import net.sf.saxon.s9api.XdmNode;

public interface AssessmentItem {
	public boolean init(String identifier, String cyquest_question_type);
	public boolean create(XdmNode assecssmentItem);
	public boolean setSequenceValues(int a, int b, int c);
}
