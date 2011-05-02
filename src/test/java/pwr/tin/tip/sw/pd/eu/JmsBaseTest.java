package pwr.tin.tip.sw.pd.eu;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Value;

public class JmsBaseTest extends BaseTest {

	@Value(value="${esb.msg_broker_url}")
	private String esbMsgBrokerUrl;
	
	@Value(value="${esb.out_queue}")
	private String esbOutQueue;

	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	
	public void sendMessageIntoEsbOutQueue(String message) {
		if (session == null) {
			init();
		}
		try {
			TextMessage textMessage = new ActiveMQTextMessage();
			textMessage.setText(message);
			if (producer == null) { 
				producer = session.createProducer(session.createQueue(esbOutQueue));
			}
			producer.send(textMessage);
		} 
		catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		try {
			connectionFactory = new ActiveMQConnectionFactory(esbMsgBrokerUrl);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	

}
