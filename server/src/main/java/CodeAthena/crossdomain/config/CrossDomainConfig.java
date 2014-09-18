package CodeAthena.crossdomain.config;

import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossDomainConfig {
	public static int port;
	public static int idleTimeout;
	public static int heartBeat;
	public static int heartBeatTimout;
	public static int heartBeatInterval;
	public static String text = "";
	private final static Logger log = LoggerFactory.getLogger(CrossDomainConfig.class);

	public static void load() {
		XMLConfiguration config = null;
		try {
			config = new XMLConfiguration("./config.xml");
			port = config.getInt("port", 843);
			idleTimeout = config.getInt("idleTimeout", 60);
			heartBeat = config.getInt("heartBeat");
			heartBeatTimout = config.getInt("heartBeatTimout");
			heartBeatInterval = config.getInt("heartBeatInterval");
			config.clear();
		} catch (Exception e) {
			e.printStackTrace();
			port = 843;
			heartBeat = 0;
			heartBeatTimout = 15;
			heartBeatInterval = 1;
		} finally {
			text = "\n================================================================";
			text += "\n  [server port]       : " + port;
			text += "\n  [idleTimeout] : " + idleTimeout;
			text += "\n  [heartBeat] : " + heartBeat;
			text += "\n  [heartBeatTimout] : " + heartBeatTimout;
			text += "\n  [heartBeatInterval] : " + heartBeatInterval;
			text += "\n================================================================";
		}
	}
}
