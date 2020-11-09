package eney.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Component;


@Component
public class IPUtils {

	private static Pattern VALID_IPV4_PATTERN = null;

	private static Pattern VALID_IPV6_PATTERN = null;

	private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";

	private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";



	static {

		try {

			VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);

			VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);

		} catch (PatternSyntaxException e) {

			// logger.severe("Unable to compile pattern", e);

		}

	}



	/**

	 * IP Address 검증

	 * @param ipAddress

	 * @return

	 */

	public static boolean isIpAddress(String ipAddress) {



		Matcher m1 = VALID_IPV4_PATTERN.matcher(ipAddress);

		if (m1.matches()) {

			return true;

		}

		Matcher m2 = VALID_IPV6_PATTERN.matcher(ipAddress);

		return m2.matches();

	}



	/**

	 * IP 값 롱타입으로 변환

	 * 

	 * @param ipAddress

	 * @return

	 */

	public static long ipToLong(String ipAddress) {

		long result = 0;

		if (isIpAddress(ipAddress)) {

			String[] ipAddressInArray = ipAddress.split("\\.");



			for (int i = 0; i < ipAddressInArray.length; i++) {

				int power = 3 - i;

				int ip = Integer.parseInt(ipAddressInArray[i]);

				result += ip * Math.pow(256, power);

			}

		}



		return result;

	}



	/**

	 * 공인 IP 가져오기 AWS API 사용해서 가져오기

	 * 

	 * @return

	 */

	public String getPublicIP() {

		String result = null;



		try {

			String sUrl = "http://checkip.amazonaws.com/";

			URL url = new URL(sUrl);

			URLConnection clsConn = getConnection(url);

			InputStream is = clsConn.getInputStream();

			java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));

			result = reader.readLine();

		} catch (Exception ex) {

			ex.printStackTrace();

		}



		return result;

	}



	public static URLConnection getConnection(URL url) {

		URLConnection conn = null;

		HttpsURLConnection https = null;



		try {

			if (url.getProtocol().toLowerCase().equals("https")) {

				trustAllHosts();

				https = (HttpsURLConnection) url.openConnection();

				https.setHostnameVerifier(DO_NOT_VERIFY);

				conn = https;

			} else {

				conn = url.openConnection();

			}

		} catch (IOException e) {

			e.printStackTrace();

		}

		return conn;

	}



	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {

		@Override

		public boolean verify(String hostname, SSLSession session) {

			return true;

		}

	};



	private static void trustAllHosts() {

		// Create a trust manager that does not validate certificate chains

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			@Override

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {

				return new java.security.cert.X509Certificate[] {};

			}



			@Override

			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {

			}



			@Override

			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {

			}

		} };



		// Install the all-trusting trust manager

		try {

			SSLContext sc = SSLContext.getInstance("TLS");

			sc.init(null, trustAllCerts, new java.security.SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}



}
