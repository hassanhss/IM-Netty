package cn.hassan.handler.server;

import cn.hassan.core.DateTimeUtils;
import cn.hassan.core.Session;
import cn.hassan.core.SessionUtil;
import cn.hassan.packet.MessageRequestPacket;
import cn.hassan.packet.MessageResponsePacket;
import io.netty.channel.Channel;
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

		//1,拿到消息发送方的会话信息
		Session session = SessionUtil.getSession(ctx.channel());
		//2,通过消息发送方的会话信息构建要发送的信息
		MessageResponsePacket responsePacket = new MessageResponsePacket();
		responsePacket.setFromUserId(session.getUserId());
		responsePacket.setFromUsername(session.getUsername());
		responsePacket.setMessage(msg.getMessage());
		//3,拿到消息接收方的channel
		Channel channel = SessionUtil.getChannel(msg.getToUserId());
		//4,将消息发送给接收方
		if (channel != null && SessionUtil.hasLogin(channel)) {
			channel.writeAndFlush(responsePacket);
		}else {
			System.err.println("[" + msg.getToUserId() + "] 不在线，发送失败!");
		}
	}
}
