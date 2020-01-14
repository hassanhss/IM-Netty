package cn.hassan.handler.clinet;

import cn.hassan.core.Packet;
import cn.hassan.packet.LoginRequestPacket;
import cn.hassan.packet.LoginResponsePacket;
import cn.hassan.packet.base.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 8:55
 * Description:
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(new Date() + ": 客户端开始登陆");
		LoginRequestPacket requestPacket = new LoginRequestPacket();
		requestPacket.setUserId(UUID.randomUUID().toString());
		requestPacket.setUsername("hassan");
		requestPacket.setPassword("123456");

		ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(requestPacket);
		ctx.channel().writeAndFlush(byteBuf);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
		if (packet instanceof LoginResponsePacket) {
			LoginResponsePacket responsePacket = (LoginResponsePacket) packet;
			if (responsePacket.isSuccess()) {
				System.out.println(new Date() + "：客户端登陆成功！");
			}else {
				System.out.println(new Date() + "：客户端登陆失败" + ": 原因" + responsePacket.getReason());
			}
		}
	}
}
