package pwr.tin.tip.sw.pd.eu.core.job;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import pwr.tin.tip.sw.pd.eu.context.Messages;
import pwr.tin.tip.sw.pd.eu.core.container.RootContainer;
import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.jms.core.DefaultMessageSender;

public class JobProcessor {

	private static final Logger log = LoggerFactory.getLogger(JobProcessor.class);
	
	private RootContainer rootContainer;
	private Messages messages;
	private DefaultMessageSender defaultMessageSender;
	private ThreadPoolTaskExecutor taskExecutor;
	private JobPoolManager jobPoolManager;
	
	private String runtimeDirectory;

	public void setRootContainer(RootContainer rootContainer) {
		this.rootContainer = rootContainer;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public void setDefaultMessageSender(DefaultMessageSender defaultMessageSender) {
		this.defaultMessageSender = defaultMessageSender;
	}
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	public void setRuntimeDirectory(String runtimeDirectory) {
		this.runtimeDirectory = runtimeDirectory;
	}
	public void setJobPoolManager(JobPoolManager jobPoolManager) {
		this.jobPoolManager = jobPoolManager;
	}
	
	public void launch(List<Job> jobs) {
		for (Job job : jobs) {
			launch(job);
		}
	}
	
	public void launch(Job job) {
		if (!rootContainer.checkIfAlgorithmIsInDeployPhase(job.getAlgorithm().getAlgorithmId())) {
			
			rootContainer.putJobIntoSpace(job);
			rootContainer.putIdIntoInstanceSpace(job.getAlgorithm().getAlgorithmId(), job.getId());
			log.debug(messages.get("job.processor.executing.job"), job.getId(), job.getAlgorithm().getAlgorithmId());
			try {
				taskExecutor.execute(new JobTask(job, defaultMessageSender, rootContainer, runtimeDirectory));
				log.info("executing");
			}
			catch(RejectedExecutionException reEx) {
				log.info("queue full");
				rootContainer.putJobIntoBlockingQueueOverflowJobsSpace(job);
				jobPoolManager.stopConsumingMessages();
			}
		}
		else {
			log.info(messages.get("job.processor.putting.job.into.temp.queue"), job.getId(), job.getAlgorithm().getAlgorithmId());
			rootContainer.putJobIntoTempSpace(job);
		}
	}
	
}
