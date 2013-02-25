package de.uniko.iwm.osa.data.dao;

import java.util.List;

import javax.sql.DataSource;

import de.uniko.iwm.osa.data.model.Config;

public interface ConfigDAO {
		  void setDataSource(DataSource ds);
		  List<Config> select(String osaName);
		  List<Config> selectAll();
}
