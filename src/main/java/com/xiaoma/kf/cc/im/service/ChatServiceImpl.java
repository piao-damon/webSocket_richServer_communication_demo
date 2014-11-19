/**
 * <p>Title: ChatServiceImpl.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月29日
 * @version 1.0
 */
package com.xiaoma.kf.cc.im.service;

import javax.transaction.Transactional;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * <p>Title: ChatServiceImpl</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月29日
 */
@Service
@Transactional
public class ChatServiceImpl implements ChatService {

//	@Autowired
//    private SimpMessagingTemplate messageTemplate;

	/* (non-Javadoc)
	 * <p>send</p>
	 * <p></p>
	 * @param destination
	 * @param message
	 * @see com.xiaoma.kf.cc.im.service.ChatService#send(java.lang.String, org.springframework.messaging.Message)
	 */
	@Override
	public void send(String destination, Message<?> message) {
		//messageTemplate.convertAndSend(destination, message.getPayload());
	}
	
}
