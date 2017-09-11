<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title>吐槽</title>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/personal_complain.css" />
	<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	
	</head>
	<body>
		<div class="body">
			<div class="title">我要吐槽</div>
			<div class="content">
				<iframe id="frame1" class="frame" frameborder="0" border="0" style="margin-top:0px; margin:0px; padding:0px;"height="100%" width="100%" src="${pageContext.request.contextPath}/getUserFeedBackContent/${userId}"></iframe>
			</div>
			<div class="bottom">
				<div class="submit-top-line"></div>
				<input type="text" class="in_text" />
				<div class="div-submit" onclick="onSubmit()"></div>
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
			});
			
			wx.error(function(res){
				alert("出错了，请关闭页面重新载入");
			});
		}
			var h=screen.height;
			$(".body").css('height',(h-82)+"px");
			$('.content').css('height',(h-72-96)+"px");
			$('.frame').css('height',(h-72-112)+"px");
				
			function onSubmit(){
				var text=$('.in_text').val();
				$.ajax({
					url :"${pageContext.request.contextPath}/userFeedBack/${userId}",
					type : "post",
					dataType : "json",
					data : {
						text
					},
					success : function(obj) {
						if(obj.length>0){
							document.getElementsByClassName("in_text")[0].value="";
							$('.frame').attr("src","${pageContext.request.contextPath}/getUserFeedBackContent/${userId}");
						}
						
					},
					error : function() {
						mui.toast('获取数据失败',{ duration:'short', type:'div' }); 
					}
				});
			}
			
			
			
			
		</script>
		
	</body>
</html>
