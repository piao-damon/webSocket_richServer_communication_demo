###[原文](http://docs.spring.io/autorepo/docs/spring-framework/4.1.0.RELEASE/spring-framework-reference/html/websocket.html)译：
#<center>WebSocket 帮助</center>
*damon*

*2014.9.29*

****

####21.WebSocket帮助
这部分参考文档涉及了spring框架对WebSocket类型在网络通信应用中的支持，其中包含了WebSocket协议的应用层子协议STOMP（流文本定向消息协议）。

21.1节，“介绍”立足于从WebSocket的视野中思考它，包括遇到的挑战，设计的注意事项和何时才是最佳的选择。

21.2节，“WebSocket API”综述了Spring WebSocket API的服务器端，再到21.3节，“SockJS后备措施”阐明了SockJS协议和如何配置。

21.4.1节，“STOMP概述”介绍了STOMP通信协议。21.4.2节，“启动WebSocket上层STOMP”演示了如何配置spring的STOMP支持。21.4.4节，“通信处理相关注解”和接下来的章节阐述如何书写带注解的通信处理方法，发送消息，选择通信代理，还有特殊的“user”在终端间的运作。最后，21.4.16节，“控制器中方法注解的测试”列出了3中测试STOMP/WebSocket应用的方法。

####21.1介绍
RFC 6455 WebSocket协议为网络应用定义了一个新的重要的能力：客户端和服务器端的双全工传输和双向通信。它是继Java applets, XMLHttpRequest, Adobe Flash, ActiveXObject, 各类Comet技术, 服务端推送事件等在推动网络交互技术历史长河中极其令人振奋的一门新的能力。

一份给WebSocket协议恰如其分的介绍不是本文力所能及的。然而至少通过本文可以明白
HTTP只用于初始化握手，这种握手依赖于一种HTTP内置请求机制，通过转译请求上层协议如果服务端响应HTTP101状态码则握手成功。假设以HTTP请求成功握手TCP传输层为基础HTTP转译请求通道将被保持并且客户和服务端都能通过它来互相通信。

spring 框架4版本中包含了一个新的全方位提供WebSocket支持的spring-websocket模块。它和Java WebSocket API标准JSR-356一致并且提供了附加的在介绍中提到过的拓展。

####21.1.1WebSocket后备措施
一些浏览器缺乏对WebSocket的支持是接受WebSocket协议面临的一个重要的挑战。Internet浏览器支持WebSocket的最低版本是10（查阅[http://caniuse.com/websockets](http://caniuse.com/websockets "浏览器兼容WebSocket参考")各浏览器兼容的版本）。此外，由于通信保持太久，一些各式配置的约束代理可能会试图阻止HTTP转译或在一定时间后使中断连接。关于这个主题的一篇好的概述是来自Peter Lubbers发表在InfoQ上的文章[How HTML5 Web Sockets Interact With Proxy Servers](http://www.infoq.com/articles/Web-Sockets-Proxy-Servers "HTML5 Web Sockets 跟代理服务器的相互作用").

因此在目前构建一个基于WebSocket的应用，做好类似WebSocket API的后备处理的后备措施是必须的。Spring框架提供了一种易懂的基于SockJS协议的后备措施。这些措施可以被配置启用并且不需要修改应用。

####21.1.2 通信体系
****
****
****
****
****
####21.2WebSocket API
spring 框架提供了一个适应各种WebSocket引擎的WebSocket API。例如，它可以运行在Tomcat (7.0.47+), GlassFish (4.0+) 和 WildFly (8.0+)JSR-356运行环境同样也适用于Jetty (9.1+)等其他支持原生WebSocket的WebSocket运行环境。


> 正如介绍中所说，在应用中直接使用WebSocket API显得有些低端，直到统一标准规范时也只有一小部分框架可以解析信息或通过注解方式使用它。这正是考虑在应用中运用子协议和产生基于WebSocket支持的Spring的STOMP的原因。

>当运用一个上层协议，WebSocket API的细节就显得不那么重要了，正如运用了HTTP后TCP的通信细节不再暴漏在应用中一样。然而这节也将提到直接运用WebSocket的细节。

####21.2.1创建和配置一个WebSocketHandler
建立一个WebSocket服务就是实现WebSocketHandler或继承TextWebSocketHandler或BinaryWebSocketHandler这么简单：

	import org.springframework.web.socket.WebSocketHandler;
	import org.springframework.web.socket.webSocketSession;
	import org.springframework.web.socket.TextMessage;

	public class MyHandler extends TextWebSocketHandler {
	
		@Override
		public void handlerTextMessage(WebSocketSession session,TextMessage message) {
			//...
		}

	}

接下来是专业的Java和XML的WebSocket配置映射到上边的WebSocket handler的地址：

	import org.springframework.web.socket.config.annotation.EnableWebSocket;
	import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
	import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
	
	@Configuration
	@EnableWebSocket
	public class WebSocketConfig implements WebSocketConfigurer {
		
		@Override
		public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
			registry.addHandler(myHandler(), "/myHandler");
		}

		@Bean
		public WebSocketHandler myHandler() {
			return new MyHandler();
		}
		
	}

等价的XML配置：

	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:websocket="http://www.springframework.org/schema/websocket"
	    xsi:schemaLocation="
	        http://www.springframework.org/schema/beans
	        http://www.springframework.org/schema/beans/spring-beans.xsd
	        http://www.springframework.org/schema/websocket
	        http://www.springframework.org/schema/websocket/spring-websocket-4.0.xsd">
	
	    <websocket:handlers>
	        <websocket:mapping path="/myHandler" handler="myHandler"/>
	    </websocket:handlers>
	
	    <bean id="myHandler" class="org.springframework.samples.MyHandler"/>

	</beans>

上面是个在Spring MVC应用中的示例，需要配置一个DispatcherServlet。但是，Spring对WebSocket的支持不是依赖于Spring MVC的。而是很容易在WebSocketHttpRequestHandler的帮助下集成到其他的HTTP服务环境中。

####21.2.2自定义WebSocket握手
最简单的自定义初始化HTTP WebSocket握手请求的方式是通过一个暴漏了“握手前”和“握手后”方法的叫做HandshakeInterceptor的拦截器。这个拦截器可以阻止握手也可以给WebSocketSession提供参数。例如，将一个HTTP会话内置的属性传递给拦截器的WebSocket会话：

	@Configuration
	@EnableWebSocket
	public class WebSocketConfig implements WebSocketConfigurer {
		
		@Override
		public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
			registry.addHandler(new MyHandler(), "/myHandler").addInterceptors(new HttpSessionHandshakeInterceptor());
		}

	}

