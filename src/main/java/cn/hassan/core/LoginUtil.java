package cn.hassan.core;


import io.netty.channel.Channel;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 14:30
 * Description:
 */
public class LoginUtil {

	/**
	 * 标识登陆
	 * @param channel
	 */
	public static void markAsLogin(Channel channel) {
		channel.attr(Attributes.LOGIN).set(true);
	}
}
