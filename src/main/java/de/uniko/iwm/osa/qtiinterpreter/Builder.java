package de.uniko.iwm.osa.qtiinterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem;
import de.uniko.iwm.osa.data.assessmentItem.AssessmentItem_Type001;
import de.uniko.iwm.osa.data.model.AssessmentSection;
import de.uniko.iwm.osa.data.model.AssessmentTest;
import de.uniko.iwm.osa.data.model.OsaItem;
import de.uniko.iwm.osa.data.model.OsaPage;
import de.uniko.iwm.osa.data.model.TestPart;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;
import de.uniko.iwm.osa.utils.UnZip;

public class Builder {
	static Logger log = Logger.getLogger(Builder.class.getName());

	@Autowired
	OsaDbQuestsService questsService;

	@Autowired
	private HashMap<String, Integer> keyword2cyquest;

	final String QTI_MEDIAFOLDER = "media";
	final String CYQUEST_MEDIAFOLDER = "media";

	final String IMSMANIFEST = "imsmanifest.xml";

	public OsaPage run(InputStream zipFile, String osaBase) {
		OsaPage osaPage = new OsaPage();

		try {
			String base = UnZip.unzipFile(zipFile);
			Parse parser = new Parse(base, keyword2cyquest);

			//
			// step zero
			// copy media files

			FileUtils
					.copyDirectory(
							new File(FilenameUtils
									.concat(base, QTI_MEDIAFOLDER)),
							new File(FilenameUtils.concat(osaBase,
									CYQUEST_MEDIAFOLDER)));

			//
			// step one
			// scan manifest
			//
			if (parser.handleManifest(IMSMANIFEST)) {
				AssessmentTest assessmentTest = parser.getAssessmentTest();

				for (TestPart testPart : assessmentTest.getTestParts()) {
					for (AssessmentSection assessmentSection : testPart
							.getAssessmentSections()) {
						OsaItem osaItem = new OsaItem();

						for (AssessmentItem item : assessmentSection
								.getAssessmentItems()) {

							// osaItem.addQuestsOldId(item.getId());
							//
							// System.out.println("AssessmentItem: " + item);
							//
							// switch (item.getAssessmentType()) {
							// case INTERESSEN:
							// AssessmentItem_Type001 t =
							// (AssessmentItem_Type001)item;
							// int newId = t.toOsaDbQuests(questsService);
							// log.info("   " + newId);
							//
							// osaItem.addQuestsNewId(newId);
							// osaItem.addQuestsQuestId(t.getQuestid());
							// break;
							// case EXTRASEITE:
							// // do nothing
							// break;
							// default:
							// log.error("ERROR: Invalid item: " + item);
							// }
						}

						osaPage.addQuestionPages(osaItem);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return osaPage;
	}
}
