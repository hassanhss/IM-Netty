package cn.hassan.start;

import cn.hassan.core.DateTimeUtils;
import cn.hassan.handler.server.AuthHandler;
import cn.hassan.handler.server.LoginRequestHandler;
import cn.hassan.handler.server.MessageRequestHandler;
import cn.hassan.handler.server.ServerHandler;
import cn.hassan.packet.base.PackerDecoder;
import cn.hassan.packet.base.PacketEncoder;
import cn.hassan.packet.base.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class IMServer {
    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
						//数据拆包粘包使用
						//ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
						ch.pipeline().addLast(new Spliter());
						ch.pipeline().addLast(new PackerDecoder());
						ch.pipeline().addLast(new LoginRequestHandler());
						ch.pipeline().addLast(new AuthHandler());
						ch.pipeline().addLast(new MessageRequestHandler());
						ch.pipeline().addLast(new PacketEncoder());
                    }
                });
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
