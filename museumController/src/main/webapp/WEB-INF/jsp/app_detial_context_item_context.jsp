<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page import="com.cn.hnust.common.Constant" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app-detial-context-item-context.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/app-detial-context-item.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<script type="text/javascript">
		var baseUrl='<%= Constant.ResouseUrl%>';
		window.parent.baseUrl=baseUrl;
		</script>
	</head>
	<body class="item-context-body">
		<div class="context">
			<div class="img">
				<!-- <img src="${pageContext.request.contextPath}/img/test2.png"style="width: 80%; height: 180px; margin-left: 10%; margin-top: 10%;"/> -->	
			</div>
			<div class="context-text">
			</div>
			<div class="explain" onclick="explainClick()">我要讲解</div>
			<div class="explain-popumenu"><img src="${pageContext.request.contextPath}/img/context-item-context-input.png" style="width: 99%; height: 40px;"/>
			<input id ='input' type="email" class="email" style="border: none; width: 80%; height: 25px; margin-top: 10px;margin-left: 5px;padding-left: 3px; position: absolute; top: 0; left: 0;" placeholder="请留下您的邮箱"></input>
			<div id = 'submit' class="submit" t='1' onclick="submitEmial()">取消</div>
			</div>
			
		</div>
		
		
	</body>
	<script>
	var h= screen.height;
	$('.context').css('height',(h-170)+"px");
	
		$('.explain-popumenu').hide();
		function GetQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]);
		     return null;
		}
		console.log(window.parent.content);
		$('.context-text').append("<div class='text' style=''>"+window.parent.content+"</div>");
		var a=$('.img').css('height');
		var ih=parseInt(a.split("p")[0]);
		$('.context-text').css('height',(h-180-ih)+"px");
		
		//添加轮播图
		$('.img').append("<div class='banner' style='width: 80%; height: 180px; margin-left: 10%; margin-top: 10%;'></div>");
		var picList=window.parent.picList;
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
		 * 轮播图的下标
		 * @param {Object}index 当前显示图片的下标 
		 */
		function bannerPoint(index){
			$('.banner-point').remove();
			$('.img').append("<div class='banner-point'></div>");
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
		 
		 $('#input').bind('input propertychange', function() {

		       var text=document.getElementById('input').value;
		       if(text.length<1){
		    	   $('#submit').remove();
		    	   $(".explain-popumenu").append("<div id = 'submit' class='submit' t='1' onclick='submitEmial()'>取消</div>");
		       }else{
		    	   $('#submit').remove();
		    	   $(".explain-popumenu").append("<div id = 'submit' class='submit' t='0' onclick='submitEmial()'>确定</div>");
		       }

		});
		
		
	</script>
</html>
