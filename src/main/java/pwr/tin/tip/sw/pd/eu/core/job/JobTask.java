package pwr.tin.tip.sw.pd.eu.core.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pwr.tin.tip.sw.pd.eu.core.container.RootContainer;
import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.jms.core.DefaultMessageSender;
import pwr.tin.tip.sw.pd.eu.jms.model.enums.Status;
import pwr.tin.tip.sw.pd.eu.utils.DateUtils;

public class JobTask implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(JobTask.class);
	
	private Job job;
	private DefaultMessageSender defaultMessageSender;
	private RootContainer rootContainer;
	private String runtimeDirectory;
	
	public JobTask(Job job, DefaultMessageSender defaultMessageSender, RootContainer rootContainer, String runtimeDirectory) {
		this.job = job;
		this.rootContainer = rootContainer;
		this.defaultMessageSender = defaultMessageSender;
		this.runtimeDirectory = runtimeDirectory;
	}
	
	@Override
	public void run() {
		try {
			String runPath = runtimeDirectory + (job.getAlgorithm().getRunPath().startsWith("/") ? job.getAlgorithm().getRunPath() : "/" + job.getAlgorithm().getRunPath());
			
			String[] cmds = new String[] {runPath,
										  String.valueOf(job.getId()),
										  job.getInMessage().getSourceFilePath(),
										  job.getInMessage().getResultFilePath()
										  ,job.getInMessage().getParamFilePath()
										  };
			
			job.getOutMessage().setStartDate(DateUtils.getDateWithTimeAsDate());
			
			Long startTime = (new Date()).getTime();
			
		    Process process = Runtime.getRuntime().exec(cmds);
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line; int i = 0;
			while ((line = br.readLine()) != null) {
				if (i == 0) {
					job.setAlgorithmPID(Integer.parseInt(line));
				}
				if (i == 1) {
					Status status;
					if (line.equals("PROCESSED")) {
						status = Status.PROCESSED;
					}
					else 
						if (line.equals("ERROR")){
							status = Status.ERROR;
						}
						else {
							status = Status.WARNING;
						}
					job.getOutMessage().setStatus(status);
				}
				if (i == 2) {
					if (job.getOutMessage().getStatus().equals(Status.WARNING)) {
						job.getOutMessage().setWarningDesc(line);
					}
					else {
						job.getOutMessage().setErrorDesc(line);
					}
				}
				i++;
			}
			br.close();
			isr.close();
			is.close();
			
			Long endTime = (new Date()).getTime();
			
			job.getOutMessage().setEndDate(DateUtils.getDateWithTimeAsDate());
			job.getOutMessage().setTime(endTime - startTime);
			
			defaultMessageSender.send(job);
			rootContainer.removeIdFromInstanceSpace(job.getAlgorithm().getAlgorithmId(), job.getId());
		} 
		catch (IOException e) {
			log.error("", e);
		}
		
		System.gc();
	}
}
