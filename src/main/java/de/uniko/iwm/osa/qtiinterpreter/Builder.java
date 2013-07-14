package de.uniko.iwm.osa.qtiinterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import de.uniko.iwm.osa.data.model.Cy_PageItem;
import de.uniko.iwm.osa.data.model.Cy_QuestItem;
import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaDbQuests;
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

	String fwdftemplate = "a:2:{s:1:\"p\";i:%d;s:1:\"t\";s:6:\"weiter\";}";

	public boolean run(InputStream zipFile, String osaBase, OsaItem oi,
			int jumpToPage, String pagesid) {
		
		try {
			String base = UnZip.unzipFile(zipFile);
			Parse parser = new Parse(base, keyword2cyquest, pagesid, oi);

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

				for (Cy_PageItem pi : generatedPages) {

					OsaDbPages p = pi.getPage();
					pagesService.addOsaDbPages(p);
					oi.addNewPage(p.getId());

					for (Cy_QuestItem qi : pi.getCy_QuestItem()) {

						OsaDbQuestitems it = qi.getQuestitem();
						it.setPagesid(p.getId());
						
						questitemsService.addOsaDbQuestitems(it);
						oi.addNewQuestitem(it.getId());

						for (OsaDbQuests q : qi.getQuestsList()) {
							q.setQuestid(it.getId());

							questsService.addOsaDbQuests(q);
							oi.addNewQuest(q.getId());
						}
					}
					System.err.println("");

				}

				//
				// set navigation
				//

				Collections.reverse(generatedPages);
				for (Cy_PageItem pi : generatedPages) {
					OsaDbPages p = pi.getPage();
					
					p.setForwardform(String.format(fwdftemplate, jumpToPage));
					pagesService.storeOsaDbPages(p);

					jumpToPage = p.getId();
				}
			}
		} catch (IOException e) {
			oi.addErrorEntry(e.getMessage());
			e.printStackTrace();

			return false;
		}

		return true;
	}
}
