package de.uniko.iwm.osa.data.model.osaitem;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author user
 *
 * reporting class
 * list of new item, deleted item und errors
 */
@XmlRootElement(name = "osaItem")
public class OsaItem {

	private Item itemNew = new Item();
	private Item itemDeleted = new Item();
	private List<String> errors = new ArrayList<String>();
 
	public OsaItem() {
	}
	
	// ------------------------------------------------------------------

	public void addNewPage(int id, String md5) {
		itemNew.addPage(id, md5);
	}
	
	public void updateFirstNewPage(String md5) {
		itemNew.updateFirstPage(md5);
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