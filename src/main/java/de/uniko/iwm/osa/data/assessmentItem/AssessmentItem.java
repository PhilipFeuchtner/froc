package de.uniko.iwm.osa.data.assessmentItem;

import java.util.List;
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

	void setTypevaluesAndShowdesc(PagesQuestitemsQuestsMisc pqiq,
			QuestConfigurer qc) {

		String template = "i:%d;s:%d:\"%s\";";

		int correct = qc.queryCorrectResponsePosition();
		List<String> responses = qc.queryResponseItemTextList();

		String tv;
		String sd;

		if (responses.isEmpty()) {
			tv = "" ; //"a:1:{i:0;i:1;}";
			sd = "" ; //"a:1:{i:0;s:11:\"G&uuml;ltig\";}";
		} else {
			int count = 0;

			//
			// set prefix
			//
			tv = "a:" + responses.size() + ":{";
			sd = "a:" + responses.size() + ":{";

			for (String item : responses) {
				//
				// build correct response
				//

				if (count == correct) {
					tv = tv + "i:" + count + ";i:1;";
				} else {
					tv = tv + "i:" + count + ";i:0;";
				}

				//
				// build descr
				//

				sd = sd + String.format(template, count, item.length(), item);

				// next item
				count++;
			}

			//
			// close array
			//
			tv = tv + "}";
			sd = sd + "}";
		}

		// System.err.println(sd);
		// System.err.println(tv);

		pqiq.setQ_showdesc(sd);
		pqiq.setQ_typevalues(tv);
	}
}
