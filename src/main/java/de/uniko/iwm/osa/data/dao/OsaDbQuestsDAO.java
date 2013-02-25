package de.uniko.iwm.osa.data.dao;

import java.util.List;

import de.uniko.iwm.osa.data.model.OsaDbQuests;


public interface OsaDbQuestsDAO {
	
	public void addOsaDbQuests(OsaDbQuests q);
	public List<OsaDbQuests> listOsaDbQuests();
	public void removeOsaDbQuests(Integer id);
	public List<OsaDbQuests> getOsaDbQuestsById(Integer id);
	public void storeOsaDbQuests(OsaDbQuests q);
}
