package de.uniko.iwm.osa.data.dao;

import java.util.List;

import de.uniko.iwm.osa.data.model.OsaDbQuestitems;

public interface OsaDbQuestitemsDAO {
	
	public void addOsaDbQuestitems(OsaDbQuestitems qi);
	public List<OsaDbQuestitems> listOsaDbQuestitems();
	public List<OsaDbQuestitems> listOsaDbQuestitemsById(Integer id);
	public List<OsaDbQuestitems> listOsaDbQuestitemsByPagesid(Integer pid);
	public void removeOsaDbQuestitems(Integer id);
}
