package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class OsaPage {

	List<OsaItem> questionPages = new ArrayList<OsaItem>();
	List<OsaItem> extraPages = new ArrayList<OsaItem>();
	List<OsaItem> deletedPages = new ArrayList<OsaItem>();
	
	public List<OsaItem> getQuestionPages() {
		return questionPages;
	}

	public void setQuestionPages(ArrayList<OsaItem> questionPage) {
		this.questionPages = questionPage;
	}

	public void addQuestionPages(OsaItem item) {
		questionPages.add(item);
	}

	public List<OsaItem> getExtraPages() {
		return extraPages;
	}

	public void setExtraPages(List<OsaItem> extraPages) {
		this.extraPages = extraPages;
	}

	public void addExtraPages(OsaItem item) {
		extraPages.add(item);
	}
	
	public List<OsaItem> getDeletedPages() {
		return deletedPages;
	}

	public void setDeletedPages(List<OsaItem> deletedPages) {
		this.deletedPages = deletedPages;
	}
	
	public void addDeletedPages(OsaItem item) {
		deletedPages.add(item);
	}
	
	public class OsaItem {
		
		Integer pagesId;
		String pagesPid;
		String pagesName;
		
		List<Integer> questsId = new ArrayList<Integer>(); 
		List<String> questsQuestId = new ArrayList<String>(); 
		
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
}
