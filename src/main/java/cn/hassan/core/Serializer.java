package cn.hassan.core;

import cn.hassan.serialisers.JsonSerializer;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/13 16:03
 * Description: 序列化接口
 */
public interface Serializer {

	byte JSON_SERIALIZER = 1;

	Serializer DEFAULT = new JsonSerializer();

	/**
	 * 序列化算法
	 */
	byte getSerializerAlgorithm();

	/**
	 * java 对象转还为二进制
	 */
	byte[] serialise(Object object);

	/**
	 *二进制转化为java对象
	 */
	<T> T deserialize(Class<T> clazz, byte[] bytes);
}
