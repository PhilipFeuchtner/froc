package de.uniko.iwm.osa.data.dao;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.uniko.iwm.osa.data.model.OsaDbQuests;

@Repository
public class OsaDbQuestsDAOImpl implements OsaDbQuestsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void addOsaDbQuests(OsaDbQuests q) {
		sessionFactory.getCurrentSession().save(q);
	}

	@SuppressWarnings("unchecked")
	public List<OsaDbQuests> listOsaDbQuests() {

		return sessionFactory.getCurrentSession().createQuery("from OsaDbQuests")
				.list();
	}

	public void removeOsaDbQuests(Integer id) {
		OsaDbQuests q = (OsaDbQuests) sessionFactory.getCurrentSession().load(
				OsaDbQuests.class, id);
		if (null != q) {
			sessionFactory.getCurrentSession().delete(q);
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<OsaDbQuests> getOsaDbQuestsById(Integer id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from OsaDbQuests p where p.id=?");
		query.setInteger(0, id);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<OsaDbQuests> getOsaDbQuestsByQuestid(Integer id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from OsaDbQuests p where p.questid=? order by p.position");
		query.setInteger(0, id);
		return query.list();
	}
	
	public void storeOsaDbQuests(OsaDbQuests q) {
		sessionFactory.getCurrentSession().saveOrUpdate(q);
		
	}
}
