package cn.hassan.packet.base;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 16:39
 * Description:
 */
public class PackerDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		out.add(PacketCodeC.INSTANCE.decode(in));
	}
}
