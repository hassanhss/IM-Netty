package cn.hassan.packet;

import cn.hassan.core.Packet;

import static cn.hassan.core.Command.HEARTBEAT_RESPONSE;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/15 11:20
 * Description:
 */
public class HeartBeatResponsePacket extends Packet {
	@Override
	public Byte getCommand() {
		return HEARTBEAT_RESPONSE;
	}
}
