package de.uniko.iwm.osa.data.dao;

import java.util.List;

import de.uniko.iwm.osa.data.model.OsaDbPages;

public interface OsaDbPagesDAO {

	public void addOsaDbPages(OsaDbPages p);

	public List<OsaDbPages> listOsaDbPages();

	public void removeOsaDbPages(Integer id);

	public List<OsaDbPages> getOsaDbPagesById(Integer id);

	public void storeOsaDbPages(OsaDbPages p);
}
