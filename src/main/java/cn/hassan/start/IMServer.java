package cn.hassan.start;

import cn.hassan.core.DateTimeUtils;
import cn.hassan.handler.HeartBeatRequestHandler;
import cn.hassan.handler.IMIdleStateHandler;
import cn.hassan.handler.server.AuthHandler;
import cn.hassan.handler.server.LoginRequestHandler;
import cn.hassan.handler.server.MessageRequestHandler;
import cn.hassan.packet.base.PackerDecoder;
import cn.hassan.packet.base.PacketEncoder;
import cn.hassan.packet.base.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class IMServer {
    public static void main(String[] args) {

        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new MyChannelInitializer());
		bind(bootstrap, 8085);
    }

	private static void bind(final ServerBootstrap bootstrap, final int port) {
		bootstrap.bind(port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println(DateTimeUtils.getLocalDate() + " 服务端启动成功");
			}else {
				System.out.println(DateTimeUtils.getLocalDate() + " 服务端启动失败");
			}
		});
	}
}
