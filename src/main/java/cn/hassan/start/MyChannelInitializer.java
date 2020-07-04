package cn.hassan.start;

import cn.hassan.handler.HeartBeatRequestHandler;
import cn.hassan.handler.IMIdleStateHandler;
import cn.hassan.handler.server.AuthHandler;
import cn.hassan.handler.server.LoginRequestHandler;
import cn.hassan.handler.server.MessageRequestHandler;
import cn.hassan.packet.base.PackerDecoder;
import cn.hassan.packet.base.PacketEncoder;
import cn.hassan.packet.base.Spliter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        // 空闲检测
        ch.pipeline().addLast(new IMIdleStateHandler());
        //数据拆包粘包使用
        //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
        ch.pipeline().addLast(new Spliter());
        ch.pipeline().addLast(new PackerDecoder());
        ch.pipeline().addLast(new LoginRequestHandler());
        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
        ch.pipeline().addLast(new AuthHandler());
        ch.pipeline().addLast(new MessageRequestHandler());
        ch.pipeline().addLast(new PacketEncoder());
    }
}
