<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title>我的团队</title>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/personal_team.css" />
	</head>
	<body >
		<div class="body">
			<div class="title">
			
			<div class="total-title">可结算收益(元)</div>
				<div class="total-sum">￥${total}</div>
				<div class="request-sum" onclick="requestMoney()">申请提现</div>
				<div class = "request-info">
					<div class = "request-info-one">
					<div style="height: 25px;">￥${total5}</div>
					<div style="height: 25px;">总收益(元)</div>
					</div>
					<div class = "request-info-line"></div>
					<div class = "request-info-two">
					<div style="height: 25px;">￥${total4}</div>
					<div style="height: 25px;">已结算收益(元)</div>
					</div>
				</div>
			
			</div>
			<div class = 'split'></div>
			
			<div class="tab">
				<div class="tab-title">
					<div id="tab1" onclick="one()">传播员收入</div>
					<div id="tab2" onclick="two()">业务员收入</div>
					<div id="tab3" onclick="three()">录入员收入</div>
				</div>
				<img class="tab_item" />
				<div class ='item'>
					<iframe id="iframe"  frameborder="0" src="${pageContext.request.contextPath}/getEarnings/${userId}/disseminate"></iframe>
				</div>
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
		var totalM="${total}";
			$('.body').css('height',(innerHeight)+"px");
		
			
			
			//2017-7-18 修改
			$('.title').css('height',(460/1080*innerHeight)+"px");
			$('.request-info').css('top',(460/1080*innerHeight-70)+"px")
			
			//tab页面的高度设置
			$('.tab').css('height',(innerHeight-(410/1080*innerHeight)-100)+"px");
			/**
			 * 申请提现
			 */
			function requestMoney(){
				if(totalM <= 0){
					mui.confirm('当前提款余额不足！','提示',['取消','确认'],function (e) {
						if(e.index==1){
							//确认事件  /goSettlement/{userId}
							
						}else if(e.index==0){
							//取消事件	
						}
					},'div');
				}else {
					
					mui.confirm('确认身份进行提现！','提示',['取消','确认'],function (e) {
						if(e.index==1){
							//确认事件  /goSettlement/{userId}
							window.top.location.href="${pageContext.request.contextPath}/goSettlement/${userId}/${total}/${total1}/${total2}/${total3}/1";
						}else if(e.index==0){
							//取消事件	
						}
					},'div');
				}
				
			}
			
			
			//初始化tab按钮选项
			$('.tab_item').attr('src','${pageContext.request.contextPath}/img/1.png');
			$('#tab1').css('color','#F0AD4E');
			$('#tab2').css('color','#8E8E8E');
			$('#tab3').css('color','#8E8E8E');
			
			//初始化tab item页面大小
			var tab_titleh=parseInt($('.tab-title').css('height').split("p")[0]);
			var tab_itemh=parseInt($('.tab_item').css('height').split("p")[0]);
			var itemh=(innerHeight-(410/1080*innerHeight))-tab_titleh-tab_itemh-150;
			$('.item').css('height',itemh+"px");
			/**
			 * tab1点击事件
			 */
			function one(){
				$('.tab_item').attr('src','${pageContext.request.contextPath}/img/1.png');
				$('#tab1').css('color','#F0AD4E');
				$('#tab2').css('color','#717276');
				$('#tab3').css('color','#717276');
				$('#iframe').attr('src',"${pageContext.request.contextPath}/getEarnings/${userId}/disseminate");
			}
			
			/**
			 * tab2点击事件
			 */
			function two(){
				$('.tab_item').attr('src','${pageContext.request.contextPath}/img/2.png');
				$('#tab2').css('color','#F0AD4E');
				$('#tab1').css('color','#717276');
				$('#tab3').css('color','#717276');
				$('#iframe').attr('src',"${pageContext.request.contextPath}/getEarnings/${userId}/salesman");
			}
			/**
			 * tab3点击事件
			 */
			function three(){
				$('.tab_item').attr('src','${pageContext.request.contextPath}/img/3.png');
				$('#tab3').css('color','#F0AD4E');
				$('#tab2').css('color','#717276');
				$('#tab1').css('color','#717276');
				$('#iframe').attr('src',"${pageContext.request.contextPath}/getEarnings/${userId}/entering");
			}
			
		</script>
		
	</body>
</html>
