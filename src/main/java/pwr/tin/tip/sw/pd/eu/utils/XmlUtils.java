package pwr.tin.tip.sw.pd.eu.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public class XmlUtils {

	public static String getXml(String messageBody, String xpathExpression) {
		try {
			return DocumentHelper.parseText(messageBody).selectSingleNode(xpathExpression).asXML();
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getString(String messageBody, String xpathExpression) {
		try {
			Node singleNode = DocumentHelper.parseText(messageBody).selectSingleNode(xpathExpression);
			if (singleNode != null) {
				return singleNode.getStringValue();
			}
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getDate(String messageBody, String xpathExpression) {
		return DateUtils.parseDate(getString(messageBody, xpathExpression));
	}
	
	public static Integer getInteger(String messageBody, String xpathExpression) {
		try {
			String string = DocumentHelper.parseText(messageBody).selectSingleNode(xpathExpression).getStringValue();
			if (string != null && !string.equals("")) {
				return Integer.parseInt(string);
			}
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Integer> getListOfIntegers(String messageBody, String xpathExpression) {
		try {
			List<Node> listOfNodes = DocumentHelper.parseText(messageBody).selectNodes(xpathExpression);
			List<Integer> listOfIntegers = new ArrayList<Integer>();
			for (Node node : listOfNodes) {
				String string = node.getStringValue();
				if (string != null && !string.equals("")) {
					listOfIntegers.add(Integer.parseInt(string));
				}
			}
			return listOfIntegers;
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getListOfStrings(String messageBody, String xpathExpression) {
		try {
			List<Node> listOfNodes = DocumentHelper.parseText(messageBody).selectNodes(xpathExpression);
			List<String> listOfString = new ArrayList<String>();
			for (Node node : listOfNodes) {
				String string = node.getStringValue();
				if (string != null && !string.equals("")) {
					listOfString.add(string);
				}
			}
			return listOfString;
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getListOfNodesAsXml(String messageBody, String xpathExpression) {
		try {
			List<Node> listOfNodes = DocumentHelper.parseText(messageBody).selectNodes(xpathExpression);
			List<String> listOfString = new ArrayList<String>();
			for (Node node : listOfNodes) {
				String string = node.asXML();
				if (string != null && !string.equals("")) {
					listOfString.add(string);
				}
			}
			return listOfString;
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String changeValue(String messageBody, String value, String xpathExpression) {
		try {
			Document doc = DocumentHelper.parseText(messageBody);
			Node node = doc.selectSingleNode(xpathExpression);
			if (node != null) {
				node.setText(value);
			}
			return doc.asXML();
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Object unmarshal(String xmlBody, Class objectClass) {
		try {
			JAXBContext context = JAXBContext.newInstance(objectClass);
			Unmarshaller u = context.createUnmarshaller();
			return u.unmarshal(new StreamSource( new StringReader(xmlBody)));
		}
		catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
