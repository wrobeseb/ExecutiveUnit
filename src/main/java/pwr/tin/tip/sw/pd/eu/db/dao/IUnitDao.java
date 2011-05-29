package pwr.tin.tip.sw.pd.eu.db.dao;

public interface IUnitDao {
	
	public void save(Object obj);
	
	public void setOverload(Integer id);
	public void setFree(Integer id);
	public void ping(Integer id);
	public void removeUnit(Integer id);
}
