package de.uniko.iwm.osa.data.assessmentItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	static Logger log = Logger.getLogger(AssessmentItem.class.getName());

	/**
	 * question or page?
	 * 
	 * unused
	 */
	static enum CYQUEST_QUESTION_TYPE {
		QESTION, EXTRASEITE
	};

	static String[] quest_ans_def = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L" };

	public abstract boolean setup(PagesQuestitemsQuestsMisc pqiqm,
			QuestConfigurer q);

	String cleanMediaUrl(String text, QuestConfigurer qc) {

		// <p xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">Finden Sie
		// die Regel und wählen Sie das Quadrat aus, das Ihrer Meinung nach am
		// besten in das noch leere Feld passt.</p><p
		// xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		// <img xmlns="http://www.imsglobal.org/xsd/imsqti_v2p1" alt=""
		// src="media/989f8f9617f5404b876e534018fa8bec.png"/>
		// </p>

		Pattern p = Pattern.compile("src=\"/?" + qc.getQti_media_folder()
				+ "/([^\"]+)\"");
		Matcher m = p.matcher(text);

		return m.find() ? m.replaceAll("src=\"" + qc.getCy_image_base()
				+ "/$1\"") : text;
	}
}
