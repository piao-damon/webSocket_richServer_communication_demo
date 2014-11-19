<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="<spring:url value='/webjars/bootstrap/2.3.0/css/bootstrap.min.css' />" rel="stylesheet"/>
</head>
<body>
	<!-- 列表 -->
   	<table class="table">
   		<thead>
			<tr>
				<th>序号</th>
				<th>在线活跃用户会话（sessionid）</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${onlineusers}" var="onlineuser" varStatus="index">
				<tr style="cursor:pointer;">
				<td>${index.count}</td>
				<td>${onlineuser.http_websocket_session_id}</td>
				<td><a class="btn" href="<spring:url value='/chat/link/to/'/>${onlineuser.http_websocket_session_id}">创建连接</a></td>
				<!--
					<td><a class="btn" onclick="javascript:newClient('<spring:url value='/chat/link/to/'/>${onlineuser.http_websocket_session_id}','',888,588);" target="_blank">创建连接</a></td>
				-->
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 延迟加载js，提高页面加载速度 -->
	<script src="<spring:url value='/webjars/jquery/2.0.3/jquery.js' />"></script>
	<script src="<spring:url value="/resources/js/common.js" />"></script>
	
</body>
</html>

