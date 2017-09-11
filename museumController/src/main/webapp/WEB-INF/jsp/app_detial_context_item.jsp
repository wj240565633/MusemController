<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page import="com.cn.hnust.common.Constant" %>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title>文物介绍</title>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app-detial-context-item.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/app-detial-context-item.js" ></script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript">
		var baseUrl='<%= Constant.ResouseUrl%>';
		window.parent.baseUrl=baseUrl;
		</script>
	</head>
	<body>
		
		<!--标题栏-->
		<div class="app-header" onclick="none()" >
			<!--标题-->
			<span style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;padding-left: 50px;padding-right:50px;">${title}</span>
			<!--分享-->
			<!--<img onclick="share()" src="${pageContext.request.contextPath}/img/share.png" style="position: absolute; top: 11.5px;right: 5%;width: 30px; height: 30px;"/>-->
			<!--返回-->
			<img onclick="back()" src="${pageContext.request.contextPath}/img/back.png" style="position: absolute; top: 11.5px;left: 5%;width: 25px; height: 25px;"/>
		</div>
		<!--内容-->
		<div class="app-context" onclick="none()">
			<!--内容区域-->
			<div class="app-context-body"  ontouchstart = "return false;"></div>
			<div class="speech" type="1" ></div>
		</div>
		
		<script>
			var share_Url = '<%= Constant.SHARE_BASEURL%>${museumId}/${historicalRelicId}?shareId=${userId}'
			//window.ontouchstart = function(e) { e.preventDefault(); };

			var h= screen.height;
			$('.app-context').css('height',(h-170)+"px");
			$('.app-context-body').css('height',(h-170)+"px");
			var userId="${userId}"; 
			window.parent.userId=userId;
			
			var freeNum="${freeNum}";
			var picList="${picList}";
			if(freeNum!=-1){
				mui.toast('还可免费收听'+freeNum,{ duration:'short', type:'div' });
			}
			
			window.parent.picList=picList.split(",");
			var shareimgUrl = window.parent.baseUrl+window.parent.picList[0];
			var shareLink = share_Url;
			console.log(shareimgUrl);
			window.parent.content="${content}";
			//去除</br>
			var shareContent = "${content}";
			shareContent =	shareContent.replace(new RegExp("</br>",'g'),"");
			window.parent.soundAddr="<%= Constant.ResouseUrl%>${soundAddr}";
			$('.app-context-body').append('<iframe class="app-context-body-iframe"></iframe>');
			$('.app-context-body-iframe').attr('src',"${pageContext.request.contextPath}/listenerContent");	
			$('.app-context-body-iframe').css('height',(h-170)+"px");
			//语音收听
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
				    	'onMenuShareQZone'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				
				wx.ready(function(){	
					var soundUrl="<%= Constant.ResouseUrl%>${soundAddr}";
					$(".speech").append("<audio id='sound' ><source src="+soundUrl+" type='audio/mpeg'>页面不支持音频标签</audio>")
					var au=document.getElementById("sound");
					$(".speech").css("background-image","url('${pageContext.request.contextPath}/img/load.png')");
					$(".speech").attr('type',"2");
					au.load()
					au.pause();
					au.play();
					$('.speech').click(function (){
						
						if($(".speech").attr('type') ==1 && au.paused){
							$(".speech").css("background-image","url('${pageContext.request.contextPath}/img/load.png')");
							$(".speech").attr('type',"2");
							au.play();
						}else if($(".speech").attr('type') ==2 && !au.paused){
							au.pause();
							$(".speech").css("background-image","url('${pageContext.request.contextPath}/img/pause.png')");
							$(".speech").attr('type',"1");
						}
						
					});
					
					au.addEventListener("ended",function(){
						$(".speech").css("background-image","url('${pageContext.request.contextPath}/img/pause.png')");
						$(".speech").attr('type',"1");
					});
					
					wx.hideMenuItems({
					    menuList: [
					    	"menuItem:editTag",
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
					    title: '${title}', // 分享标题
					    link: shareLink, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
					    imgUrl: shareimgUrl, // 分享图标
					    success: function () { 
					        // 用户确认分享后执行的回调函数
					    },
					    cancel: function () { 
					        // 用户取消分享后执行的回调函数
					    }
					});
					
					wx.onMenuShareAppMessage({
					    title: '${title}', // 分享标题
					    desc: shareContent, // 分享描述
					    link: shareLink, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
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
					    title: '${title}', // 分享标题
					    desc: shareContent, // 分享描述
					    link: shareLink, // 分享链接
					    imgUrl: shareimgUrl, // 分享图标
					    success: function () { 
					       // 用户确认分享后执行的回调函数
					    },
					    cancel: function () { 
					       // 用户取消分享后执行的回调函数
					    }
					});
					
					wx.onMenuShareQZone({
					    title: '${title}', // 分享标题
					    desc: shareContent, // 分享描述
					    link: shareLink, // 分享链接
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
			
			
			function none(){}
			
			
			
			
			</script>
			
		
	</body>
</html>
