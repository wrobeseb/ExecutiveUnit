package pwr.tin.tip.sw.pd.eu.db.dao;

import pwr.tin.tip.sw.pd.eu.db.IBaseDao;
import pwr.tin.tip.sw.pd.eu.db.model.Job;

public interface IJobDao extends IBaseDao {

	public Job getJobById(Integer id);
}
