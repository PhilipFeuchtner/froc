package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class OsaItem {
	
	private Integer pagesId;
	private String pagesPid;
	private String pagesName;
	
	private List<Integer> questsNewId = new ArrayList<Integer>(); 
	private List<Integer> questsOldId = new ArrayList<Integer>(); 
	private List<Integer> questsQuestId = new ArrayList<Integer>(); 
	
	public Integer getPagesId() {
		return pagesId;
	}
	public void setPagesId(Integer pagesId) {
		this.pagesId = pagesId;
	}
	public String getPagesPid() {
		return pagesPid;
	}
	public void setPagesPid(String pagesPid) {
		this.pagesPid = pagesPid;
	}
	public String getPagesName() {
		return pagesName;
	}
	public void setPagesName(String pagesName) {
		this.pagesName = pagesName;
	}
	
	/* ----------------------- */
	
	public List<Integer> getQuestsNewId() {
		return questsNewId;
	}
	public void setQuestsNewId(List<Integer> questsId) {
		this.questsNewId = questsId;
	}
	public void addQuestsNewId(Integer id) {
		questsNewId.add(id);
	}
	public List<Integer> getQuestsOldId() {
		return questsOldId;
	}
	public void setQuestsOldId(List<Integer> questsId) {
		this.questsOldId = questsId;
	}
	public void addQuestsOldId(Integer id) {
		questsOldId.add(id);
	}
	public List<Integer> getQuestsQuestId() {
		return questsQuestId;
	}
	public void setQuestsQuestId(List<Integer> questsQuestId) {
		this.questsQuestId = questsQuestId;
	}
	public void addQuestsQuestId(Integer questId) {
		questsQuestId.add(questId);
	}
}