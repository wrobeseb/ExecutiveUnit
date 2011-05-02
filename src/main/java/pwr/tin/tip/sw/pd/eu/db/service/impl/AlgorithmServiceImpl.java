package pwr.tin.tip.sw.pd.eu.db.service.impl;

import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pwr.tin.tip.sw.pd.eu.db.dao.IAlgorithmDao;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.db.service.IAlgorithmService;

@Service(value="algorithmService")
public class AlgorithmServiceImpl implements IAlgorithmService {
	
	@Autowired
	private IAlgorithmDao algorithmDao;

	@Value(value="${executive.unit.id}")
	private Integer executiveUnitId;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public void loadAlgorithmIntoSpace(Hashtable<Integer, Algorithm> algorithmSpace) {
		algorithmDao.getListOfDeployedAlgorithms(executiveUnitId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveAlgorithm(Algorithm algorithm) {
		algorithm.setExecutiveUnitId(executiveUnitId);
		algorithmDao.save(algorithm);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Algorithm getAlgorithmByAlgorithmId(Integer algorithmId) {
		return algorithmDao.getAlgorithmByAlgorithmId(algorithmId, executiveUnitId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteAlgorithms() {
		algorithmDao.deleteAlgorithms(executiveUnitId);
	}
}
