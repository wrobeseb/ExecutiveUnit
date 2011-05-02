package pwr.tin.tip.sw.pd.eu.jms.core;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;

import pwr.tin.tip.sw.pd.eu.JmsBaseTest;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.db.service.IAlgorithmService;
import pwr.tin.tip.sw.pd.eu.jms.model.InMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.utils.Marshaller;

public class JmsEngineTest extends JmsBaseTest {
	
	@Autowired
	private IAlgorithmService algorithmService;
	
	@Autowired
	private Marshaller marshaller;

	private InMessage inMessage;
	
	@Before
	public void init() {
		inMessage = new InMessage();
		inMessage.setAlgorithmId(0);
		inMessage.setSessionId(1);
		inMessage.setAlgorithmName("algorithmTest");
		inMessage.setSourceFilePath("/path/to/source/file");
		inMessage.setResultFilePath("/path/to/result/file");
		inMessage.setParamFilePath("/path/to/param/file");
	}
	
	@Test
	public void consumMessagesTest() {
		Algorithm algorithm = new Algorithm();
		algorithm.setName("algorithmTest");
		algorithm.setAppName("algorithm");
		algorithm.setRunPath("algorithmTest/app/algorithm.exe");
		
		algorithmService.saveAlgorithm(algorithm);
		
		for (int i = 0; i < 1500; i++) {
			inMessage.setSessionId(i);
			sendMessageIntoEsbOutQueue(marshaller.getXmlForInMessageForTests(inMessage));
		}
		
		assertTrue(true);
	}
}
