package cn.hassan.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/6 13:32
 * Description:
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		System.out.println(new Date() + ": 服务端读到数据 -> " + buf.toString(StandardCharsets.UTF_8));
		ByteBuf out = getByteBuf(ctx);
		ctx.channel().writeAndFlush(out);
	}

	private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
		byte[] bytes = "你好!".getBytes(StandardCharsets.UTF_8);
		ByteBuf buffer = ctx.alloc().buffer();
		buffer.writeBytes(bytes);
		return buffer;
	}
}
