/**
 * <p>Title: kfSessionChannelInterceptor.java</p>
 * <p></p>
 * @author damon
 * @date 2014年11月12日
 * @version 1.0
 */
package websocket.interceptor;

import org.springframework.web.socket.WebSocketMessage;

/**
 * <p>Title: kfSessionChannelInterceptor</p>
 * <p>通信会话通道过滤器</p> 
 * @author damon
 * @date 2014年11月12日
 */
public interface kfSessionChannelInterceptor {

//	/**
//	 * <p>send</p>
//	 * <p>广播</p>
//	 * @param message
//	 */
//	void send(WebSocketMessage<?> message);
	
	/**
	 * <p>send</p>
	 * <p>点对点通话</p>
	 * @param from @see websocket.constants.Constant.FROM_HTTP_WEBSOCKET_SESSION_ID
	 * @param to @see websocket.constants.Constant.TO_HTTP_WEBSOCKET_SESSION_ID
	 * @param message 
	 */
	void send(String from, String to, WebSocketMessage<?> message);
	
//	void send(WebSocketSession session, WebSocketMessage<?> message) throws IOException;
	
	
}
