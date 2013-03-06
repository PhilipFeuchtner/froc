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
			System.out.println("- A -");
			String base = UnZip.unzipFile(zipFile);
			System.out.println("- B -");
			Parse parser = new Parse(base, image_base);
			System.out.println("- C -");
			
			//
			// step one
			// scan manifest
			//
			AssessmentTest assessmentTest = parser.handleManifest("imsmanifest.xml");
			System.out.println("- D -");
			
			for (TestPart testPart : assessmentTest.getTestParts()) {
				System.out.println("- E -");
 				for (AssessmentSection assessmentSection : testPart.getAssessmentSections()) {
 					System.out.println("- F -");
					for (AssessmentItem assessmentItem : assessmentSection.getAssessmentItems()) {
						System.out.println("- G -");
						System.out.println("AssessmentItem: " + assessmentItem);
						int newId = assessmentItem.toOsaDbQuests(questsService);
						System.out.println("   " + newId);
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
