<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page import="com.cn.hnust.common.Constant" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<!--<script type="text/javascript" src="${pageContext.request.contextPath}/js/app-detial.js" ></script>-->
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app-detial-context-pay.css" />
		<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
		<script type="text/javascript">
		var baseUrl='<%= Constant.ResouseUrl%>';
		window.parent.baseUrl=baseUrl;
		</script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		
		
	</head>
	<body >
		<div class="body" style='background-color: #272A33;'></div>
		<!--内容-->
		<div class="divbody">
		
			<c:forEach var="s" items="${batchDataFetchList}" varStatus='item'>
			
				<div id='${s.historicalRelicId}' class='item' onclick='onItemClickListener(this.id,${item.index})'>
					<img src='<%= Constant.ResouseUrl%>${s.pictureAddress}' style='top:15%;'/>
					<div class='item-bg'></div>
					<div class='info' style='font-size:16px; width:45%;left:15%; top:32%'>
					<ul>
					<li class='one'>${s.historicalRelicName}</li>
					<li class='other'>${s.level}</li>
					<li class='other'>${s.dynasty}</li>
					</ul>
					</div>
					<input type='checkbox' id='cb${s.historicalRelicId}' />
					
				</div>
					
			</c:forEach>
			<div style='width: 100%; height: 48px; background-color: #272A33;'></div>
		
		</div>
		<div class="check"></div>
		<div id='progressBar'></div>
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
			document.getElementById("progressBar").style.display="none";
			window.parent.single=false;
			var map=window.parent.map;
			var arr=document.getElementsByClassName("item");
			/**
			 * 条目的点击事件
			 */
			function onItemClickListener(itemId,pos){
				var cbName="#cb"+itemId;
				var cb=$(cbName).is(':checked');
				if(cb){
					map[pos]=itemId;
					var count1=Object.getOwnPropertyNames(map).length;
					console.log(Object.getOwnPropertyNames(map).length);
					$('.check-num').text("共选"+count1+"个");
					
				}else{
					 delete map[pos];
					console.log(Object.getOwnPropertyNames(map).length);
					var count2=Object.getOwnPropertyNames(map).length;
					$('.check-num').text("共选"+count2+"个");
					
				}
				var count3=Object.getOwnPropertyNames(map).length;
				if(count3>=arr.length){
					$('.check-value').text("全不选");
					//$("#checkAll").attr(':checked',"true");
					document.getElementById('checkAll').checked=true;
				}else{
					$('.check-value').text("全选");
					document.getElementById('checkAll').checked=false;
				}
//				 for(var i in map){
//			           console.log(map);
//			            
//			        }
			}
			
			$(".check").append("<input type='checkbox' id='checkAll' onclick='checkAllClick()'/>");
			$(".check").append("<div class='check-value'>全选</div>");
			$(".check").append("<div class='check-num'>共选0个</div>");
			$(".check").append("<div class='check-pay' onclick='pay()'>确认</div>");
			
			/**
			 * 全选按钮点击事件
			 */
			
			function checkAllClick(){
				var cb=$('#checkAll').is(':checked');
				if(cb){
					$('.check-value').text("全不选");
					document.getElementById('checkAll').checked=true;
					checkAllItem();
					console.log(map);
					var count=Object.getOwnPropertyNames(map).length;
					$('.check-num').text("共选"+count+"个");
					
				}else{
					$('.check-value').text("全选");
					document.getElementById('checkAll').checked=false;
					checkAllItemNo();
					console.log(map);
					$('.check-num').text("共选0个");
				}
			}
			/**
			 * 将所有的条目选中
			 */
			function checkAllItem(){
				checkAllItemNo();
				for (var i = 0; i < arr.length; i++) {
					var cbItemName="cb"+arr[i].attributes['id'].value;
					document.getElementById(cbItemName).checked=true;
					map[i]=arr[i].attributes['id'].value;
				}
			}
			
			/**
			 * 将所有的条目不选中（全不选）
			 */
			
			function checkAllItemNo(){
				for (var i = 0; i < arr.length; i++) {
					var cbItemName="cb"+arr[i].attributes['id'].value;
					document.getElementById(cbItemName).checked=false;
					delete map[i];
				}
			}
			
			/**
			 * 确认按钮点击事件
			 */
			var cancancelPaySomeMask=false;
			var paySomeMask = mui.createMask(function () {
					return cancancelPaySomeMask;
				});
			function pay(){
				if(Object.getOwnPropertyNames(map).length<1)return;
				if(paySomeMask._show){
					cancancelPaySomeMask=true;
					paySomeMask.close();
				}else{
					cancancelPaySomeMask=false;
					paySomeMask.show();
				}
				$('.mui-backdrop').css('height',(screen.height-168)+"px");
				$('.mui-backdrop').empty();
				$('.mui-backdrop').append("<div class='mask-paySome-body'></div>");
				$('.mask-paySome-body').append("<div class='mask-paySome-body-top' onclick='closePaySomeMask()'></div>");
				$('.mask-paySome-body').append("<div class='mask-paySome-body-bottom'></div>");
				$('.mui-backdrop').animate({top:0},300);
				setTimeout(function(){
					addLanguage();
				},300);
			}
			/**
			 * 关闭蒙板
			 */
			function closePaySomeMask(){
				var h=screen.height;
				$('.mui-backdrop').animate({top:h+"px"},300);
				setTimeout(function(){
					cancancelPaySomeMask=true;
					paySomeMask.close();
				},300);
				
			}
			/**
			 * 蒙板添加语言选择 数量 总价 支付方式
			 */
			function addLanguage(){
				$(".mask-paySome-body-bottom").append("<div class='language'></div>");
				/**
				 * 语种选择
				 */
				 $(".language").append("<span>语种选择：</span>");
				 
					 
				//$(".language").append("<input class='radio' type='radio' name='radio' id='l1'><span id='s1' class='radio-l' onclick='onLanguageSelect(this.id)'>英语</span></input>");
				//$(".language").append("<input class='radio' type='radio' name='radio' id='l2'><span id='s2' class='radio-l' onclick='onLanguageSelect(this.id)'>法语</span></input>");
				//$(".language").append("<input class='radio' type='radio' name='radio' id='l3'><span id='s3' class='radio-l' onclick='onLanguageSelect(this.id)'>俄语</span></input>");
				/**
				 * 数量
				 */
				 
				 
				 
				var num=Object.getOwnPropertyNames(map).length;//选择收听的数量
				$(".mask-paySome-body-bottom").append("<div class='pay-count'></div>");
				$(".pay-count").append("<span>数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</span>");
				/**
				 * 总价
				 */
				$(".mask-paySome-body-bottom").append("<div class='pay-sum'></div>");
				$(".pay-sum").append("<span>共&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计：</span>");
				initTotalAndSum(window.parent.mLanguageId);
				/**
				 * 分割线
				 */
				$(".mask-paySome-body-bottom").append("<div class='pay-sum-bottom-line'></div>");
				/**
				 * 支付方式
				 */
				$(".mask-paySome-body-bottom").append("<div class='pay-sum-bottom-line-bottom'></div>");
				$(".pay-sum-bottom-line-bottom").append("<span class='paytype'>支付方式：</span>");
				$(".pay-sum-bottom-line-bottom").append("<img class='alipay' src='${pageContext.request.contextPath}/img/wechat.png' onclick='wxpay()'/>");
				//$(".pay-sum-bottom-line-bottom").append("<img class='wxpay' src='${pageContext.request.contextPath}/img/wechat.png' onclick='wxpay()'/>");
			}
			
			/**
			 * 语种点击事件
			 */
			var language_position;
			function onLanguageSelect(itemId){
				var sps=document.getElementsByClassName("radio-l");
				for (var i = 0; i < sps.length; i++) {
					var n="#"+sps[i].id;
					if(itemId==sps[i].id){
						//console.log(sps[i].id.split("s"));
						language_position=sps[i].id.split("s")[1];
						initTotalAndSum(language_position)
						$(n).css('background-color','#F0AD4E');
						//var c="l"+sps[i].id.split("s");
						//document.getElementById(c).checked=true;
					}else{
						$(n).css('background-color','#FFFFFF');
					}
				}
			}
			
			/**
			 *获取支付数据
			 */
			 
			 var total;
			 var saletotal;
			 var discountId;
			 var num;
			 var itemid;
			 function initTotalAndSum(languageId){
				 var jsonstr="";
				 $.each(map, function(i) {     
					    jsonstr=jsonstr+map[i]+" ";
					});
				 $.ajax({
						url :"${pageContext.request.contextPath}/batchListenIn/"+languageId,
						type : "put",
						dataType : "json",
						data:{
							jsonstr
						},
						success : function(obj) {
							$('.p-sum').remove();
							$('.p-count').remove();
							console.log(obj.saletotal);
							console.log(obj.total);
							total =obj.total;
							saletotal = obj.saletotal;
							discountId= obj.discountId;
							num = obj.number;
							itemid = obj.ids;
							if(obj.saletotal != "-1"){
								$(".pay-sum").append("<span  class='p-sum' style='color: #FF0000;'>"+obj.saletotal+"元</span>");		
							}else{
								$(".pay-sum").append("<span class='p-sum'  style='color: #FF0000;'>"+obj.total+"元</span>");	
							}
							$(".pay-count").append("<span class='p-count'   style='color: #000000;'>已选"+obj.number+"个文物</span>");
							$(".language").append("<input class='radio' type='radio' name='radio' id='r"+window.parent.mLanguageId+"'><span id='s"+window.parent.mLanguageId+"' class='radio-l' onclick='onLanguageSelect(this.id)'>"+obj.lng+"</span></input>");
							 var n="#s"+window.parent.mLanguageId;
							 $(n).css('background-color','#F0AD4E');
							 num = 	obj.number;
						},
						error : function() {
							
						}
					});
			}
			
			
			/**
			 * 支付宝支付
			 */
			function alipay(){
				console.log("支付宝支付")
			}
			/**
			 * 微信支付
			 
			 var total;
			 var saletotal;
			 var discountId;
			 var num;
			 var itemid;
			 */
			function wxpay(){
				 document.getElementById("progressBar").style.display="";
				 closePaySomeMask();
				 $.ajax({
						url :"${pageContext.request.contextPath}/WechatDefray/"+total+"/"+returnCitySN.cip+"/"+parent.openId,
						type : "get",
						dataType : "json",
						async: false,
						success : function(obj1) {
							
							document.getElementById("progressBar").style.display="none";
							if(!obj1){
								alert("出错了，请关闭页面重新载入");
								return;
							}
							parent.WXPay(obj1,total,saletotal,discountId,num,window.parent.mLanguageId,window.parent.userId,window.parent.mMuseumId,itemid);
						},
						error : function() {
						alert("出错了，请关闭页面重新载入");
						}
					});
			}
			
			
			
		</script>
	</body>
</html>
