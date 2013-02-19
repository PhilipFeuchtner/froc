package de.uniko.iwm.osa.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.uniko.iwm.osa.data.dao.OsaDbQuestitemsDAO;
import de.uniko.iwm.osa.data.form.OsaDbQuestitems;


@Service
public class OsaDbQuestitemsServiceImpl implements OsaBdQuestitemsService {

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
	public void removeOsaDbQuestitems(Integer id) {
		qiDAO.removeOsaDbQuestitems(id);
	}
}
