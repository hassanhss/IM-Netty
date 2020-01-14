package cn.hassan.handler.server;

import cn.hassan.core.Packet;
import cn.hassan.packet.LoginRequestPacket;
import cn.hassan.packet.LoginResponsePacket;
import cn.hassan.packet.base.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 8:55
 * Description:
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		//解码
		Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
		//判断是否是登陆请求
		LoginResponsePacket responsePacket = null;
		if (packet instanceof LoginRequestPacket) {
			LoginRequestPacket requestPacket = (LoginRequestPacket) packet;

			responsePacket = new LoginResponsePacket();
			responsePacket.setVersion(packet.getVersion());
			if (validate(requestPacket)) {
				responsePacket.setSuccess(true);
			}else {
				responsePacket.setSuccess(false);
				responsePacket.setReason("用户名或密码错误！");
			}
		}
		ByteBuf responseBuf = PacketCodeC.INSTANCE.encode(responsePacket);
		ctx.channel().writeAndFlush(responseBuf);
	}

	private boolean validate(LoginRequestPacket requestPacket) {
		if (requestPacket.getUsername().equals("hassan") && requestPacket.getPassword().equals("123456")) {
			return true;
		}else {
			return false;
		}
	}
}
