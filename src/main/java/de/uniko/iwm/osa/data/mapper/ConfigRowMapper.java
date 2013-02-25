package de.uniko.iwm.osa.data.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

@SuppressWarnings("rawtypes")
public class ConfigRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
	    ConfigResultSetExtractor extractor = new ConfigResultSetExtractor();
	    return extractor.extractData(rs);
	  }
}
