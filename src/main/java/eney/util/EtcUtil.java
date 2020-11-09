package eney.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class EtcUtil {
	/**
	 * 현재 서버의 IP 주소를 가져옵니다.
	 * 
	 * @return IP 주소
	 */
	public static String getLocalServerIp()
	{
		try
		{
		    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
		    {
		        NetworkInterface intf = en.nextElement();
		        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
		        {
		            InetAddress inetAddress = enumIpAddr.nextElement();
		            if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress())
		            {
		            	return inetAddress.getHostAddress().toString();
		            }
		        }
		    }
		}
		catch (SocketException ex) {}
		return null;
	}

	public static String makePhoneNumber(String phoneNoStr) {

		System.out.println("====");
		System.out.println(phoneNoStr);
		System.out.println("====");

		String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
		if(!Pattern.matches(regEx, phoneNoStr)) return null;
		return phoneNoStr.replaceAll(regEx, "$1-$2-$3");

	}




}
