<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
  
    <base href="<%=basePath%>">
    
    <title>My JSP 'Shi.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <h1>验证</h1>
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
    
    </script>
  </body>
</html>
