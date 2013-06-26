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
	private List<Item> headerList;

	@XmlElementWrapper(name="pages")
    @XmlElements({
    @XmlElement(name="item") }
    )
	private List<Item> pageList;

	public OsaItem() {
	}

	public OsaItem(String message) {
		this.message = message;
		headerList = new ArrayList<Item>();
		pageList = new ArrayList<Item>();
	}
	
	public void addHeader(String key, String value) {
		headerList.add(new Item(key, value));
	}	
}