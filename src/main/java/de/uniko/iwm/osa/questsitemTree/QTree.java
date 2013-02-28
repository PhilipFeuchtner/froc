package de.uniko.iwm.osa.questsitemTree;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import org.lorecraft.phparser.SerializedPhpParser;

import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.service.OsaDbPagesService;
import de.uniko.iwm.osa.data.service.OsaDbQuestitemsService;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

public class QTree {

	// OsaDbPagesService pagesService;
	// OsaDbQuestitemsService questsitemsService;
	// OsaDbQuestsService questsService;

	static final int MAGIC_START_PAGES = 37;
	static final int MAGIC_END_PAGES = 51;

	// public QTree(OsaDbPagesService pagesService, OsaDbQuestitemsService
	// questsitemsService, OsaDbQuestsService questsService) {
	// this.pagesService = pagesService;
	// this.questsitemsService = questsitemsService;
	// this.questsService = questsService;
	// }

	public StringBuilder toDot(OsaDbPagesService pagesService,
			OsaDbQuestitemsService questsitemsService,
			OsaDbQuestsService questsService) {
		StringBuilder result = new StringBuilder();

		OsaDbPages pages;
		OsaDbQuestitems questsitems;
		OsaDbQuests quests;

		//
		// parse pages
		//

		int page = MAGIC_START_PAGES;
		boolean hasNextPage = true;

		while (hasNextPage) {

			OsaDbPages current_page = pagesService.getOsaDbPagesById(
					new Integer(page)).get(0);

			SerializedPhpParser serializedPhpParser = new SerializedPhpParser(
					current_page.getForwardform());
			Object res = serializedPhpParser.parse();

			@SuppressWarnings("unchecked")
			HashMap<String, Object> val = (HashMap<String, Object>) res;
			page = (int) val.get("p");

			System.out.println("HERE: " + current_page.getForwardform());
			// System.out.println("    - " + res);
			// System.out.println("    - " + res.getClass());
			System.out.println("    - " + page);
			
			//
			// process next level
			//
			
			parse_questitems(page, questsitemsService, questsService);
			
			//
			// end reached?
			//
			
			hasNextPage = page != MAGIC_END_PAGES;
		}
		return result;
	}

	public StringBuilder parse_questitems(int id,
			OsaDbQuestitemsService questsitemsService,
			OsaDbQuestsService questsService) {
		StringBuilder result = new StringBuilder();
		
		List<OsaDbQuestitems> questitems_list = questsitemsService.listOsaDbQuestitemsByPagesid(id);
		for (OsaDbQuestitems item : questitems_list) {
			System.out.println("   -> " + item);
			
			parse_quests(item.getId(), questsService);
		}

		return result;
	}
	
	public StringBuilder parse_quests(int id,
			OsaDbQuestsService questsService) {
		StringBuilder result = new StringBuilder();
		
		List<OsaDbQuests> quests_list = questsService.getOsaDbQuestsByQuestid(id);
		for (OsaDbQuests item : quests_list) {
			System.out.println("   +> " + item);
		}

		return result;
	}
}
