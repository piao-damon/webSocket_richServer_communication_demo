/**
 * <p>Title: ChatService.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月29日
 * @version 1.0
 */
package com.xiaoma.kf.cc.im.service;

import org.springframework.messaging.Message;

/**
 * <p>Title: ChatService</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月29日
 */
public interface ChatService {

	/**
	 * <p>send</p>
	 * <p></p>
	 * @param destination
	 * @param message
	 */
	void send(String destination, Message<?> message);

}
