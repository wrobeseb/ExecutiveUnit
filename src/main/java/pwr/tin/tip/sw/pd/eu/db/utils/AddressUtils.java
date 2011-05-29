package pwr.tin.tip.sw.pd.eu.db.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddressUtils {

	public static String getLocalIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
}
