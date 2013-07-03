package de.uniko.iwm.osa.qtiinterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import de.uniko.iwm.osa.data.model.Cy_PageItem;
import de.uniko.iwm.osa.data.model.Cy_QuestItem;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaItem;
import de.uniko.iwm.osa.data.service.OsaDbPagesService;
import de.uniko.iwm.osa.data.service.OsaDbQuestitemsService;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;
import de.uniko.iwm.osa.utils.UnZip;

public class Builder {
	static Logger log = Logger.getLogger(Builder.class.getName());

	@Autowired
	OsaDbPagesService pagesService;
	
	@Autowired
	OsaDbQuestsService questsService;
	
	@Autowired
	OsaDbQuestitemsService questitemsService;

	@Autowired
	private HashMap<String, Integer> keyword2cyquest;

	@Value("${QTI_MEDIAFOLDER}")
	String QTI_MEDIAFOLDER;
	@Value("${CYQUEST_MEDIAFOLDER}")
	String CYQUEST_MEDIAFOLDER;

	@Value("${IMSMANIFEST}")
	String IMSMANIFEST = "imsmanifest.xml";

	public OsaItem run(InputStream zipFile, String osaBase) {
		OsaItem changedPages = new OsaItem();

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
				List<Cy_PageItem> generatedPages = parser.getGenerated_pages();

				int i = 0;
				for (Cy_PageItem pi : generatedPages) {
					int j = 0;
					i++;
					
					pagesService.addOsaDbPages(pi.getPage());

					for (Cy_QuestItem qi : pi.getCy_QuestItem()) {
						int k = 0;
						j++;
						
						questsService.addOsaDbQuests(qi.getQuest());

						for (OsaDbQuestitems it : qi.getItemList()) {
							System.err.print(" --> [" + i + ", " + j + ", " + k
									+ "]");
							
							questitemsService.addOsaDbQuestitems(it);	
						}
					}
					System.err.println("");

				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return changedPages;
	}
}
