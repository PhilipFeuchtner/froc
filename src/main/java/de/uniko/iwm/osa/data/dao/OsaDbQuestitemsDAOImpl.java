package de.uniko.iwm.osa.data.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.uniko.iwm.osa.data.model.OsaDbQuestitems;

@Repository
public class OsaDbQuestitemsDAOImpl implements OsaDbQuestitemsDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addOsaDbQuestitems(OsaDbQuestitems qi) {
		sessionFactory.getCurrentSession().save(qi);
	}

	@SuppressWarnings("unchecked")
	public List<OsaDbQuestitems> listOsaDbQuestitems() {
		return sessionFactory.getCurrentSession().createQuery("from OsaDbQuestitems")				
				.list();
	}

	public void removeOsaDbQuestitems(Integer id) {
		OsaDbQuestitems qi = (OsaDbQuestitems) sessionFactory.getCurrentSession().load(
				OsaDbQuestitems.class, id);
		if (null != qi) {
			sessionFactory.getCurrentSession().delete(qi);
		}

	}

}
