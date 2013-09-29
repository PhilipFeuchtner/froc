package de.uniko.iwm.osa.qtiinterpreter;

import java.util.List;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;

public class PageQuestitemConfigurer extends ItemConfigurer {

	final String QUERY_IMSQTI_ASSESSMENTITEMREF = "imsqti:assessmentItemRef/@href";
	final String QUERY_TITLE_ATTRIBUTE = "@title";

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
}
