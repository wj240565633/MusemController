<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cn.hnust.common.Constant" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title>二维码</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		
		<style>
			*{
				margin: 0;
				padding: 0;
				background-color: #32363F;
			}
			.body{
				width: 100%;
				margin-top: 50px;
				text-align: center;
			}
			.title{
				font: "å¾®è½¯éé»";
				font-size: 18px;
				font-weight: 600;
				color: #F8F8F8;
			}
			.image img{
				width: 200px;
				height: 200px;
				margin-top: 50px;
				background-size: 100% ,100%;
			}
			
		</style>
		
		<script>
		var sharetitle = '<%= Constant.CLIENT_NAME%>：${nick}';
		var shareimgUrl = '<%= Constant.ResouseUrl%>${url}';
		var des = "亲，送你一件珍贵的文物,让他带你详细了解我们自己的历史吧！";
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
							wx.hideMenuItems({
							    menuList: ["menuItem:editTag",
							    	"menuItem:delete",
							    	"menuItem:copyUrl",
							    	"menuItem:originPage",
							    	"menuItem:readMode", 
							    	"menuItem:openWithQQBrowser",
							    	"menuItem:openWithSafari",
							    	"menuItem:share:email",
							    	"menuItem:share:brand",
							        "menuItem:share:weiboApp",
							    	"menuItem:share:facebook"
							    	] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
							});
							
							wx.onMenuShareTimeline({
							    title: sharetitle, // 分享标题
							    link: document.URL, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
							    imgUrl: shareimgUrl, // 分享图标
							    success: function () { 
							        // 用户确认分享后执行的回调函数
							    },
							    cancel: function () { 
							        // 用户取消分享后执行的回调函数
							    }
							});
							
							wx.onMenuShareAppMessage({
							    title: sharetitle, // 分享标题
							    desc: des, // 分享描述
							    link: document.URL, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
							    imgUrl: shareimgUrl, // 分享图标
							    type: '', // 分享类型,music、video或link，不填默认为link
							    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
							    success: function () { 
							        // 用户确认分享后执行的回调函数
							    },
							    cancel: function () { 
							        // 用户取消分享后执行的回调函数
							    }
							});
							
							wx.onMenuShareQQ({
							    title: sharetitle, // 分享标题
							    desc: des, // 分享描述
							    link: document.URL, // 分享链接
							    imgUrl: shareimgUrl, // 分享图标
							    success: function () { 
							       // 用户确认分享后执行的回调函数
							    },
							    cancel: function () { 
							       // 用户取消分享后执行的回调函数
							    }
							});
							
							wx.onMenuShareQZone({
							    title: sharetitle, // 分享标题
							    desc: des, // 分享描述
							    link: document.URL, // 分享链接
							    imgUrl: shareimgUrl, // 分享图标
							    success: function () { 
							       // 用户确认分享后执行的回调函数
							    },
							    cancel: function () { 
							        // 用户取消分享后执行的回调函数
							    }
							});
						});
						
						wx.error(function(res){
							alert("出错了，请关闭页面重新载入");
						});
					}
			
		</script>
	</head>
	<body>
		
		<div class="body">
			<div class="title">我的二维码</div>
			<div class="image"><img src="<%= Constant.ResouseUrl%>${url}"></div>
		</div>
	</body>
</html>
