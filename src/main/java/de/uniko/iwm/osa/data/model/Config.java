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
	
	public Config(Integer id, String osaName, String jeeDatasource, String fileBasePath) {
		this.id = id;
		this.osaName = osaName;
		this.jeeDatasource = jeeDatasource;
		this.fileBasePath = fileBasePath;
	}
	
	public Integer getId() {
		return id;
	}

	public String getOsaName() {
		return osaName;
	}

	public String getJeeDatasource() {
		return jeeDatasource;
	}

	public String getFileBasePath() {
		return fileBasePath;
	}
	
	@Override
	public String toString() {
		return String.format("Config [%s] [%s] [%s] [%s]", id, osaName, jeeDatasource, fileBasePath);
	}
}
