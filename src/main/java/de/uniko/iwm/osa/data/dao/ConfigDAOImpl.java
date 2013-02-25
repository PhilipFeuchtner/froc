package de.uniko.iwm.osa.data.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import de.uniko.iwm.osa.data.mapper.ConfigRowMapper;
import de.uniko.iwm.osa.data.model.Config;

public class ConfigDAOImpl implements ConfigDAO {

	private DataSource osaConfiguration;

	@Override
	public void setDataSource(DataSource ds) {
		this.osaConfiguration = ds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Config> select(String osaName) {
		   JdbcTemplate select = new JdbcTemplate(osaConfiguration);
		    return select
		        .query("select  ID, OSANAME, JEEDATASOURCE, FILEBASEPATH from CONFIG where OSANAME = ?",
		            new Object[] { osaName },
		            new ConfigRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Config> selectAll() {
		   JdbcTemplate select = new JdbcTemplate(osaConfiguration);
		    return select
		        .query("select  ID, OSANAME, JEEDATASOURCE, FILEBASEPATH from CONFIG",
		            new ConfigRowMapper());
	}
}
