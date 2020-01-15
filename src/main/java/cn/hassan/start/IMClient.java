package cn.hassan.start;

import cn.hassan.core.SessionUtil;
import cn.hassan.handler.HeartBeatTimerHandler;
import cn.hassan.handler.IMIdleStateHandler;
import cn.hassan.handler.clinet.LoginResponseHandler;
import cn.hassan.handler.clinet.MessageResponseHandler;
import cn.hassan.packet.LoginRequestPacket;
import cn.hassan.packet.MessageRequestPacket;
import cn.hassan.packet.base.PackerDecoder;
import cn.hassan.packet.base.PacketEncoder;
import cn.hassan.packet.base.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
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
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					protected void initChannel(SocketChannel ch) throws Exception {
						// 空闲检测
						ch.pipeline().addLast(new IMIdleStateHandler());
						//数据拆包粘包使用
						//ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
						ch.pipeline().addLast(new Spliter());
						ch.pipeline().addLast(new PackerDecoder());
						ch.pipeline().addLast(new LoginResponseHandler());
						ch.pipeline().addLast(new MessageResponseHandler());
						ch.pipeline().addLast(new PacketEncoder());
						// 心跳定时器
						ch.pipeline().addLast(new HeartBeatTimerHandler());
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
		Scanner scanner = new Scanner(System.in);
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
		new Thread(() -> {
			while (!Thread.interrupted()) {
				if (!SessionUtil.hasLogin(channel)) {
					System.out.print("输入用户名登录: ");
					String username = scanner.nextLine();
					loginRequestPacket.setUsername(username);
					// 密码使用默认的
					loginRequestPacket.setPassword("pwd");
					// 发送登录数据包
					channel.writeAndFlush(loginRequestPacket);
					waitForLoginResponse();
				}else {
					String toUserId = scanner.next();
					String message = scanner.next();
					channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
				}
			}
		}).start();
	}

	private static void waitForLoginResponse() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
	}
}
