package pwr.tin.tip.sw.pd.eu.db.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pwr.tin.tip.sw.pd.eu.db.BaseDao;
import pwr.tin.tip.sw.pd.eu.db.dao.IJobDao;
import pwr.tin.tip.sw.pd.eu.db.model.Job;

@Repository(value="jobDao")
public class JobDaoImpl extends BaseDao implements IJobDao {

	public @Autowired(required=true) JobDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Job getJobById(Integer id) {
		return (Job) getSession().get(Job.class, id);
	}
}
