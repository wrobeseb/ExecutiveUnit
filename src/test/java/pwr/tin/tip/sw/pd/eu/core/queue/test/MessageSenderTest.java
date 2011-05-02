package pwr.tin.tip.sw.pd.eu.core.queue.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pwr.tin.tip.sw.pd.eu.BaseTest;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.jms.core.DefaultMessageSender;
import pwr.tin.tip.sw.pd.eu.jms.model.OutMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.enums.Status;
import pwr.tin.tip.sw.pd.eu.utils.DateUtils;

public class MessageSenderTest extends BaseTest {
	
    @Autowired
    private DefaultMessageSender defaultMessageSender;
    
    private Job errorJob;
    private OutMessage errorOutMessage;
    private Job warningJob;
    private OutMessage warningOutMessage;
    private Job processedJob;
    private OutMessage processedOutMessage;
    
    @Before
    public void initVariables() {
    	
		Algorithm algorithm = new Algorithm();
		algorithm.setName("algorithmTest");
		algorithm.setAppName("algorithmTest");
		algorithm.setRunPath("/algorithmTest/app/algorithm.exe");
    	
    	errorOutMessage = new OutMessage();
    	errorOutMessage.setAlgorithmId(1);
    	errorOutMessage.setSessionId(1);
    	errorOutMessage.setStatus(Status.ERROR);
    	errorOutMessage.setErrorDesc("Error description!");
    	errorOutMessage.setStartDate(DateUtils.getDateWithTimeAsDate());
    	errorOutMessage.setEndDate(DateUtils.getDateWithTimeAsDate());
    	errorOutMessage.setTime(new Long(1111));
    	errorJob = new Job();
    	errorJob.setOutMessage(errorOutMessage);
    	errorJob.setAlgorithm(algorithm);
    	errorJob.setSessionId(1);
    	
    	warningOutMessage = new OutMessage();
    	warningOutMessage.setAlgorithmId(1);
    	warningOutMessage.setSessionId(1);
    	warningOutMessage.setStatus(Status.WARNING);
    	warningOutMessage.setWarningDesc("Warning description!");
    	warningOutMessage.setStartDate(DateUtils.getDateWithTimeAsDate());
    	warningOutMessage.setEndDate(DateUtils.getDateWithTimeAsDate());
    	warningOutMessage.setTime(new Long(1111));
    	warningJob = new Job();
    	warningJob.setOutMessage(warningOutMessage);
    	warningJob.setAlgorithm(algorithm);
    	warningJob.setSessionId(1);
    	
    	processedOutMessage = new OutMessage();
    	processedOutMessage.setAlgorithmId(1);
    	processedOutMessage.setSessionId(1);
    	processedOutMessage.setStatus(Status.PROCESSED);
    	processedOutMessage.setStartDate(DateUtils.getDateWithTimeAsDate());
    	processedOutMessage.setEndDate(DateUtils.getDateWithTimeAsDate());
    	processedOutMessage.setTime(new Long(1111));
    	processedJob = new Job();
    	processedJob.setOutMessage(processedOutMessage);
    	processedJob.setAlgorithm(algorithm);
    	processedJob.setSessionId(1);
    }
    
	@Test
	public void sendMessageTest() {
		defaultMessageSender.send(processedJob);
		defaultMessageSender.send(errorJob);
		defaultMessageSender.send(warningJob);
	}
}
