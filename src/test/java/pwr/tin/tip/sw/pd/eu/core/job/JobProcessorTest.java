package pwr.tin.tip.sw.pd.eu.core.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.Assert.*;

import pwr.tin.tip.sw.pd.eu.BaseTest;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.jms.model.InMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.OutMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.enums.Status;
import pwr.tin.tip.sw.pd.eu.utils.DateUtils;

public class JobProcessorTest extends BaseTest {
	
	private @Value("${algorithm.runtime.directory}") String runtimeDirectory;
	
	private Job job;
	
	@Before
	public void init() {
		
		Algorithm algorithm = new Algorithm();
		algorithm.setId(1);
		algorithm.setName("algorithmTest");
		algorithm.setRunPath("/algorithmTest/app/algorithm.exe");
		
		InMessage inMessage = new InMessage();
		inMessage.setAlgorithmId(1);
		inMessage.setSessionId(1);
		inMessage.setAlgorithmName("algorithmTest");
		inMessage.setSourceFilePath("/path/to/source/file");
		inMessage.setResultFilePath("/path/to/result/file");
		inMessage.setParamFilePath("/path/to/param/file");
		
		OutMessage outMessage = new OutMessage();
		outMessage.setAlgorithmId(1);
		outMessage.setSessionId(1);
		
		job = new Job();
		job.setId(1);
		job.setAlgorithm(algorithm);
		job.setInMessage(inMessage);
		job.setOutMessage(outMessage);
		job.setInputMessageArrivalDt(DateUtils.getDateWithTimeAsDate());
		job.setOutputMessageSended(false);
	}
	
	@Test
	public void jobLauncherTest() {
		try {
			String[] cmds = new String[] {runtimeDirectory + job.getAlgorithm().getRunPath(),
										  String.valueOf(job.getId()),
										  job.getInMessage().getSourceFilePath(),
										  job.getInMessage().getResultFilePath()
										  ,job.getInMessage().getParamFilePath()
										  };
			
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
				i++;
			}
			br.close();
			isr.close();
			is.close();
		} catch (IOException e) {}
		
		assertNotNull(job.getAlgorithmPID());
		assertNotNull(job.getOutMessage().getStatus());
		assertEquals(job.getOutMessage().getStatus(), Status.PROCESSED);
	}
}
