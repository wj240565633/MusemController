<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@page import="com.cn.hnust.common.Constant" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js"></script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<!-- base64 js -->
		<script src="${pageContext.request.contextPath}/js/Base64.js"></script>
		<style>
			*{
				margin: 0;
				padding: 0;
				background-color: #32363F;
			}
			
		</style>
		
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
			    jsApiList: ['scanQRCode',
			    	'hideAllNonBaseMenuItem'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
			
			wx.ready(function(){
				
				wx.hideAllNonBaseMenuItem(); 
				
				wx.scanQRCode({
				    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
				    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
				    success: function (res) {
				    	
				    	function GetRequest(url) { 
				    		var theRequest = new Object();

				    		if (url.indexOf("?")) { 
					    		var str = url.split("?")[1]; 
					    		strs = str.split("&"); 
					    		for(var i = 0; i < strs.length; i ++) { 
					    			theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
					    		} 
				    		} 
				    		return theRequest; 
				    	} 
				    	
				    	var request = new Object(); 
				    	request = GetRequest(res.resultStr); 
				    	var url = '<%= Constant.SHARE_BASEURL%>';
				    	var mId = request['museumId'];
				    	var dId = request['result'];
				  	window.location.href=url+mId+"/"+dId;
				}
				});

			});
			
			wx.error(function(res){
				alert("出错了，请关闭页面重新扫描");
			});
		}
		
		
		</script>
	</head>
	<body>
		
		
	</body>
</html>

