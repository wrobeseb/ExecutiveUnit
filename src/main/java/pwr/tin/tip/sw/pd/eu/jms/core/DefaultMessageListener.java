package pwr.tin.tip.sw.pd.eu.jms.core;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.XmlMappingException;

import pwr.tin.tip.sw.pd.eu.context.Messages;
import pwr.tin.tip.sw.pd.eu.core.job.JobProcessor;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.db.service.IJobService;
import pwr.tin.tip.sw.pd.eu.jms.model.OutMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.utils.Marshaller;
import pwr.tin.tip.sw.pd.eu.utils.DateUtils;

public class DefaultMessageListener {

	private static final Logger log = LoggerFactory.getLogger(DefaultMessageListener.class);
	
	private IJobService jobService;
	private Marshaller marshaller;
	private Messages messages;
	private JobProcessor jobProcessor;

	public void setJobService(IJobService jobService) {
		this.jobService = jobService;
	}
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public void setJobProcessor(JobProcessor jobProcessor) {
		this.jobProcessor = jobProcessor;
	}
	
	public void receive(TextMessage textMessage) {
		Job job;
		try {	
			job = prepareJob(textMessage.getText());
			jobService.saveStartedJob(job);
			log.debug(messages.get("default.message.listener.message.received"), job.getId(), job.getInputMessageBody());
			jobProcessor.launch(job);
		} 
		catch (XmlMappingException e) {
			log.error("", e);
		} 
		catch (DocumentException e) {
			log.error("", e);
		} 
		catch (JMSException e) {
			log.error("", e);
		}
	}
	
	private Job prepareJob(String messageBody) throws XmlMappingException, DocumentException {
		Job job = new Job();
		job.setInMessage(marshaller.getInMessageFromXml(messageBody));
		job.setSessionId(job.getInMessage().getSessionId());
		job.setInputMessageBody(messageBody);
		job.setInputMessageArrivalDt(DateUtils.getDateWithTimeAsDate());
		
		Algorithm algorithm = new Algorithm();
		algorithm.setAlgorithmId(job.getInMessage().getAlgorithmId());
		
		job.setAlgorithm(algorithm);
		
		OutMessage outMessage = new OutMessage();
		outMessage.setAlgorithmId(job.getInMessage().getAlgorithmId());
		outMessage.setSessionId(job.getInMessage().getSessionId());
		
		job.setOutMessage(outMessage);
		
		return job;
	}
}
