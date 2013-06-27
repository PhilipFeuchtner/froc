package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "osaItem")
public class OsaItem {

	@XmlElement
	private String message;
	
	@XmlElementWrapper(name="headers")
    @XmlElements({
    @XmlElement(name="item") }
    )
	private List<Item> headerList = new ArrayList<Item>();

	@XmlElementWrapper(name="pages")
    @XmlElements({
    @XmlElement(name="item") }
    )
	private List<Item> pageList = new ArrayList<Item>();

	public OsaItem() {
	}

	public OsaItem(String message) {
		this.message = message;
	}

	public void addHeader(String key, String value) {
		headerList.add(new Item(key, value));
	}	
	public void addPage(String key, String value) {
		pageList.add(new Item(key, value));
	}
	
	// ------------------------------------------------------------------
	
	public List<String> getNewPageListAsStrings() {
		
		List <String> result = new ArrayList<String>();
		for (Item item : pageList) {
			result.add(item.getName());
		}
		return result;
	}
	
	// ------------------------------------------------------------------
	
	public List<Item> getPageList() {
		return pageList;
	}

	public void setPageList(List<Item> pageList) {
		this.pageList = pageList;
	}

}