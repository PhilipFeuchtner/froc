package de.uniko.iwm.osa.data.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "osaItem")
public class OsaItem {

	private String message;

	public OsaItem() {
	}

	public OsaItem(String message) {
		this.message = message;
	}

	@XmlElement
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}