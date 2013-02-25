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
	    Config c = new Config();
	    c.setId(rs.getInt(1));
	    c.setOsaName(rs.getString(2));
	    c.setJeeDatasource(rs.getString(3));
	    c.setFileBasePath(rs.getString(4));
	    return c;
	}

}
