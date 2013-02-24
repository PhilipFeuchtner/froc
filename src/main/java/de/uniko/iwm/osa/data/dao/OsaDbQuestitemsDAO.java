package de.uniko.iwm.osa.data.dao;

import java.util.List;

import de.uniko.iwm.osa.data.form.OsaDbQuestitems;

public interface OsaDbQuestitemsDAO {
	
	public void addOsaDbQuestitems(OsaDbQuestitems qi);
	public List<OsaDbQuestitems> listOsaDbQuestitems();
	public void removeOsaDbQuestitems(Integer id);
}