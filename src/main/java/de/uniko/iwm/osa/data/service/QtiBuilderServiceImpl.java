package de.uniko.iwm.osa.data.service;

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
import org.springframework.transaction.annotation.Transactional;

import de.uniko.iwm.osa.data.dao.OsaDbPagesDAO;
import de.uniko.iwm.osa.data.dao.OsaDbQuestitemsDAO;
import de.uniko.iwm.osa.data.dao.OsaDbQuestsDAO;
import de.uniko.iwm.osa.data.model.Cy_PageItem;
import de.uniko.iwm.osa.data.model.Cy_QuestItem;
import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.model.OsaItem;
import de.uniko.iwm.osa.utils.UnZip;

public class QtiBuilderServiceImpl implements QtiBuilderServive {
	static Logger log = Logger.getLogger(QtiBuilderServiceImpl.class.getName());

	@Autowired
	OsaDbPagesDAO pagesDAO;

	@Autowired
	OsaDbQuestsDAO questsDAO;

	@Autowired
	OsaDbQuestitemsDAO questitemsDAO;

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

	/* (non-Javadoc)
	 * @see de.uniko.iwm.osa.qtiinterpreter.QtiBuilderServive#build(de.uniko.iwm.osa.data.model.OsaItem, java.util.List)
	 */
	@Transactional
	public boolean build(OsaItem oi, List<Cy_PageItem> generatedPages) {

		for (Cy_PageItem pi : generatedPages) {

			OsaDbPages p = pi.getPage();
			pagesDAO.addOsaDbPages(p);
			oi.addNewPage(p.getId());

			for (Cy_QuestItem qi : pi.getCy_QuestItem()) {

				OsaDbQuestitems it = qi.getQuestitem();
				it.setPagesid(p.getId());

				questitemsDAO.addOsaDbQuestitems(it);
				oi.addNewQuestitem(it.getId());

				for (OsaDbQuests q : qi.getQuestsList()) {
					q.setQuestid(it.getId());

					questsDAO.addOsaDbQuests(q);
					oi.addNewQuest(q.getId());
				}
			}
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see de.uniko.iwm.osa.qtiinterpreter.QtiBuilderServive#setNavigation(java.util.List, int, java.lang.String, java.lang.String)
	 */
	@Transactional
	public boolean setNavigation(List<Cy_PageItem> generatedPages,
			int jumpToPage, String firstMd5, String firstPagesId) {

		if (generatedPages.size() >= 1) {
			OsaDbPages firstPage = generatedPages.get(0).getPage();  
			firstPage.setMd5key(firstMd5);
			firstPage.setPid(firstPagesId);

			Collections.reverse(generatedPages);
			for (Cy_PageItem pi : generatedPages) {
				OsaDbPages p = pi.getPage();

				p.setForwardform(String.format(fwdftemplate, jumpToPage));
				pagesDAO.storeOsaDbPages(p);

				jumpToPage = p.getId();
			}
		}

		return true;
	}
}
