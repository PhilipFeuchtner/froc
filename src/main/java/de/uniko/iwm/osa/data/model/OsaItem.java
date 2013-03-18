package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class OsaItem {
	
	private Integer pagesId;
	private String pagesPid;
	private String pagesName;
	
	private List<Integer> questsId = new ArrayList<Integer>(); 
	private List<String> questsQuestId = new ArrayList<String>(); 
	
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
	
	public List<Integer> getQuestsId() {
		return questsId;
	}
	public void setQuestsId(List<Integer> questsId) {
		this.questsId = questsId;
	}
	public void addQuestsId(Integer id) {
		questsId.add(id);
	}
	public List<String> getQuestsQuestId() {
		return questsQuestId;
	}
	public void setQuestsQuestId(List<String> questsQuestId) {
		this.questsQuestId = questsQuestId;
	}
	public void addQuestsQuestId(String questId) {
		questsQuestId.add(questId);
	}
	
}