package de.uniko.iwm.osa.data.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



public class UploadItem {
	private String name;
	private CommonsMultipartFile fileData;
	
	List<String> osaList = new ArrayList<String>();

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
	
	public void  generateOsaList(String fileBase) {
		File baseDir = new File(fileBase);
		File[] fileList = baseDir.listFiles();
		
		osaList = new ArrayList<String>();
		for (int i=0; i<fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				String name=fileList[i].getName(); 
				osaList.add(name);
				System.out.println("osa found: " + name);
			}
		}
	}
	
	public List<String> getOsaList() {
		return osaList;
	}

	public void setOsaList(ArrayList<String> osaList) {
		this.osaList = osaList;
	}

}