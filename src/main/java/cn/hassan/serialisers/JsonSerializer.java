package cn.hassan.serialisers;

import cn.hassan.core.Serializer;
import cn.hassan.core.SerializerAlgorithm;
import com.alibaba.fastjson.JSON;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/13 16:10
 * Description:
 */
public class JsonSerializer implements Serializer {

	public byte getSerializerAlgorithm() {
		return SerializerAlgorithm.JSON;
	}

	public byte[] serialise(Object object) {
		return JSON.toJSONBytes(object);
	}

	public <T> T deserialize(Class<T> clazz, byte[] bytes) {
		return JSON.parseObject(bytes, clazz);
	}
}
