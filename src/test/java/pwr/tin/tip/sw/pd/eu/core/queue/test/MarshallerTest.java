package pwr.tin.tip.sw.pd.eu.core.queue.test;

import java.util.Date;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

import pwr.tin.tip.sw.pd.eu.BaseTest;
import pwr.tin.tip.sw.pd.eu.jms.model.OutMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.enums.Status;
import pwr.tin.tip.sw.pd.eu.jms.model.utils.Marshaller;

public class MarshallerTest extends BaseTest {

	@Autowired
	public Marshaller marshaller;
	
	@Test
	public void marshallTest() throws TransformerException {
		OutMessage outMessage = new OutMessage();
		outMessage.setAlgorithmId(1);
		outMessage.setSessionId(1);
		outMessage.setStatus(Status.PROCESSED);
		outMessage.setStartDate(new Date());
		outMessage.setEndDate(new Date());
		outMessage.setTime(new Long(10101));
		
		String xml = marshaller.getXmlForOutMessage(outMessage);
		
		assertTrue(xml.contains("<algorithmId>1</algorithmId>"));
		assertTrue(xml.contains("<status>PROCESSED</status>"));
		
		outMessage.setStatus(Status.ERROR);
		outMessage.setErrorDesc("duypdduhdfd");
		
		xml = marshaller.getXmlForOutMessage(outMessage);
		
		assertTrue(xml.contains("<algorithmId>1</algorithmId>"));
		assertTrue(xml.contains("<status>ERROR</status>"));
		assertTrue(xml.contains("<errorDesc>duypdduhdfd</errorDesc>"));
		
		outMessage.setStatus(Status.WARNING);
		outMessage.setWarningDesc("sfddfgdfg");
		outMessage.setErrorDesc(null);
		
		xml = marshaller.getXmlForOutMessage(outMessage);
		
		assertTrue(xml.contains("<algorithmId>1</algorithmId>"));
		assertTrue(xml.contains("<status>WARNING</status>"));
		assertTrue(xml.contains("<warningDesc>sfddfgdfg</warningDesc>"));
	}
}
