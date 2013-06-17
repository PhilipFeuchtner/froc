package de.uniko.iwm.osa.questsitemTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import java.util.List;

import org.lorecraft.phparser.SerializedPhpParser;
import org.springframework.beans.factory.annotation.Autowired;

import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.service.OsaDbPagesService;
import de.uniko.iwm.osa.data.service.OsaDbQuestitemsService;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

public class QTree {

	@Autowired
	private OsaDbPagesService pagesService;

	@Autowired
	private OsaDbQuestitemsService questsitemsService;

	@Autowired
	private OsaDbQuestsService questsService;

	// resultset
	Set<Integer> pages2remove = null;
	Set<Integer> quests2remove = null;
	Set<Integer> questitems2remove = null;

	public int scanDatabase(int startPage) {

		pages2remove = new TreeSet<Integer>();
		quests2remove = new TreeSet<Integer>();
		questitems2remove = new TreeSet<Integer>();
		
		//
		// parse pages
		//

		int page = startPage;
		boolean hasNextPage = true;

		while (hasNextPage) {

			List<OsaDbPages> current_pages = pagesService
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

				List<OsaDbQuestitems> questitems_list = questsitemsService
						.listOsaDbQuestitemsByPagesid(page);
				if (questitems_list.size() == 0) {
					hasNextPage = false;
				}

			} else {
				hasNextPage = false;
			}
		}

		System.err.println("next content page: " + page);
		System.err.println("pages    : " + pages2remove);
		System.err.println("quests   : " + quests2remove);
		System.err.println("questitem: " + questitems2remove);

		//
		// remove items from db
		//

		removeAllContent();

		return page;
	}

	void parse_questitems(int id) {

		List<OsaDbQuestitems> questitems_list = questsitemsService
				.listOsaDbQuestitemsByPagesid(id);
		for (OsaDbQuestitems item : questitems_list) {
			int itemId = item.getId();
			System.out.println("   -> " + item);

			questitems2remove.add(itemId);

			parse_quests(itemId);
		}
	}

	void parse_quests(int id) {

		List<OsaDbQuests> quests_list = questsService
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
			pagesService.removeOsaDbPages(id);
		}

		for (Integer id : quests2remove) {
			questsService.removeOsaDbQuests(id);
		}

		for (Integer id : questitems2remove) {
			questsitemsService.removeOsaDbQuestitems(id);
		}

		return true;
	}
}
