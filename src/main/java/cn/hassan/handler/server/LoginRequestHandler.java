package cn.hassan.handler.server;

import cn.hassan.core.DateTimeUtils;
import cn.hassan.packet.LoginRequestPacket;
import cn.hassan.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 16:44
 * Description:
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
		System.out.println(DateTimeUtils.getLocalDate() + " 收到客户端登陆请求");
		LoginResponsePacket responsePacket = new LoginResponsePacket();
		responsePacket.setVersion(msg.getVersion());
		if (validate(msg)) {
			responsePacket.setSuccess(true);
			responsePacket.setReason("登陆成功");
		}else {
			responsePacket.setSuccess(false);
			responsePacket.setReason("用户名或密码错误！");
		}
		ctx.channel().writeAndFlush(responsePacket);
	}

	private boolean validate(LoginRequestPacket requestPacket) {
		if (requestPacket.getUsername().equals("hassan") && requestPacket.getPassword().equals("123456")) {
			return true;
		}else {
			return false;
		}
	}
}
