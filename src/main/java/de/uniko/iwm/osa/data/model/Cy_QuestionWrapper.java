package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author user
 * 
 *         wrapper class
 *         cyquest db-structure
 * 
 */
public class Cy_QuestionWrapper {

	static Logger log = Logger.getLogger(Cy_QuestionWrapper.class.getName());

	OsaDbPages page = null;
	OsaDbQuestitems questsitem = null;

	List<OsaDbQuests> questList = new ArrayList<OsaDbQuests>();

	public Cy_QuestionWrapper(PagesQuestitemsQuestsMisc pqiqm) {
		page = pqiqm.getPages();
		questsitem = pqiqm.getQuestitems();
	}

	public OsaDbPages getPage() {
		return page;
	}

	public OsaDbQuestitems getOsaDbQuestitems() {
		return questsitem;
	}

	public List<OsaDbQuests> getOsaDbQuest() {
		return questList;
	}
	
	public void addQuest(OsaDbQuests q) {
		questList.add(q);
	}
}
