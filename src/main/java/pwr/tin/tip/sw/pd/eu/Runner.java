package pwr.tin.tip.sw.pd.eu;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pwr.tin.tip.sw.pd.eu.core.AppContext;

public class Runner 
{
	final static Logger logger = LoggerFactory.getLogger(Runner.class);
	
    public static void main( String[] args ) throws JMSException {
    	AppContext.initContext();
    }
}
