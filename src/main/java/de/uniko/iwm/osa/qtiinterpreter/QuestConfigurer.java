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

	private final String PART_HTML = "//imsqti:itemBody/child::node()";
	// private final String PART_HTML = "//imsqti:itemBody";

	private final String IQ_QUERY_CHOICES = PART_ITEM_BODY
			+ "/imsqti:choiceInteraction"
			+ "/imsqti:simpleChoice/imsqti:img/@src";

	private final String QUERY_TITLE_ATTRIBUTE = "/imsqti:assessmentItem/@title";

	// --------------------------
	
	private final String QUERRY_CORRECT_RESP_ID = "/imsqti:assessmentItem"
			+ "/imsqti:responseDeclaration/imsqti:correctResponse/imsqti:value/text()";

	private final String TEMPLATE_QUERY_CHOICES_ITEM = PART_ITEM_BODY
			+ "/imsqti:choiceInteraction"
			+ "/imsqti:simpleChoice[@identifier=\"%s\"]";
	
	private final String TEMPLATE_QUERY_ITEM_POSITION = "count(" + TEMPLATE_QUERY_CHOICES_ITEM + "/preceding-sibling::*)";
		
	// --------------------------
	
	private final String QUERY_CHOICE_TEXTLIST = PART_ITEM_BODY
			+ "/imsqti:choiceInteraction"
			+ "/imsqti:simpleChoice/text()";
	
	// --------------------------
	
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

	public String queryQuestionText() {
		return cleanHtmlContent(PART_ITEM_BODY, PART_HTML);
	}
	
	public int queryCorrectResponsePosition() {
		String corr_resp_id =  queryToString(QUERRY_CORRECT_RESP_ID);		
		String corr_resp_count_query = String.format(TEMPLATE_QUERY_ITEM_POSITION, corr_resp_id);
		String corr_resp_position = queryToString(corr_resp_count_query);

		// System.err.println("correct resp id: " + corr_resp_id);
		// System.err.println("count          : " + corr_resp_position);
		// System.err.println(corr_resp_count_query);
		
		return Integer.parseInt(corr_resp_position);
	}
	
	public List<String> queryResponseItemTextList() {
		return queryToStringList(QUERY_CHOICE_TEXTLIST);
	}
}