等价的XML配置：

	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:websocket="http://www.springframework.org/schema/websocket"
	    xsi:schemaLocation="
	        http://www.springframework.org/schema/beans
	        http://www.springframework.org/schema/beans/spring-beans.xsd
	        http://www.springframework.org/schema/websocket
	        http://www.springframework.org/schema/websocket/spring-websocket-4.0.xsd">
	
	    <websocket:handlers>
	        <websocket:mapping path="/myHandler" handler="myHandler"/>
	        <websocket:handshake-interceptors>
	            <bean class="org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor"/>
	        </websocket:handshake-interceptors>
	    </websocket:handlers>
	
	    <bean id="myHandler" class="org.springframework.samples.MyHandler"/>

	</beans>

一个更好的选择是继承DefaultHandshakeHandler来执行握手过程，它包括客户端来源验证，甄选子协议等步骤。应用程序可能也需要配置一个自定义的RequestUpgradeStrategy来适应相应版本的WebSocket服务引擎因为DefaultHandshakeHandler没有提供这方面的支持（可以查看21.2.4节，“部署的注意事项”来获取更多关于这方面的信息）Java和XML配置都可以自定义的拓展HandshakeHandler。

####21.2.3WebSocketHandler的装饰器
Spring提供了一个叫WebSocketHandlerDecorator的基础装饰器类来给WebSocketHandler装饰上附加的行为。WebSocket的Java和XML配置默认提供日志记录和异常处理。ExceptionWebSocketHandlerDecorator装饰器能捕获任何WebSocketHandler的方法中产生的异常并关闭WebSocket会话同时反馈1011状态码来显示服务的异常。

####21.2.4部署注意事项
DispatcherServlet很容易像提供其他HTTP请求一样通过Spring WebSocket API给Spring MVC应用提供HTTP WebSocket握手。同样也很容易通过调用WebSocketHttpRequestHandler集成到其他的HTTP程序脚本中。通俗易懂。
然而，特殊的请求需要访问提供JSR-356的运行环境。

JSR-356提供两种调度机制。第一种通过Servlet容器启动时扫描类路径（典型如Servlet 3）；另一种在Servlet容器初始化时通过接口注册。两种机制都不能仅仅通过前端控制器提供所有的HTTP处理，这些处理包括WebSocket握手和其他的HTTP请求，如同Spring MVC的DispatcherServlet。

JSR-356的一个很大的局限是没有Spring的WebSocket提供的对一个同样能在JSR-356运行环境中运行的特殊的RequestUpgradeStrategy策略的支持。目前这个支持在Tomcat 7.0.47+, Jetty 9.1+,GlassFish 4.0+和WildFly 8.0+也都是可用的。额外的支持将使WebSocket的运行时环境更有用。

>一种克服上述局限的需求在Java WebSocket API中的到体现可以通过参考[WEBSOCKET_SPEC-211](https://java.net/jira/browse/WEBSOCKET_SPEC-211)了解。同样注意到Tomcat和Jetty已经提供了供选择的原生API更容易的克服这种局限。不管JSR-356中有木有提供我们也期望更多的服务能仿效Tomcat和Jetty提供支持。

次要的考虑因素是在某些情况下，支持JSR-356的Servlet容器会由于预计执行的SCI扫描延缓应用程序的启动。如果某个版本的Servlet容器提供的JSR-356支持被检测到在转译中影响较大，那么应该为它提供可供选择的启动或禁用的web片段（关于SCI扫描同样也需要）可以通过在web.xml文件中配置<absolute-ordering />元素：

	<web-app xmlns="http://java.sun.com/xml/ns/javaee"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="
        	http://java.sun.com/xml/ns/javaee
        	http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
    	version="3.0">

    	<absolute-ordering/>

	</web-app>

同样可以通过名称有选择的启用，如果需要的话，Spring的SpringServletContainerInitializer可以提供Servlet 3初始化所用API的支持。

	<web-app xmlns="http://java.sun.com/xml/ns/javaee"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xsi:schemaLocation="
	        http://java.sun.com/xml/ns/javaee
	        http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	    version="3.0">
	
	    <absolute-ordering>
	        <name>spring_web</name>
	    </absolute-ordering>

	</web-app>

####21.2.5配置WebSocket引擎
下面的每种WebSocket引擎都展示了运行时独特的配置属性，如消息缓冲区的大小，闲置超时等。

为你的Tomcat,WildFly和Glassfish的Java WebSocket配置添加ServletServerContainerFactoryBean：

	@Configuration
	@EnableWebSocket
	public class WebSocketConfig implements WebSocketConfigurer {
		
		@Bean
		public ServletServerContainerFactoryBean createWebSocketContainer() {
			ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
			container.setMaxTextMessageBufferSize(8192);
			container.setMaxBinaryMessageBufferSize(8192);
			return container;
		}
	}

等价的XML配置：

	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:websocket="http://www.springframework.org/schema/websocket"
	    xsi:schemaLocation="
	        http://www.springframework.org/schema/beans
	        http://www.springframework.org/schema/beans/spring-beans.xsd
	        http://www.springframework.org/schema/websocket
	        http://www.springframework.org/schema/websocket/spring-websocket.xsd">
	
	    <bean class="org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean">
	        <property name="maxTextMessageBufferSize" value="8192"/>
	        <property name="maxBinaryMessageBufferSize" value="8192"/>
	    </bean>
	
	</beans>

>用XML配置的WebSocketContainerFactoryBean或Java配置的ContainerProvier.getWebSocketContainer（）配置WebSocket客户端。

在Jetty中，需要预先通过如下Java配置接入Spring的DefaultHandshakeHandler中配置Jetty的WebSocketServerFactory：

	@Configuration
	@EnableWebSocket
	public class WebSocketConfig implements WebSocketConfigurer {
		@Override
		public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
			registry.addHandler(echoWebSocketHandler(),"/echo").setHandshakeHandler(handshakeHandler());
		}

		@Bean
		public DefaultHandshakeHandler handshakeHandler() {
			WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
			policy.setInputBufferSize(8192);
			policy.setIdleTimeout(600000);
			return new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
		}
	}

