<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page import="com.cn.hnust.common.Constant" %> 
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/personal-indent-context.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<title></title>
		<script type="text/javascript">
		var baseUrl='<%= Constant.ResouseUrl%>';
		window.parent.baseUrl=baseUrl;
		</script>
		
	</head>
	<body>
		<div class="body">
			<c:forEach var="s" items="${list}" varStatus='item'>
					
				<div class='item' onclick='onItemClick("${s.museumId}",this.id,"${s.languageId}")' id='${s.historicalRelicId}'>
					<div class='time'>${s.buyTime}</div>
					<div class='pic' style='background-image: url("<%= Constant.ResouseUrl%>${s.pictureAddress}");'>
						<c:choose>
					    <c:when test="${s.listen == 'YES'}">
					    		<div class='pic-type' style='background-image: url("${pageContext.request.contextPath}/img/y.png");'></div>
					    </c:when>
					    <c:when test="${s.listen == 'NO'}">
					    		<div class='pic-type' style='background-image: url("${pageContext.request.contextPath}/img/n.png");'></div>
					    </c:when>
					  </c:choose>
					  
					  <div class = 'lid-type'>${s.languageType}</div>
						
					</div>
					<div class='info'>
						<div class='info-name'><strong>${s.historicalRelicName}</strong></div>
						<div class='info-type'>${s.level}</div>
						<div class='info-time'>${s.dynasty}</div>
						<div class='info-parent'>${s.museumName}</div>
					</div>
				</div>
			</c:forEach>
		
		</div>
		
		<script>
			var languageId=${languageId};
			/* var museumId=${museumId}; */
			var userId=${userId};
			function onItemClick(museumId, id, lId){
				window.top.location.href="${pageContext.request.contextPath}/singleHeritageListening/"+museumId+"/"+id+"/"+userId+"/"+lId;
			}
			
		</script>
	</body>
</html>
