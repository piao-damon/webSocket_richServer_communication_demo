/**
 * <p>Title: Constant.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月31日
 * @version 1.0
 */
package websocket.constants;

/**
 * <p>Title: Constant</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月31日
 */
public class Constant {

	//-- ----------------------------------
	//-- 注明：WebSocket STOMP 通信相关常量
	//-- ----------------------------------
	
	//目标请求前缀
	public static final String APP_DESTINATION_PREFIXES = "/app";
	
	
	//STOMP 终端
	public static final String STOMP_END_POINT = "/chat";
	
	
	//通信类型	双边会谈	我们规定它提供点对点通信（一对一聊天）
	public static final String CHAT_SINGLE = "/single/";
	//通信类型	首脑会议	我们规定它提供多点通信（群聊）
	public static final String CHAT_MULTI = "/multi/";
	
	
	
	
	
	
	//-- -----------------------------------------------------
	//-- 注明：建立握手后存储到WebSocket session attributes中
	//-- -----------------------------------------------------
	
	
	//基于http session的拓展，加入WebSocket session 用来区别各种WebSocket客户端会话
	public static final String HTTP_SESSION_ID = "http-session-id";
	public static final String HTTP_WEBSOCKET_SESSION_ID = "http-websocket-session-id";
	public static final String FROM_HTTP_WEBSOCKET_SESSION_ID = "from-http-websocket-session-id";
	public static final String TO_HTTP_WEBSOCKET_SESSION_ID = "to-http-websocket-session-id";
	public static final String WEBSOCKET_FROM_USER = "websocket-from-user";
	public static final String WEBSOCKET_TO_USER = "websocket-to-user";
	
	
	
}
