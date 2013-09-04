package de.uniko.iwm.osa.data.assessmentItem;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

public class AssessmentItem_Type008 implements AssessmentItem {

	static Logger log = Logger
			.getLogger(AssessmentItem_Type008.class.getName());

	ItemConigurator ic = null;

	int identifier = 8;
	String cyquest_question_type = null;
	String title;
	
	int itemPerPage = 1;

	String[] quest_ans_def = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L" };

	OsaDbQuests quest;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uniko.iwm.osa.data.model.AssessmantItemI#getQuestid()
	 */

	public AssessmentItem_Type008(OsaDbQuests quest, ItemConigurator ic, String title) {
		log.info("Assessment item type 008 created");

		this.quest = quest;
		this.ic = ic;
		this.title = title;

		buildShowdesc();
		buildTypevalues();

		// showdesc:
		// a:2:{s:4:"type";s:3:"img";s:5:"value";s:31:"/media/images/pid5301_quest.png";}

		// typvalues:
		// a:4:{i:0;a:8:{s:3:"img";s:27:"/media/images/pid5301_a.png";s:8:"imgwidth";s:3:"110";s:9:"imgheight";s:3:"110";s:3:"svg";s:0:"";s:8:"svgwidth";s:0:"";s:9:"svgheight";s:0:"";s:4:"desc";s:1:"A";s:4:"text";s:0:"";}i:1;a:8:{s:3:"img";s:27:"/media/images/pid5301_b.png";s:8:"imgwidth";s:3:"110";s:9:"imgheight";s:3:"110";s:3:"svg";s:0:"";s:8:"svgwidth";s:0:"";s:9:"svgheight";s:0:"";s:4:"desc";s:1:"B";s:4:"text";s:0:"";}i:2;a:8:{s:3:"img";s:27:"/media/images/pid5301_c.png";s:8:"imgwidth";s:3:"110";s:9:"imgheight";s:3:"110";s:3:"svg";s:0:"";s:8:"svgwidth";s:0:"";s:9:"svgheight";s:0:"";s:4:"desc";s:1:"C";s:4:"text";s:0:"";}i:3;a:8:{s:3:"img";s:27:"/media/images/pid5301_d.png";s:8:"imgwidth";s:3:"110";s:9:"imgheight";s:3:"110";s:3:"svg";s:0:"";s:8:"svgwidth";s:0:"";s:9:"svgheight";s:0:"";s:4:"desc";s:1:"D";s:4:"text";s:0:"";}}

		// log.info(ic.queryIQTask());
	}

	@Override
	public int getIdentifier() {
		return identifier;
	}

	@Override
	public String getCqt() {
		return cyquest_question_type;
	}

	@Override
	public OsaDbQuests getOsaDbQuest() {
		return quest;
	}

	// ------------------------------------------------------------------------------------

	// showdesc:
	// a:2:{s:4:"type";s:3:"img";s:5:"value";s:31:"/media/images/pid5301_quest.png";}

	void buildShowdesc() {
		String question = ic.queryIQQuestion();

		if (question != null)
			question = cleanImagePath(question);

		String text = String.format(
				"a:2:{s:4:\"type\";s:3:\"img\";s:5:\"value\";s:%d:\"%s\";}",
				question.length(), question);

		System.err.println("---> " + text);

		quest.setShowdesc(text);
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

	void buildTypevalues() {
		List<String> choices = ic.queryIQChoices();

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

			item = cleanImagePath(item);

			String answer = quest_ans_def[count];

			arrayText = arrayText
					+ String.format(imageEntryFormat, count, item.length(),
							item, answer.length(), answer, answer.length(),
							answer);

			count++;
		}

		String text = String.format(arrayFormat, choices.size(), arrayText);

		System.err.println(text);

		quest.setTypevalues(text);
	}

	private String cleanImagePath(String path) {
		Pattern p = Pattern.compile("/?" + ic.getQti_media_folder() + "/(.*)");
		Matcher m = p.matcher(path);

		return m.find() ? "/" + ic.getCy_image_base() + "/" + m.group(1) : path;

	}
	
	@Override
	public int getItemPerPage() {
		return itemPerPage;
	}
	
	@Override
	public void setTitle(String text) {
		title = text;	
	}

	@Override
	public String getTitle() {
		return title;
	}
}
