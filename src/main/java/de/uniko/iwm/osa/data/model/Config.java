package de.uniko.iwm.osa.data.model;

public class Config {

	// create table config (
	//  id integer identity primary key, 
	//  osaName varchar(200) not null, 
	//  jeeDatasource varchar(200) not null, 
	//  fileBasePath varchar(200) not null );

	private Integer id;
	private String osaName;
	private String jeeDatasource;
	private String fileBasePath;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOsaName() {
		return osaName;
	}
	public void setOsaName(String osaName) {
		this.osaName = osaName;
	}
	public String getJeeDatasource() {
		return jeeDatasource;
	}
	public void setJeeDatasource(String jeeDatasource) {
		this.jeeDatasource = jeeDatasource;
	}
	public String getFileBasePath() {
		return fileBasePath;
	}
	public void setFileBasePath(String fileBasePath) {
		this.fileBasePath = fileBasePath;
	}
	
	@Override
	public String toString() {
		return String.format("Config [%s] [%s] [%s] [%s]", id, osaName, jeeDatasource, fileBasePath);
	}
}
