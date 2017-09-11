<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.cn.hnust.common.Constant" %> 
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title>博物馆详情</title>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/app-detial.js" ></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app-detial.css" />
		<script type="text/javascript">
		var baseUrl='<%= Constant.ResouseUrl%>';
		window.parent.baseUrl=baseUrl;
		</script>
		<style>
		
			.speech{
			position: fixed;
			bottom: 70px;
			right: 5%;
			width: 60px;
			height: 60px;
			background-image: url("${pageContext.request.contextPath}/img/unload.png");
			background-size: 100% 100%;
			z-index: 998;
			}
		
		</style>
	</head>
	<body>
		<!--头部-->
		<div class="app-header"   >
			<!--标题-->
			<span>${museumName}</span>
			<!--语种-->
			
			
			<select id="app-header-se" onchange="onlanguagechange()">
				<c:forEach var="s" items="${languaList}" varStatus='item'>
					<option value="${s.languageId}">${s.languageType}</option>
					<script>
					mLanguageList.push("${s.languageType}");
					mLanguageIdList.push("${s.languageId}");
					</script>
				</c:forEach>
			</select>
			<!--返回按钮-->
			<img onclick="back()" src="${pageContext.request.contextPath}/img/back.png" style="position: absolute; top: 11.5px;left: 5%;width: 25px; height: 25px;"/>
		</div>
		<!--内容区域-->
		<div class="app-context">
			<!--搜索按钮-->
			<div class="app-context-search">
			<div style="position: absolute;width: 96%;height: 1px;bottom: 10px;background-color: #444444;right: 2%;left: 2%;"></div>
				<input id="app-context-search-input" style="bottom: 5px;background: #1A1E21; border: none; position: absolute; width: 80%;height: 30px; line-height: 30px; bottom: 0;left: 0; font-family: '微软雅黑';font-size: 14px; color: #CCCCCC;" type="text" placeholder="输入您要找的文物名称、级别或朝代" />
				<img src="${pageContext.request.contextPath}/img/search.png" style="position: absolute; width: 20px;bottom: 5px; top: 10px;right: 5%; height: 20px;" onclick="search()"/>
			</div>
			<!--内容-->
			<div class="app-context-body">	
			</div>
		</div>
		
		<div class="speech" type="1"></div>
		 		
		
		<script type="text/javascript">
		
		var sh=screen.height-72;
		var body=sh-48*2;
		$('.app-context-body').css('height',(body-50)+"px");
		
		
		function GetQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]);
		     return null;
		}
		
			var openId=GetQueryString("openid");
			var picArr="${pictureAddress}";
			var soundAddress="${soundAddress}"
			window.parent.soundAddress=soundAddress;
			mPicList=picArr.split(",");
			//当前语种
			mCurrentLanguage="${languageName}";
			window.parent.mCurrentLanguage = mCurrentLanguage;
			mMuseumName="${museumName}";
			mSoundPath="${soundAddress}";
			mMuseumDesc="${describe}";
	
			mMuseumId="${museumId}";
			
			var languageId="${languageId}";
			mLanguageId=languageId;
			window.parent.nomalLanguageId=languageId;
			$("#app-header-se option[value='"+languageId+"']").attr("selected","selected");
			var userId="${userId}";
			window.parent.userId=userId;
			if(GetQueryString("type")=="100"){
				$('.app-context-body').append("<iframe class='app-context-body-iframe'></iframe>");
				$('.app-context-body-iframe').attr('src',"${pageContext.request.contextPath}/historicalRelicTabulation/"+mMuseumId+"/"+mLanguageId+"/"+userId+"/0/15");
					$.ajax({
						url :"${pageContext.request.contextPath}/jsConfig",
						type : "get",
						headers :{"url":document.URL.split("#")[0]},
						dataType : "json",
						async: true,
						success : function(obj) {
							configJSSDK1(obj);
						},
						error : function() {
						alert("出错了，请关闭页面重新载入");
						}
					});
				
			}else{
				$('.speech').hide();
				$('.app-context-body').append("<iframe class='app-context-body-iframe'></iframe>");
				$('.app-context-body-iframe').attr('src',"${pageContext.request.contextPath}/batchDataFetch/"+mMuseumId+"/"+mLanguageId+"/"+userId);
			}
			
			$('.app-context-body-iframe').css('height',body+"px");
			
			window.parent.single=true;
			function configJSSDK1(obj){
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: obj.appid, // 必填，公众号的唯一标识
				    timestamp: obj.timeStamp, // 必填，生成签名的时间戳
				    nonceStr: obj.nonceStr, // 必填，生成签名的随机串
				    signature: obj.signature+"", // 必填，签名，见附录1
				    jsApiList: ['playVoice','pauseVoice','stopVoice','chooseWXPay','hideAllNonBaseMenuItem'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				
				wx.ready(function(){
					
					wx.hideAllNonBaseMenuItem(); 
					
					if(GetQueryString("type") == "1"){
						
						$('.speech').hide();
					}
					var soundUrl="<%= Constant.ResouseUrl%>"+mSoundPath;
					$(".speech").append("<audio id='sound' ><source src="+soundUrl+" type='audio/mpeg'>页面不支持音频标签</audio>")
					var au=document.getElementById("sound");
					au.load()
					au.pause();
					au.play();
					au.pause();
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
					
				});
				
				wx.error(function(res){
					alert("出错了，请关闭页面重新载入");
				});
			}
			
			//微信支付
			function WXPay(obj1,total,saletotal,discountId, num,languageId,userid,mMuseumid,itemid){
					
				$.ajax({
					url :"${pageContext.request.contextPath}/jsConfig",
					type : "get",
					headers :{"url":document.URL.split("#")[0]},
					dataType : "json",
					async: false,
					success : function(obj) {
						configJSSDK(obj);
					},
					error : function() {
					alert("出错了，请关闭页面重新载入");
					}
				});
				function configJSSDK(obj){
					wx.config({
					    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					    appId: obj.appid, // 必填，公众号的唯一标识
					    timestamp: obj.timeStamp, // 必填，生成签名的时间戳
					    nonceStr: obj.nonceStr, // 必填，生成签名的随机串
					    signature: obj.signature+"", // 必填，签名，见附录1
					    jsApiList: ['playVoice','pauseVoice','stopVoice','chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
					});
					
					wx.ready(function(){
						//开始支付
						pay();
						function onBridgeReady(){
							var appid = obj1.appId;
							var timeStamp=obj1.timeStamp;
							var nonceStr=obj1.nonceStr;
							var pkg= obj1.pkg;
							var signType=obj1.signType;
							var paySign=obj1.paySign;
					        WeixinJSBridge.invoke(
					                'getBrandWCPayRequest', {
					                   "appId" : appid,     //公众号名称，由商户传入     
					                    "timeStamp":timeStamp,         //时间戳，自1970年以来的秒数     
					                    "nonceStr" : nonceStr, //随机串     
					                    "package" : pkg,     
					                    "signType" : signType,         //微信签名方式:     
					                    "paySign" : paySign //微信签名 
					               },
					                function(res){
										// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返
					                   if(res.err_msg == "get_brand_wcpay_request:ok" ) {  
					                	   postPayRes();
			                          	}
					               }
					           ); 
					         }
					    function pay(){  
					        if (typeof WeixinJSBridge == "undefined"){
					           if( document.addEventListener ){
					                 document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
					             }else if (document.attachEvent){
					                 document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
					                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
					            }
					         }else{
					        	 onBridgeReady();
					         }
					     }
						
					});
					
					wx.error(function(res){
						alert("出错了，请关闭页面重新载入");
					});
				}
				
				//total,saletotal,discountId, num,languageId,userid,mMuseumid,itemid
				function postPayRes(){
					var id = itemid;
					$.ajax({
					url :"${pageContext.request.contextPath}/postPayRes",
					type : "post",
					data :{"total":total,"saletotal":saletotal,"discountId":discountId,"number":num,"languageId":languageId,
						"userId":userid,"museumId":mMuseumid,"item":itemid
					},
					dataType : "text",
					async: false,
					success : function(obj) {
						if (100 == obj ){
							if(itemid.indexOf(",") > 0 ){
								$('.app-context-body-iframe').attr('src',"${pageContext.request.contextPath}/historicalRelicTabulation/"+window.parent.mMuseumId+"/"+window.parent.mLanguageId+"/"+window.parent.userId+"/0/15");
							}else{
								window.top.location.href="${pageContext.request.contextPath}/singleHeritageListening/"+window.parent.mMuseumId+"/"+itemid+"/"+window.parent.userId+"/"+window.parent.mLanguageId;
							}
							
						}else{
							alert("出错了，请关闭页面重新重新载入");
						}
						
						
					},
					error : function() {
					alert("出错了，请关闭页面重新载入");
					}
				});
					
				}
			}
			
		
		</script>
		
	</body>
</html>
