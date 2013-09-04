package de.uniko.iwm.osa.data.model.osaitem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Page {
	int id;
	String md5;
	
	public Page() {
		id = 0;
		md5 = "";
	}

	public Page(int id) {
		this.id = id;
		md5 = null;
	}
	
	public Page(int id, String md5) {
		this.id = id;
		this.md5 = md5;
	}
	
	@XmlValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
}
