package cn.hassan.start;

import cn.hassan.handler.clinet.ClientHandler;
import cn.hassan.packet.MessageRequestPacket;
import cn.hassan.packet.base.PacketCodeC;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/6 10:23
 * Description:
 */
public class IMClient {
	public static void main(String[] args) {
		NioEventLoopGroup worker = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(worker)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new ClientHandler());
					}
				});
		connect(bootstrap,"127.0.0.1",8085);
	}

	private static void connect(Bootstrap bootstrap, String host, int port) {
		bootstrap.connect(host,port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println("连接成功");
				//控制台开一下连接channel的线程
				Channel channel = ((ChannelFuture)future).channel();
				startConsoleThread(channel);
			}else {
				System.out.println("连接失败");
				//可以重新新连接
				connect(bootstrap,"127.0.0.1",8085);
			}
		});
	}

	private static void startConsoleThread(Channel channel) {
		new Thread(() -> {
			while (!Thread.interrupted()) {
				System.out.println("输入消息发送到服务端：");
				Scanner scanner = new Scanner(System.in);
				String line = scanner.nextLine();

				MessageRequestPacket requestPacket = new MessageRequestPacket();
				requestPacket.setMessage(line);

				ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), requestPacket);
				channel.writeAndFlush(byteBuf);
			}
		}).start();
	}
}
