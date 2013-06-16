package de.uniko.iwm.osa.questsitemTree;

import java.util.HashMap;

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
	
	public StringBuilder toDot(int startPage) {
		StringBuilder result = new StringBuilder();

		//
		// parse pages
		//

		int page = startPage; 
		boolean hasNextPage = true;
		
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
			// is quest?, end reached?
			// 
			
			List<OsaDbQuestitems> questitems_list = questsitemsService.listOsaDbQuestitemsByPagesid(page);
			hasNextPage = questitems_list.size() != 0;
			
			// System.err.println("Next " + hasNextPage);

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
