package de.uniko.iwm.osa.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.uniko.iwm.osa.data.form.OsaDbPages;

@Repository
public class OsaDbPagesDAOImpl implements OsaDbPagesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void addOsaDbPages(OsaDbPages p) {
		sessionFactory.getCurrentSession().save(p);
	}

	public List<OsaDbPages> listOsaDbPages() {

		List<OsaDbPages> list = (List<OsaDbPages>) sessionFactory.getCurrentSession().createQuery("from OsaDbPages")
				.list();
		
//		// -------------------------
//		List<Book> result = hibernateTemplate.execute(new HibernateCallback<List<Book>>() {
//		    public List<Book> doInHibernate(Session session) throws HibernateException, SQLException {
//		        Query query = session.createQuery("SELECT DISTINCT b FROM Book as b LEFT JOIN FETCH b.authors");
//
//		        List list = query.list();
//
//		        return list;
//		    }
//		});
//		// -------------------------
		
		return list;
	}

	public void removeOsaDbPages(Integer id) {
		OsaDbPages q = (OsaDbPages) sessionFactory.getCurrentSession().load(
				OsaDbPages.class, id);
		if (null != q) {
			sessionFactory.getCurrentSession().delete(q);
		}

	}

	public List<OsaDbPages> getOsaDbPagesById(Integer id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from OsaDbPages p where p.id=?");
		query.setInteger(0, id);
		return query.list();
	}
}
