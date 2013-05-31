package de.uniko.iwm.osa.qtiinterpreter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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

	String image_base = null;

	public OsaPage run(InputStream zipFile) {
		OsaPage osaPage = new OsaPage();

		try {
			String base = UnZip.unzipFile(zipFile);
			Parse parser = new Parse(base, image_base);

			//
			// step one
			// scan manifest
			//
			if (parser.handleManifest("imsmanifest.xml")) {
				AssessmentTest assessmentTest = parser.getAssessmentTest();

				for (TestPart testPart : assessmentTest.getTestParts()) {
					for (AssessmentSection assessmentSection : testPart
							.getAssessmentSections()) {
						OsaItem osaItem = new OsaItem();

						for (AssessmentItem item : assessmentSection
								.getAssessmentItems()) {

//							osaItem.addQuestsOldId(item.getId());
//
//							System.out.println("AssessmentItem: " + item);
//
//							switch (item.getAssessmentType()) {
//							case INTERESSEN:
//								AssessmentItem_Type001 t = (AssessmentItem_Type001)item;
//								int newId = t.toOsaDbQuests(questsService);
//								log.info("   " + newId);
//
//								osaItem.addQuestsNewId(newId);
//								osaItem.addQuestsQuestId(t.getQuestid());
//								break;
//							case EXTRASEITE:
//								// do nothing
//								break;
//							default:
//								log.error("ERROR: Invalid item: " + item);
//							}
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
