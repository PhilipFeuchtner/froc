package de.uniko.iwm.osa.data.assessmentItem;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.bytecode.LineNumberAttribute.Pc;

import de.uniko.iwm.osa.data.model.PagesQuestitemsQuestsMisc;
import de.uniko.iwm.osa.qtiinterpreter.QuestConfigurer;

public class AssessmentItem_Type008 extends AssessmentItem {

	int identifier = 8;
	int itemPerPage = 1;

	public AssessmentItem_Type008() {
	}

	public boolean setup(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		pqiq.setQi_questtype(identifier);

		// pqiq.setQi_questdesc(qc.queryQuestionText());

		pqiq.setM_itemPerPage(itemPerPage);

		buildShowdesc(pqiq, qc);
		buildTypevalues(pqiq, qc);
		buildQuestdesc(pqiq, qc);

		return true;
	}

	// ------------------------------------------------------------------------------------

	// showdesc:
	// a:2:{s:4:"type";s:3:"img";s:5:"value";s:31:"/media/images/pid5301_quest.png";}
	// a:3:{s:4:"type";s:3:"img";s:5:"value";s:31:"/media/images/pid5301_quest.png";s:6:"answer";s:1:"B";}

	void buildShowdesc(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		String question = qc.queryIQQuestion();

		int coorect_resp = qc.queryCorrectResponsePosition();
		String answer = quest_ans_def[coorect_resp];

		if (question != null)
			question = cleanImagePath(question, qc);

		String text = String
				.format("a:2:{s:4:\"type\";s:3:\"img\";s:5:\"value\";s:%d:\"%s\";s:6:\"answer\";s:%d:\"%s\";}",
						question.length(), question, answer.length(), answer);

		System.err.println("---> " + text);

		pqiq.setQ_showdesc(text);
	}

	// typevalues:
	// ---> Number images
	// a:4:{
	// ---> Image index
	// i:0;
	// ---> Image path
	// a:8:{s:3:"img";s:27:"/media/images/pid5301_a.png";
	// s:8:"imgwidth";s:3:"110";s:9:"imgheight";s:3:"110";s:3:"svg";s:0:"";s:8:"svgwidth";s:0:"";s:9:"svgheight";s:0:"";s:4:"desc";
	// ---> Image Caption
	// s:1:"A";s:4:"text";s:0:"";}
	// i:1;a:8:{s:3:"img";s:27:"/media/images/pid5301_b.png";
	// s:8:"imgwidth";s:3:"110";s:9:"imgheight";s:3:"110";s:3:"svg";s:0:"";s:8:"svgwidth";s:0:"";s:9:"svgheight";s:0:"";s:4:"desc";
	// s:1:"B";s:4:"text";s:0:"";}
	// i:2;a:8:{s:3:"img";s:27:"/media/images/pid5301_c.png";
	// s:8:"imgwidth";s:3:"110";s:9:"imgheight";s:3:"110";s:3:"svg";s:0:"";s:8:"svgwidth";s:0:"";s:9:"svgheight";s:0:"";s:4:"desc";
	// s:1:"C";s:4:"text";s:0:"";}
	// i:3;a:8:{s:3:"img";s:27:"/media/images/pid5301_d.png";
	// s:8:"imgwidth";s:3:"110";s:9:"imgheight";s:3:"110";s:3:"svg";s:0:"";s:8:"svgwidth";s:0:"";s:9:"svgheight";s:0:"";s:4:"desc";
	// s:1:"D";s:4:"text";s:0:"";}}

	void buildTypevalues(PagesQuestitemsQuestsMisc pqiq, QuestConfigurer qc) {
		List<String> choices = qc.queryIQChoices();

		// num entries + array-string
		String arrayFormat = "a:%d:{%s}";

		// array-string:
		// index
		String imageEntryFormat = "i:%d;"
				// size imagepath + image path
				+ "a:8:{s:3:\"img\";s:%d:\"%s\";"
				+ "s:8:\"imgwidth\";s:3:\"110\";s:9:\"imgheight\";s:3:\"110\";"
				+ "s:3:\"svg\";s:0:\"\";s:8:\"svgwidth\";s:0:\"\";s:9:\"svgheight\";s:0:\"\";"
				// size description + description text
				+ "s:4:\"desc\";s:%d:\"%s\";s:4:\"text\";s:%d:\"%s\";}";

		String arrayText = "";
		int count = 0;
		for (String item : choices) {

			item = cleanImagePath(item, qc);

			String answer = AssessmentItem.quest_ans_def[count];

			arrayText = arrayText
					+ String.format(imageEntryFormat, count, item.length(),
							item, answer.length(), answer, answer.length(),
							answer);

			count++;
		}

		String text = String.format(arrayFormat, choices.size(), arrayText);

		System.err.println(text);

		pqiq.setQ_typevalues(text);
	}

	private void buildQuestdesc(PagesQuestitemsQuestsMisc pqiq,
			QuestConfigurer qc) {

		// <p xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">Finden Sie
		// die Regel und wählen Sie das Quadrat aus, das Ihrer Meinung nach am
		// besten in das noch leere Feld passt.</p><p
		// xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		// <img xmlns="http://www.imsglobal.org/xsd/imsqti_v2p1" alt=""
		// src="media/989f8f9617f5404b876e534018fa8bec.png"/>
		// </p>

		String quest_text = qc.queryQuestionText();
		Pattern p = Pattern.compile("src=\"/?" + qc.getQti_media_folder()
				+ "/([^\"]+)\"");
		Matcher m = p.matcher(quest_text);

		String questdesc = m.find() ? m.replaceAll("src=\""
				+ qc.getCy_image_base() + "/$1\"") : quest_text;

		pqiq.setQi_questdesc(questdesc);
	}

	private String cleanImagePath(String path, QuestConfigurer qc) {
		Pattern p = Pattern.compile("/?" + qc.getQti_media_folder() + "/(.*)");
		Matcher m = p.matcher(path);

		return m.find() ? "/" + qc.getCy_image_base() + "/" + m.group(1) : path;

	}
}
