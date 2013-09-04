package de.uniko.iwm.osa.data.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.uniko.iwm.osa.data.dao.OsaDbPagesDAO;
import de.uniko.iwm.osa.data.dao.OsaDbQuestitemsDAO;
import de.uniko.iwm.osa.data.dao.OsaDbQuestsDAO;
import de.uniko.iwm.osa.data.model.Cy_QuestionWrapper;
import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.model.osaitem.OsaItem;

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

	private OsaItem oi;

	// @Value("${QTI_MEDIAFOLDER}")
	// String QTI_MEDIAFOLDER;
	// @Value("${CYQUEST_MEDIAFOLDER}")
	// String CYQUEST_MEDIAFOLDER;
	//
	// @Value("${IMSMANIFEST}")
	// String IMSMANIFEST;

	String fwdftemplate = "a:2:{s:1:\"p\";i:%d;s:1:\"t\";s:6:\"weiter\";}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniko.iwm.osa.qtiinterpreter.QtiBuilderServive#build(de.uniko.iwm.
	 * osa.data.model.OsaItem, java.util.List)
	 */
	@Transactional
	public boolean build(OsaItem oi, List<Cy_QuestionWrapper> generatedPages) {
		this.oi = oi;

		for (Cy_QuestionWrapper qw : generatedPages) {

			OsaDbPages p = qw.getPage();
			pagesDAO.addOsaDbPages(p);
			oi.addNewPage(p.getId(), p.getMd5key());

			OsaDbQuestitems qi = qw.getOsaDbQuestitems();

			qi.setPagesid(p.getId());

			questitemsDAO.addOsaDbQuestitems(qi);
			oi.addNewQuestitem(qi.getId());

			for (OsaDbQuests q : qw.getOsaDbQuest()) {
				q.setQuestid(qi.getId());

				questsDAO.addOsaDbQuests(q);
				oi.addNewQuest(q.getId());
			}
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniko.iwm.osa.qtiinterpreter.QtiBuilderServive#setNavigation(java.
	 * util.List, int, java.lang.String, java.lang.String)
	 */
	@Transactional
	public boolean setNavigation(List<Cy_QuestionWrapper> generatedPages,
			int jumpToPage, String firstMd5, String firstPagesId) {

		if (generatedPages.size() >= 1) {
			OsaDbPages firstPage = generatedPages.get(0).getPage();
			firstPage.setMd5key(firstMd5);
			firstPage.setPid(firstPagesId);

			oi.updateFirstNewPage(firstMd5);

			Collections.reverse(generatedPages);
			for (Cy_QuestionWrapper pi : generatedPages) {
				OsaDbPages p = pi.getPage();

				p.setForwardform(String.format(fwdftemplate, jumpToPage));
				pagesDAO.storeOsaDbPages(p);

				jumpToPage = p.getId();
			}
		}

		return true;
	}
}
