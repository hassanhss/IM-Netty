package cn.hassan.packet.base;

import cn.hassan.core.Packet;
import cn.hassan.core.Serializer;
import cn.hassan.packet.LoginRequestPacket;
import cn.hassan.packet.LoginResponsePacket;
import cn.hassan.packet.MessageRequestPacket;
import cn.hassan.packet.MessageResponsePacket;
import cn.hassan.serialisers.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static cn.hassan.core.Command.*;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/13 16:41
 * Description:
 */
public class PacketCodeC {

	private static final int MAGIC_NUMBER = 0x66688898;

	public static final PacketCodeC INSTANCE = new PacketCodeC();

	private final Map<Byte, Class<? extends Packet>> packetTypeMap;
	private final Map<Byte, Serializer> serializerMap;


	private PacketCodeC() {
		packetTypeMap = new HashMap<>();

		packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
		packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
		packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
		packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

		serializerMap = new HashMap<>();
		Serializer serializer = new JsonSerializer();
		serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
	}


	public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
		// 1. 创建 ByteBuf 对象
		ByteBuf byteBuf = byteBufAllocator.ioBuffer();
		// 2. 序列化 java 对象
		byte[] bytes = Serializer.DEFAULT.serialise(packet);

		// 3. 实际编码过程
		byteBuf.writeInt(MAGIC_NUMBER);
		byteBuf.writeByte(packet.getVersion());
		byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
		byteBuf.writeByte(packet.getCommand());
		byteBuf.writeInt(bytes.length);
		byteBuf.writeBytes(bytes);

		return byteBuf;
	}


	public Packet decode(ByteBuf byteBuf) {
		// 跳过 magic number
		byteBuf.skipBytes(4);

		// 跳过版本号
		byteBuf.skipBytes(1);

		// 序列化算法
		byte serializeAlgorithm = byteBuf.readByte();

		// 指令
		byte command = byteBuf.readByte();

		// 数据包长度
		int length = byteBuf.readInt();

		byte[] bytes = new byte[length];
		byteBuf.readBytes(bytes);

		Class<? extends Packet> requestType = getRequestType(command);
		Serializer serializer = getSerializer(serializeAlgorithm);

		if (requestType != null && serializer != null) {
			return serializer.deserialize(requestType, bytes);
		}

		return null;
	}

	private Serializer getSerializer(byte serializeAlgorithm) {

		return serializerMap.get(serializeAlgorithm);
	}

	private Class<? extends Packet> getRequestType(byte command) {

		return packetTypeMap.get(command);
	}
}
