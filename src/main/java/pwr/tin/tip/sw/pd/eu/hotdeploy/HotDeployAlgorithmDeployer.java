package pwr.tin.tip.sw.pd.eu.hotdeploy;

import java.util.ArrayList;

import pwr.tin.tip.sw.pd.eu.core.container.RootContainer;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;

public class HotDeployAlgorithmDeployer {
	
	private HotDeployManager hotdeployManager;
	private RootContainer rootContainer;

	public void setRootContainer(RootContainer rootContainer) {
		this.rootContainer = rootContainer;
	}
	public void setHotdeployManager(HotDeployManager hotdeployManager) {
		this.hotdeployManager = hotdeployManager;
	}
	
	public void checkIfRunningInstancesExists() {
		ArrayList<Algorithm> algorithmsInDeploymentPhase = new ArrayList<Algorithm>(rootContainer.getHotDeploySpaceForSync().values());
		synchronized (rootContainer.getHotDeploySpaceForSync()) {
			for (Algorithm algorithmApp : algorithmsInDeploymentPhase) {
				if (!rootContainer.checkIfAlgorithmHasRunningInstances(algorithmApp.getAlgorithmId())) {
					hotdeployManager.endDeployPhaseForApplication(algorithmApp);
				}
			}
		}
		algorithmsInDeploymentPhase.clear();
		algorithmsInDeploymentPhase = null;
	}
	

}
