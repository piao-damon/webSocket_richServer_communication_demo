/**
 * <p>Title: kfWebSocketHandler.java</p>
 * <p></p>
 * @author damon
 * @date 2014年9月28日
 * @version 1.0
 */
package websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import websocket.constants.Constant;
import websocket.interceptor.kfAbstractSessionChannelInterceptor;

/**
 * <p>Title: kfWebSocketHandler</p>
 * <p></p> 
 * @author damon
 * @date 2014年9月28日
 */
public class kfWebSocketHandler extends kfAbstractSessionChannelInterceptor implements WebSocketHandler {

	private final static Logger log = LoggerFactory.getLogger(kfWebSocketHandler.class);
	
    public static final ArrayList<WebSocketSession> users;
    
    public static final Map<String, WebSocketSession> usersMap;
 
    static {
        users = new ArrayList<WebSocketSession>();
        usersMap = new HashMap<String, WebSocketSession>();
    }
 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	String http_websocket_session_id = session.getAttributes().get(Constant.HTTP_SESSION_ID) + session.getId();
        session.getAttributes().put(Constant.HTTP_WEBSOCKET_SESSION_ID, http_websocket_session_id);
        String to_http_websocket_session_id = (String) session.getAttributes().get(Constant.TO_HTTP_WEBSOCKET_SESSION_ID);
        if(to_http_websocket_session_id != null && !("").equals(to_http_websocket_session_id)){
        	WebSocketSession to_session = usersMap.get(to_http_websocket_session_id);
        	to_session.getAttributes().put(Constant.TO_HTTP_WEBSOCKET_SESSION_ID, http_websocket_session_id);
        	//发送消息通知连接建立
        	sendTextMessage(to_session, http_websocket_session_id + "客服为您服务!");
        }
        
        users.add(session);
        usersMap.put(session.getAttributes().get(Constant.HTTP_WEBSOCKET_SESSION_ID).toString(), session);
        //发送文本消息
        sendTextMessage(session, "编号:" + http_websocket_session_id + "号#会话窗口创建成功！");
        
        log.info("------------------------------------------------------------------");
        
        Iterator<String> attr_iterator = session.getAttributes().keySet().iterator();
        while(attr_iterator.hasNext()){
        	log.info(attr_iterator.next() + "/" + session.getAttributes().get(attr_iterator.next()).toString());
        	
        }
        
        log.info("------------------------------------------------------------------");
        
        log.info("------------------------------------------------------------------");
        
        Iterator<String> iterator = usersMap.keySet().iterator();
        WebSocketSession webSocketSession;
        while(iterator.hasNext()){
        	webSocketSession = usersMap.get(iterator.next());
        	log.info(webSocketSession.getAttributes().get(Constant.HTTP_WEBSOCKET_SESSION_ID).toString());
        	
        }
        
        log.info("------------------------------------------------------------------");
        
    }
 
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	log.info("。。。飘过#handleMessage。。。");
    	log.info("Payload/" + message.getPayload());
    	log.info("PayloadLength/" + message.getPayloadLength());
//    	String from = session.getAttributes().get(Constant.FROM_HTTP_WEBSOCKET_SESSION_ID).toString();
    	String to = session.getAttributes().get(Constant.TO_HTTP_WEBSOCKET_SESSION_ID).toString();
    	if(to != null && !("").equals(to)){
    		//目标session
    		WebSocketSession to_session = usersMap.get(to);
    		//向目标发送消息
    		sendMessage(to_session, message);
    	} else {
    		sendTextMessage(session, "正在为您连接客服，请稍后...");
    	}
    	
    }
 
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        users.remove(session);
        usersMap.remove(session.getAttributes().get(Constant.HTTP_WEBSOCKET_SESSION_ID));
        log.info("oh, no");
        log.info("------------------------------------------------------------------");
        
        Iterator<String> iterator = usersMap.keySet().iterator();
        WebSocketSession webSocketSession;
        while(iterator.hasNext()){
        	webSocketSession = usersMap.get(iterator.next());
        	log.info(webSocketSession.getAttributes().get(Constant.HTTP_WEBSOCKET_SESSION_ID).toString());
        	
        }
        
        log.info("------------------------------------------------------------------");
    }
 
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        users.remove(session);
        usersMap.remove(session.getAttributes().get(Constant.HTTP_WEBSOCKET_SESSION_ID));
        int code = closeStatus.getCode();
        String reason = closeStatus.getReason(); 
        log.info("状态码：" + code);
        log.info("退出原因：" + reason);
        log.info("See you.");
        
        log.info("------------------------------------------------------------------");
        
        Iterator<String> iterator = usersMap.keySet().iterator();
        WebSocketSession webSocketSession;
        while(iterator.hasNext()){
        	webSocketSession = usersMap.get(iterator.next());
        	log.info(webSocketSession.getAttributes().get(Constant.HTTP_WEBSOCKET_SESSION_ID).toString());
        	
        }
        
        log.info("------------------------------------------------------------------");
    }
 
    @Override
    public boolean supportsPartialMessages() {
    	log.info("。。。飘过#supportsPartialMessages。。。");
//    	return false;
        return true;
    }
 
//    /**
//     * 给所有在线用户发送消息
//     *
//     * @param message
//     */
//    public void sendMessageToUsers(TextMessage message) {
//        for (WebSocketSession user : users) {
//            try {
//                if (user.isOpen()) {
//                    user.sendMessage(message);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
// 
//    /**
//     * 给某个用户发送消息
//     *
//     * @param userName
//     * @param message
//     */
//    public void sendMessageToUser(String sessionid, TextMessage message) {
//        for (WebSocketSession user : users) {
//            if (user.getAttributes().get(Constant.HTTP_WEBSOCKET_SESSION_ID).equals(sessionid)) {
//                try {
//                    if (user.isOpen()) {
//                        user.sendMessage(message);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//        }
//    }
    //---------------------------------------------------------------------------------------------------
    //sendTextMessage
    //sendBinaryMessage
    //sendPingMessage
    //sendPongMessage
    //---------------------------------------------------------------------------------------------------
    private void sendMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
    	session.sendMessage(message);
	}
    
    private void sendTextMessage(WebSocketSession session, String message){
    	try {
			sendMessage(session, new TextMessage(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
//	
//    private void sendBinaryMessage(WebSocketSession session, ByteBuffer message){
//    	try {
//			send(session, new BinaryMessage(message));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
//	
//    private void sendPingMessage(WebSocketSession session, ByteBuffer message){
//    	try {
//			send(session, new PingMessage(message));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
//	
//    private void sendPongMessage(WebSocketSession session, ByteBuffer message){
//    	try {
//			send(session, new PongMessage(message));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
    
    
}
