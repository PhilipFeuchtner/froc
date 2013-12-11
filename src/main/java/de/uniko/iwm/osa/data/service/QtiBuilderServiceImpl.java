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

			if (null != qi) {
				qi.setPagesid(p.getId());

				questitemsDAO.addOsaDbQuestitems(qi);
				oi.addNewQuestitem(qi.getId());

				for (OsaDbQuests q : qw.getOsaDbQuest()) {
					q.setQuestid(qi.getId());

					questsDAO.addOsaDbQuests(q);
					oi.addNewQuest(q.getId());
				}
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
			// ToDo copy file if its simple text
			//
			
			// ----
			firstPage.setMd5key(firstMd5);
			firstPage.setPid(firstPagesId);

			oi.updateFirstNewPage(firstMd5);

			Collections.reverse(generatedPages);
			for (Cy_QuestionWrapper pi : generatedPages) {
				OsaDbPages p = pi.getPage();

				p.setForwardform(String.format(fwdftemplate, jumpToPage));
				
				//Adjust PageID before store
				int newPageId = Integer.parseInt( p.getPid().substring( p.getPid().length()-2, p.getPid().length()) );
				
				Logger.getRootLogger().error( "OPageId: "+newPageId );
				
				newPageId += Integer.parseInt(firstPagesId)-1;
				
				Logger.getRootLogger().error( "NPageId: "+(newPageId+(Integer.parseInt(firstPagesId)-1)) );
				
				Logger.getRootLogger().error( String.valueOf(newPageId) );
				
				//Fix PageID
				p.setPid( String.valueOf(newPageId) );
				
				//Eliminate Clones
				List<OsaDbPages> clones = pagesDAO.getOsaDbPagesByPid( String.valueOf(newPageId) );
				Logger.getRootLogger().error("Clone Amount: "+clones.size());
				for(OsaDbPages clone : clones)
				{
					int cloneId = clone.getId();
					Logger.getRootLogger().error("Remove Id_"+clone.getId());
					pagesDAO.removeOsaDbPages( cloneId );
				}
				
				//Save DBPage
				pagesDAO.storeOsaDbPages(p);

				jumpToPage = p.getId();
			}
			
			//TODO: Fix link to from 5X01 (input page) to firstPage generated Page.
			String categoriePage = String.valueOf(Integer.parseInt(firstPagesId)-1);
			OsaDbPages p5400 = pagesDAO.getOsaDbPagesByPid(categoriePage).get(0);
			OsaDbPages p5401 = pagesDAO.getOsaDbPagesByPid(firstPagesId).get(0);
			p5400.setForwardform("a:2:{s:1:\"p\";i:"+p5401.getId()+";s:1:\"t\";s:6:\"weiter\";}");
			pagesDAO.storeOsaDbPages(p5400);
			
			//Fix all of added section (5X00)
			if(pagesDAO.getOsaDbPagesByPid(firstPagesId).isEmpty())
			{
				Logger.getRootLogger().error("Page missing - FirstPageID: "+firstPagesId );
			}
			OsaDbPages curPage = pagesDAO.getOsaDbPagesByPid(firstPagesId).get(0);
			int pageCounter = 0;
			
			while(curPage.getForwardform().contains("weiter")){
				//Fix Pid
				curPage.setPid(String.valueOf(Integer.parseInt(firstPagesId)+pageCounter));
				pageCounter++;
				
				//Extract forwardForm Id
				String forForm = curPage.getForwardform();
				forForm = forForm.substring(forForm.indexOf("i:")+2, forForm.length());
				forForm = forForm.substring(0, forForm.indexOf(";"));
				int nextId = Integer.parseInt(forForm);
				
				//SavePage
				pagesDAO.storeOsaDbPages(curPage);
				
				//NextPage
				if(pagesDAO.getOsaDbPagesById(nextId).isEmpty())
				{
					Logger.getRootLogger().error("Page missing - Id: "+nextId );
					break;
				}
				curPage = pagesDAO.getOsaDbPagesById(nextId).get(0);
			}
			
			
		}

		return true;
	}
}
