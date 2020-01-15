package cn.hassan.packet;

import cn.hassan.core.Packet;

import static cn.hassan.core.Command.HEARTBEAT_REQUEST;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/15 11:07
 * Description:
 */
public class HeartBeatRequestPacket extends Packet {
	@Override
	public Byte getCommand() {
		return HEARTBEAT_REQUEST;
	}
}
