package de.uniko.iwm.osa.data.model.osaitem;

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

	private List<Page> pagesList = new ArrayList<Page>();
	private List<Integer> questsList = new ArrayList<Integer>();
	private List<Integer> questitemList = new ArrayList<Integer>();

	public Item() {
	}

	@XmlElement(name = "pages")
	public List<Page> getPagesList() {
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
	
	public void setPageList(List<Page> pageList) {
		this.pagesList = pageList;
	}

	public void setQuestsList(List<Integer> questsList) {
		this.questsList = questsList;
	}

	public void setQuestitemList(List<Integer> questitemList) {
		this.questitemList = questitemList;
	}
	
	// ---------------------------------------------------------
	
	public void addPage(int id) {
		pagesList.add(new Page(id));
	}
	
	public void addPage(int id, String md5) {
		pagesList.add(new Page(id, md5));
	}
	
	public void updateFirstPage(String md5) {
		Page p = pagesList.get(0);
		p.setMd5(md5);
	}
	
	public void addQuest(Integer id) {
		questsList.add(id);
	}
	
	public void addQuestitem(Integer id) {
		questitemList.add(id);
	}
}