package de.uniko.iwm.osa.qtiinterpreter;

import java.util.List;

import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;

public class QuestConfigurer extends ItemConfigurer {
	
	private final String PART_ASS_IDENTIFIER = "/imsqti:assessmentItem/@identifier";

	private final String PART_ITEM_BODY = "/imsqti:assessmentItem/imsqti:itemBody";

	private final String IQ_QUERY_TASK = PART_ITEM_BODY + "/p/text()";
	private final String IQ_QUERY_QUESTION = PART_ITEM_BODY
			+ "/p/descendant::imsqti:img/@src";
	// "/p/imsqti:img/@src"
	// + "|" + PART_ITEM_BODY + "/xsi:p/imsqti:img/@src";

	private final String IQ_QUERY_CHOICES = PART_ITEM_BODY
			+ "/imsqti:choiceInteraction"
			+ "/imsqti:simpleChoice/imsqti:img/@src";
	
	private final String QUERY_TITLE_ATTRIBUTE = "/imsqti:assessmentItem/@title";

	public QuestConfigurer(String base, String href) throws SaxonApiException {
		super(base, href);
	}

	public QuestConfigurer(XdmNode node) throws SaxonApiException {
		super(node);
	}

	public String queryIdentifier() {
		return queryToString(PART_ASS_IDENTIFIER);
	}
	
	public String queryTitle() {
		return queryToString(QUERY_TITLE_ATTRIBUTE);
	}

	public String queryIQTask() {
		return queryToString(IQ_QUERY_TASK);
	}

	public String queryIQQuestion() {
		return queryToString(IQ_QUERY_QUESTION);
	}

	public List<String> queryIQChoices() {
		return queryToStringList(IQ_QUERY_CHOICES);
	}
}
