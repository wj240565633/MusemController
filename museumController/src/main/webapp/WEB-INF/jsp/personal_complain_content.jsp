<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>
	<head>
	<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title>吐槽</title>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<style type="text/css">
		 .item{
		 position: relative;
		 width: 100%;
		 }
		 
		 .bodao{
		  float:left;
		 width:30px;
		 height:30px;
		 background-image: url("${pageContext.request.contextPath}/img/bo.png");
		 background-size: 100% ,100%;
		 
		 }
		 
		  .person{
		 float:right;
		 width:30px;
		 height:30px;
		 background-image: url("${pageContext.request.contextPath}/img/user.png");
		 background-size: 100% ,100%;
		 
		 }
		 
		 .content{
		 width:70%;
		 word-break:break-all;
		 }
		 .mui-table-view-cell{
		 height: auto;}


		   
		</style>
	</head>
	<body>
	
		<div class="body"> 
		
		<ul class="mui-table-view">
			<c:forEach var="s" items="${list}" varStatus='item'>
			<c:choose>
				 	<c:when test="${null==s.answerContent}">
				 		
						 <li class="mui-table-view-cell">
						    <div class="item">
						    <div class="person"></div>
						    	<span class="content"  style='float: right;'><span class=" content_span"  style='float: right; margin-right: 10px; margin-top: 4px;'>${s.content}</span></span>
						    	
				 			</div>
						</li> 	
					</c:when>
					<c:when test="${null!=s.answerContent}">
						    <li class="mui-table-view-cell">
							     <div class="item">
							    	<div class="bodao"></div>
							    	<span class="content" style='float: left; margin-left: 10px;  margin-top: 4px;'>${s.answerContent}</span>
					 			</div>
						    </li>  		
					</c:when> 
				</c:choose>
			
			</c:forEach>
		</ul>
		
			
				
		</div>
		
		<script type="text/javascript">
		

		$.ajax({
						url :"${pageContext.request.contextPath}/jsConfig",
						type : "get",
						headers :{"url":document.URL.split("#")[0]},
						dataType : "json",
						async: true,
						success : function(obj) {
							configJSSDK(obj);
						},
						error : function() {
						alert("出错了，请关闭页面重新扫描");
						}
					});
					function configJSSDK(obj){
						wx.config({
						    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
						    appId: obj.appid, // 必填，公众号的唯一标识
						    timestamp: obj.timeStamp, // 必填，生成签名的时间戳
						    nonceStr: obj.nonceStr, // 必填，生成签名的随机串
						    signature: obj.signature+"", // 必填，签名，见附录1
						    jsApiList: ['playVoice',
						    	'pauseVoice',
						    	'stopVoice',
						    	'hideOptionMenu',
						    	'showOptionMenu',
						    	'hideMenuItems',
						    	'showMenuItems',
						    	'hideAllNonBaseMenuItem',
						    	'showAllNonBaseMenuItem',
						    	'onMenuShareTimeline',
						    	'onMenuShareAppMessage',
						    	'onMenuShareQQ',
						    	'onMenuShareWeibo',
						    	'onMenuShareQZone',
						    	'hideAllNonBaseMenuItem'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
						});
						
						wx.ready(function(){	
							wx.hideAllNonBaseMenuItem(); 
						});
						
						wx.error(function(res){
							alert("出错了，请关闭页面重新载入");
						});
					}
					
			var h=screen.height;
			$('.frame').css('width',"100%");
			$('.frame').css('height',(h-72-96)+"px");
			
			window.scrollTo(0, 99999);
			
		
		</script>
	
		
	</body>
</html>
