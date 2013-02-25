package de.uniko.iwm.osa.data.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import de.uniko.iwm.osa.data.model.Config;

public class ConfigResultSetExtractor implements ResultSetExtractor {

	@Override
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
	    return new Config(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
	}
}
