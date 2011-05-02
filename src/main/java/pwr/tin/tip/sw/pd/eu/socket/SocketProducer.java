package pwr.tin.tip.sw.pd.eu.socket;

import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

public class SocketProducer {

	public Message<String> send() {
		return MessageBuilder.withPayload("Test").build();
	}
}
