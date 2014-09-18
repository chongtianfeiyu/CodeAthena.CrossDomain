package CodeAthena.crossdomain.config;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossDomainInfo {
	
	static final Logger log = LoggerFactory.getLogger(CrossDomainInfo.class);
	
	public static String version = "V1.00_2014.09.16.1003";
	public static String author = "camus";
	
	public final static String responseXml="<cross-domain-policy> " + "<allow-access-from domain=\"*\" to-ports=\"1025-9999\"/>" + "</cross-domain-policy> ";
	public static byte[] responseBytes = null;
	
	public static void init(){
		try {
			responseBytes = responseXml.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			log.info("responseXml load failed.");
		}
	}

}
