package cn.hassan.start;

import cn.hassan.handler.server.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class IMServer {
    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
						ch.pipeline().addLast(new ServerHandler());
                    }
                });
		bind(bootstrap, 8085);
    }

	private static void bind(final ServerBootstrap bootstrap, final int port) {
		bootstrap.bind(port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println("端口[" + port + "]绑定成功");
			}else {
				System.out.println("端口[" + port + "]绑定失败");
			}
		});
	}
}
