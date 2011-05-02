package pwr.tin.tip.sw.pd.eu.db;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseDao extends HibernateDaoSupport {

	public BaseDao(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	public final void save(Object obj) {
		getSession().persist(obj);
	}
	
	public final void update(Object obj) {
		getSession().merge(obj);
	}
	
	public final void delete(Object obj) {
		getSession().delete(obj);
	}
} 
