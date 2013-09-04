package de.uniko.iwm.osa.data.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import java.util.List;

import org.lorecraft.phparser.SerializedPhpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.uniko.iwm.osa.data.dao.OsaDbPagesDAO;
import de.uniko.iwm.osa.data.dao.OsaDbQuestitemsDAO;
import de.uniko.iwm.osa.data.dao.OsaDbQuestsDAO;
import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.model.osaitem.OsaItem;

public class QtiTreeServiceImpl implements QtiTreeService {

	@Autowired
	private OsaDbPagesDAO pagesDAO;

	@Autowired
	private OsaDbQuestitemsDAO questsitemsDAO;

	@Autowired
	private OsaDbQuestsDAO questsDAO;

	// resultset
	Set<Integer> pages2remove = null;
	Set<Integer> quests2remove = null;
	Set<Integer> questitems2remove = null;
	
	String firstMd5 = null;

	/* (non-Javadoc)
	 * @see de.uniko.iwm.osa.qtiinterpreter.QtiTreeService#scanDatabase(int, de.uniko.iwm.osa.data.model.OsaItem)
	 */
	@Transactional
	public int scanDatabase(int startPage, OsaItem oi) {

		pages2remove = new TreeSet<Integer>();
		quests2remove = new TreeSet<Integer>();
		questitems2remove = new TreeSet<Integer>();

		// 
		// rescue md5 of start page
		//
		
		List<OsaDbPages> first_pages = pagesDAO
				.getOsaDbPagesById(new Integer(startPage));

		if (first_pages.size() == 1) {
			OsaDbPages first_page = first_pages.get(0);
			firstMd5 = first_page.getMd5key();
		} else {
			oi.addErrorEntry("missing or abigous id: " + startPage);
			return 0;
		}
		
		//
		// parse pages
		//

		int page = startPage;
		boolean hasNextPage = true;

		while (hasNextPage) {

			List<OsaDbPages> current_pages = pagesDAO
					.getOsaDbPagesById(new Integer(page));

			if (!current_pages.isEmpty()) {

				pages2remove.add(page);

				OsaDbPages current_page = current_pages.get(0);

				System.out.println("HERE: " + current_page.getForwardform());
				// System.out.println("    - " + res);
				// System.out.println("    - " + res.getClass());
				System.out.println("    - " + page);

				//
				// process next level
				//

				parse_questitems(page);

				//
				// extract next page
				//

				SerializedPhpParser serializedPhpParser = new SerializedPhpParser(
						current_page.getForwardform());
				Object res = serializedPhpParser.parse();

				@SuppressWarnings("unchecked")
				HashMap<String, Object> val = (HashMap<String, Object>) res;
				page = (int) val.get("p");

				//
				// is quest?, end reached?
				//

				List<OsaDbQuestitems> questitems_list = questsitemsDAO
						.listOsaDbQuestitemsByPagesid(page);
				if (questitems_list.size() == 0) {
					hasNextPage = false;
				}

			} else {
				hasNextPage = false;
			}
		}

		// System.err.println("next content page: " + page);
		// System.err.println("pages    : " + pages2remove);
		// System.err.println("quests   : " + quests2remove);
		// System.err.println("questitem: " + questitems2remove);

		for (Integer i : pages2remove) {
			oi.addDeletedPage(i);
		}

		for (Integer i : quests2remove) {
			oi.addDeletedQuest(i);
		}

		for (Integer i : questitems2remove) {
			oi.addDeletedQuestitem(i);
		}

		//
		// remove items from db
		//

		removeAllContent();

		return page;
	}

	void parse_questitems(int id) {

		List<OsaDbQuestitems> questitems_list = questsitemsDAO
				.listOsaDbQuestitemsByPagesid(id);
		for (OsaDbQuestitems item : questitems_list) {
			int itemId = item.getId();
			System.out.println("   -> " + item);

			questitems2remove.add(itemId);

			parse_quests(itemId);
		}
	}

	void parse_quests(int id) {

		List<OsaDbQuests> quests_list = questsDAO
				.getOsaDbQuestsByQuestid(id);
		for (OsaDbQuests item : quests_list) {
			int questId = item.getId();
			System.out.println("   +> " + item);

			quests2remove.add(questId);
		}
	}

	// -----------------------------------------------------------------------

	boolean removeAllContent() {

		for (Integer id : pages2remove) {
			pagesDAO.removeOsaDbPages(id);
		}

		for (Integer id : quests2remove) {
			questsDAO.removeOsaDbQuests(id);
		}

		for (Integer id : questitems2remove) {
			questsitemsDAO.removeOsaDbQuestitems(id);
		}

		return true;
	}

	// -----------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see de.uniko.iwm.osa.qtiinterpreter.QtiTreeService#getPages2remove()
	 */
	@Override
	public List<Integer> getPages2remove() {
		List<Integer> result = new ArrayList<Integer>();
		result.addAll(pages2remove);

		return result;
	}

	/* (non-Javadoc)
	 * @see de.uniko.iwm.osa.qtiinterpreter.QtiTreeService#getQuests2remove()
	 */
	@Override
	public List<Integer> getQuests2remove() {
		List<Integer> result = new ArrayList<Integer>();
		result.addAll(quests2remove);

		return result;
	}

	/* (non-Javadoc)
	 * @see de.uniko.iwm.osa.qtiinterpreter.QtiTreeService#getQuestitems2remove()
	 */
	@Override
	public List<Integer> getQuestitems2remove() {
		List<Integer> result = new ArrayList<Integer>();
		result.addAll(questitems2remove);

		return result;
	}
	
	/* (non-Javadoc)
	 * @see de.uniko.iwm.osa.qtiinterpreter.QtiTreeService#getFirstMd5()
	 */
	@Override
	public String getFirstMd5() {
		return firstMd5;
	}
}
