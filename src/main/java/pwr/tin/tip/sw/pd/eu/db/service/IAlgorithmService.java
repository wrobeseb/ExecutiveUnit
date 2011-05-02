package pwr.tin.tip.sw.pd.eu.db.service;

import java.util.Hashtable;

import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;

public interface IAlgorithmService {

	/**
	 * £aduje algorytmy do przestrzeni centralnej
	 * 
	 * @param algorithmSpace przestrzeñ centralna dla algorytmów
	 */
	public void loadAlgorithmIntoSpace(Hashtable<Integer, Algorithm> algorithmSpace);
	
	public void saveAlgorithm(Algorithm algorithm);
	
	public void deleteAlgorithms();
	
	public Algorithm getAlgorithmByAlgorithmId(Integer algorithmId);
}
