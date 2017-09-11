<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page import="com.cn.hnust.common.Constant" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/app-detial.js" ></script>
		<!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mui/css/mui.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app-detial.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app-detial-context.css" />
		<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>  
		<script type="text/javascript">
		var baseUrl='<%= Constant.ResouseUrl%>';
		window.parent.baseUrl=baseUrl;
		</script>
		
	</head>
	<body class="body" >
		<!--内容-->
		<div class="divbody">
			<c:forEach var="s" items="${historicalList}" varStatus='item'>
					<div id="${s.historicalRelicId}" class='item' onclick='onItemClickListener(this.id)'>
					<img src="<%= Constant.ResouseUrl%>${s.pictureAddress}">
					<div class='item-bg'></div>
					<div class='info'>
					<ul>
					<li class='one'>${s.historicalRelicName}</li>
					<li class='other'>${s.level}</li>
					<li class='other'>${s.dynasty}</li>
					</ul>
					</div>
					 <c:choose>
					    <c:when test="${s.buy == 'YES'}">
					    		<div class='kst' ></div>
					    		<div class='mf' hidden></div>
					    </c:when>
					    <c:when test="${s.buy == 'NO'}">
					    		 <div class='kst' hidden></div>
					    		 <div class='mf' hidden></div>
					    </c:when>
					    <c:when test="${s.buy == 'OK'}">
					    		 <div class='mf'></div>
					    		 <div class='kst' hidden></div>
					    </c:when>  
					  </c:choose>
					</div>
			</c:forEach>
		</div>
		<!--去支付-->
		<div class="plst" onclick="goBuy()"></div>
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
			window.onload = function(){  
	            mui('#offCanvasSideScroll').scroll();    
	            mui('#offCanvasContentScroll').scroll(); 
	            mui('.mui-scroll-wrapper').scroll();
	            mui('.mui-scroll-wrapper').scroll({
                    deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006 
				});
	        }
			
			var userId="${userId}";
			//获取配置js-sdk配置信息
			var total=0;
			window.parent.single=true;
			
			var canClickWXPay =true;
					
			/**
			 * 是否取消蒙板
			 */
			var cancelMask=false;
			var  maskTop;
			var mask = mui.createMask(function(){
							return cancelMask;
						});
			
			mask.show();
			$('.plst').hide();
			/**
			 * 初始化蒙板位置
			 */
			$('.mui-backdrop').css('top',"0px");
			
			/**
			 * 初始化蒙板布局
			 */
			$('.mui-backdrop').append("<div class='mask-body'></div>");
			if(screen.width>0){
				$('.mask-body').css('width',screen.width+"px");
				
			}else{
				var w=$('.mui-backdrop').css('width');
				$('.mask-body').css('width',w);
			}
			var h=$('.mui-backdrop').css('height');
			$('.mask-body').css('height',h);
			$('.mask-body').append("<div class='mask-bottom'></div>");
			$('.mask-bottom').append("<div class='mask-nav' onclick='navigationListener()'></div>");
			$('.mask-nav').css('background-image', "url('${pageContext.request.contextPath}/img/jiantou.png')");
			/**
			 * 蒙板添加数据内容布局
			 */
			$('.mask-bottom').append("<div class='mask-bottom-context'></div>");
			//添加轮播图
			$('.mask-bottom-context').append("<div class='banner'></div>");
			var picList=window.parent.mPicList;
			var count=picList.length+1;
			var index=1;
			for (var i=1;i<count;i++) {
				$('.banner').append("<li class='li"+i+"'><img src="+window.parent.baseUrl+picList[i-1]+" align='center'></li>");
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
			 * 轮播图下边的内容
			 */
			// $('.mask-bottom-context').append("<div id='mui-backdrop-context-if' class='mui-backdrop-context-info1' hidden>"+window.parent.mMuseumDesc+"</div>");
			// var mui_h=parseInt($('#mui-backdrop-context-if').css('height').split("p")[0])+30;
			// $('.mask-bottom-context').append("<div class='mui-backdrop-context-info'><div id='offCanvasSideScroll' class='mui-scroll-wrapper'>"+
			//		 "<div class='mui-scroll' style ='top:0; height:"+mui_h+"px;'>"+window.parent.mMuseumDesc+"</div></div></div>");


			// var startY;
			// var sc=document.getElementsByClassName('mui-backdrop-context-info')[0];
			 //$('.mui-backdrop-context-info').on("scrollstart",function(){
			//	  alert("开始滚动！");
			//	});
			// sc.addEventListener('touchstart',function(e){
			//	 console.log(e.pageY);
			// });
			 
			//sc.addEventListener('touchmove',function(e){
			//	console.log(e.pageY);		 
			//});
			 
			 
			 //测试
				var mh=parseInt($('.mui-backdrop').css('height').split("p")[0]);
				if(mh>50){ 
					$(window.parent.document).find(".speech").hide();					
					var top1=mh-30;
					setTimeout(function(){
						$('.mui-backdrop').animate({top:top1+"px"},1);
						$('.mui-backdrop').css('height',30+"px");
						$('.mui-backdrop').css('bottom','0');
						$('.mui-backdrop').empty();
						$('.mui-backdrop').append("<div class='mask-backdrop'></div>");
						$('.mask-backdrop').append("<div id='mask-nav-parent' style='width:100%; height:30px;' onclick='navigationListener()'><div class='mask-nav'></div></div>");
						$('.mask-backdrop').css('background-color',"#1A1D24");
						$('.mask-nav').css('top',10+"px");
						$('.mask-nav').css('background-image', "url('${pageContext.request.contextPath}/img/shangjiantou.png')");
						$('.plst').show();	
						$('.item').attr('disabled',true);
						
						window.parent.itemCanClick=true;
					},1);
						
				}
				
			
			
			function navigationListener(){
				var mh=$('.mui-backdrop').css('height').split("p")[0];
				/**
				 * 向下滑动
				 */
				if(mh>50){ 
					$(window.parent.document).find(".speech").hide();
					window.clearInterval(t1);
					window.clearInterval(t);
					var top2=mh-30;
					$('.mui-backdrop').animate({top:top2+"px"},250);
					$('.mui-backdrop').css('height',30+"px");
					setTimeout(function(){
						$('.mui-backdrop').empty();
						$('.mui-backdrop').append("<div class='mask-backdrop'></div>");
						$('.mask-backdrop').append("<div id='mask-nav-parent' style='width:100%; height:30px;' onclick='navigationListener()'><div class='mask-nav'></div></div>");
						$('.mask-backdrop').css('background-color',"#1A1D24");
						$('.mask-nav').css('top',10+"px");
						$('.mask-nav').css('background-image', "url('${pageContext.request.contextPath}/img/shangjiantou.png')");
						$('.plst').show();	
						$('.item').attr('disabled',true);
						
						window.parent.itemCanClick=true;
					},220);
						
				}
				/**
				 * 向上滑动
				 */
				else{
					
					window.parent.itemCanClick=false;
					$('.plst').hide();
					$('.speech').show();
					$(window.parent.document).find(".speech").show();
					console.log(h+"--------------");
					$('.mui-backdrop').css('height',h);
					$('.mui-backdrop').animate({top:0},200);
					$('.mui-backdrop').append("<div class='nouse'></div>");
					setTimeout(function(){
						$('.mui-backdrop').empty();
						$('.mui-backdrop').append("<div class='mask-body'></div>");
						if(screen.width>0){
							$('.mask-body').css('width',screen.width+"px");
							
						}else{
							var w=$('.mui-backdrop').css('width');
							$('.mask-body').css('width',w);
						}
						var h=$('.mui-backdrop').css('height');
						$('.mask-body').css('height',h);
						$('.mask-body').append("<div class='mask-bottom'></div>");
						$('.mask-bottom').append("<div id='mask-nav-parent' style='width:100%; height:18px;' onclick='navigationListener()'><div class='mask-nav'></div></div>");
						$('.mask-nav').css('background-image', "url('${pageContext.request.contextPath}/img/jiantou.png')");
						addMaskBottomContext();
						$('.item').attr('disabled',false);
						
					},120);
					
				}
			}
			
			var t,t1;
			function addMaskBottomContext(){
				/**
				 * 蒙板添加数据内容布局
				 */
				$('.mask-bottom').append("<div class='mask-bottom-context'></div>");
				//添加轮播图
				$('.mask-bottom-context').append("<div class='banner'></div>");
				var picList=window.parent.mPicList;
				var count=picList.length+1;
				var index=1;
				for (var i=1;i<count;i++) {
					$('.banner').append("<li class='li"+i+"'><img src="+window.parent.baseUrl+picList[i-1]+" align='center'></li>");
				}
				/**
				 * 轮播图下标
				 */
				t = window.setInterval("banner()","5000");
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
				 * 轮播图下边的内容 mui_h  不够
				 */
			     $('.mask-bottom-context').append("<div id='mui-backdrop-context-if' class='mui-backdrop-context-info1' hidden>"+window.parent.mMuseumDesc+"</div>");
			     var cnt = $('#mui-backdrop-context-if').css('width').split("p")[0]/14.5;
				 var strlength = window.parent.mMuseumDesc.length/cnt*22;
				 //var mui_h=parseInt($('#mui-backdrop-context-if').css('height').split("p")[0])+30;
			     $('.mask-bottom-context').append("<div class='mui-backdrop-context-info' ><div id='offCanvasSideScroll' class='mui-scroll-wrapper'>"+
						 "<div class='mui-scroll' style ='top:0;height:"+strlength+"px;'>"+window.parent.mMuseumDesc+"</div></div></div>");
				 var sc=document.getElementsByClassName('mui-backdrop-context-info')[0];
				 var initS = 0;
				 var canScroll =true;
				 sc.addEventListener('touchstart',function(e){
					 var touch = e.touches[0];
					 initS = touch.pageY;
					
				});
				 sc.addEventListener('touchmove',function(e){
					 var touch = e.touches[0];
					 if(initS != 0 && canScroll){
						 canScroll =false;
					     mui('.mui-scroll-wrapper').scroll().scrollTo(0,(touch.pageY-initS),100);
						
					 }
					
				});
						 
			}
			
			

			/**
			 * 轮播图的下标
			 * @param {Object}index 当前显示图片的下标 
			 */
			function bannerPoint(index){
				$('.banner-point').remove();
				$('.mask-bottom-context').append("<div class='banner-point'></div>");
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
				 * 条目的点击事件
				 * @param {Object} itemId
				 */
			var itemid;
			function onItemClickListener(itemId){
				if(!window.parent.itemCanClick)return;
				window.parent.itemCanClick=false;
					//先判断是否已经购买  
				if(needPay(itemId)){//如果没有购买则吊起支付界面，反之后台控制跳转界面，进入详情界面进行收听
					canClickWXPay =true;
					$('.plst').hide();
					$('.mui-backdrop').empty();
					$('.mui-backdrop').animate({top:"0px"},500);
					$('.mui-backdrop').append("<div class='pay-one-body'></div>");
					$('.pay-one-body').css('height',h);
					$('.pay-one-body').append("<div class='pay-one-body-top' onclick='showMask()'></div>");
					var payonebbh=h.split("p")[0]-150;
					console.log(payonebbh)
					$('.pay-one-body').append("<div class='pay-one-body-bottom'></div>");
					$('.pay-one-body-bottom').css('height',200+"px");
					
					/**
					 * 支付单个界面
					 */
					 $.ajax({
							url :"${pageContext.request.contextPath}/singleListening/"+window.parent.mLanguageId,
							type : "put",
							dataType : "json",
							data : {
								itemId
							},
							success : function(obj) {
								if(obj.saletotal.length >0){
									total=obj.saletotal
									saletotal=total;
									itemid=itemId
									payOneMaskAddContext(obj.saletotal,obj.lng);
								}else{
									total=obj.total;
									saletotal=0;
									itemid=itemId
									payOneMaskAddContext(obj.total,obj.lng);
								}
								
							},
							error : function() {
								mui.toast('获取数据失败',{ duration:'short', type:'div' }); 
							}
						});
					
					
				}
				
				
			}
				 /**
					 * 添加支付单个文物
					 */
					 
			var sum;
			var saletotal;
			function payOneMaskAddContext(total,lng){
				window.parent.itemCanClick=false;
				sum=total;
				$(".pay-one-body-bottom").append("<div class='language'></div>");
				/**
				 * 语种选择
				 */
				$(".language").append("<span>语&nbsp;种&nbsp;选&nbsp;择：</span>");
				$(".language").append("<input class='radio' type='radio' name='radio' id='r"+window.parent.mLanguageId+"'>&nbsp;&nbsp;<span id='s"+window.parent.mLanguageId+"' class='radio-l' onclick='onLanguageSelect(this.id)'>"+lng+"</span></input>");
				 var n="#s"+window.parent.mLanguageId;
				 $(n).css('background-color','#F0AD4E');
				/**
				 * 数量
				 */
				$(".pay-one-body-bottom").append("<div class='pay-count'></div>");
				$(".pay-count").append("<span>数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</span>");
				$(".pay-count").append("&nbsp;&nbsp;&nbsp;<span style='color: #000000;'>1个文物</span>");
				/**
				 * 价格
				 */
				$(".pay-one-body-bottom").append("<div class='pay-sum'></div>");
				$(".pay-sum").append("<span>共&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计：</span>");
				$(".pay-sum").append("&nbsp;&nbsp;&nbsp;<span style='color: #FF0000;'>￥"+total+"</span>");
				/**
				 * 分割线
				 */
				$(".pay-one-body-bottom").append("<div class='pay-sum-bottom-line'></div>");
				/**
				 * 支付方式
				 */
				$(".pay-one-body-bottom").append("<div class='pay-sum-bottom-line-bottom'></div>");
				$(".pay-sum-bottom-line-bottom").append("<span class='paytype'>支&nbsp;付&nbsp;方&nbsp;式：</span>");
				$(".pay-sum-bottom-line-bottom").append("<img class='alipay'  onclick='wxpay()'  src='${pageContext.request.contextPath}/img/wechat.png' />");
				//$(".pay-sum-bottom-line-bottom").append("<img class='wxpay' onclick='wxpay()' src='${pageContext.request.contextPath}/img/wechat.png' />");
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
						$(n).css('background-color','#F0AD4E');
						//var c="l"+sps[i].id.split("s");
						//document.getElementById(c).checked=true;
					}else{
						$(n).css('background-color','#FFFFFF');
					}
				}
			}
			
			
			function showMask(){
				var payonebbh=h.split("p")[0]-30;
				$('.mui-backdrop').animate({top:payonebbh+"px"},500);
				
				setTimeout(function(){
				$('.mui-backdrop').empty();
				$('.mui-backdrop').append("<div class='mask-backdrop'></div>");
				$('.mask-backdrop').append("<div class='mask-nav' onclick='navigationListener()'></div>");
				$('.mask-backdrop').css('background-color',"#1A1D24");
				$('.mask-nav').css('top',10+"px");
				$('.mask-nav').css('background-image', "url('${pageContext.request.contextPath}/img/shangjiantou.png')");
				$('.plst').show();
				window.parent.itemCanClick=true;
				},500);
				
			}
			
			/**
			 * 批量收听按钮点击事件
			 * 内部页面跳转到批量收听界面
			 */
			//function goBuy(){
				/* window.parent.isGoplst=true;
				location.href="${pageContext.request.contextPath}/batchDataFetch/"+window.parent.mMuseumId+"/"+window.parent.mLanguageId+"/6";
				*/
			//}
			
			/**
			 * 支付宝支付
			 */
			
			function alipay(){
				console.log('支付宝支付')
			}
			
			/**
			 * 微信支付
			 */
			
			 function wxpay(){
				 if(!canClickWXPay) return;
				 canClickWXPay = false;
				 showMask();
				 document.getElementById("progressBar").style.display="";
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
							var num=1;
							parent.WXPay(obj1,total,"-1","-1",num,window.parent.mLanguageId,window.parent.userId,window.parent.mMuseumId,itemid);
						},
						error : function() {
						alert("出错了，请关闭页面重新载入");
						}
					});
			}

			
			
			/**
			 *判断是否需要购买
			 */
			 var PAY_CODE=204;
			function needPay(itemId){
				var needpay;
				///needPay/{museumId}/{historicalRelicId}/{userId}/{languageId}
				$.ajax({
					url :"${pageContext.request.contextPath}/needPay/"+window.parent.mMuseumId+"/"+itemId+"/"+window.parent.userId+"/"+window.parent.mLanguageId,
					type : "get",
					dataType : "json",
					async: false,
					success : function(obj) {
						if(PAY_CODE==obj){
							needpay = true;
						}else if (100 == obj){
							var url="${pageContext.request.contextPath}/singleHeritageListening/"+window.parent.mMuseumId+"/"+itemId+"/"+window.parent.userId+"/"+window.parent.mLanguageId;
							window.top.location.href=url;
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
			
			//var div_body =document.getElementsByClassName("divbody")[0];
			
			
		
			//div_body.addEventListener("touchend", function(e){ 
				//e.stopPropagation()
			    // $('.mui-backdrop').css('height',30+"px");
				//	setTimeout(function(){
				//		$('.mui-backdrop').empty();
				//		$('.mui-backdrop').append("<div class='mask-backdrop'></div>");
				//		$('.mask-backdrop').append("<div class='mask-nav' onclick='navigationListener()'></div>");
				//		$('.mask-backdrop').css('background-color',"#1A1D24");
				//		$('.mask-nav').css('top',10+"px");
				//		$('.mask-nav').css('background-image', "url('${pageContext.request.contextPath}/img/shangjiantou.png')");
				//	},10);
			//});
			
			//div_body.addEventListener("touchstart", function(e){  
			//	e.stopPropagation()
			//	$('.mui-backdrop').css('height',30+"px");
			//	setTimeout(function(){
			//		$('.mui-backdrop').empty();
			//	},10);
			//});
			
			
			
			
		</script>
	</body>
</html>
