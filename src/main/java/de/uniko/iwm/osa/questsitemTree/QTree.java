package de.uniko.iwm.osa.questsitemTree;

import java.util.HashMap;
import java.util.List;

import org.lorecraft.phparser.SerializedPhpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaDbQuests;
import de.uniko.iwm.osa.data.service.OsaDbPagesService;
import de.uniko.iwm.osa.data.service.OsaDbQuestitemsService;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService; 

public class QTree {
			
	private @Value("${MAGIC_START_PAGES}") int MAGIC_START_PAGES;
	private @Value("${MAGIC_END_PAGES}") int MAGIC_END_PAGES;

	@Autowired
	private OsaDbPagesService pagesService;
	
	@Autowired
	private OsaDbQuestitemsService questsitemsService;
	
	@Autowired
	private OsaDbQuestsService questsService;
	
	public StringBuilder toDot() {
		StringBuilder result = new StringBuilder();

		//
		// parse pages
		//

		int page = MAGIC_START_PAGES;
		boolean hasNextPage = true;

		System.out.println("Magic values: " +MAGIC_START_PAGES + " : " + MAGIC_END_PAGES);
		
		while (hasNextPage) {

			OsaDbPages current_page = pagesService.getOsaDbPagesById(
					new Integer(page)).get(0);

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
			// end reached?
			//
			
			hasNextPage = page != MAGIC_END_PAGES;
		}
		return result;
	}

	public StringBuilder parse_questitems(int id) {
		StringBuilder result = new StringBuilder();
		
		List<OsaDbQuestitems> questitems_list = questsitemsService.listOsaDbQuestitemsByPagesid(id);
		for (OsaDbQuestitems item : questitems_list) {
			System.out.println("   -> " + item);
			
			parse_quests(item.getId());
		}

		return result;
	}
	
	public StringBuilder parse_quests(int id) {
		StringBuilder result = new StringBuilder();
		
		List<OsaDbQuests> quests_list = questsService.getOsaDbQuestsByQuestid(id);
		for (OsaDbQuests item : quests_list) {
			System.out.println("   +> " + item);
		}

		return result;
	}
}
