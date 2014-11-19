<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="izy" uri="/WEB-INF/tags/pagination.tld"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="<spring:url value='/webjars/bootstrap/2.3.0/css/bootstrap.min.css' />" rel="stylesheet"/>
</head>
<body>
	
	<!-- 搜索form -->
	<spring:url value="/country/ip" var="search" />
	<form:form id="search_form" modelAttribute="model" action="${search}" method="post" class="breadcrumb form-search">
		<input type="hidden" id="pageNum" name="pageNum" />
		<input type="hidden" id="pageSize" name="pageSize" />
		
		<div style="margin-top:8px;">
			
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	
    <!-- 列表 -->
   	<table class="table">
   		<thead>
			<tr>
				<th>序号</th>
				<th>start_ip</th>
				<th>end_ip</th>
				<th>code2</th>
				<th>start_ip1</th>
				<th>end_ip1</th>
				<th>country_cn</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ips}" var="ip" varStatus="index">
				<tr style="cursor:pointer;">
				<td>${index.count}</td>
				<td>${ip.start_ip}</td>
				<td>${ip.end_ip}</td>
				<td>${ip.code2}</td>
				<td>${ip.start_ip1}</td>
				<td>${ip.end_ip1}</td>
				<td>${ip.country_cn}</td>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页DIV -->
	<izy:pagination pager="${pager}" pageTagClass="pagination pagination-large"/>
	
	<!-- 延迟加载js，提高页面加载速度 -->
	<script src="<spring:url value="/webjars/jquery/2.0.3/jquery.js" />"></script>
	<script src="<spring:url value="/resources/js/common.js" />"></script>
</body>
</html>

