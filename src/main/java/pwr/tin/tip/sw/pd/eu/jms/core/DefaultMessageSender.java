package pwr.tin.tip.sw.pd.eu.jms.core;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import pwr.tin.tip.sw.pd.eu.db.model.Job;
import pwr.tin.tip.sw.pd.eu.db.service.IJobService;
import pwr.tin.tip.sw.pd.eu.jms.model.utils.Marshaller;
import pwr.tin.tip.sw.pd.eu.utils.DateUtils;

public class DefaultMessageSender {

	private JmsTemplate jmsTemplate;
	private Marshaller marshaller;
	private IJobService jobService;
	private String esbReplayQueue;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	public void setJobService(IJobService jobService) {
		this.jobService = jobService;
	}
	public void setEsbReplayQueue(String esbReplayQueue) {
		this.esbReplayQueue = esbReplayQueue;
	}
	
	public synchronized void send(final Job job) {
		jmsTemplate.send(esbReplayQueue, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = prepareMessage(job, session);
				job.setOutputMessageBody(textMessage.getText());
				job.setOutputMessageCreatedDt(DateUtils.getDateWithTimeAsDate());
				return textMessage;
			}
		});
		job.setOutputMessageSended(true);
		jobService.saveEndedJob(job);
	}
	
	private TextMessage prepareMessage(Job job, Session session) throws JMSException {
		job.setOutputMessageBody(marshaller.getXmlForOutMessage(job.getOutMessage()));
		TextMessage textMessage = session.createTextMessage();
		textMessage.setIntProperty("cuId",job.getCuId());
		textMessage.setText(job.getOutputMessageBody());
		return textMessage;
	}
}
