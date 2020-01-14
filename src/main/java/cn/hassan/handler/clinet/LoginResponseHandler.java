package cn.hassan.handler.clinet;

import cn.hassan.core.DateTimeUtils;
import cn.hassan.core.LoginUtil;
import cn.hassan.packet.LoginRequestPacket;
import cn.hassan.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 17:04
 * Description:
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LoginRequestPacket requestPacket = new LoginRequestPacket();
		requestPacket.setUserId(UUID.randomUUID().toString());
		requestPacket.setUsername("hassan");
		requestPacket.setPassword("123456");
		ctx.channel().writeAndFlush(requestPacket);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
		if (msg.isSuccess()) {
			System.out.println(DateTimeUtils.getLocalDate() + ": 客户端登陆成功");
			LoginUtil.markAsLogin(ctx.channel());
		}else {
			System.out.println(DateTimeUtils.getLocalDate() + ": 客户端登陆失败：原因" + msg.getReason());
		}
	}
}
