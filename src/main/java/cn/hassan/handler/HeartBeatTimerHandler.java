package cn.hassan.handler;

import cn.hassan.packet.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/15 11:05
 * Description:
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
	private static final int HEARTBEAT_INTERVAL = 5;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		scheduleSendHeartBeat(ctx);

		super.channelActive(ctx);
	}

	private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
		ctx.executor().schedule(() -> {

			if (ctx.channel().isActive()) {
				ctx.writeAndFlush(new HeartBeatRequestPacket());
				scheduleSendHeartBeat(ctx);
			}

		}, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
	}
}
