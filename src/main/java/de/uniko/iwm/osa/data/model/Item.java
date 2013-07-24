package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author user
 * 
 * wrapperclass
 */
@XmlRootElement(name = "Item")
public class Item {

	private List<Integer> pagesList = new ArrayList<Integer>();
	private List<Integer> questsList = new ArrayList<Integer>();
	private List<Integer> questitemList = new ArrayList<Integer>();

	public Item() {
	}

	@XmlElement(name = "pages")
	public List<Integer> getPagesList() {
		return pagesList;
	}

	@XmlElement(name = "quests")
	public List<Integer> getQuestsList() {
		return questsList;
	}

	@XmlElement(name = "questsitems")
	public List<Integer> getQuestitemList() {
		return questitemList;
	}
	
	public void setPageList(List<Integer> pageList) {
		this.pagesList = pageList;
	}

	public void setQuestsList(List<Integer> questsList) {
		this.questsList = questsList;
	}

	public void setQuestitemList(List<Integer> questitemList) {
		this.questitemList = questitemList;
	}
	
	// ---------------------------------------------------------
	
	public void addPage(Integer id) {
		pagesList.add(id);
	}
	
	public void addQuest(Integer id) {
		questsList.add(id);
	}
	
	public void addQuestitem(Integer id) {
		questitemList.add(id);
	}
}