等价的XML配置：

	<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:websocket="http://www.springframework.org/schema/websocket" xsi:schemaLocation="
		http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/websocket
		http://www.springframework.org/schema/websocket/spring-websocket.xsd">
		<websocket:handlers>
			<websocket:mapping path="/echo" handler="echoHandler"/>
			<websocket:handshake-handler ref="handshakeHandler"/>
		</websocket:handlers>
		<bean id="handshakeHandler" class="org.springframework.web.socket.server.support.DefaultHandshakeHandler">
			<constructor-arg ref="upgradeStrategy"/>
		</bean>
		<bean id="upgradeStrategy" class="org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy">
			<constructor-arg ref="serverFactory"/>
		</bean>
		<bean id="serverFactory" class="org.eclipse.jetty.websocket.server.WebSocketServerFactory">
			<constructor-arg>
				<bean class="org.eclipse.jetty.websocket.api.WebSocketPolicy">
					<constructor-arg value="SERVER"/>
					<property name="inputBufferSize" value="8092"/>
					<property name="idleTimeout" value="600000"/>
				</bean>
			</constructor-arg>
		</bean>
	</beans>

####21.3SockJS后备措施
正如介绍中所说，WebSocket目前并不能支持所有的浏览器而且可能被局限的网络代理束缚。这正是Spring提供近似WebSocket API处理的基于SockJS协议的后备措施的原因。

####21.3.1SockJS 概述
SockJS的目的是让用到WebSocket API的应用在运行时遇到无WebSocket时有可供选择的后备措施而不用去修改应用的代码。

SockJS的组成：

+ 可实行的草案列表中定义了SockJS协议的内容。
+ SockJS 客户端JS脚本-一个应用在浏览器上的客户端的知识库。
+ Spring框架中spring-websocket模块包含一个SockJS服务实现。
+ 4.1版本的spring-websocket模块甚至提供了一个Java实现的SockJS客户端。

