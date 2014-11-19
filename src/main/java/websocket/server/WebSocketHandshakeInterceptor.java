/**
 * <p>Title: WebSocketHandshakeInterceptor.java</p>
 * <p></p>
 * @author damon
 * @date 2014年9月28日
 * @version 1.0
 */
package websocket.server;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import websocket.constants.Constant;

/**
 * <p>Title: WebSocketHandshakeInterceptor</p>
 * <p></p> 
 * @author damon
 * @date 2014年9月28日
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
	
	private final static Logger log = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, 
    		WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    	if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(true);
            if (session != null) {
//            	log.info(session.getId());
            	//-- ---------------------------------------------------------
            	//-- 使用sessionid 区分 WebSocketHandler，这里sessionid为32位，
            	//-- 在建立握手后还需要给此属性补充上WebSocketSession的id，
            	//-- 共同来确定当前用户的当前窗口下的会话，以便定向发送消息
            	//-- ---------------------------------------------------------
                String sessionid = (String) session.getId();
                String to_http_websocket_session_id = (String) session.getAttribute(Constant.TO_HTTP_WEBSOCKET_SESSION_ID);
                log.info("#beforeHandshake/HttpSession/to_http_websocket_session_id/" + to_http_websocket_session_id);
                attributes.put(Constant.HTTP_SESSION_ID, sessionid);
                attributes.put(Constant.HTTP_WEBSOCKET_SESSION_ID, "");
                attributes.put(Constant.FROM_HTTP_WEBSOCKET_SESSION_ID, "");
                attributes.put(Constant.TO_HTTP_WEBSOCKET_SESSION_ID, to_http_websocket_session_id == null ? "" : to_http_websocket_session_id);
                attributes.put(Constant.WEBSOCKET_FROM_USER, "");
                attributes.put(Constant.WEBSOCKET_TO_USER, "");
            }
        }
    	
        return true;
    }
 
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    	log.info("afterHandshake/" + System.currentTimeMillis());
    	if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            //强制失效
            session.invalidate();
    	}
    }

}
