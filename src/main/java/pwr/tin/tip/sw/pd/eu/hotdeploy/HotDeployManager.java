package pwr.tin.tip.sw.pd.eu.hotdeploy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;

import pwr.tin.tip.sw.pd.eu.context.Messages;
import pwr.tin.tip.sw.pd.eu.core.container.RootContainer;
import pwr.tin.tip.sw.pd.eu.core.job.JobProcessor;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.utils.DateUtils;
import pwr.tin.tip.sw.pd.eu.utils.FileUtils;
import pwr.tin.tip.sw.pd.eu.utils.ZipUtils;

public class HotDeployManager {

	private static Logger logger = LoggerFactory.getLogger(HotDeployManager.class);
	
	private RootContainer rootContainer;
	private Messages messages;
	private JobProcessor jobProcessor;
	private File runtimeDirectory;
	private File backupDirectory;

	public void setRootContainer(RootContainer rootContainer) {
		this.rootContainer = rootContainer;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public void setJobProcessor(JobProcessor jobProcessor) {
		this.jobProcessor = jobProcessor;
	}
	public void setRuntimeDirectory(UrlResource runtimeDirectory) throws IOException {
		this.runtimeDirectory = runtimeDirectory.getFile();
	}
	public void setBackupDirectory(UrlResource backupDirectory) throws IOException {
		this.backupDirectory = backupDirectory.getFile();
	}
	
	public void startDeployPhaseForApplication(Algorithm algorithmApp) {
		if (algorithmApp != null) {
			rootContainer.setAlgorithmForDeployment(algorithmApp);
		}
	}
	
	public void endDeployPhaseForApplication(Algorithm algorithmApp) {
		Algorithm actualAlgorithmAppObject = rootContainer.pullAlgorithmFromSpace(algorithmApp.getAlgorithmId());
		
		if (actualAlgorithmAppObject != null) {
			logger.info(messages.get("hotdeploy.deployment.process.backup.phase"), algorithmApp.getName());
			try {
				FileUtils.moveDirIntoDestination(new File(runtimeDirectory.getAbsolutePath() + "/" + actualAlgorithmAppObject.getName()), new File(backupDirectory + "/" + actualAlgorithmAppObject.getName() + "/" + DateUtils.getDateWithTime().replace(":", ".")));
			}
			catch (IOException ioEx) {
				logger.error("Nie znaleziono pliku w katalogu runtime...", ioEx);
			}
			rootContainer.removeAlgorithmFromSpace(actualAlgorithmAppObject.getAlgorithmId());
		}
		logger.info(messages.get("hotdeploy.deployment.process.deployment.files.phase"), algorithmApp.getName(), algorithmApp.getName());
		ZipUtils.unpackApplicationDir(algorithmApp.getAppPackage(), new File(runtimeDirectory + "/" + algorithmApp.getName()));
		FileUtils.writeMetaInfFile(algorithmApp, new File(runtimeDirectory.getAbsolutePath() + "/" + algorithmApp.getName() + "/META-INF"));
		rootContainer.putAlgorithmIntoSpace(algorithmApp);
		logger.info(messages.get("hotdeploy.deployment.process.finalization.phase"), algorithmApp.getName());
		rootContainer.algorithmDeployed(algorithmApp);
		processWaitingJobs(algorithmApp.getAlgorithmId());
	}
	
	private void processWaitingJobs(Integer algorithmId) {
		logger.info(messages.get("hotdeploy.deployment.process.running.waiting.jobs"));
		List<Job> waitingJobsList = rootContainer.getJobsByAlgorithmIdFromTempSpace(algorithmId);
		for (Job job : waitingJobsList) {
			jobProcessor.launch(job);
			rootContainer.removeJobFromTempSpace(algorithmId, job.getId());
		}
	}
}
