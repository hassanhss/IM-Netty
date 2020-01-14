package cn.hassan.packet.base;

import cn.hassan.core.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 16:50
 * Description:
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		PacketCodeC.INSTANCE.encode(out, msg);
	}
}
