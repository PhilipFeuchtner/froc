package de.uniko.iwm.osa.data.service;

import java.util.List;

import de.uniko.iwm.osa.data.model.OsaDbPages;

public interface OsaDbPagesService {

	public void addOsaDbPages(OsaDbPages p);

	public List<OsaDbPages> listOsaDbPages();

	public void removeOsaDbPages(Integer id);

	public List<OsaDbPages> getOsaDbPagesById(Integer id);

	public List<OsaDbPages> getOsaDbPagesByPid(String pid);
	
	public void storeOsaDbPages(OsaDbPages p);
}
