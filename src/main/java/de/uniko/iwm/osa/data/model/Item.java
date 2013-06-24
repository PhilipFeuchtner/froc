package de.uniko.iwm.osa.data.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item")
public class Item {
	
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String value;

	public Item(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public Item() {
	}

	public void setName(String name) {
		this.name = name;
	}
}