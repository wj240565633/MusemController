<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page import="com.cn.hnust.common.Constant" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app-context.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/app.js" ></script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	</head>
	<body>
	
		<div class="context-body" style="overflow:auto">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
				<!-- 博物馆列表条目 -->
					<c:forEach var="s" items="${museumList}" varStatus='item'>
					 <div class='mui-scroll-item' id='${s.museumId}' style="left:${item.index*36}%;" onclick='onItemClickListener(this.id)'>
						<img src='<%= Constant.ResouseUrl%>${s.pictureAddress}'/>
						<div class='bg'></div>
						<div class='title'>${s.museumName}</div>	
					</div>
					</c:forEach>
				</div>
				<script>
				
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
					var sh=screen.height;
					var statusBarHeight=72;
					var context_body_height=sh-statusBarHeight-96;
					$('.context-body').css('height',context_body_height+"px");
					$('.mui-scroll-item').css('height',context_body_height+"px");
					$('.mui-scroll-wrapper').css('height',"100%");
					$('.mui-scroll').css('height',"100%");
					//$('.bg').css('height',context_body_height+"px");

				</script>
			</div>
		</div>
	</body>
</html>
