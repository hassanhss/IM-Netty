package cn.hassan.core;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/13 15:20
 * Description: 指令常量
 */
public interface Command {

	Byte LOGIN_REQUEST = 1;
	Byte LOGIN_RESPONSE = 2;
	Byte MESSAGE_REQUEST = 3;
	Byte MESSAGE_RESPONSE = 4;
	Byte HEARTBEAT_REQUEST = 5;
	Byte HEARTBEAT_RESPONSE = 6;
}
