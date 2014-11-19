/**
 * <p>Title: ConstantsUtil.java</p>
 * <p></p>
 * @author damon
 * @date 2014年11月3日
 * @version 1.0
 */
package com.xiaoma.kf.common.global;

import websocket.constants.Constant;

/**
 * <p>Title: ConstantsUtil</p>
 * <p></p> 
 * @author damon
 * @date 2014年11月3日
 */
public class ConstantsUtil {
	
	
	//目标请求前缀
	public static String getApp_Destination_Prefix() {
		return Constant.APP_DESTINATION_PREFIXES;
	}
	
	//STOMP 终端
	public static String getStomp_End_Point() {
		return Constant.STOMP_END_POINT;
	}
	
	//通信类型	（一对一单聊）
	public static String getChat_Single() {
		return Constant.CHAT_SINGLE;
	}
	
	//通信类型	（多对多群聊）
	public static String getChat_Multi() {
		return Constant.CHAT_MULTI;
	}

}
