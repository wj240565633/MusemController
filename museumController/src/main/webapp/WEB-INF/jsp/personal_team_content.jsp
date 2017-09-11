<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
    <!--微信js-sdk-->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="${pageContext.request.contextPath}/mui/js/mui.min.js" ></script>
		<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
        <title></title>
        <style>

        	.body{
        		position: relative;
        		width: 100%;
        		height: 100%;
        	}
        	.list ,  .hint{
        		position: absolute;
        		width: 100%;
        		height: 100%;
        		left: 0;
        		top: 0;
        	}
        	.item{
        		width: 100%;
        		height: 15px;
        		margin-bottom: 30px;
        		
        	}
        	.user{
        		float: left;
        		width: 60%;
        		height: 100%;
        	}
        	.money{
        		float: left;
        		width: 30%;
        		text-align: right;
        		height: 100%;
        	}
        	.bg{
        		text-align: center;
        		float: left;
        		width: 10%;
        		height: 5px;
        		margin-top: 5px;
        		background-image: url(../img/虚线.png);
        		background-repeat: no-repeat;
        	}
        	
        	.hint_content{
        		text-align: center;
        		width: 60%;
        		height: auto;
        		margin-top: 15%;
        		margin-left: 20%;
        		line-height: 30px;
        		color: #8E8E8E;
        	}
        	
        	
        </style>
    </head>
    <body>
    		<div class="body" style='font-family: 微软雅黑; color: #717276;'>
	    			<c:choose>
					<c:when test="${null != list}">
						<div class="list">
							<c:forEach var="s" items="${list}" varStatus='item'>
				    				<div class="item">
				    					<div class="user">来自：${s.nickname}</div>
				    					<div class="bg" ></div>
				    					<div class="money">￥${s.income}</div>
				    				</div>
				    			</c:forEach>
				    		<!--  <div class="item" style='text-align:right;'> 小计：¥${total}元</div>  -->	
				    		</div>
						
					</c:when>
					<c:when test="${null == list}">
						<div class="hint">
			    				<div class="hint_content">
			    					当前身份暂无收益。
			    				</div>
			    			</div>
					</c:when>
				</c:choose>
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
    			
    		</script>
 	</body>
</html>