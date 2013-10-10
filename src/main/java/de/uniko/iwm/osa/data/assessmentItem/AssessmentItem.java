package de.uniko.iwm.osa.data.assessmentItem;

import org.apache.log4j.Logger;

import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.QuestConfigurer;

/**
 * @author user
 * 
 *         interface connection assessmentitem to osadbquest
 * 
 *         most work done in constructor, intentionally left undefined
 * 
 *         constructor take an itemconfigurator as parameter
 * 
 */
public abstract class AssessmentItem {
	static Logger log = Logger
			.getLogger(AssessmentItem.class.getName());

	/**
	 * question or page?
	 * 
	 * unused
	 */
	static enum CYQUEST_QUESTION_TYPE {
		QESTION, EXTRASEITE
	};

	static String[] quest_ans_def = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L" };

	public abstract boolean setup(PagesQuestitemsQuestsMisc pqiqm, QuestConfigurer q);
}
