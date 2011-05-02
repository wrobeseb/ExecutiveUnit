package pwr.tin.tip.sw.pd.eu.hotdeploy;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;

import pwr.tin.tip.sw.pd.eu.context.Messages;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.utils.ZipUtils;

public class HotDeployDirectoryListener {

	public static Logger logger = LoggerFactory.getLogger(HotDeployDirectoryListener.class);

	private HotDeployManager hotdeployManager;
	private Messages messages;
	private File hotdeployDirectory;

	public void setHotdeployManager(HotDeployManager hotdeployManager) {
		this.hotdeployManager = hotdeployManager;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public void setHotdeployDirectory(UrlResource hotdeployDirectory) throws IOException {
		this.hotdeployDirectory = hotdeployDirectory.getFile();
	}
	
	public void checkDir() throws IOException {
		if (hotdeployDirectory.exists()) {
			if (hotdeployDirectory.isDirectory()) {
				File[] files = hotdeployDirectory.listFiles();
				if (files.length != 0) {
					for (File file : files) {
						if (file.getName().endsWith(".zip")) {
							Algorithm algApp = ZipUtils.getAppFromZipPackage(file);
							logger.info(messages.get("hotdeploy.deployment.process.start"), algApp.getName());
							hotdeployManager.startDeployPhaseForApplication(algApp);
							file.delete();
						}
					}
				}
			}
			else {
				logger.warn("Œcie¿ka wskazana w prametrach dla katalogu hotdeploy jest b³êdna. Prawdopodobnie nie wskazuje na katalog!!!!");
			}
		}
		else {
			if (!hotdeployDirectory.exists())
				logger.warn("Katalog hotdeploy wskazany w parametrach nie istnieje!!!!");
		}
	}
}
