package cn.hassan.core;

import org.joda.time.DateTime;

/**
 * Created with idea
 * Author: hss
 * Date: 2020/1/14 15:53
 * Description:
 */
public class DateTimeUtils {

	/**
	 * 返回格式化的时间格式
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getLocalDate() {
		DateTime dateTime = new DateTime();
		return dateTime.toString("yyyy-MM-dd HH:mm:ss");
	}
}
