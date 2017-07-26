<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fnc" uri="/WEB-INF/tlds/global.tld"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="<spring:url value='/webjars/bootstrap/2.3.0/css/bootstrap.min.css' />" rel="stylesheet" />
	<style type="text/css">
#connect-container {
	float: left;
	width: 400px
}

#connect-container div {
	padding: 5px;
}

#console-container {
	float: left;
	margin-left: 15px;
	width: 400px;
}

#console {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 170px;
	overflow-y: scroll;
	padding: 5px;
	width: 100%;
}

#console p {
	padding: 0;
	margin: 0;
}
</style>
</head>
<body>
	<div>
		<div id="connect-container">
		</div>
		<div id="console-container">
			<div id="console"></div>
			<div>
				<textarea id="message" style="width: 350px">${fnc:getAppPrefix()}</textarea>
				<button id="connect" onclick="connect();">Connect</button>
				<button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
				<button id="echo" onclick="sendMessage()" disabled="disabled">send</button>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		var stompClient = null;
		var request = "/xmkf${fnc:getStompEndPoint()}";

		function connect() {
			var socket = new SockJS(request);
			stompClient = Stomp.over(socket);
			
			stompClient.connect({}, function(frame) {
				setConnected(true);
				log('Connected: ' + frame);
				stompClient.subscribe('${fnc:getChatSingle()}${channelid}', function(message){
					log(JSON.parse(message.body).content);
	            });
			});
		}

		function setConnected(connected) {
			document.getElementById('connect').disabled = connected;
			document.getElementById('disconnect').disabled = !connected;
			document.getElementById('echo').disabled = !connected;
		}
		
		function disconnect() {
			stompClient.disconnect();
			setConnected(false);
			log("Disconnected");
		}

		function sendMessage() {
			var msg = document.getElementById('message').value;
			stompClient.send("${fnc:getAppPrefix()}${fnc:getChatSingle()}${channelid}", {}, JSON.stringify({
				'content' : msg,
				'id' : 1
			}));
			document.getElementById('message').value = "";
		}

		function log(message) {
			var console = document.getElementById('console');
			var p = document.createElement('p');
			p.style.wordWrap = 'break-word';
			p.appendChild(document.createTextNode(message));
			console.appendChild(p);
			while (console.childNodes.length > 25) {
				console.removeChild(console.firstChild);
			}
			console.scrollTop = console.scrollHeight;
		}
	</script>

	<!-- 延迟加载js，提高页面加载速度 -->
	<script src="<spring:url value="/webjars/jquery/2.0.3/jquery.js" />"></script>
	<script src="<spring:url value='/resources/js/sockjs-0.3.min.js' />"></script>
	<script src="<spring:url value='/resources/js/stomp.js' />"></script>
</body>
</html>

