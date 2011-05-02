package pwr.tin.tip.sw.pd.eu.hotdeploy;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pwr.tin.tip.sw.pd.eu.BaseTest;
import pwr.tin.tip.sw.pd.eu.core.container.RootContainer;

public class HotDeployProcessTest extends BaseTest {

	@Autowired
	private RootContainer rootContainer;
	
	
	@Before
	public void init() {
		rootContainer.putIdIntoInstanceSpace(1, 1);
		rootContainer.putIdIntoInstanceSpace(1, 2);
	}
	
	@Test
	public void hotdeploymentTest() throws InterruptedException {
		
		//FileUtils.moveFileIntoDir(HotDeployUtils.getZippedAppPackage(1, "algorithmName"), new File(parameters.value("algorithm.hotdeploy.directory")));
		
		//stop(1000);
		rootContainer.removeIdFromInstanceSpace(1, 1);
		//stop(2000);
		rootContainer.removeIdFromInstanceSpace(1, 2);
	}
}
