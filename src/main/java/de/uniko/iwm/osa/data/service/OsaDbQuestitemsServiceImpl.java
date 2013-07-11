package de.uniko.iwm.osa.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.uniko.iwm.osa.data.dao.OsaDbQuestitemsDAO;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;

@Service
public class OsaDbQuestitemsServiceImpl implements OsaDbQuestitemsService {

	@Autowired
	private OsaDbQuestitemsDAO qiDAO;

	@Transactional
	public void addOsaDbQuestitems(OsaDbQuestitems qi) {
		qiDAO.addOsaDbQuestitems(qi);
	}

	@Transactional
	public List<OsaDbQuestitems> listOsaDbQuestitems() {

		return qiDAO.listOsaDbQuestitems();
	}

	@Transactional
	public List<OsaDbQuestitems> listOsaDbQuestitemsById(Integer id) {

		return qiDAO.listOsaDbQuestitemsById(id);
	}

	@Transactional
	public List<OsaDbQuestitems> listOsaDbQuestitemsByPagesid(Integer id) {

		return qiDAO.listOsaDbQuestitemsByPagesid(id);
	}

	@Transactional
	public void removeOsaDbQuestitems(Integer id) {
		qiDAO.removeOsaDbQuestitems(id);
	}

	@Transactional
	public void storeOsaDbQuestitems(OsaDbQuestitems qi) {
		qiDAO.storeOsaDbQuestitems(qi);
	}
}
