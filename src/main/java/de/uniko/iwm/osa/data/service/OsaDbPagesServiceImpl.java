package de.uniko.iwm.osa.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.uniko.iwm.osa.data.dao.OsaDbPagesDAO;
import de.uniko.iwm.osa.data.model.OsaDbPages;

@Service
public class OsaDbPagesServiceImpl implements OsaDbPagesService {

	@Autowired
	private OsaDbPagesDAO pDAO;

	@Transactional
	public void addOsaDbPages(OsaDbPages p) {
		pDAO.addOsaDbPages(p);
	}

	@Transactional
	public List<OsaDbPages> listOsaDbPages() {

		return pDAO.listOsaDbPages();
	}

	@Transactional
	public void removeOsaDbPages(Integer id) {
		pDAO.removeOsaDbPages(id);
	}

	@Transactional
	public List<OsaDbPages> getOsaDbPagesById(Integer id) {
		return pDAO.getOsaDbPagesById(id);
	}

	@Transactional
	public List<OsaDbPages> getOsaDbPagesByPid(String pid) {
		return pDAO.getOsaDbPagesByPid(pid);
	}
	
	@Transactional
	public void storeOsaDbPages(OsaDbPages p) {
		pDAO.storeOsaDbPages(p);
	}
}
