package pwr.tin.tip.sw.pd.eu.db.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import pwr.tin.tip.sw.pd.eu.db.dao.IUnitDao;
import pwr.tin.tip.sw.pd.eu.db.model.Unit;
import pwr.tin.tip.sw.pd.eu.db.utils.DateTime;

@Repository(value="unitDao")
public class UnitDaoImpl extends HibernateDaoSupport implements IUnitDao {

	@Autowired(required=true)
	public UnitDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void save(Object obj) {
		Transaction transaction = getSession().beginTransaction();
		Unit unit = (Unit)obj;
		SQLQuery query = getSession().createSQLQuery("INSERT INTO unit (id, id_unit, address_ip, last_update_dt, max_process_no, overload_flg, type) VALUES (nextval ('unit_seq'),?,?,?,?,?,?)");
		query.setInteger(0, unit.getIdUnit());
		query.setString(1, unit.getAddressIp());
		query.setDate(2, unit.getLastUpdateDt());
		query.setInteger(3, unit.getMaxProcessNo());
		query.setBoolean(4, unit.getOverloadFlg());
		query.setInteger(5, unit.getType().ordinal());
		query.executeUpdate();
		transaction.commit();
	}

	@Override
	public void setOverload(Integer id) {
		SQLQuery query = getSession().createSQLQuery("UPDATE unit SET overload_flg = true WHERE id_unit = ? AND type = 1");
		query.setInteger(0, id);
		query.executeUpdate();
	}

	@Override
	public void setFree(Integer id) {
		SQLQuery query = getSession().createSQLQuery("UPDATE unit SET overload_flg = false WHERE id_unit = ? AND type = 1");
		query.setInteger(0, id);
		query.executeUpdate();
	}

	@Override
	public void ping(Integer id) {
		SQLQuery query = getSession().createSQLQuery("UPDATE unit SET last_update_dt = ? WHERE id_unit = ? AND type = 1");
		query.setDate(0, DateTime.now());
		query.setInteger(1, id);
		query.executeUpdate();
	}

	@Override
	public void removeUnit(Integer id) {
		SQLQuery query = getSession().createSQLQuery("DELETE FROM unit WHERE id_unit = ? AND type = 1");
		query.setInteger(0, id);
		query.executeUpdate();
	}

}
