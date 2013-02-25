package de.uniko.iwm.osa.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.uniko.iwm.osa.data.model.OsaDbPages;

@Repository
public class OsaDbPagesDAOImpl implements OsaDbPagesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void addOsaDbPages(OsaDbPages p) {
		sessionFactory.getCurrentSession().save(p);
	}

	@SuppressWarnings("unchecked")
	public List<OsaDbPages> listOsaDbPages() {

		return (List<OsaDbPages>) sessionFactory.getCurrentSession().createQuery("from OsaDbPages")
				.list();
	}

	public void removeOsaDbPages(Integer id) {
		OsaDbPages q = (OsaDbPages) sessionFactory.getCurrentSession().load(
				OsaDbPages.class, id);
		if (null != q) {
			sessionFactory.getCurrentSession().delete(q);
		}
	}

	@SuppressWarnings("unchecked")
	public List<OsaDbPages> getOsaDbPagesById(Integer id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from OsaDbPages p where p.id=?");
		query.setInteger(0, id);
		return query.list();
	}
}
