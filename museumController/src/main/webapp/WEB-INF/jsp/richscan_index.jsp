<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.cn.hnust.common.Constant" %>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title>文物详情</title>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>  
		<!-- base64 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/Base64.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/richscan-index.css" />
		<script type="text/javascript">
			var baseUrl='<%= Constant.ResouseUrl%>';
			window.parent.baseUrl=baseUrl;
		</script>

	</head>
	<body style="background-color: #FFFFFF ;">
		<div class="richscan-body">
			<div class="app-header">
			<!-- 添加不断行 -->
				<span style="white-space: nowrap;overflow: hidden; text-overflow: ellipsis;padding-right: 72px;">${name}</span>
				<select id="app-header-select" onchange='onlanguagechange()'>
					<c:forEach var="s" items="${languaList}" varStatus='item'>
					<option value="${s.languageId}">${s.languageType}</option>
					<script>
					//mLanguageList.push("${s.languageType}");
					//mLanguageIdList.push("${s.languageId}");
					</script>
				</c:forEach>
				</select>
			
			</div>
			<div class="richscan-title"></div>
			<div class="richscan-img"></div>
			<div class="richscan-info">
				<div class="info-title">${level}·${dynasty}
				<a class= "a" onclick="goMuseum()">${museumName}</a>
				</div>
				<div class="info-text">
					<span>详情:</span>
					${describe}
				</div>
				
			</div>
			<div class='richscan' >
				<img class='rs-shareQRCode'>
				<img class='richscan-voice'  onclick="isPay()" src = "${pageContext.request.contextPath}/img/unload.png">
			</div> 
			<div id='progressBar'></div>
		</div>
	</body>
		<script>
			var parentHeight = window.innerHeight;
			console.log(parentHeight);
			
			var a ="${shareQRCode}";
			var shareQRCode = window.parent.baseUrl+a;
			console.log(shareQRCode)
			if(a.length >0){
				$(".rs-shareQRCode").attr("src",shareQRCode)
			}else{
				$(".rs-shareQRCode").remove();
			}
			
			document.getElementById("progressBar").style.display="none";
		
			function GetQueryString(name){
			     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			     var r = window.location.search.substr(1).match(reg);
			     if(r!=null)return  unescape(r[2]);
			     return null;
			}
			
			var userId="${userId}";
			var shareId = GetQueryString("shareId");
			var openid="${openid}";
			
			var mLanguageId;
		 	var mCurrentLanguage;
		 	var ll=${languaList};
		 	var lids=[];
		 	var ltys=[];
		 	for(var i=0;i<ll.length;i++){
		 		ltys.push(ll[i].languageType);
		 		lids.push(ll[i].languageId);
		 		mLanguageId=ll[0].languageId;
		 		mCurrentLanguage=ll[0].languageType
		 	}
			var id="${id}";
			$(document).ready(function(){
				if(userId == undefined ||userId == ""){
			 		var museumId="${museumId}";
			 		
			 		var result
			 		if(shareId == undefined || shareId == ""){
			 			result = "scan,"+museumId+","+id+",-1";
			 		}else{
			 			result = "scan,"+museumId+","+id+","+shareId;
			 		}
				    var b = new Base64();
				    var str = b.encode(result);
				    var baseUrl='<%=Constant.CurrentURL%>';
				  	window.location.href=baseUrl+"wechatCode?state="+str;
				  	return;
		
			 	}
			});

	     
		 	function goMuseum(){
			 		var u=encodeURI("${pageContext.request.contextPath}/museumIntroduce?id=${museumId}&languageId="+mLanguageId+"&languageName="+mCurrentLanguage+"&userId="+userId+"&type=1&openid="+openid);
			 		window.location.href=u;
		 	}
		 	
			
			$('.richscan-body').css('height',innerHeight+"px");
			$('.can-live').attr("src","../img/kst.png");
			var offset=screen.width*0.04;
			var t1=screen.height-offset-140;
			//$('.richscan-voice').css('top',t1+"px");
			$('.richscan-voice-loading').css('top',t1+"px");
			
			
			var cancelMask=false;
			var mask = mui.createMask(function () {
				return cancelMask;
			})
			mask.show();
			$('.mui-backdrop').css('top',screen.height+"px");
				
				
		    /**
			 * 蒙板添加语言选择 数量 总价 支付方式
			 */
			function addLanguage(total){
				t=total;
				$(".mask-paySome-body-bottom").append("<div class='language'></div>");
				/**
				 * 雨中选择
				 */
				$(".language").append("<span>语种选择：</span>");
				$(".language").append("<input class='radio' type='radio' name='radio' id='l0'><span id='s0' class='radio-l' onclick='onLanguageSelect(this.id)'>"+mCurrentLanguage+"</span></input>");

				/**
				 * 总价
				 */
				$(".mask-paySome-body-bottom").append("<div class='pay-sum'></div>");
				$(".pay-sum").append("<span>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</span>");
				$(".pay-sum").append("<span style='color: #FF0000;'>&nbsp;&nbsp;¥"+total+"</span>");
				/**
				 * 分割线
				 */
				$(".mask-paySome-body-bottom").append("<div class='pay-sum-bottom-line'></div>");
				/**
				 * 支付方式
				 */
				$(".mask-paySome-body-bottom").append("<div class='pay-sum-bottom-line-bottom'></div>");
				$(".pay-sum-bottom-line-bottom").append("<span class='paytype' >支付方式：</span>");
				$(".pay-sum-bottom-line-bottom").append("<img class='alipay' onclick='wxpayre()' src='${pageContext.request.contextPath}/img/wechat.png' />");
			}
			
			
			
			/**onclick='wxpayres()'
			 * 微信支付
			 */
			var t
			function wxpayre(){
				 document.getElementById("progressBar").style.display="";
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
					    jsApiList: ['chooseWXPay',
					    	'hideAllNonBaseMenuItem'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
					});
					//window.parent.mMuseumId/itemid/window.parent.mLanguageId,
					wx.ready(function(){
						
						wx.hideAllNonBaseMenuItem(); 
						
							$.ajax({
								url :"${pageContext.request.contextPath}/WechatDefray/"+t+"/"+returnCitySN.cip+"/"+openid,
								type : "get",
								dataType : "json",
								async: false,
								success : function(obj1) {
									if(!obj1){
										alert("出错了，请关闭页面重新载入");
										return;
									}
									var num=1;
									goPay(obj1,t,"-1","-1",num,mLanguageId,userId,"${museumId}","${id}");
								},
								error : function() {
								alert("出错了，请关闭页面重新载入");
								}
							});

						//开始支付
						function goPay(obj1,total,saletotal,discountId, num,languageId,userid,mMuseumid,itemid){
							document.getElementById("progressBar").style.display="none";
							closePayMask();
							//test
							//postPayRes();
							pay();
							function onBridgeReady(){
								var apId = obj1.appId;
								var timeStamp=obj1.timeStamp;
								var nonceStr=obj1.nonceStr;
								var pkg= obj1.pkg;
								var signType=obj1.signType;
								var paySign=obj1.paySign;
						        WeixinJSBridge.invoke(
					                'getBrandWCPayRequest', {
					                   "appId" : apId,     //公众号名称，由商户传入     
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
						    
						    function postPayRes(){
						   
								var id = itemid;
								var mMuseumid = "${museumId}";
							 	if(shareId==null||shareId==""){
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
												var url="${pageContext.request.contextPath}/singleHeritageListening/${museumId}/"+id+"/"+userId+"/"+mLanguageId;
												window.location.href=url;
											}else{
												alert("出错了，请关闭页面重新载入");
											}
						
										},
										error : function() {
											alert("出错了，请关闭页面重新载入");
										}
									});
							 	}else{
									$.ajax({
										url :"${pageContext.request.contextPath}/postPayRes",
										type : "post",
										data :{"total":total,"saletotal":saletotal,"discountId":discountId,"number":num,"languageId":languageId,
											"userId":userid,"museumId":mMuseumid,"item":itemid,"shareId":shareId
										},
										dataType : "text",
										async: false,
										success : function(obj) {
											if (100 == obj ){
												var url="${pageContext.request.contextPath}/singleHeritageListening/${museumId}/"+id+"/"+userId+"/"+mLanguageId;
												window.location.href=url;
											}else{
												alert("出错了，请关闭页面重新载入");
											}
						
										},
										error : function() {
											alert("出错了，请关闭页面重新载入");
										}
									});
							 	}
							}
					}	

				});
				
				wx.error(function(res){
					alert("出错了，请关闭页面重新载入");
				});
			}
		}
		
		/**
		 * 判断购买并执行相应逻辑
		 */
		function isPay(){
			var total ;	
			if(needPay(id)){
				/**
				 * 支付单个界面
				 */
				 $.ajax({
					url :"${pageContext.request.contextPath}/singleListening/"+mLanguageId,
					type : "put",
					dataType : "json",
					data : {
						id
					},
					success : function(obj) {
						var top=$('.mui-backdrop').css('top');
						if(top.split("p")[0]==screen.height){
							$('.mui-backdrop').animate({top:0},200);
							setTimeout(function() {
								$('.mui-backdrop').append("<div class='mask-paySome-body'></div>");
								$('.mask-paySome-body').append("<div class='mask-paySome-body-top' onclick='closePayMask()'></div>");
								$('.mask-paySome-body').append("<div class='mask-paySome-body-bottom'></div>");
								total = obj.total;
								addLanguage(obj.total);
							}, 200);
							
						}
						
					},
					error : function() {
						mui.toast('获取数据失败',{ duration:'short', type:'div' }); 
					}
				});
			}
			
		}
		
		/**
		 * 关闭蒙版
		 */
		function closePayMask(){
			var top=$('.mui-backdrop').css('top');
			if(top.split("p")[0]==0){
				$('.mui-backdrop').animate({top:screen.height+"px"},200);
			}
		}
		
		//添加轮播图
		$('.richscan-img').append("<div class='banner' style='width: 100%; height: 100%; '></div>");
		var pics="${picList}";
		var picList=pics.split(",");
		var count=picList.length+1;
		var index=1;
		for (var i=1;i<count;i++) {
			$('.banner').append("<li class='li"+i+"'><img src="+baseUrl+picList[i-1]+" align='center'></li>");
		}
		var t1 = window.setInterval("banner()","5000");
		bannerPoint(1);
		function banner(){
			index++;
			if(index==count){
				index=1;
			}
			for(var i=1;i<count;i++){
		        var t=".li"+i;
		        $(t).fadeOut();
		   }
			var name=".li"+index;
		    $(name).fadeIn();
		    bannerPoint(index);

		}
		
		/**
		 * 轮播图的下标
		 * @param {Object}index 当前显示图片的下标 
		 */
		function bannerPoint(index){
			$('.banner-point').remove();
			$('.richscan-img').append("<div class='banner-point'></div>");
			for (var i = 1; i < count; i++) {
				$('.banner-point').append("<div id='point"+i+"'></div>");
				var n="#point"+i;
				if(i==index){
					$(n).css('background-color','#F0AD4E');
				}else{
					$(n).css('background-color','#817b7b');
				}
			}
			var l=50-((count-1)*11/screen.width)*50;
			$('.banner-point').css('left',l+"%")	
		}
		 
		 /**
		  * 切换语言事件
		  */
		 function onlanguagechange(){
		 	var select=document.getElementById('app-header-select');
		 	var item=select.value;
		 	if(mLanguageId!=item){
		 		mLanguageId=item;
		 		for(var i=0;i<lids.length;i++){
		 			if(mLanguageId==lids[i]){
		 				mCurrentLanguage=ltys[i];
		 			}
		 		}
		 	}
		 }
		 
		/**
	    *判断是否需要购买
		*/
		var PAY_CODE=204;
		function needPay(itemId){
			var needpay;
			///needPay/{museumId}/{historicalRelicId}/{userId}/{languageId}
			$.ajax({
				url :"${pageContext.request.contextPath}/needPay/${museumId}/"+itemId+"/"+userId+"/"+mLanguageId,
				type : "get",
				dataType : "json",
				async: false,
				success : function(obj) {
					if(PAY_CODE==obj){
						needpay = true;
					}else if (100 == obj){
						var url="${pageContext.request.contextPath}/singleHeritageListening/${museumId}/"+itemId+"/"+userId+"/"+mLanguageId;
						window.location.href=url;
					}else{
						window.top.location.href="${pageContext.request.contextPath}/error";
					}	
						
				},
				error : function() {
					mui.toast('获取数据失败',{ duration:'short', type:'div' }); 
				}
			});
			return needpay;
		}
				
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
			    jsApiList: ['chooseWXPay',
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
</html>
