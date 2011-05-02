package pwr.tin.tip.sw.pd.eu.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pwr.tin.tip.sw.pd.eu.db.dao.IJobDao;
import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.db.service.IAlgorithmService;
import pwr.tin.tip.sw.pd.eu.db.service.IJobService;

@Service(value="jobService")
public class JobServiceImpl implements IJobService {

	@Autowired(required=true)
	private IJobDao jobDao;
	
	@Autowired(required=true)
	private IAlgorithmService algorithmService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveStartedJob(Job job) {
		job.setAlgorithm(algorithmService.getAlgorithmByAlgorithmId(job.getAlgorithm().getAlgorithmId()));
		jobDao.save(job);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveEndedJob(Job job) {
		jobDao.update(job);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Job getJobById(Integer id) {
		return jobDao.getJobById(id);
	}
}
