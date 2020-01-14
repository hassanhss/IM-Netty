package cn.hassan.packet;

import cn.hassan.core.Packet;
import lombok.Data;

import static cn.hassan.core.Command.LOGIN_RESPONSE;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 9:33
 * Description:
 */
@Data
public class LoginResponsePacket extends Packet {

	private String userId;
	private String username;
	private boolean success;
	private String reason;

	@Override
	public Byte getCommand() {
		return LOGIN_RESPONSE;
	}
}
