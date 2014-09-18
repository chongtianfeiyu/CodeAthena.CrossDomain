package CodeAthena.crossdomain.business;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CodeAthena.crossdomain.config.CrossDomainInfo;

public class CrossdomainProcesser extends IoHandlerAdapter {

	private final static Logger log = LoggerFactory.getLogger(CrossdomainProcesser.class);
	
	
	public void sessionCreated(IoSession session) throws Exception {
		//log.info("sessionCreated");
	}

	public void sessionOpened(IoSession session) throws Exception {
		//log.info("sessionOpened");
	}

	public void sessionClosed(IoSession session) throws Exception {
		log.debug("sessionClosed");
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		//log.info("sessionIdle");
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error("exceptionCaught, close session", cause);
		session.close(true);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		String info = (String) message;
		log.info("[R]"+info);
		session.write(CrossDomainInfo.responseBytes);
		session.close(false);
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		//log.info("messageSent");
	}

}
