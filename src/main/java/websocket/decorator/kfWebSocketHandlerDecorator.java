/**
 * <p>Title: kfWebSocketHandlerDecorator.java</p>
 * <p></p>
 * @author damon
 * @date 2014年11月12日
 * @version 1.0
 */
package websocket.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

/**
 * <p>Title: kfWebSocketHandlerDecorator</p>
 * <p></p> 
 * @author damon
 * @date 2014年11月12日
 */
public class kfWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

	private final static Logger log = LoggerFactory.getLogger(kfWebSocketHandlerDecorator.class);
	
	/**
	 * <p></p>
	 * @param delegate
	 */
	public kfWebSocketHandlerDecorator(WebSocketHandler delegate) {
		super(delegate);
		log.info(System.currentTimeMillis() + "/#kfWebSocketHandlerDecorator");
		// TODO Auto-generated constructor stub
	}

}
