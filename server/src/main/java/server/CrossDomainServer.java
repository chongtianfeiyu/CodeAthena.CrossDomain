package server;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CodeAthena.crossdomain.business.CrossdomainProcesser;
import CodeAthena.crossdomain.config.CrossDomainConfig;
import CodeAthena.crossdomain.config.CrossDomainInfo;
import CodeAthena.pack.PackFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;

public class CrossDomainServer {
	static CrossdomainProcesser processer;

	public static void printVersion() {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("	                 version : " + CrossDomainInfo.version);
		System.out.println("	                 author  : " + CrossDomainInfo.author);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	public static boolean logbackInit() {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		boolean flag = false;
		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			context.reset();
			configurator.doConfigure("./logback.xml");
			
			flag = true;
		} catch (Exception je) {
			je.printStackTrace();
			if (context != null) {
				StatusPrinter.print(context);
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		if (!logbackInit()) {
			System.out.println("[logback] Init error");
			return;
		}

		CrossDomainConfig.load();
		CrossDomainInfo.init();

		final Logger log = LoggerFactory.getLogger(CrossDomainServer.class);
		try {
			processer = new CrossdomainProcesser();

			NioSocketAcceptor acceptor = new NioSocketAcceptor();
			acceptor.setReuseAddress(true);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, CrossDomainConfig.idleTimeout);
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PackFactory()));

			   

			acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newSingleThreadExecutor()));
			acceptor.setHandler(processer);
			acceptor.getSessionConfig().setReadBufferSize(1024);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			printVersion();
			System.out.println(CrossDomainConfig.text);
			System.out.println("server is running...");
			acceptor.bind(new InetSocketAddress(CrossDomainConfig.port));
		} catch (Exception e) {
			log.error("CrossDomainServer error", e);
		}
	}
}
