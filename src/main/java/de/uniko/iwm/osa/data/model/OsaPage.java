package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class OsaPage {

	private List<OsaItem> questionPages = new ArrayList<OsaItem>();
	private List<OsaItem> extraPages = new ArrayList<OsaItem>();
	private List<OsaItem> deletedPages = new ArrayList<OsaItem>();
	
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
}
