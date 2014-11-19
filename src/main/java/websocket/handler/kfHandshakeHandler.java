/**
 * <p>Title: kfHandshakeHandler.java</p>
 * <p></p>
 * @author damon
 * @date 2014年11月10日
 * @version 1.0
 */
package websocket.handler;

import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;

/**
 * <p>Title: kfHandshakeHandler</p>
 * <p></p> 
 * @author damon
 * @date 2014年11月10日
 */
public class kfHandshakeHandler implements HandshakeCompletedListener,
		HandshakeHandler {

	private final static Logger log = LoggerFactory.getLogger(kfHandshakeHandler.class);
			
	/* (non-Javadoc)
	 * <p>doHandshake</p>
	 * <p></p>
	 * @param request
	 * @param response
	 * @param wsHandler
	 * @param attributes
	 * @return
	 * @throws HandshakeFailureException
	 * @see org.springframework.web.socket.server.HandshakeHandler#doHandshake(org.springframework.http.server.ServerHttpRequest, org.springframework.http.server.ServerHttpResponse, org.springframework.web.socket.WebSocketHandler, java.util.Map)
	 */
	@Override
	public boolean doHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws HandshakeFailureException {
		// TODO Auto-generated method stub
		log.info(request.getHeaders().getOrigin());
    	log.info(request.getHeaders().getUpgrade());
    	log.info(request.getHeaders().getPragma());
    	
    	log.info(response.getHeaders().getOrigin());
    	log.info(response.getHeaders().getUpgrade());
    	log.info(response.getHeaders().getPragma());
    	
    	Iterator<String> iter = attributes.keySet().iterator();
    	String key;
    	while(iter.hasNext()){
    		if(attributes.containsKey(key = iter.next())){
    			log.info(attributes.get(key).toString());
    		}
    	}
    	
		return true;
	}

	/* (non-Javadoc)
	 * <p>handshakeCompleted</p>
	 * <p></p>
	 * @param arg0
	 * @see javax.net.ssl.HandshakeCompletedListener#handshakeCompleted(javax.net.ssl.HandshakeCompletedEvent)
	 */
	@Override
	public void handshakeCompleted(HandshakeCompletedEvent arg0) {
		// TODO Auto-generated method stub

		log.info(System.currentTimeMillis() + "/#handshakeCompleted");
		log.info(arg0.getSocket().getLocalSocketAddress().toString());
		log.info(arg0.getSocket().getRemoteSocketAddress().toString());
	}

}
