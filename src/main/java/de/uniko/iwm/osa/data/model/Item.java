package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item")
public class Item {

	private List<Integer> pageList = new ArrayList<Integer>();
	private List<Integer> questsList = new ArrayList<Integer>();
	private List<Integer> questitemList = new ArrayList<Integer>();

	public Item() {
	}

	@XmlElement(name = "pages")
	public List<Integer> getPageList() {
		return pageList;
	}

	@XmlElement(name = "quests")
	public List<Integer> getQuestsList() {
		return questsList;
	}

	@XmlElement(name = "questsitems")
	public List<Integer> getQuestItemList() {
		return questitemList;
	}
	
	// ---------------------------------------------------------
	
	public void addPage(Integer id) {
		pageList.add(id);
	}
	
	public void addQuests(Integer id) {
		questsList.add(id);
	}
	
	public void addQuestitem(Integer id) {
		questitemList.add(id);
	}
}