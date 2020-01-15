package cn.hassan.handler;

import cn.hassan.packet.HeartBeatRequestPacket;
import cn.hassan.packet.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/15 11:20
 * Description:
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
	public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

	private HeartBeatRequestHandler() {

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
		ctx.writeAndFlush(new HeartBeatResponsePacket());
	}
}
