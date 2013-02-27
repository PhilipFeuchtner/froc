package de.uniko.iwm.osa.data.dao;

import java.util.List;

import org.hibernate.Query;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<OsaDbQuestitems> listOsaDbQuestitemsById(Integer id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from OsaDbQuestitems p where p.id=?");
		query.setInteger(0, id);

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OsaDbQuestitems> listOsaDbQuestitemsByPagesid(Integer pid) {
		Query query = sessionFactory.getCurrentSession().createQuery("from OsaDbQuestitems p where pagesid p.pagesid=?");			
		query.setInteger(0, pid);

		return query.list();
	}
}
