package CodeAthena.pack;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
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

public class PackDecoder extends CumulativeProtocolDecoder {
	static final Logger log = LoggerFactory.getLogger(PackDecoder.class);

	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
		if (buffer.remaining() >= 23) {
			byte[] info = new byte[22];
			buffer.get(info);
			String s = new String(info, "UTF-8");
			out.write(s);
			return true;
		}
		return false;

	}

}
