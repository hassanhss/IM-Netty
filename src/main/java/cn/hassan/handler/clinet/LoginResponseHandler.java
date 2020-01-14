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
	protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
		if (msg.isSuccess()) {
			System.out.println(DateTimeUtils.getLocalDate() + ": 客户端登陆成功" + "userId --- " + msg.getUserId());
			LoginUtil.markAsLogin(ctx.channel());
		}else {
			System.out.println(DateTimeUtils.getLocalDate() + ": 客户端登陆失败：原因" + msg.getReason());
		}
	}
}
