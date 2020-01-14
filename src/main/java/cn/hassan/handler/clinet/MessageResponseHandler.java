package cn.hassan.handler.clinet;

import cn.hassan.core.DateTimeUtils;
import cn.hassan.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 17:04
 * Description:
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
		System.out.println(DateTimeUtils.getLocalDate() + "收到服务端的消息：" + msg.getMessage());
	}
}
