<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title></title>
		<!--mui-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<!--js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/personal_center.css" />
	</head>
	<body>
		<div class="body">
			<div class="title">账户信息</div>
			<div class="context">
				<div class="div_name">真实姓名</div>
				<input type="text" class="in_name" />
				<div class="div_phone">联系方式</div>
				<input type="number" class="in_phone" />
				<div class="div_bankName">账号类型<span style='color: #F00; '>（微信、银行卡所属行）</span></div>
				<input type="text" class="in_bankName" />
				<div class="div_bankCode">申请账号</div>
				<input type="text" class="in_bankCode" style="ime-mode: disabled;" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\u4e00-\u9fa5]/g,''))" onkeyup="this.value=this.value.replace(/[\u4e00-\u9fa5]/g,'')" />
			</div>
			<div class="submit" onclick="onSubmit()">提交</div>
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
		
			$('.body').css('height',(screen.height-72)+"px");
			$('.submit').css('top',(screen.height-118)+"px");
			
			function getQueryString(name){
			     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			     var r = window.location.search.substr(1).match(reg);
			     if(r!=null)return  unescape(r[2]);
			     return null;
			}
			var userId="${userId}";
			var total="${total}";
			var total1="${total1}";
			var total2="${total2}";
			var total3="${total3}";
			function onSubmit(){
				var name=$('.in_name').val();
				var phone=$('.in_phone').val();
				var bankName=$('.in_bankName').val();
				var bankCode=$('.in_bankCode').val();
				if($.trim(name).length == 0){
					mui.confirm('请输入姓名！','提示',['确认'],function (e) {},'div');
					return;
				}
				if($.trim(phone).length == 0){
					mui.confirm('请输入联系方式！','提示',['确认'],function (e) {},'div');
					return;
				}
				if($.trim(bankName).length == 0){
					mui.confirm('请输入账号类型！','提示',['确认'],function (e) {},'div');
					return;
				}
				if($.trim(bankCode).length == 0){
					mui.confirm('请输入申请账户！','提示',['确认'],function (e) {},'div');
					return;
				}
				
				 $.ajax({
						url :"${pageContext.request.contextPath}/postSettlement",
						type : "post",
						dataType : "json",
						data:{
							userId,
							phone,
							bankCode,
							bankName,
							name,
							total,
							total1,
							total2,
							total3
						},
						success : function(obj) {
							if(obj=="100"){
								mui.confirm('提交信息成功！','提示',['确认'],function (e) {
									if(e.index==0){
										//确认事件
										window.location.href="${pageContext.request.contextPath}/goCenter/"+userId;
									}
								},'div');
							}else if(obj=="215"){
								mui.confirm('当前用户有申请未结算，不能重复申请！','提示',['确认'],function (e) {
									if(e.index==0){
										//确认事件
										window.location.href="${pageContext.request.contextPath}/goCenter/"+userId;
									}
								},'div');
							}else if(obj=="212"){
								mui.confirm('请先成为平台用户！','提示',['确认'],function (e) {
									if(e.index==0){
										//确认事件
										
									}
								},'div');
							}else if(obj=="217"){
								mui.confirm('当前提款余额不足！','提示',['确认'],function (e) {
									if(e.index==0){
										//确认事件
										
									}
								},'div');
							}else{
								mui.confirm("申请失败",'提示',['确认'],function (e) {
									if(e.index==0){
										//确认事件
									}
								},'div');
							}
						},
						error : function() {
							mui.confirm('提交信息失败！','提示',['确认'],function (e) {
								if(e.index==0){
									//确认事件
								}
							},'div');
						}
					});
			}
		</script>
		
		
		
	</body>
</html>
