<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title>文物短号</title>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<!--js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cornet.css" />
		<!--文物短号界面-->
	</head>
	<body>
		
		
		<div class="body">
			<div class="title">文物短号</div>
			<div class="context">
				<div class="context-title"><strong>${museumName}</strong></div>
				<div class="context-name">
					<div class="left">文物短号</div>
					<div class="right">文物名称</div>
				</div>
				<div class="line"></div>
				<c:forEach var="s" items="${cornetList}" varStatus='item'>
					<div class='context-name' id = '${s.cornette}' onclick='itemClickListener(this.id)'>
						<div class='left'>${s.cornette}</div>
						<div class='right'>${s.historicalRelicName}</div>
					</div>
					
				</c:forEach>
				
			</div>
		</div>
		
		<script>
		
		/**
		*条目点击事件
		*/
		
		function itemClickListener(id){
			var a = "#"+id;
			$(a).css('backgroundColor','#D08D00');
			location.href = "${pageContext.request.contextPath}/nameAndCornet?userId=${userId}&museumName=${museumName}&cornette="+id
		}
		
		
		
		
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
		
			/* $('.body').css('height',screen.height+"px");
			var count=20;
			for (var i = 0; i < count; i++) {
				var id=i;
				var name="翡翠 "+i;
				$('.context').append("<div class='context-name'>"+
				"<div class='left'>"+i+"</div>"+
				"<div class='right'>"+name+"</div>"+
				"</div>");
			} */
			
		</script>
		
	</body>
</html>
