package pwr.tin.tip.sw.pd.eu.jms.model.utils;

import java.io.StringWriter;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DocumentSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import pwr.tin.tip.sw.pd.eu.jms.model.InMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.OutMessage;

public class Marshaller {

	private static final Logger log = LoggerFactory.getLogger(Marshaller.class);
	
	private Jaxb2Marshaller jaxb2Marshaller;

	public void setJaxb2Marshaller(Jaxb2Marshaller jaxb2Marshaller) {
		this.jaxb2Marshaller = jaxb2Marshaller;
	}
	
	public String getXmlForInMessageForTests(InMessage inMessage) {
		return xmlFromObject(inMessage);
	}
	
	public String getXmlForOutMessage(OutMessage outMessage) {
		return xmlFromObject(outMessage);
	}
	
	public InMessage getInMessageFromXml(String xml) throws XmlMappingException, DocumentException {
		return (InMessage)jaxb2Marshaller.unmarshal(new DocumentSource(DocumentHelper.parseText(xml)));
	}
	
	private String xmlFromObject(Object obj) {
		DOMResult result = new DOMResult();
		if (obj instanceof OutMessage) {
			jaxb2Marshaller.marshal((OutMessage)obj, result);
		}
		else {
			jaxb2Marshaller.marshal((InMessage)obj, result);
		}
		StringWriter writer = new StringWriter();
		try {
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(result.getNode()), new StreamResult(writer));
		} catch (TransformerConfigurationException e) {
			log.error("", e);
		} catch (TransformerException e) {
			log.error("", e);
		} catch (TransformerFactoryConfigurationError e) {
			log.error("", e);
		}
		return writer.toString();
	}
}
