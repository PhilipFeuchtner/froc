package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author user
 *
 * wrapper class
 */
public class Cy_QuestItem {
	
	OsaDbQuestitems questitem = null;
	List<OsaDbQuests> questsList = new ArrayList<OsaDbQuests>();
	
	public Cy_QuestItem() {
	}
	
	public Cy_QuestItem(OsaDbQuestitems item) {
		this.questitem = item;
	}
	
	public OsaDbQuestitems getQuestitem() {
		return questitem;
	}
	public void setQuestitem(OsaDbQuestitems item) {
		this.questitem = item;
	}
	public List<OsaDbQuests> getQuestsList() {
		return questsList;
	}
	public void setQuestsList(List<OsaDbQuests> itemList) {
		this.questsList = itemList;
	}
	
	// ----------------------------------------------------------
	
	public void addQuest(OsaDbQuests quest) {
		questsList.add(quest);
	}

}
