<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fnc" uri="/WEB-INF/tlds/global.tld"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="<spring:url value='/webjars/bootstrap/2.3.0/css/bootstrap.min.css' />" rel="stylesheet" />
	<link href="<spring:url value='/resources/css/chat.css' />" rel="stylesheet" />
</head>
<body>
	<div>
		<div id="console-container">
			<div id="console">
			    <ul class="breadcrumb">
				    <li><button id="disconnect" onclick="disconnect();">关闭窗口</button></li>
			    </ul>
			</div>
			<br />
			<div>
				<textarea id="message" style="width: 788px"></textarea>
				<button id="echo" onclick="echo()" >send</button>
			</div>
		</div>
	</div>
	

	<!-- 延迟加载js，提高页面加载速度 -->
	<script src="<spring:url value="/webjars/jquery/2.0.3/jquery.js" />"></script>
	<script src="<spring:url value='/resources/js/sockjs-0.3.min.js' />"></script>
	
	<script type="text/javascript">
		var ws = null;
		var url = null;
		function setConnected(connected) {
			document.getElementById('echo').disabled = !connected;
		}
		$(function(){
			updateUrl('/xmkf/webSocketServer');
			if (!url) {
				alert('Select whether to use W3C WebSocket or SockJS');
				return;
			}
			ws = (url.indexOf('sockjs') != -1) ? new SockJS(url, undefined, {
				protocols_whitelist : transports
			}) : new WebSocket(url);
			ws.onopen = function() {
				setConnected(true);
				//log('Info: connection opened.');
			};
			ws.onmessage = function(event) {
				//log('Received: ' + event.data);
				log(event.data);
			};
			ws.onclose = function(event) {
				setConnected(false);
				window.close();
				//log('Info: connection closed.');
				//log(event);
			};
		});
		
		function connect() {
			updateUrl('/xmkf/webSocketServer');
			if (!url) {
				alert('Select whether to use W3C WebSocket or SockJS');
				return;
			}
			ws = (url.indexOf('sockjs') != -1) ? new SockJS(url, undefined, {
				protocols_whitelist : transports
			}) : new WebSocket(url);
			ws.onopen = function() {
				setConnected(true);
				//log('Info: connection opened.');
			};
			ws.onmessage = function(event) {
				log(event.data);
			};
			ws.onclose = function(event) {
				setConnected(false);
				//log('Info: connection closed.');
			};
		}
		
		function disconnect() {
			if (ws != null) {
				ws.close();
				ws = null;
			}
			setConnected(false);
		}
		
		function echo() {
			if (ws != null) {
				var message = document.getElementById('message').value;
				log("mine : "+message);
				ws.send(message);
				document.getElementById('message').value = "";
			} else {
				alert('connection not established, please connect.');
			}
		}
		
		function updateUrl(urlPath) {
			if (window.location.protocol == 'http:') {
				url = 'ws://' + window.location.host + urlPath;
			} else {
				url = 'wss://' + window.location.host + urlPath;
			}
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
</body>
</html>

