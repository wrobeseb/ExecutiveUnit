package pwr.tin.tip.sw.pd.eu.db;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

import pwr.tin.tip.sw.pd.eu.BaseTest;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.db.service.IAlgorithmService;
import pwr.tin.tip.sw.pd.eu.db.service.IJobService;
import pwr.tin.tip.sw.pd.eu.utils.DateUtils;

public class JobTest extends BaseTest {
	
	@Autowired IJobService jobService;
	
	@Autowired IAlgorithmService algorithmService;
	
	@Test
	public void saveJob() {
		
		Algorithm algorithm = new Algorithm();
		algorithm.setName("algorithmTest");
		algorithm.setAppName("algorithmTest");
		algorithm.setRunPath("/algorithmTest/app/algorithm.exe");
		
		algorithmService.saveAlgorithm(algorithm);
		
		Job job = new Job();
		job.setAlgorithmPID(1111);
		job.setAlgorithm(algorithm);
		job.setSessionId(1);
		job.setInputMessageArrivalDt(DateUtils.getDateWithTimeAsDate());
		job.setInputMessageBody("sfssfsf");
		
		jobService.saveStartedJob(job);
		
		Job job1 = jobService.getJobById(0);
		
		assertNotNull(job1);
	}
}
