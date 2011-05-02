package pwr.tin.tip.sw.pd.eu.context.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import pwr.tin.tip.sw.pd.eu.jms.model.InMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.OutMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.utils.Marshaller;

@Configuration
@Import({HibernateConfig.class, MessageBrokerConfig.class})
//@ImportResource("classpath:applicationContext.xml")
public class ApplicationConfig {

	public @Bean Marshaller marshaller() {
		Marshaller marshaller = new Marshaller();
		marshaller.setJaxb2Marshaller(jaxb2marshaller());
		return marshaller;
	}
	
	public @Bean Jaxb2Marshaller jaxb2marshaller() {
		Jaxb2Marshaller jaxb2Marshaller= new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(InMessage.class, OutMessage.class);
		return jaxb2Marshaller;
	}
}
