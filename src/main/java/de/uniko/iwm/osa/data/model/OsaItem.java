package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "osaItem")
public class OsaItem {

	private Item itemNew = new Item();
	private Item itemDeleted = new Item();
	private List<String> errors = new ArrayList<String>();
 
	public OsaItem() {
	}
	
	// ------------------------------------------------------------------

	public void addNewPage(Integer id) {
		itemNew.addPage(id);
	}
	
	public void addNewQuest(Integer id) {
		itemNew.addQuest(id);
	}
	
	public void addNewQuestitem(Integer id) {
		itemNew.addQuestitem(id);
	}
	
	public void addDeletedPage(Integer id) {
		itemDeleted.addPage(id);
	}
	
	public void addDeletedQuest(Integer id) {
		itemDeleted.addQuest(id);
	}
	
	public void addDeletedQuestitem(Integer id) {
		itemDeleted.addQuestitem(id);
	}
	
	public void addErrorEntry(String text) {
		errors.add(text);
	}
		
	// ------------------------------------------------------------------

    @XmlElement(name="new")
  	public Item getItemNew() {
		return itemNew;
	}

    @XmlElement(name="deleted")
	public Item getItemDeleted() {
		return itemDeleted;
	}

    @XmlElement(name="error")
	public List<String> getErrors() {
		return errors;
	}
}