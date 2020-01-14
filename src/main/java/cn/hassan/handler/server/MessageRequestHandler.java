package cn.hassan.handler.server;

import cn.hassan.core.DateTimeUtils;
import cn.hassan.packet.MessageRequestPacket;
import cn.hassan.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 16:48
 * Description:
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
		MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
		System.out.println(DateTimeUtils.getLocalDate() + ": 收到客户端消息: " + msg.getMessage());
		messageResponsePacket.setMessage("服务端回复【" + msg.getMessage() + "】");
		ctx.channel().writeAndFlush(messageResponsePacket);
	}
}
