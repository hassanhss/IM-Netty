package cn.hassan.handler.server;

import cn.hassan.core.DateTimeUtils;
import cn.hassan.core.Packet;
import cn.hassan.packet.LoginRequestPacket;
import cn.hassan.packet.LoginResponsePacket;
import cn.hassan.packet.MessageRequestPacket;
import cn.hassan.packet.MessageResponsePacket;
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
		if (packet instanceof LoginRequestPacket) {
			LoginRequestPacket requestPacket = (LoginRequestPacket) packet;

			LoginResponsePacket responsePacket = new LoginResponsePacket();
			responsePacket.setVersion(requestPacket.getVersion());
			if (validate(requestPacket)) {
				responsePacket.setSuccess(true);
			}else {
				responsePacket.setSuccess(false);
				responsePacket.setReason("用户名或密码错误！");
			}
			ByteBuf responseBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(),responsePacket);
			ctx.channel().writeAndFlush(responseBuf);
		} else if (packet instanceof MessageRequestPacket) {
			MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
			System.out.println(DateTimeUtils.getLocalDate() + "收到客户端消息：" + messageRequestPacket.getMessage());

			MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
			messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

			ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
			ctx.channel().writeAndFlush(responseByteBuf);
		}
	}

	private boolean validate(LoginRequestPacket requestPacket) {
		if (requestPacket.getUsername().equals("hassan") && requestPacket.getPassword().equals("123456")) {
			return true;
		}else {
			return false;
		}
	}
}
