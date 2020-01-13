package cn.hassan.core;

import lombok.Data;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/13 15:16
 * Description: 协议基类
 */
@Data
public abstract class Packet {

	/**
	 * 协议版本
	 */
	private Byte version = 1;

	/**
	 * 指令
	 */
	public abstract Byte getCommand();
}
