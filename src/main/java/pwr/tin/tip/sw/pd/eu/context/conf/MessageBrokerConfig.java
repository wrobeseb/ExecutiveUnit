package pwr.tin.tip.sw.pd.eu.context.conf;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import pwr.tin.tip.sw.pd.eu.context.Messages;
import pwr.tin.tip.sw.pd.eu.core.job.JobProcessor;
import pwr.tin.tip.sw.pd.eu.db.service.IJobService;
import pwr.tin.tip.sw.pd.eu.jms.core.DefaultMessageListener;
import pwr.tin.tip.sw.pd.eu.jms.core.DefaultMessageSender;
import pwr.tin.tip.sw.pd.eu.jms.model.utils.Marshaller;

@Configuration
public class MessageBrokerConfig {

	private @Autowired(required=true) IJobService jobService;
	private @Autowired(required=true) Marshaller marshaller;
	private @Autowired(required=true) Messages messages;
	private @Autowired(required=true) JobProcessor jobProcessor;
	
	private @Value("${esb.msg_broker_url}") String esbMsgBrokerUrl;
	private @Value("${esb.out_queue}") String esbOutQueue;
	private @Value("${esb.replay_queue}") String esbReplayQueue;
	
	private @Value("${thread.pool.queue.capacity}") Integer threadPoolQueueCapacity;
	private @Value("${thread.pool.max.tasks}") Integer threadPoolMaxTasks;
	
	private @Value("${msg.selector}") String messageSelector;
	
	public @Bean ConnectionFactory amqConnectionFactory() {
		return new ActiveMQConnectionFactory("failover:(" + esbMsgBrokerUrl + ")?maxReconnectDelay=15000");
	}
	
	public @Bean ConnectionFactory jmsConnectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(amqConnectionFactory());
		cachingConnectionFactory.setSessionCacheSize(10);
		return cachingConnectionFactory;
	}
	
	public @Bean DefaultMessageListenerContainer messageContainer() {
		DefaultMessageListenerContainer messageContainer = new DefaultMessageListenerContainer();
		messageContainer.setConnectionFactory(jmsConnectionFactory());
		messageContainer.setMaxMessagesPerTask(threadPoolMaxTasks + threadPoolQueueCapacity);
		messageContainer.setDestinationName(esbOutQueue);
		messageContainer.setMessageListener(messageListener());
		messageContainer.setMessageSelector(messageSelector);
		messageContainer.setCacheLevelName("CACHE_SESSION");
		messageContainer.setConcurrentConsumers(20);
		messageContainer.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		return messageContainer;
	}
	
	public @Bean MessageListenerAdapter messageListener() {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(defaultMessageListener());
		messageListenerAdapter.setDefaultListenerMethod("receive");
		messageListenerAdapter.setMessageConverter(null);
		return messageListenerAdapter;
	}
	
	public @Bean DefaultMessageListener defaultMessageListener() {
		DefaultMessageListener listener = new DefaultMessageListener();
		listener.setJobService(jobService);
		listener.setMarshaller(marshaller);
		listener.setMessages(messages);
		listener.setJobProcessor(jobProcessor);
		return listener;
	}
	
	public @Bean JmsTemplate jmsTemplate() {
		return new JmsTemplate(jmsConnectionFactory());
	}
	
	public @Bean DefaultMessageSender defaultMessageSender() {
		DefaultMessageSender sender = new DefaultMessageSender();
		sender.setJmsTemplate(jmsTemplate());
		sender.setMarshaller(marshaller);
		sender.setJobService(jobService);
		sender.setEsbReplayQueue(esbReplayQueue);
		return sender;
	}
	
}
