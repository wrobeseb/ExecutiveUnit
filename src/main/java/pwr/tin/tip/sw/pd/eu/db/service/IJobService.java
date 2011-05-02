package pwr.tin.tip.sw.pd.eu.db.service;

import pwr.tin.tip.sw.pd.eu.db.model.Job;

public interface IJobService {

	public void saveStartedJob(Job job);
	
	public void saveEndedJob(Job job);
	
	public Job getJobById(Integer id);
}
