/**
 * <p>Title: kfChannelInterceptor.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月28日
 * @version 1.0
 */
package websocket.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * <p>Title: kfChannelInterceptor</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月28日
 */
public class kfChannelInterceptor1 extends ChannelInterceptorAdapter {

	private final static Logger log = LoggerFactory.getLogger(kfChannelInterceptor1.class);
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		log.info(System.currentTimeMillis() + "/#preSend");
	    log.info("message : " + (message.getPayload() instanceof String ? (String)message.getPayload() : ""));
//	    log.info("channel : " + channel.getClass().getSimpleName());
	    
	    return message;
	}
	
	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		log.info(System.currentTimeMillis() + "/#postSend");
//		log.info("channel : " + channel);
		
	}

	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		log.info(System.currentTimeMillis() + "/#afterSendCompletion");
//		log.info("channel : " + channel);
		
	}

	public boolean preReceive(MessageChannel channel) {
		log.info(System.currentTimeMillis() + "/#preReceive");
//		log.info("channel : " + channel);
		
		return true;
	}

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		log.info(System.currentTimeMillis() + "/#postReceive");
//		log.info("channel : " + channel);
		
		return message;
	}

	@Override
	public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
		log.info(System.currentTimeMillis() + "/#afterReceiveCompletion");
//		log.info("channel : " + channel);
		
	}

}
