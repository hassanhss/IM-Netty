package cn.hassan.core;


import io.netty.channel.Channel;
import io.netty.util.Attribute;

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

	public static boolean hasLogin(Channel channel) {
		Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

		return loginAttr.get() != null;
	}
}
