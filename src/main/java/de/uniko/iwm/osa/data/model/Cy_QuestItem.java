package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class Cy_QuestItem {
	
	OsaDbQuests quest = null;
	List<OsaDbQuestitems> itemList = new ArrayList<OsaDbQuestitems>();
	
	public Cy_QuestItem() {
	}
	
	public Cy_QuestItem(OsaDbQuests quest) {
		this.quest = quest;
	}
	
	public OsaDbQuests getQuest() {
		return quest;
	}
	public void setQuest(OsaDbQuests quest) {
		this.quest = quest;
	}
	public List<OsaDbQuestitems> getItemList() {
		return itemList;
	}
	public void setItemList(List<OsaDbQuestitems> itemList) {
		this.itemList = itemList;
	}
	
	// ----------------------------------------------------------
	
	public void addItem(OsaDbQuestitems item) {
		itemList.add(item);
	}

}
