package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

public class Cy_PageItem {

	OsaDbPages page = null;
	List<Cy_QuestItem> questItemList = new ArrayList<Cy_QuestItem>();

	public Cy_PageItem() {
	}

	public Cy_PageItem(OsaDbPages page) {
		this.page = page;
	}

	public OsaDbPages getPage() {
		return page;
	}

	public void setPage(OsaDbPages page) {
		this.page = page;
	}

	public List<Cy_QuestItem> getCy_QuestItem() {
		return questItemList;
	}

	public void setCy_QuestItem(List<Cy_QuestItem> quests) {
		questItemList = quests;
	}

	// ----------------------------------------------------------------------

	public void addQuest(Cy_QuestItem item) {
		questItemList.add(item);
	}

}
