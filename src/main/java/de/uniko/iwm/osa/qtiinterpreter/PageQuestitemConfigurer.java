package de.uniko.iwm.osa.qtiinterpreter;

import java.util.List;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;

public class PageQuestitemConfigurer extends ItemConfigurer {

	private final String QUERY_IMSQTI_ASSESSMENTITEMREF = "imsqti:assessmentItemRef/@href";
	private final String QUERY_TITLE_ATTRIBUTE = "@title";
	
	private final String QUERY_ASSESSMENTSECTION_RUBRIC = "imsqti:rubricBlock";
	private final String PART_RUBRIC = "imsqti:rubricBlock/child::node()";

	public PageQuestitemConfigurer(String base, String href)
			throws SaxonApiException {
		super(base, href);
	}

	public PageQuestitemConfigurer(XdmNode node) {
		super(node);
	}

	public List<String> queryAssessmentItemref() {
		return queryToStringList(QUERY_IMSQTI_ASSESSMENTITEMREF);
	}

	public String queryQuestTitle() {
		return queryToString(QUERY_TITLE_ATTRIBUTE);
	}
	
	public String queryRubric() {
		return cleanHtmlContent(QUERY_ASSESSMENTSECTION_RUBRIC, PART_RUBRIC);
	}
}
