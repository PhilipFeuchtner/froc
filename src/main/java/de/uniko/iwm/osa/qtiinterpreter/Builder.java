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

	// @Value("${QTI_MEDIAFOLDER}")
	// String QTI_MEDIAFOLDER;
	// @Value("${CYQUEST_MEDIAFOLDER}")
	// String CYQUEST_MEDIAFOLDER;
	//
	// @Value("${IMSMANIFEST}")
	// String IMSMANIFEST;

	String fwdftemplate = "a:2:{s:1:\"p\";i:%d;s:1:\"t\";s:6:\"weiter\";}";

	/**
	 * writes pages to ds
	 * 
	 * @param oi
	 *            collect reports
	 * @param generatedPages
	 *            list of pages, quest & questitem
	 * @return true
	 */
	public boolean build(OsaItem oi, List<Cy_PageItem> generatedPages) {

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
		}

		return true;
	}

	/**
	 * links pages with forward-navigation
	 * 
	 * @param generatedPages
	 * @param jumpToPage
	 *            id where the last question directs to
	 * @return true
	 */
	public boolean setNavigation(List<Cy_PageItem> generatedPages,
			int jumpToPage, String firstMd5) {

		if (generatedPages.size() >= 1) {
			generatedPages.get(0).getPage().setMd5key(firstMd5);

			Collections.reverse(generatedPages);
			for (Cy_PageItem pi : generatedPages) {
				OsaDbPages p = pi.getPage();

				p.setForwardform(String.format(fwdftemplate, jumpToPage));
				pagesService.storeOsaDbPages(p);

				jumpToPage = p.getId();
			}
		}

		return true;
	}
}
