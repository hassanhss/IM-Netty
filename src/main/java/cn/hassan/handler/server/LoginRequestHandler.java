package cn.hassan.handler.server;

import cn.hassan.core.DateTimeUtils;
import cn.hassan.core.LoginUtil;
import cn.hassan.core.Session;
import cn.hassan.core.SessionUtil;
import cn.hassan.packet.LoginRequestPacket;
import cn.hassan.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 16:44
 * Description:
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {

		String userId = randomUserId();

		SessionUtil.bindSession(new Session(userId,msg.getUsername()),ctx.channel());

		LoginResponsePacket responsePacket = new LoginResponsePacket();
		responsePacket.setUserId(userId);
		System.out.println(DateTimeUtils.getLocalDate() + " 收到客户端登陆请求");
		responsePacket.setVersion(msg.getVersion());
		if (validate(msg)) {
			LoginUtil.markAsLogin(ctx.channel());
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
			return true;
		}
	}
	private static String randomUserId() {
		return UUID.randomUUID().toString().split("-")[0];
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SessionUtil.unBindSession(ctx.channel());
	}
}
