package de.uniko.iwm.osa.qtiinterpreter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.uniko.iwm.osa.data.dao.OsaDbQuestsDAO;
import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

public class Compile {

	InputStream zipFile;
	String image_base;

	Parse parser;
	String base;
	OsaDbQuestsService questsService;

	public Compile(InputStream zipFile, String image_base,
			OsaDbQuestsService questsService) {
		this.zipFile = zipFile;
		this.image_base = image_base;
		this.questsService = questsService;
	}

	public void run() {

		try {
			base = UnZip.unzipFile(zipFile);
			parser = new Parse(base, image_base);
			//
			// step one
			// scan manifest
			//
			List<String> manifest_entries = stepOne();

			//
			// step two
			// scan each qti item
			//
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//
			// byte[] buffer = new byte[1024];
			// int len;
			// while ((len = xsltFile.read(buffer)) > -1 ) {
			// baos.write(buffer, 0, len);
			// }
			// baos.flush();
			int count = 0;

			for (String href : manifest_entries) {
				count++;

				System.out.println(String.format(" %3d Parsing: %s", count,
						href));

				stepTwo(href);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//
	// handle manifestfile
	//

	public List<String> stepOne() {
		return parser.handleManifest("imsmanifest.xml");
	}

	//
	// handle assessment item ref
	//

	public void stepTwo(String href) {
//		List<OsaDbQuests> questById = questsService
//				.getOsaDbQuestsById(new Integer(id));
//		OsaDbQuests q;
//		if (questById.size() == 1) {

//			q = questById.get(0);

			try {
				List <AssessmentItem> itemList = parser.handle_assessmentTest(href);
				
				for (AssessmentItem item : itemList) {
					System.out.println("-->" + item);
					
					item.toOsaDbQuests(questsService);
				}
				// questsService.storeOsaDbQuests(q);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//		} else {
//			System.out.println("--> ERRON incorrect questsdb:" + id);
//		}
	}

//	public void stepThree(String href, int id) throws FileNotFoundException {
//
//		List<OsaDbQuests> questById = questsService
//				.getOsaDbQuestsById(new Integer(id));
//		OsaDbQuests q;
//		if (questById.size() == 1) {
//
//			q = questById.get(0);
//			parser.handle_imsqti_item_xmlv2p1(href, q);
//			// questsService.storeOsaDbQuests(q);
//
//		} else {
//			System.out.println("--> ERRON incorrect questsdb:" + id);
//		}
//	}

}
