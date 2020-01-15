package cn.hassan.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/15 10:57
 * Description:
 */
public class IMIdleStateHandler extends IdleStateHandler {
	private static final int READER_IDLE_TIME = 15;

	public IMIdleStateHandler() {
		/**
		 * 其中第一个表示读空闲时间，指的是在这段时间内如果没有数据读到，就表示连接假死
		 * 第二个是写空闲时间，指的是 在这段时间如果没有写数据，就表示连接假死
		 * 第三个参数是读写空闲时间，表示在这段时间内如果没有产生数据读或者写，就表示连接假死
		 */
		super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
	}

	@Override
	protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
		System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
		ctx.channel().close();
	}
}
