package cn.hassan.core;

import io.netty.util.AttributeKey;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 14:29
 * Description:
 */
public interface Attributes {
	AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
