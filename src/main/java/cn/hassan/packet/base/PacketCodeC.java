package cn.hassan.packet.base;

import cn.hassan.core.Packet;
import cn.hassan.core.Serializer;
import cn.hassan.serialisers.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/13 16:41
 * Description:
 */
public class PacketCodeC {

	private static final int MAGIC_NUMBER = 0x66688898;

	private  final Map<Byte, Class<? extends Packet>> packetTypeMap;
	private  final Map<Byte, Serializer> serializerMap;

	private PacketCodeC(){
		packetTypeMap = new HashMap<>();

		serializerMap = new HashMap<>();
		Serializer serializer = new JsonSerializer();
		serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
	}


	/**
	 * 协议结构： 魔数（4个字节）---版本号（1字节）---序列化算法（1字节）---指令（1字节）---数据长度（4字节）---数据（N字节）
	 * @param packet
	 * @return
	 */
	public ByteBuf encode(Packet packet) {
		//1，创建ByteBuf对象
		ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
		//2，序列化java对象
		byte[] bytes = Serializer.DEFAULT.serialise(packet);
		//封装对象
		byteBuf.writeInt(MAGIC_NUMBER);
		byteBuf.writeByte(packet.getVersion());
		byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
		byteBuf.writeByte(packet.getCommand());
		byteBuf.writeInt(bytes.length);
		byteBuf.writeBytes(byteBuf);

		return byteBuf;
	}

	public Packet decode(ByteBuf byteBuf) {
		//跳过魔数
		byteBuf.skipBytes(4);
		//跳过版本
		byteBuf.skipBytes(1);
		//序列化算法
		byte serializerAlgorithm = byteBuf.readByte();
		//指令
		byte command = byteBuf.readByte();
		//数据长度
		int length = byteBuf.readByte();
		byte[] bytes = new byte[length];
		byteBuf.writeBytes(bytes);

		Class<? extends Packet> requestType = getRequestType(command);
		Serializer serializer = getSerializer(serializerAlgorithm);
		if (requestType != null && serializer != null) {
			return serializer.deserialize(requestType, bytes);
		}
		return null;
	}

	private Class<? extends Packet> getRequestType(byte command) {
		return packetTypeMap.get(command);
	}

	private Serializer getSerializer(byte serializeAlgorithm) {
		return serializerMap.get(serializeAlgorithm);
	}
}
