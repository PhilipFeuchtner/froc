package de.uniko.iwm.osa.data.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadItem {
	private String name;
	private CommonsMultipartFile fileData;

	List<String> osaList = new ArrayList<String>();
	String pagesid;

	public String getPagesid() {
		return pagesid;
	}

	public void setPagesid(String pagesid) {
		this.pagesid = pagesid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	// -----------------------------------------------------------------------

	public List<String> getOsaList() {
		return osaList;
	}

	public void setOsaList(List<String> osaList) {
		this.osaList = osaList;
	}

}