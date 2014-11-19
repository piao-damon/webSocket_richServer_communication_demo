/**
 * <p>Title: kfAbstractSessionChannelInterceptor.java</p>
 * <p></p>
 * @author damon
 * @date 2014年11月12日
 * @version 1.0
 */
package websocket.interceptor;

import org.springframework.web.socket.WebSocketMessage;

/**
 * <p>Title: kfAbstractSessionChannelInterceptor</p>
 * <p></p> 
 * @author damon
 * @date 2014年11月12日
 */
public abstract class kfAbstractSessionChannelInterceptor implements kfSessionChannelInterceptor {
	
	@Override
	public void send(String from, String to, WebSocketMessage<?> message) {
		
	}
	
}
