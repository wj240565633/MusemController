<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page import="com.cn.hnust.common.Constant" %>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,height=device-height, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title>博物馆列表</title>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css" />
		<script type="text/javascript">
		var baseUrl='<%= Constant.ResouseUrl%>';
		window.parent.baseUrl=baseUrl;
		</script>
	</head>
	<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/app.js" ></script>
	
	<script>
	
		function getQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]);
		     return null;
		}
		
		var userId="${userId}";
		window.parent.openId="${openid}";
		//给当前界面全局变量赋值。
		window.parent.userId=userId;
	
	</script>
		<!--头部-->
		<div class="app-header">
			<!--标题-->
			<span>博物馆列表</span>
			<!--语种-->
			<select id="app-header-select" onchange="onlanguagechange()">
			</select>
		</div>
		<!--内容区域-->
		<div class="app-context">
			<!--搜索-->
			<div class="app-context-search">
			<div style="position: absolute;width: 96%;height: 1px;bottom: 10px;background-color: #444444;right: 2%;left: 2%;"></div>
				<input id="app-context-search-input" style="bottom: 5px;background: #1A1E21; border: none; position: absolute; width: 80%;height: 30px; line-height: 30px; bottom: 0;left: 0; font-family: '微软雅黑';font-size: 14px; color: #CCCCCC;" type="text" placeholder="输入您要找的博物馆名称" />
				<img src="${pageContext.request.contextPath}/img/search.png" style="position: absolute; width: 20px;bottom: 5px; top: 10px;right: 5%; height: 20px;" onclick="search()"/>
			</div>
			<!--内容-->
			<div class='app-context-body'>
			<iframe class="app-context-body-iframe" frameborder="0"></iframe>
			</div>
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
							wx.hideOptionMenu();
							document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
							    // 通过下面这个API隐藏右上角按钮
							    WeixinJSBridge.call('hideOptionMenu');
							});

						});
						
						wx.error(function(res){
							alert("出错了，请关闭页面重新载入");
						});
					}
		
		console.log("${openid}");
		//src="${pageContext.request.contextPath}/museumHomePage/0/15"
			/*设置布局模块大小 */
		    var sh=screen.height-72;
			var app_header_h=$('.app-header').css('height').split("p")[0];
			var a_c_h=(sh-app_header_h)+"px";
			var a_c_b_h=(sh-app_header_h*2)+"px";
			var a_c_b_i_h=(sh-app_header_h*2)+"px";
			/* 设置标题栏以下的区域大小 */
			$('.app-context').css('height',a_c_h);
			/* 设置条目区域占据的布局大小(iframe的布局大小) */
			$('.app-context-body').css('height',a_c_b_h);
			$('.app-context-body-iframe').css('height',a_c_b_i_h);
			$('.app-context').css('background-color','#1A1E21');
		</script>
		
	</body>
</html>
