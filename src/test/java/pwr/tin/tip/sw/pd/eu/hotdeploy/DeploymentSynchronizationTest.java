package pwr.tin.tip.sw.pd.eu.hotdeploy;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

import pwr.tin.tip.sw.pd.eu.core.CycleHelper;
import pwr.tin.tip.sw.pd.eu.core.container.RootContainer;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;

public class DeploymentSynchronizationTest extends CycleHelper {

	@Autowired
	private RootContainer rootContainer;
	
	@Test
	public void syncTest() {
		new Thread(new Runnable() {
			public void run() {
				/*
				 * Trzeba miec na uwadze aby podczas procesu od sprawdzenia dostepnosci
				 * algorytmu tzn. czy czasem nie jest w fazie podmiany, do jego uruchomienia
				 * nie zostala wykonana podmiana juz uzywanego algorytmu przez mechanizm hotdeploy
				 * z tad potrzeba synchronizacji na poziomie przestrzenia algorytmow aktualnie
				 * w fazie podmiany...
				 * 
				 * W debug stop(10000) zadziala, dla run nie...
				 */
				synchronized (rootContainer.getHotDeploySpaceForSync()) {
					assertTrue(!rootContainer.checkIfAlgorithmIsInDeployPhase(1));
					stop(10000);
				}
			}
		}).start();
		
		rootContainer.setAlgorithmForDeployment(new Algorithm(1, null, null, null, null));
	
		assertTrue(rootContainer.checkIfAlgorithmIsInDeployPhase(1));
	}
}
