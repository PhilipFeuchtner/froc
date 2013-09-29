package de.uniko.iwm.osa.qtiinterpreter;

import java.io.File;
import java.util.List;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;

public class AssessmentConfigurer extends ItemConfigurer {

	final String QUERY_MANIFEST_ASSESSMENT = "/imscp:manifest/imscp:resources"
			+ "/imscp:resource[@type='imsqti_assessment_xmlv2p1']";
	final String QUERY_MANIFEST_ITEM = "/imscp:manifest"
			+ "/imscp:resources/imscp:resource[@type='imsqti_item_xmlv2p1']";

	final String QUERY_IMSQTI_ASSESSMENTTEST = "imsqti:assessmentTest";
	final String QUERY_IMSQTI_TESTPART = "imsqti:testPart";
	final String QUERY_IMSQTI_ASSESSMENTSECTION = "imsqti:assessmentSection";	

	public AssessmentConfigurer(String base, String href)
			throws SaxonApiException {
		super(base, href);
	}

	public AssessmentConfigurer(XdmNode node) {
		super(node);
	}

	public XdmValue queryAssessment() {
		return queryToNodelist(QUERY_MANIFEST_ASSESSMENT);
	}

	public XdmValue queryItem() {
		return queryToNodelist(QUERY_MANIFEST_ITEM);
	}

	public XdmValue queryAssessmentTest() {
		return queryToNodelist(QUERY_IMSQTI_ASSESSMENTTEST);
	}

	public XdmValue queryTestpart() {
		return queryToNodelist(QUERY_IMSQTI_TESTPART);
	}

	public XdmValue queryAssessmentSection() {
		return queryToNodelist(QUERY_IMSQTI_ASSESSMENTSECTION);
	}
}
