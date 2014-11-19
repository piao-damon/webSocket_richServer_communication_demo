/**
 * <p>Title: HttpWebSocketSession.java</p>
 * <p></p>
 * @author damon
 * @date 2014年11月11日
 * @version 1.0
 */
package websocket.entity;

/**
 * <p>Title: HttpWebSocketSession</p>
 * <p></p> 
 * @author damon
 * @date 2014年11月11日
 */
public class HttpWebSocketSession {

	private String http_session_id;
	
	private String websocket_session_id;
	
	private String http_websocket_session_id;
	
//***********************************	getter and setter	*************************************//
	
	/**
	 * @return the http_websocket_session_id
	 */
	public String getHttp_websocket_session_id() {
		return http_websocket_session_id;
	}

	/**
	 * @param http_websocket_session_id the http_websocket_session_id to set
	 */
	public void setHttp_websocket_session_id(String http_websocket_session_id) {
		this.http_websocket_session_id = http_websocket_session_id;
	}

	/**
	 * @return the http_session_id
	 */
	public String getHttp_session_id() {
		return http_session_id;
	}

	/**
	 * @param http_session_id the http_session_id to set
	 */
	public void setHttp_session_id(String http_session_id) {
		this.http_session_id = http_session_id;
	}

	/**
	 * @return the websocket_session_id
	 */
	public String getWebsocket_session_id() {
		return websocket_session_id;
	}

	/**
	 * @param websocket_session_id the websocket_session_id to set
	 */
	public void setWebsocket_session_id(String websocket_session_id) {
		this.websocket_session_id = websocket_session_id;
	}
	
}
