package pwr.tin.tip.sw.pd.eu.db.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pwr.tin.tip.sw.pd.eu.db.BaseDao;
import pwr.tin.tip.sw.pd.eu.db.dao.IAlgorithmDao;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;

@Repository(value="algorithmDao")
public class AlgorithmDao extends BaseDao implements IAlgorithmDao {

	public @Autowired(required=true) AlgorithmDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListOfDeployedAlgorithms(Integer executiveUnitId) {
		return null;
	}
	
	@Override
	public Algorithm getAlgorithmByAlgorithmId(Integer algorithmId, Integer executiveUnitId) {
		return (Algorithm) getSession().createCriteria(Algorithm.class)
									   .add(Restrictions.eq("algorithmId", algorithmId))
									   .add(Restrictions.eq("executiveUnitId", executiveUnitId))
									   .uniqueResult();
	}

	@Override
	public void deleteAlgorithms(Integer executiveUnitId) {
		SQLQuery query = getSession().createSQLQuery("DELETE FROM eu_algorithm WHERE executive_unit_id = :executiveUnitId");
		query.setInteger("executiveUnitId", executiveUnitId);
		query.executeUpdate();
	}
}
