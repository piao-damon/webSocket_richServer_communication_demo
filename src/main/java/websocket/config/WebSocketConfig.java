/**
 * <p>Title: WebSocketConfig.java</p>
 * <p></p>
 * @author damon
 * @date 2014年9月26日
 * @version 1.0
 */
package websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import websocket.server.WebSocketHandshakeInterceptor;

/**
 * <p>Title: WebSocketConfig</p>
 * <p></p> 
 * @author damon
 * @date 2014年9月26日
 */

@Configuration
@EnableWebSocket
//@EnableScheduling
//@EnableWebSocketMessageBroker
//public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer implements WebSocketConfigurer {
public class WebSocketConfig implements WebSocketConfigurer {

	private final static Logger log = LoggerFactory.getLogger(WebSocketConfig.class);
	
	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		log.info(System.currentTimeMillis() + "/#registerWebSocketHandlers");
		
        registry.addHandler(kfWebSocketHandler(),"/webSocketServer")
        		.addInterceptors(new WebSocketHandshakeInterceptor());
 
//        registry.addHandler(kfWebSocketHandler(), "/sockjsServer")
//        		.addInterceptors(new WebSocketHandshakeInterceptor())
//                .withSockJS();
        
    }
	
	@Bean
	public WebSocketHandler kfWebSocketHandler(){
		log.info(System.currentTimeMillis() + "/#kfWebSocketHandler");
		
		return new websocket.handler.kfWebSocketHandler();
	}
 
//	/* (non-Javadoc)
//	 * <p>registerStompEndpoints</p>
//	 * <p></p>
//	 * @param registry
//	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#registerStompEndpoints(org.springframework.web.socket.config.annotation.StompEndpointRegistry)
//	 */
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		// TODO Auto-generated method stub
//		//log.info(System.currentTimeMillis() + "/#registerStompEndpoints");
//		
//		registry.addEndpoint(Constant.STOMP_END_POINT).withSockJS();
//	}
//	
//	/* (non-Javadoc)
//	 * <p>configureMessageBroker</p>
//	 * <p></p>
//	 * @param registry
//	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry)
//	 */
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry registry) {
//		// TODO Auto-generated method stub
//		//log.info(System.currentTimeMillis() + "/#configureMessageBroker");
//		registry.setApplicationDestinationPrefixes(Constant.APP_DESTINATION_PREFIXES);
//		registry.enableSimpleBroker(Constant.CHAT_SINGLE, Constant.CHAT_MULTI);
////		registry.enableStompBrokerRelay(Constant.CHAT_SINGLE, Constant.CHAT_MULTI);
//	}
//    
//    @Bean
//    public ServletServerContainerFactoryBean createWebSocketContainer() {
//    	log.info(System.currentTimeMillis() + "/#createWebSocketContainer");
//    	
//        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
//        container.setMaxTextMessageBufferSize(8192);
//        container.setMaxBinaryMessageBufferSize(8192);
//        return container;
//    }
//    
//	/* (non-Javadoc)
//	 * <p>configureWebSocketTransport</p>
//	 * <p></p>
//	 * @param registry
//	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#configureWebSocketTransport(org.springframework.web.socket.config.annotation.WebSocketTransportRegistration)
//	 */
//	@Override
//	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
//		// TODO Auto-generated method stub
//		log.info(System.currentTimeMillis() + "/#configureWebSocketTransport");
//		registry.setSendTimeLimit(15 * 1000).setSendBufferSizeLimit(512 * 1024);
//	}
//
//	/* (non-Javadoc)
//	 * <p>configureClientInboundChannel</p>
//	 * <p></p>
//	 * @param registration
//	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#configureClientInboundChannel(org.springframework.messaging.simp.config.ChannelRegistration)
//	 */
//	@Override
//	public void configureClientInboundChannel(ChannelRegistration registration) {
//		// TODO Auto-generated method stub
//		log.info(System.currentTimeMillis() + "/#configureClientInboundChannel");
//		registration.setInterceptors(new kfChannelInterceptor());
//	}
//
//	/* (non-Javadoc)
//	 * <p>configureClientOutboundChannel</p>
//	 * <p></p>
//	 * @param registration
//	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#configureClientOutboundChannel(org.springframework.messaging.simp.config.ChannelRegistration)
//	 */
//	@Override
//	public void configureClientOutboundChannel(ChannelRegistration registration) {
//		// TODO Auto-generated method stub
//		log.info(System.currentTimeMillis() + "/#configureClientOutboundChannel");
//		registration.setInterceptors(new kfChannelInterceptor());
//	}
//
//	/* (non-Javadoc)
//	 * <p>configureMessageConverters</p>
//	 * <p></p>
//	 * @param messageConverters
//	 * @return
//	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#configureMessageConverters(java.util.List)
//	 */
//	@Override
//	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//		// TODO Auto-generated method stub
//		log.info(System.currentTimeMillis() + "/#configureMessageConverters");
//		for(MessageConverter messageConverter : messageConverters){
//			log.info(System.currentTimeMillis() + "/#" + messageConverter);
//		}
//		return true;
//	}

	
}



