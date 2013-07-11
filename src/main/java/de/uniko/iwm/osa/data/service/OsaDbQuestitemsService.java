package de.uniko.iwm.osa.data.service;

import java.util.List;

import de.uniko.iwm.osa.data.model.OsaDbQuestitems;

public interface OsaDbQuestitemsService {

	public void addOsaDbQuestitems(OsaDbQuestitems qi);

	public List<OsaDbQuestitems> listOsaDbQuestitems();

	public List<OsaDbQuestitems> listOsaDbQuestitemsById(Integer id);

	public List<OsaDbQuestitems> listOsaDbQuestitemsByPagesid(Integer pid);

	public void removeOsaDbQuestitems(Integer id);

	public void storeOsaDbQuestitems(OsaDbQuestitems qi);
}
