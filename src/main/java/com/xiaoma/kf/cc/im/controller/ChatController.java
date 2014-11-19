/**
 * <p>Title: ChatController.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月29日
 * @version 1.0
 */
package com.xiaoma.kf.cc.im.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketSession;

import websocket.constants.Constant;
import websocket.entity.HttpWebSocketSession;
import websocket.handler.kfWebSocketHandler;

import com.xiaoma.kf.cc.im.service.ChatService;

/**
 * <p>Title: ChatController</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月29日
 */
@Controller
@RequestMapping("chat")
public class ChatController {
	private final static Logger log = LoggerFactory.getLogger(ChatController.class);
	
	@Autowired
	private ChatService chatService;
	
	@RequestMapping( value = "/kf", method = RequestMethod.GET)
	public String kf() {
		log.info("#kf currentTimeMillis : " + System.currentTimeMillis());
		return "im/kfclient/index";
	}
	
	@RequestMapping( value = "/kh", method = RequestMethod.GET)
	public String kh(ModelMap map) {
		log.info("#kh currentTimeMillis : " + System.currentTimeMillis());
		return "im/khclient/chat";
	}
	
	@RequestMapping( value = "/onlineusers", method = RequestMethod.GET)
	public ModelAndView online_user_list() {
		log.info("#online_user_list currentTimeMillis : " + System.currentTimeMillis());
		ModelAndView mav = new ModelAndView("im/chat/onlineusers");
		List<HttpWebSocketSession> onlineusers = new ArrayList<HttpWebSocketSession>();
		HttpWebSocketSession httpWebSocketSession;
		ArrayList<WebSocketSession> users = kfWebSocketHandler.users;
		for(WebSocketSession session : users){
			String http_websocket_session_id = session.getAttributes().get(Constant.HTTP_WEBSOCKET_SESSION_ID).toString();
			httpWebSocketSession = new HttpWebSocketSession();
			httpWebSocketSession.setHttp_websocket_session_id(http_websocket_session_id);
			onlineusers.add(httpWebSocketSession);
		}
		mav.addObject("onlineusers", onlineusers);
		return mav;
	}
	
	@RequestMapping( value = "/link/to/{toSessionid}", method = RequestMethod.GET)
	public ModelAndView link_to(@PathVariable(value = "toSessionid") String to_http_websocket_session_id, HttpServletRequest request) {
		log.info("#link_to currentTimeMillis : " + System.currentTimeMillis());
		ModelAndView mav = new ModelAndView("im/kfclient/chat");
		log.info(to_http_websocket_session_id);
		//建立连接，绑定对方的会话
        HttpSession session = request.getSession(false);
        if(session != null){
        	session.setAttribute(Constant.TO_HTTP_WEBSOCKET_SESSION_ID, to_http_websocket_session_id);
        }
		
		return mav;
	}

}
