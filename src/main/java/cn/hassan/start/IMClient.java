package cn.hassan.start;

import cn.hassan.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
						ch.pipeline().addLast(new FirstClientHandler());
					}
				});
		connect(bootstrap,"127.0.0.1",8085);
	}

	private static void connect(Bootstrap bootstrap, String host, int port) {
		bootstrap.connect(host,port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println("连接成功");
			}else {
				System.out.println("连接失败");
				//可以重新新连接
				connect(bootstrap,"127.0.0.1",8085);
			}
		});
	}
}
