package pwr.tin.tip.sw.pd.eu.db.dao;

import java.util.List;

import pwr.tin.tip.sw.pd.eu.db.IBaseDao;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;

public interface IAlgorithmDao extends IBaseDao {

	/**
	 * Pobiera listê algorytmów znajduj¹cych siê w kontenerze jednostki uruchomieniowej o wskazanym id
	 * 
	 * @param executiveUnitId id jednostki uruchomieniowej
	 * @return lista algorytmów za³adowanych do kontenera
	 */
	@SuppressWarnings("rawtypes")
	public List getListOfDeployedAlgorithms(Integer executiveUnitId);
	
	public void deleteAlgorithms(Integer executiveUnitId);
	
	public Algorithm getAlgorithmByAlgorithmId(Integer algorithmId, Integer executiveUnitId);
}
