package de.uniko.iwm.osa.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.uniko.iwm.osa.data.dao.OsaDbQuestsDAO;
import de.uniko.iwm.osa.data.form.OsaDbQuests;

@Service
public class OsaDbQuestsServiceImpl implements OsaDbQuestsService {

	@Autowired
	private OsaDbQuestsDAO qDAO;
	
	@Transactional
	public void addOsaDbQuests(OsaDbQuests q) {
		qDAO.addOsaDbQuests(q);
	}

	@Transactional
	public List<OsaDbQuests> listOsaDbQuests() {

		return qDAO.listOsaDbQuests();
	}

	@Transactional
	public void removeOsaDbQuests(Integer id) {
		qDAO.removeOsaDbQuests(id);
	}

	@Transactional
	public List<OsaDbQuests> getOsaDbQuestsById(Integer id) {
		return qDAO.getOsaDbQuestsById(id);
	}

	@Transactional
	public void storeOsaDbQuests(OsaDbQuests q) {
		qDAO.storeOsaDbQuests(q);
	}
}
