package common;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;

import java.security.*;
import java.security.cert.*;

/**
 * static utility helper methods to send http request 
 */
public class HttpUtils
{
	public static final String jsonContentType = "application/json";
	public static final String xmlContentType = "application/xml";
	public static final String defaultContentType = jsonContentType;

	// configure ssl certificate validation
	static {
		TrustManager[] trustAllCertificates = new TrustManager[] {
				new X509TrustManager() {
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null; // Not relevant.
					}

					@Override
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
						// allow all
					}

					@Override
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
						// allow all
					}
				}
		};

		HostnameVerifier trustAllHostnames = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true; // allow all
			}
		};

		try {
			System.setProperty("jsse.enableSNIExtension", "false");
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCertificates, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);
		} catch (GeneralSecurityException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * send HTTP POST request
	 * @param url {@code String} full url of destination. including protocol, server domain/ip, port and path
	 * @param payload {@code String} request payload (body) in json format 
	 * @return HTTP response status code
	 * @throws IOException if url arg cannot be parsed or an error occurred during http send/receive 
	 */
	public static int sendPost(String url, String payload) throws IOException {
		return sendPost(url, payload, defaultContentType);
	}

	/**
	 * send HTTP POST request
	 * @param url {@code String} full url of destination. including protocol, server domain/ip, port and path
	 * @param payload {@code String} request payload (body) 
	 * @param contentType media type of request payload, json or xml are valid values
	 * @return HTTP response status code
	 * @throws IOException if url arg cannot be parsed or an error occurred during http send/receive 
	 */
	public static int sendPost(String url, String payload, String contentType) throws IOException {
		HttpURLConnection con = getConnection(url);
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Java client");
		con.setRequestProperty("Content-Type", contentType);
		con.setDoOutput(true);
		try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
			out.writeBytes(payload);
			out.flush();
		}
		return con.getResponseCode();
	}

	private static HttpURLConnection getConnection(String url) throws IOException {
		return url.startsWith("https:") ?
			(HttpsURLConnection)new URL(url).openConnection() :
			(HttpURLConnection)new URL(url).openConnection();
	}
}
