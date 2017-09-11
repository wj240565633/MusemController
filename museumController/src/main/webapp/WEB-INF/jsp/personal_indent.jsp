<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title>我的宝贝</title>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/personal-indent.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/personal-indent.js" ></script>
		<script>
			/**
			 * 获取url参数
			 * @param {Object} name
			 */
			function GetQueryString(name){
			     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			     var r = window.location.search.substr(1).match(reg);
			     if(r!=null)return  unescape(r[2]);
			     return null;
			}
			
			
		</script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		
	</head>
	<body>
	<div class="body">
		<div class="title">
			<div>我的宝贝</div>
			<select id="app-header-select" onchange="onTypeChanged()">
				<option value="All">全部</option>
				<option value="Listened">已收听</option>
				<option value="Not_listening">未收听</option>
				
			</select>
		</div>
		<div class="search">
			<input type="text"id="search-text" class="search-text" placeholder="输入博物馆名称或文物名称、级别、朝代"
				 style="width: 90%;height: 30px;margin-top: 10px;border: none;font-family: '微软雅黑';
				 font-size: 12px; background: #EBEBEB;float: left;"/>
				 <div class="search-button" onclick="searchThing()"></div>
			
		</div>
		<div class="line"></div>
		<div class="child">
			<iframe class="child-item-body" frameborder="0px" src="${pageContext.request.contextPath}/purchaseRecordContext/${userId}/All"></iframe>
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
			//alert("出错了，请关闭页面重新载入");
		});
	}
		window.parent.userId="${userId}"
		$('.body').css('height',(screen.height-72)+"px");
		var titleh= $('.title').css('height').split("p")[0];
		var searchj=$('.search').css('height').split("p")[0];
		var childh=screen.height-titleh-searchj-72;
		$('.child').css('height',childh+"px");
	</script>
	</body>
</html>
