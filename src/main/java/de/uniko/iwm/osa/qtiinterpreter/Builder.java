package de.uniko.iwm.osa.qtiinterpreter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import de.uniko.iwm.osa.data.model.AssessmentItem;
import de.uniko.iwm.osa.data.model.AssessmentSection;
import de.uniko.iwm.osa.data.model.AssessmentTest;
import de.uniko.iwm.osa.data.model.TestPart;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

public class Builder {

	@Autowired
	OsaDbQuestsService questsService;

	String image_base = null;

	public void run(InputStream zipFile) {

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
						for (AssessmentItem assessmentItem : assessmentSection
								.getAssessmentItems()) {
							System.out.println("AssessmentItem: "
									+ assessmentItem);
							int newId = assessmentItem
									.toOsaDbQuests(questsService);
							System.out.println("   " + newId);
						}
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

	}
}
