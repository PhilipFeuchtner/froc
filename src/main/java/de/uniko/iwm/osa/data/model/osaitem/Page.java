package de.uniko.iwm.osa.data.model.osaitem;

public class Page {
	int id;
	String md5;

	public Page(int id) {
		this.id = id;
		md5 = "";
	}
	
	public Page(int id, String md5) {
		this.id = id;
		this.md5 = md5;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
}
