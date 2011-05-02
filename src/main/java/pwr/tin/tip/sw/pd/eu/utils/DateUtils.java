package pwr.tin.tip.sw.pd.eu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static Date parseDate(String stringDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getTime() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
	
	public static String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static String getDateWithTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	public static Date getDateWithTimeAsDate() {
		return parseDate(getDateWithTime());
	}
}
