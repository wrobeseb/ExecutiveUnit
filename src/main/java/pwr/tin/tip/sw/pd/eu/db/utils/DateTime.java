package pwr.tin.tip.sw.pd.eu.db.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
	public static Date now() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(sdf.format(Calendar.getInstance().getTime()));
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
