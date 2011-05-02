package pwr.tin.tip.sw.pd.eu.jms.model.utils;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.ibm.icu.text.SimpleDateFormat;

public class DateAdapter extends XmlAdapter<String, Date> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public String marshal(Date arg0) throws Exception {
		return dateFormat.format(arg0);
	}

	@Override
	public Date unmarshal(String arg0) throws Exception {
		return dateFormat.parse(arg0);
	}

}