SockJS是为浏览器应用而设计的。通过各种技术对它的延伸和拓展从而大范围的支持各种版本的浏览器。全部的SockJS传输种类和浏览器支持的条目请参考[SockJS client](https://github.com/sockjs/sockjs-client)页面。3大类传输：WebSocket，HTTP Streaming（HTTP流）和HTTP Long Polling（HTTP长轮询）。关于这几类的概述可以参考[this blog post](https://spring.io/blog/2012/05/08/spring-mvc-3-2-preview-techniques-for-real-time-updates/ "这篇博文")。

客户端SockJS首先向服务端发送“GET /info”来获取基础信息。然后它必须选择传输方式。如果WebSocket可用就用。不能用的话，在大多数的浏览器中至少会有一种HTTP流可供使用再次就是使用HTTP长轮询。

所有的传输请求都有如下URL结构：

	http://host:port/myApp/myEndpoint/{server-id}/{session-id}/{transport}

+ {server-id}-用于集群路由请求其他方面不常用。
+ {session-id}-属于SockJS会话关联的HTTP请求。
+ {transport}-表示传输类型，例如“websocket”、“xhr-streaming”等。

WebSocket传输只需要一个单独的HTTP请求来执行WebSocket握手。此后通过socket套接字交互所有信息。

HTTP传输需要更多的请求方式。例如Ajax/XHR流依赖于一个长时间运行的请求来维持服务端到客户端的通信和额外的HTTP POST请求来维持客户端到服务端的通信。长轮询除了在每次服务端对客户端通信后结束当前请求外和Ajax/XHR流很类似。

SockJS加入了微型消息帧。例如服务端首先发送字母o（"open"帧），信息通过如a["message1","message2"]的（JSON数组）传输，默认发送字母h（"heartbeat"帧）后25秒如果没有信息流就发送字母c（"close"帧）来关闭会话。

可以通过在浏览器中运行一个例子并查看HTTP请求来学习更多的运用。SockJS客户端记录了传输列表所有可以查看每次的每个传输。SockJS也提供往浏览器控制台发送有用讯息的调试功能。在服务器端为org.springframework.web.socket启用跟踪日志记录。更多细节请参阅[SockJS协议测试](http://sockjs.github.io/sockjs-protocol/sockjs-protocol-0.3.3.html)。

####21.3.2启用SockJS

配置启用SockJS很容易：

	@Configuration
	@EnableWebSocket
	public class WebSocketConfig implements WebSocketConfigurer {

	    @Override
	    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	        registry.addHandler(myHandler(), "/myHandler").withSockJS();
	    }
	
	    @Bean
	    public WebSocketHandler myHandler() {
	        return new MyHandler();
	    }
	
	}

等价的XML配置：

	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:websocket="http://www.springframework.org/schema/websocket"
	    xsi:schemaLocation="
	        http://www.springframework.org/schema/beans
	        http://www.springframework.org/schema/beans/spring-beans.xsd
	        http://www.springframework.org/schema/websocket
	        http://www.springframework.org/schema/websocket/spring-websocket-4.0.xsd">
	
	    <websocket:handlers>
	        <websocket:mapping path="/myHandler" handler="myHandler"/>
	        <websocket:sockjs/>
	    </websocket:handlers>
	
	    <bean id="myHandler" class="org.springframework.samples.MyHandler"/>
	
	</beans>

上面是Spring MVC的应用，应在配置中加入DispatcherServlet。然而，Spring的WebSocket和SockJS支持并不依赖于Spring MVC。而是很容易通过SockJsHttpRequestHandler的帮助集成到HTTP服务环境中。

在浏览器端，应用程序可以使用的仿效W3C WebSocket API的[SockJS客户端](https://github.com/sockjs/sockjs-client)和与服务器通信的最佳传输选择取决于运行的浏览器。查阅[SockJS客户端](https://github.com/sockjs/sockjs-client)页面和浏览器支持的传输类型的列表。客户端还提供了一些可选配置，例如定向传输。

####21.3.3IE8,9中的流媒体：Ajax/XHR vs IFrame

IE8,9已经也将并存一段时间。这是SockJS诞生的关键因素。本节讨论的重点是它在这些浏览器中的运行。

SockJS客户端通过微软的[XDomainRequest](http://blogs.msdn.com/b/ieinternals/archive/2010/05/13/xdomainrequest-restrictions-limitations-and-workarounds.aspx)对IE8、9提供Ajax/XHR流支持。可以跨流域工作但是不支持发送cookies。Cookies经常是Java应用程序所必需的。然而自从SockJS客户端可以使用许多服务类型(不仅仅是Java的),它需要清楚cookies的类型。如果这样，SockJS更倾向Ajax/XHR流或依赖于其他iframe-based技术。

第一次从客户端发送的“/info”请求会影响客户端传输方式的选择。其中的一个细节是应用服务是否依赖于cookies，例如进行身份验证或聚集了粘滞会话。Spring的SockJS支持包括称为sessionCookieNeeded的属性。默认启用因为大多数Java应用程序依赖于JSESSIONID cookie。如果在IE8,9中你的应用不需要上述支持，可以关闭相关设置然后为SockJS客户端选择使用xdr-streaming。

如果你使用基础的内置框架来传输，无论如何，众所周知，可以通过设置HTTP响应头X-Frame-Options 为 DENY,SAMEORIGIN或ALLOW-FROM<origin>来让浏览器指示在一个给定的页面区域中iframes的使用情况。这是用于防止[Clickjacking](https://www.owasp.org/index.php/Clickjacking "点击劫持")。

>Spring Security 3.2 +支持设置每个响应的X-Frame-Options。默认情况下,Spring Security Java配置中不启用。3.2版本的Spring安全XML配置头文件中没设置默认,但是是可配置的,将来也可能设置为默认的。参见7.1节。[默认安全标头](http://docs.spring.io/spring-security/site/docs/3.2.2.RELEASE/reference/htmlsingle/#headers)的Spring Security文档详细这块没有如何配置X-Frame-Options标头的设置。你可以查看[SEC-2501](https://jira.spring.io/browse/SEC-2501)了解更多相关的背景资料。

如果你的应用中需要添加头部X-Frame-Options相应并且依赖于基础内置框架的传输，你应该把头部设置成SAMEORIGIN或ALLOW-FROM <origin>。与此同时因为Spring的SockJS支持是从内置框架开始加载的所以需要知道SockJs客户端的位置。默认设置内置框架从一个CDN的位置加载SockJS客户端。和应用程序根源配置相同的URL是个很好的选择。

Java配置如下所示。XML配置提供了一个<websocket:sockjs>元素的选项:

	@Configuration
	@EnableWebSocket
	public class WebSocketConfig implements WebSocketConfigurer {
	
	    @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/portfolio").withSockJS()
	                .setClientLibraryUrl("http://localhost:8080/myapp/js/sockjs-client.js");
	    }
	
	    // ...
	
	}

>开发阶段，启动SockJS客户端的devel模式来阻止浏览器缓存SockJS请求（类似内置框架）否则会缓存请求。如何启动请查阅[SockJS客户端](https://github.com/sockjs/sockjs-client)页面。

####21.3.4心跳消息

SockJS协议要求服务器发送心跳消息来防止代理中有挂起的连接。Spring SockJS配置中有个heartbeatTime可以设置心跳的频率。假设这条连接上没有其他消息的发送将默认25秒发送一次心跳。这个25秒值符合[IETF建议](http://tools.ietf.org/html/rfc6202)提供给公共互联网应用程序。

>当在WebSocket/SockJS上应用STOMP，当STOMP客户端和服务端需要协商交换心跳时，SockJS心跳应该被禁用。

Spring的SockJS支持还提供了TaskScheduler的配置用来调度心跳事物。任务调度器是由线程池的基于可用的处理器数量的默认设置支持的。应用程序应该根据具体需求设置。

####21.3.5Servlet 3异步请求

HTTP流媒体和HTTP长轮询SockJS传输需要比平时保持更长时间的连接。对这些技术的概述参考[this blog post](https://spring.io/blog/2012/05/08/spring-mvc-3-2-preview-techniques-for-real-time-updates/ "这篇博客")。

在Servlet容器中通过允许退出Servlet容器线程处理请求继续从另一个线程写入响应这种Servlet 3异步支持。

Servlet API不为客户消失提供通知是个问题,查阅[SERVLET_SPEC-44](https://java.net/jira/browse/SERVLET_SPEC-44)。然而，Servlet容器试图往响应中写入产生的异常。因为Spring的SockJS服务支持服务端发送的心跳，如果消息被更频繁地发送，客户端断开连接将被更早发现。

>因此发生网络IO失败可能仅仅是因为客户端连接断开,导致不必要的堆栈跟踪写满了日志。Spring通过描述每个服务器的每个客户端的连接断开和通过AbstractSockJsSession中定义的DISCONNECTED_CLIENT_LOG_CATEGORY特殊的日志类型更精练的记录日志来尽可能地标识这种网络故障。如果你需要查看堆栈跟踪日志,设置日志类别为TRACE。

####21.3.6为SockJS提供的CORS头信息

SockJS协议通过[CORS](http://baike.baidu.com/view/1009102.htm?fr=aladdin "跨域资源共享")来跨域支持XHR流和轮询传输。因此除了响应中已经有了CORS头自热而然的被加入SockJS中。所有如果一个应用已经提供了CORS的支持，也可以像设置Servlet过滤器跳过Spring的SockJS服务一样设置。

下面是头元素列表和SockJS的预期值：

+ "Access-Control-Allow-Origin" - 从头部请求“origin”的值或“*”开始初始化。
+ "Access-Control-Allow-Credentials" - 总是设成true。
+ "Access-Control-Request-Headers" - 等价头部请求的值的初始化。
+ "Access-Control-Allow-Methods" - HTTP方法的传输支持（参考枚举类型TransportType）。
+ "Access-Control-Max-Age" - 设置成31536000（一年）。

具体实现参考AbstractSockJsService的addCorsHeaders方法，也可以参考TransportType枚举的源代码。

另一种选择是可以考虑在CORS配置中让URL不包含SockJS的前缀从而让Spring的SockJsService来处理它。

####21.3.7SockJS客户端

一个Java实现的SockJS客户端提供无浏览器下对SockJS远程终端的连接。这对2台暴漏在WebSocket协议不被许可的因特网中的服务器之间的双向通信是尤其有用的。它在例如模拟大规模用户并发的测试等同样也是相当有用的。

Java实现的SockJS客户端支持“websocket”，“xhr流”和“xhr轮询”传输。剩下的意义就是在浏览器中的应用。

WebSocketTransport可以做如下配置：

+ JSR-356运行时环境中的StandardWebSocketClient
+ Jetty 9+原生WebSocket API的JettyWebSocketClient
+ 任何Spring的WebSocketClient的实现

除了客户端连接到服务端的URL，定义的XhrTransport中提供的“xhr流”和“xhr轮询”没有什么不同。目前有两种实现：

+ RestTemplateXhrTransport 通过RestTemplate来发送HTTP请求。
+ JettyXhrTransport 通过Jetty的HttpClient来发送HTTP请求。

下面展示如何创建SockJS客户端和如何连接SockJS终端：

	List<Transport> transports = new ArrayList<>(2);
	transports.add(new WebSocketTransport(StandardWebSocketClient()));
	transports.add(new RestTemplateXhrTransport());
	
	SockJsClient sockJsClient = new SockJsClient(transports);
	sockJsClient.doHandshake(new MyWebSocketHandler(), "ws://example.com:8080/sockjs");

>SockJS使用JSON格式数组的消息。默认情况下应用和配置JSON 2的classpath。也可以自定义实现SockJsMessageCodec并在SockJsClient中配置。

运用SockJsClient模拟大规模用户并发需要做如下HTTP客户端相关配置来支持足够数量的连接和线程。Jetty的例子：

	HttpClient jettyHttpClient = new HttpClient();
	jettyHttpClient.setMaxConnectionsPerDestination(1000);
	jettyHttpClient.setExecutor(new QueuedThreadPool(1000));

也可以考虑自定义这些服务器端SockJS相关的属性（请参阅Javadoc获得相关详细信息）：

	@Configuration
	public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport {
	
	    @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/sockjs").withSockJS()
	            .setStreamBytesLimit(512 * 1024)
	            .setHttpMessageCacheSize(1000)
	            .setDisconnectDelay(30 * 1000);
	    }
	
	    // ...
	
	}

####21.4WebSocket消息传递体系结构上层的STOMP

WebSocket协议定义了两种消息类型——text和binary——但是没明确其具体的内容。反而计划让客户端和服务端利用达成一致的子协议，即定义了消息内容的高层协议。运用子协议是可选的但至少客户端和服务端需要知道双方如何互相解析消息。

####21.4.1STOMP概述

STOMP最初作为（Ruby,Python和Perl等）脚本语言的一个简单消息传输协议来连接企业消息代理。它是为了解决常用模式消息传递协议的一个子集。STOMP可以在TCP和WebSocket等任何可靠的双向流的网络协议中应用。

STOMP协议的基础框架仿效HTTP。下面是框架构成：

	COMMAND
	header1:value1
	header2:value2
	
	Body^@

例如，客户端可以用SEND命令来发送消息或用SUBSCRIBE命令根据兴趣订阅消息。两种命令都需要指明"destination"头部信息来指明消息的去向，同样指明订阅的指向。

下面是一个示例客户机发送请求购买股票:

	SEND
	destination:/queue/trade
	content-type:application/json
	content-length:44
	
	{"action":"BUY","ticker":"MMM","shares",44}^@

这是客户端订阅接收股票报价的一个例子:

	SUBSCRIBE
	id:sub-1
	destination:/topic/price.stock.*
	
	^@

>STOMP的规范中没有明确定义destination的含义。可以是任何字符串，完全取决于STOMP服务提供的定义的语义和语法的目的。然而通常，像"/topic/.."这样指定的路径意味着发布订阅（一对多），像"/queue/"这样指定的路径意味着点对点（一对一）消息交互。

STOMP服务可以执行MESSAGE命令向所有订阅者发送广播。下面是一个股票报价服务器端给一个订阅客户端发送订阅的例子：

	MESSAGE
	message-id:nxahklf6-1
	subscription:sub-1
	destination:/topic/price.stock.MMM
	
	{"ticker":"MMM","price":129.45}^@

>要知道服务器是不能发送未经请求的消息的。服务端相应的所有的消息必须针对特定的客户端并且服务端消息的头信息中的"subscription-id"必须和客户端头信息中的"id"一致。

上面概述的目的是提供对STOMP协议最基本的了解。建议参考[specification](http://stomp.github.io/stomp-specification-1.2.html "协议规范"),很容易遵循和控制。

以下总结了应用程序利用STOMP WebSocket的好处：

+ 标准的消息规范
+ 提供常见的消息传递模式的应用层级协议
+ 提供客户端支持，例如stomp.js, msgs.js
+ 在客户端和服务器端翻译，路由，处理消息方面的能力
+ 可供选择的消息代理——RabbitMQ, ActiveMQ等消息中间件——广播消息（稍后讲解）

和一般的WebSocket相比STOMP最明显的好处是Spring框架提供了应用层的开发模板同时Spring MVC也提供了基于HTTP的开发模板。

####21.4.2启用WebSocket上层的STOMP

Spring框架通过spring-messaging和spring-websocket模块提供对应用WebSocket上层STOMP的支持。很容易启动它。

下面是一个配置STOMP WebSocket端点与SockJS后备选择的示例。可用的客户端点连接的URL路径是/app/portfolio：

	import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
	import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
	
	@Configuration
	@EnableWebSocketMessageBroker
	public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	    @Override
	    public void configureMessageBroker(MessageBrokerRegistry config) {
	        config.setApplicationDestinationPrefixes("/app");
	        config.enableSimpleBroker("/queue", "/topic");
	    }
	
	    @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/portfolio").withSockJS();
	    }
	
	    // ...
	
	}

等价的XML配置：

	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:websocket="http://www.springframework.org/schema/websocket"
	    xsi:schemaLocation="
	        http://www.springframework.org/schema/beans
	        http://www.springframework.org/schema/beans/spring-beans.xsd
	        http://www.springframework.org/schema/websocket
	        http://www.springframework.org/schema/websocket/spring-websocket-4.0.xsd">
	
	    <websocket:message-broker application-destination-prefix="/app">
	        <websocket:stomp-endpoint path="/portfolio">
	            <websocket:sockjs/>
	        </websocket:stomp-endpoint>
	        <websocket:simple-broker prefix="/queue, /topic"/>
	        ...
	    </websocket:message-broker>
	
	</beans>

在浏览器端，客户端连接可能会用到如下[stomp.js](https://github.com/jmesnil/stomp-websocket)和[sockjs-client](https://github.com/sockjs/sockjs-client)：

	var socket = new SockJS("/spring-websocket-portfolio/portfolio");
	var stompClient = Stomp.over(socket);
	
	stompClient.connect({}, function(frame) {
	}

或者通过WebSocket连接(不使用SockJS)：

	var socket = new WebSocket("/spring-websocket-portfolio/portfolio");
	var stompClient = Stomp.over(socket);
	
	stompClient.connect({}, function(frame) {
	}

需要注意的是上边提到的stompClient不需要在头部指明登陆和密码。即使指明了也会被服务端忽略或覆盖。参考21.4.8节，“连接到功能全面的代理”和21.4.10节，“身份验证”获得更多相关信息。

####21.4.3消息流

当STOMP端点配置好，Spring应用充当STOMP代理给客户端提供连接。控制传入的消息并反馈消息。这节将通过一个大图描述消息流如何在应用内部运转。

spring-messaging模块包含起源于[Spring Integration项目](http://projects.spring.io/spring-integration/)的大量的抽象，目的是为了构建消息应用的模块：

+ [Message](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/messaging/Message.html)——存在消息头和有效负载的消息。
+ [MessageHandler](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/messaging/MessageHandler.html)——一份处理消息的合约。
+ [MessageChannel](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/messaging/MessageChannel.html)——在发送方和接收方之间发送消息的松散耦合的合约。
+ [SubscribableChannel](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/messaging/SubscribableChannel.html)——继承MessageChannel并发送消息来注册选用的MessageHandler。
+ [ExecutorSubscribableChannel](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/messaging/support/ExecutorSubscribableChannel.html)——SubscribableChannel的一个可以通过线程池实现异步消息的具体实现。

WebSocket上提供的STOMP的Java和XML配置，消息流使用上述组装一个具体应用包括以下三种渠道：

+ "clientInboundChannel"——来自WebSocket客户端的消息。每条传入的携带STOMP帧的WebSocket消息通过这条通道。
+ "clientOutboundChannel"——返给WebSocket客户端的消息。每条代理发送出的STOMP消息通过这个通道发送到客户的WebSocket会话。
+ "brokerChannel"——应用内部的消息代理。每条从应用到代理的消息通过这条通道。

"clientInboundChannel"里的消息能流向指定的应用程序的处理方法（例如一个股票交易执行请求）或可以转发给代理（例如客户端订阅股票报价）。STOMP目的地是用于简单prefix-based路由。例如以"/app"前缀的能路由到指定的方法，以"/topic"和"/queue"前缀的能路由到服务代理。

当一个处理消息的带注解的方法存在返回值时，它的返回值作为Spring的消息的有效负载发送到"brokerChannel"。代理将消息广播给客户。在消息模板的帮助下，应用程序的任何地方都可以向目标发送消息。例如一个HTTP POST处理方法可以向连接的客户端发送广播或服务组件可以定期的广播股票报价。

下面是一个简单的例子来说明了信息的流动:

	@Configuration
	@EnableWebSocketMessageBroker
	public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	    @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/portfolio");
	    }
	
	    @Override
	    public void configureMessageBroker(MessageBrokerRegistry registry) {
	        registry.setApplicationDestinationPrefixes("/app");
	        registry.enableSimpleBroker("/topic");
	    }
	
	}

	@Controller
	public class GreetingController {
	
	    @MessageMapping("/greeting") {
	    public String handle(String greeting) {
	        return "[" + getTimestamp() + ": " + greeting;
	    }
	
	}

下面是上边信息流示例的解释：

+ WebSocket客户端从"/portfolio"终端连接WebSocket。
+ 订阅的"/topic/greeting"通过"clientInboundChannel"转发给代理。
+ 发送给"/app/greeting"的问候通过"clientInboundChannel"转发给GreetingController。控制器在信息上添加当前时间戳然后通过和"/topic/greeting"传来信息相同的那条"brokerChannel"（目标的选择是约定好的,但也可以通过@SendTo覆盖）。
+ 代理通过"clientOutboundChannel"依次向订阅者发送广播。

下一节将详细介绍注解的方法和它的参数及返回值。

####21.4.4注解信息处理

@MessageMapping注解用在被@Controller注解的类的方法上。@MessageMapping可以用来方法上映射消息目标，也可以用在类级别上共享给控制器中所有的方法。

默认情况目标映射被看作ant样式、slash-separated、路径模式，例如"/foo*", "/foo/**"等。也可以含有模板变量，如"/foo/{id}"，然后可以通过用@DestinationVariable注解的形参引用变量。

>应用中同样可以用圆点分隔的目标（相对于用斜线分隔的）。参考21.4.9节，“用点分隔@MessageMapping注解中的目标”。

如下是@MessageMapping注解的方法支持的参数：

+ Message方法参数可以使用正在处理中的完整的消息。
+ @Payload注解的参数访问被org.springframework.messaging.converter.MessageConverter转化的有效消息。带验证注解（如@Validated注解）的@Payload注解的方法参数应该遵循JSR-303校验。
+ @Header注解的参数用来访问一个必要时通过org.springframework.core.convert.converter.Converter进行类型转换的特殊的头部信息值。
+ @Headers注解的方法参数必须也能转换成java.util.Map类型来访问所有消息的头部信息。
+ MessageHeaders方法参数用来访问所有头部信息的map集合。
+ MessageHeaderAccessor,SimpMessageHeaderAccessor或StompHeaderAccessor用来访问经过访问器方法标记过的头部信息。
+ @DestinationVariable注解的参数访问消息目标中提到模板变量。如果需要它的值将被转化成声明的参数的类型。
+ java.security.Principal 方法参数反射WebSocket HTTP握手后已经登录的用户。

@MessageMapping注解的方法返回org.springframework.messaging.converter.MessageConverter类型的值，然后作为消息体发送，默认情况下，作为客户端消息和"brokerChannel"有着相同的目的地，但是通常用个"/topic"前缀。消息级的注解@SendTo可以给它替换成任何指定的目的地。

@SubscribeMapping注解也能映射订阅请求到@Controller注解的类的方法。它是方法级的，但是也能和类级别的@MessageMapping注解结合使用共享给控制器中所有的处理消息的方法。

默认情况@SubscribeMapping注解的方法返回值作为一个消息直接发送回连接的客户端而不通过代理。这是请求-应答消息交互很不错的实现；例如，当应用程序的UI正在初始化时获取应用程序的数据。另外可以用@SendTo注解@SubscribeMapping注解的方法来将返回的消息通过指定的目标地址发送到"brokerChannel"。

####21.4.5消息发送

如果你想给应用程序中的任何连接着的客户端发送消息，如何做？任何应用程序组件都能向"brokerChannel"发送消息。最简单的方法是注入SimpMessagingTemplate，用它来发送消息。通常它是很容易的类型注入，如：

	@Controller
	public class GreetingController {
			
		private SimpMessagingTemplate template;

		@Autowired
		public GreetingController(SimpMessagingTemplate template) {
			this.template = template;
		}

		@RequestMapping(value="/greetings", method=POST)
		public void greet(String greeting) {
			String text = "[" + getTimestamp() + "]:" + greeting;
			this.template.convertAndSend("/topic/greetings", text);
		}


	}

如果已经有其相同类型的注入存在，同样可以将它声明成"brokerMessagingTemplate"。

####21.4.6简单的代理

客户端自带的处理订阅请求的简单的消息代理是把信息存储在内存中并向匹配的连接着的客户端广播消息。这种代理支持路径式的目标，包括Ant-style模式目标的订阅。

>应用中同样可以用圆点分隔的目标（相对于用斜线分隔的）。参考21.4.9节，“用点分隔@MessageMapping注解中的目标”。

####21.4.7功能全面的代理

简单的代理是个伟大的开始但是只支持STOMP命令的一小部分(如没有延迟，收据等),依赖一个简单的发送消息的回路，不适用于集群。应用程序可以升级成一个功能全面的消息代理替代它。

查看STOMP文档选择适合你的消息代理（RabbitMQ，ActiveMQ等），启动STOMP支持安装和运行它。然后把启动STOMP代理的操作配到Spring的配置中。

下面是配置启用功能全面的代理的示例：

	@Configuration
	@EnableWebSocketMessageBroker
	public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	    @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/portfolio").withSockJS();
	    }
	
	    @Override
	    public void configureMessageBroker(MessageBrokerRegistry registry) {
	        registry.enableStompBrokerRelay("/topic", "/queue");
	        registry.setApplicationDestinationPrefixes("/app");
	    }
	
	}

等价的XML配置：

	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:websocket="http://www.springframework.org/schema/websocket"
	    xsi:schemaLocation="
	        http://www.springframework.org/schema/beans
	        http://www.springframework.org/schema/beans/spring-beans.xsd
	        http://www.springframework.org/schema/websocket
	        http://www.springframework.org/schema/websocket/spring-websocket-4.0.xsd">
	
	    <websocket:message-broker application-destination-prefix="/app">
	        <websocket:stomp-endpoint path="/portfolio" />
	            <websocket:sockjs/>
	        </websocket:stomp-endpoint>
	        <websocket:stomp-broker-relay prefix="/topic,/queue" />
	    </websocket:message-broker>
	
	</beans>

上边配置中的STOMP代理适配器是个Spring [MessageHandler](http://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/messaging/MessageHandler.html)，把消息转发给外部的消息代理。这样将建立TCP和代理间的连接，将所有消息转发给TCP，相反的从代理接收的所有信息通过它们之间的会话传给客户端。本质上它作为一个适配器双向转发消息。

>请为TCP连接管理添加org.projectreactor:reactor-net的引用。

此外，应用程序组件（例如HTTP请求处理方法，业务服务等）也可以发送消息给代理适配器，正如21.4.5节，“消息发送”中描述的那样，将广播消息发送给订阅的WebSocket客户端。

事实上，代理适配器能提供健壮和可伸缩的消息广播。

####21.4.8连接功能全面的代理

STOMP代理适配器维持一个系统TCP和代理间的连接。这个连接仅用于来自应用服务端产生的消息，不是用来接受消息的。你可以给这个连接配置STOMP证书，即STOMP帧的登录和密码头。它们是暴漏在Java配置和XML配置中的默认值为guest/guest的systemLogin/systemPasscode属性。

STOMP代理适配器也为每个连接着的WebSocket客户端创建分离的TCP连接。你可以给客户端委托的所有TCP连接配置上STOMP证书。它们是暴漏在Java配置和XML配置中的默认值为guest/guest的clientLogin/clientPasscode属性。

>STOMP代理适配器为每个客户端委托的转发代理的连接设置登录和密码头。因此WebSocket客户端不需要设置这些也不用理会。以下部分解释说相反WebSocket客户应该依靠HTTP身份验证来保护WebSocket端点和建立客户身份。

STOMP代理适配器也向系统TCP连接上的消息代理发送和接收心跳信息。可以配置发送和接收心跳的间隔（默认为10秒）。如果到代理的连接失效，代理适配器将每隔5秒尝试重连直到成功建立连接。

>当到代理的系统连接失效又重新建立时可以通过实现ApplicationListener<BrokerAvailabilityEvent>的Spring实体来接收相关通知。例如一个股票报价广播服务可以在没有活跃的“系统”连接时停止发送消息。

STOMP代理适配器也可以配置虚拟主机属性。这个属性的值将设置为每一个连接帧的主机头，这将对在云中确定真正指向的主机很有帮助，建立TCP连接不同于主机提供的云STOMP服务。

####21.4.9用"."作为@MessageMapping中目标的分隔器

尽管"/"分隔路径模式为开发者熟知，在消息传递中通常也会用到"."来分隔，例如在主题，队列，交流等的命名中。应用程序中也可以通过配置一个自定义的AntPathMatcher从而用"."替代"/"作为@MessageMapping映射的分隔器。

Java配置：

	@Configuration
	@EnableWebSocketMessageBroker
	public class WebsocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	  // ...
	
	  @Override
	  public void configureMessageBroker(MessageBrokerRegistry registry) {
	    registry.enableStompBrokerRelay("/queue/", "/topic/");
	    registry.setApplicationDestinationPrefixes("/app");
	    registry.setPathMatcher(new AntPathMatcher("."));
	  }
	
	}

XML配置：

	<beans xmlns="http://www.springframework.org/schema/beans"
	  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	  xmlns:websocket="http://www.springframework.org/schema/websocket"
	  xsi:schemaLocation="
	    http://www.springframework.org/schema/beans
	    http://www.springframework.org/schema/beans/spring-beans.xsd
	    http://www.springframework.org/schema/websocket
	    http://www.springframework.org/schema/websocket/spring-websocket.xsd">
	
	  <websocket:message-broker application-destination-prefix="/app" path-matcher="pathMatcher">
	    <websocket:stomp-endpoint path="/stomp" />
	    <websocket:simple-broker prefix="/topic, /queue"/>
	  </websocket:message-broker>
	
	  <bean id="pathMatcher" class="org.springframework.util.AntPathMatcher">
	    <constructor-arg index="0" value="." />
	  </bean>
	
	</beans>

如下是个控制器中用"."分隔的小例子：

	@Controller
	@MessageMapping("foo")
	public class FooController {
	
	  @MessageMapping("bar.{baz}")
	  public void handleBaz(@DestinationVariable String baz) {
	  }
	
	}

如果映射的前边拼接上"/app"那么有效的映射变成"/app/foo.bar.{baz}"。

####21.4.10身份验证

WebSocket类型应用程序很容易知道消息发送方。因此需要建立将用户身份和当前会话绑定的身份认证。

现存的Web应用程序已经用了基于HTTP的身份认证。如Spring Security可以保护应用程序中的基于HTTP的URLs。自从基于HTTP的握手作为WebSocket会话的开始，STOMP/WebSocket的URLs自然而然的需要身份认证和受到保护。此外在真正握手前页面打开的连接本身就是受保护的，用户应该通过身份验证。

当创建了WebSocket握手和WebSocket会话，Spring的WebSocket支持自动地将java.security.Principal从HTTP请求中传到WebSocket会话中。之后应用程序中那个WebSocket会话中的每个消息流都携带这个用户信息。目前它被放在消息的头部。控制器方法可以同添加一个javax.security.Principal类型的方法参数来访问当前的用户。

尽管STOMP连接帧存在可以用来身份认证的"login"和"passcode"头，Spring的STOMP WebSocket支持不理会它们而是只认已经通过HTTP身份认证的用户。

有些时候这对给WebSocket会话分配身份很有用尤其是当用户还没有获得正式的身份认证时。例如一个移动端应用可能给分配一些或许具有地理位置的匿名用户。可以在应用程序中继承DefaultHandshakeHandler重写determineUser方法。可以加入自定义的握手处理（参考21.2.4节，“部署注意事项”）。

####21.4.11用户目标地址

应用程序可以发送消息标记一个特定的用户。Spring的STOMP支持承认"/user/"前缀的目标地址。例如，一个客户端可能订阅到"/user/queue/position-updates"地址。这个地址将被UserDestinationMessageHandler处理成一个用户会话特有的地址，如"/queue/position-updates-user123"。这给一个通用地址中不同用户互不干扰的接收自己订阅的股票更新提供了便利。

在消息发送方，如发送到"/user/{username}/queue/position-updates"的消息的地址将被UserDestinationMessageHandler依次转换成一到多个跟用户相关的会话。这使应用程序内置组件向只有特定的名称和通用目的地址的用户发送消息成为可能。同消息模板，这也支持注解方式。

例如消息处理方法可以向被@sendToUser注解的信息相关的用户发送消息：

	@Controller
	public class PortfolioController {
	
	    @MessageMapping("/trade")
	    @SendToUser("/queue/position-updates")
	    public TradeResult executeTrade(Trade trade, Principal principal) {
	        // ...
	        return tradeResult;
	    }
	}

如果用户存在多个会话，默认所有会话给定的地址都要被标记。但是有时候，只需要标记正在处理的消息的会话。可以通过设置broadcast属性为false做到，如：

	@Controller
	public class MyController {
	
	    @MessageMapping("/action")
	    public void handleAction() throws Exception{
	        // raise MyBusinessException here
	    }
	
	    @MessageExceptionHandler
	    @SendToUser(value="/queue/errors", broadcast=false)
	    public ApplicationError handleException(MyBusinessException exception) {
	        // ...
	        return appError;
	    }
	}

>用户目标地址通常意味着一个验证的用户，没有严格这样要求。一个WebSocket会话能不能订阅用户目标跟用户是否经过身份验证无关。这种情况下@SendToUser注解中加不加broadcast=false都一样，例如，针对唯一会话发送处理的消息。

同样可以在任何应用组件中用Java或XML配置注入SimpMessageTemplate从而给目标用户发送消息，如（叫"brokerMessagingTemplate"的实体，如果需要资格加上@Qualifier注解）：

	@Service
	public class TradeServiceImpl implements TradeService {
	
		private final SimpMessageTemplate messagingTemplate;
	
		@Autowired
		public TradeServiceImpl(SimpMessageTemplate messagingTemplate) {
			this.messagingTemplate = messagingTemplate;
		}
	
		// ...
	
		public void afterTradeExecuted(Trade trade) {
			this.messagingTemplate.convertAndSendToUser(
					trade.getUserName(), "/queue/position-updates", trade.getResult());
		}
	}

>当用到外部消息代理的用户目标时，检查代理的文档获得的如何管理非活动的队列帮助，这样当用户的会话结束时所有的用户队列将会被移除。例如，当RabbitMQ开启如/exchange/amq.direct/position-updates的目标时会创建自动清除的队列。所有那种情况下客户端将订阅到/user/exchange/amq.direct/position-updates。ActiveMQ有清除非活动目标的配置项可供使用。

####21.4.12应用程序环境中的事件和消息拦截的监听

一些发布的应用程序环境中的事件（列表如下）可以通过实现ApplicationListener接口的监听捕获。

+ BrokerAvailabilityEvent——表明代理何时有效/何时无效。
+ SessionConnectEvent——
+ SessionConnectedEvent
+ SessionSubscribeEvent 
+ SessionUnsubscribeEvent
+ SessionDisconnectEvent

####21.4.13WebSocket作用域

每个WebSocket会话含有一个属性map集合。入站客户端消息附带着这个属性集合，可以通过控制器的方法访问到它，如：

	@Controller
	public class MyController {
	
	    @MessageMapping("/action")
	    public void handle(SimpMessageHeaderAccessor headerAccessor) {
	        Map<String, Object> attrs = headerAccessor.getSessionAttributes();
	        // ...
	    }
	}

在"websocket"作用域声明一个Spring监管的实体也是可行的。"websocket"作用域中的实体可以注入到控制器和任何注册在"clientInboundChannel"上的通道拦截器中。它们都是单例的并且比任何WebSocket会话都存活更久。因此你需要使用一个"websocket"作用域实体的代理：

	@Component
	@Scope(value="websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public class MyBean {
	
	    @PostConstruct
	    public void init() {
	        // Invoked after dependencies injected
	    }
	
	    // ...
	
	    @PreDestroy
	    public void destroy() {
	        // Invoked when the WebSocket session ends
	    }
	}
	
	@Controller
	public class MyController {
	
	    private final MyBean myBean;
	
	    @Autowired
	    public MyController(MyBean myBean) {
	        this.myBean = myBean;
	    }
	
	    @MessageMapping("/action")
	    public void handle() {
	        // this.myBean from the current WebSocket session
	    }
	}

正如自定义的作用域，Spring在访问控制器的一瞬间初始化一个新的MyBean实例并且将实例存储为WebSocket会话的属性。随后返回相同的实例,直到会话结束。WebSocket作用域的实体将存在如上展示的所有Spring生命周期中方法的调用。

####21.4.14配置和


####21.4.15运行时监控


####21.4.16注解的控制器方法的测试


----
----
----
----
----