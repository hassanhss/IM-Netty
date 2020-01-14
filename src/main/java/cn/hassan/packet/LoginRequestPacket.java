package cn.hassan.packet;

import cn.hassan.core.Packet;
import lombok.Data;

import static cn.hassan.core.Command.LOGIN_REQUEST;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/13 15:22
 * Description: 登录数据包
 */
@Data
public class LoginRequestPacket extends Packet {

	private String userId;
	private String username;
	private String password;

	public Byte getCommand() {
		return LOGIN_REQUEST;
	}
}
