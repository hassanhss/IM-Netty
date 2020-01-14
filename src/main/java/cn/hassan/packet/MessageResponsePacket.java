package cn.hassan.packet;

import cn.hassan.core.Packet;
import lombok.Data;

import static cn.hassan.core.Command.MESSAGE_RESPONSE;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 14:59
 * Description:
 */
@Data
public class MessageResponsePacket extends Packet {

	private String fromUserId;

	private String fromUsername;

	private String message;

	@Override
	public Byte getCommand() {
		return MESSAGE_RESPONSE;
	}
}
