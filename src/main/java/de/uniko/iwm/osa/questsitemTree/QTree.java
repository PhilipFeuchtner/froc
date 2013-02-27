package de.uniko.iwm.osa.questsitemTree;

import java.io.StringWriter;

import de.uniko.iwm.osa.data.dao.OsaDbPagesDAO;
import de.uniko.iwm.osa.data.dao.OsaDbQuestitemsDAO;
import de.uniko.iwm.osa.data.dao.OsaDbQuestsDAO;
import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaDbQuests;

public class QTree {
	
	OsaDbPagesDAO pagesDao;
	OsaDbQuestitemsDAO questsitemsDao;
	OsaDbQuestsDAO questsDao;
	
	static final int MAGIC_START_PAGES = 37;
	static final int MAGIC_END_PAGES = 50;
	
	public QTree(OsaDbPagesDAO pagesDao, OsaDbQuestitemsDAO questsitemsDao, OsaDbQuestsDAO questsDao) {
		this.pagesDao = pagesDao;
		this.questsitemsDao = questsitemsDao;
		this.questsDao = questsDao;
	}
	
	public StringWriter toDot() {
		StringWriter result = new StringWriter();
		
		OsaDbPages pages;
		OsaDbQuestitems questsitems;
		OsaDbQuests quests;
		
		for (int p = MAGIC_START_PAGES; p <= MAGIC_END_PAGES; p++) {
			
		}
		return result;
	}
}
