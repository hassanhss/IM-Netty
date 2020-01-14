package cn.hassan.packet;

import cn.hassan.core.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import static cn.hassan.core.Command.MESSAGE_REQUEST;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 14:27
 * Description:
 */
@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet {

	private String message;

	@Override
	public Byte getCommand() {
		return MESSAGE_REQUEST;
	}
}
