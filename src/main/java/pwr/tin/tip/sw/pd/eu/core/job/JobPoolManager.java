package pwr.tin.tip.sw.pd.eu.core.job;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import pwr.tin.tip.sw.pd.eu.core.container.RootContainer;
import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.db.service.IUnitService;

public class JobPoolManager {

	private static final Logger log = LoggerFactory.getLogger(JobPoolManager.class);
	
	private DefaultMessageListenerContainer messageContainer;
	private ThreadPoolTaskExecutor taskExecutor;
	private JobProcessor jobProcessor;
	private RootContainer rootContainer;
	
	private IUnitService unitService;

	public void setMessageContainer(DefaultMessageListenerContainer messageContainer) {
		this.messageContainer = messageContainer;
	}
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	public void setJobProcessor(JobProcessor jobProcessor) {
		this.jobProcessor = jobProcessor;
	}
	public void setRootContainer(RootContainer rootContainer) {
		this.rootContainer = rootContainer;
	}
	public void setUnitService(IUnitService unitService) {
		this.unitService = unitService;
	}
	
	public void stopConsumingMessages() {
		messageContainer.stop();
		unitService.setOverload();
		log.info("stop");
	}
	
	public void checkIfQueueIsFull() {
		ThreadPoolExecutor executor = taskExecutor.getThreadPoolExecutor();
		if (executor.getQueue().size() == 0) {
			Job job;
			while ((job = rootContainer.pullJobFromBlockingQueueOverflowJobsSpace()) != null) {
				log.info("Executing job from temp");
				jobProcessor.launch(job);
				
			}
			if (!messageContainer.isRunning()) {
				messageContainer.start();
				unitService.setFree();
				log.info("start");
			}
		}
		else {
			log.info("poolSize = {}",executor.getPoolSize());
			log.info("queue = {}",executor.getQueue().size());
		}
	}
}
