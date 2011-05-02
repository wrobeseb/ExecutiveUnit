package pwr.tin.tip.sw.pd.eu.context;

import org.springframework.context.MessageSource;

public class Messages {

	private MessageSource messageSource;
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String get(String key) {
		return messageSource.getMessage(key, null, null);
	}
}
