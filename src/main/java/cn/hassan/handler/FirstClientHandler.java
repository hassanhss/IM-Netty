package cn.hassan.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/6 11:20
 * Description:
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(new Date() + "客户端写出数据");
		ByteBuf buf = getByteBuf(ctx);
		ctx.channel().writeAndFlush(buf);
	}

	private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
		ByteBuf buffer = ctx.alloc().buffer();
		byte[] bytes = "hello,IM-Netty".getBytes(StandardCharsets.UTF_8);
		buffer.writeBytes(bytes);
		return buffer;
	}
}
