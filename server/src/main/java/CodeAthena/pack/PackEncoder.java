package CodeAthena.pack;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 不论是低于还是高于1024端口,基于文件系统时都不会请求策略文件,基于网络时才会请求.
 * 首先发出以null结尾的<policy-file-request/>的消息,检查服务器843端口是否有安全策略文件,策略文件格式为:
 * <cross-domain-policy>
 * <allow-access-from domain="*" to-ports="80-9000" />
 * </cross-domain-policy>
 * 发回策略文件的时候必要以0结尾,如果843端口在3秒内没有请求到策略文件或者to-ports配置的端口不允许链接,
 * 则断掉链接抛securityError,这个是flash主动发起的.
 */

public class PackEncoder implements ProtocolEncoder {
	static final Logger log = LoggerFactory.getLogger(PackEncoder.class);

	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		byte [] info = (byte[])message;
		IoBuffer newBuffer = IoBuffer.allocate(info.length+1);
		newBuffer.put(info);
		newBuffer.put((byte) 0);
		newBuffer.flip();
		out.write(newBuffer);
		log.info("[S]"+session.getRemoteAddress());
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {

	}

}