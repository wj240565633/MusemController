<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
    <title></title>
    <!--微信js-sdk-->
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!--jquery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.1.1.min.js" ></script>
		<style>
			*{
				margin: 0;
				padding: 0;
				font-family: "微软雅黑";
				font-size: 18px;
			}
			.body{
				position: relative;
				width: 100%;
				background-color: #32363F;
			}
			.e{
				position: absolute;
				width: 100%;
				text-align: center;
				margin-top: 200px;
				color: #F8F8F8;
			}
			img{
				width: 30px;
				height: 30px;
			}
		</style>

  </head>
  
  <body>
     <div class="body">
			<div class="e">
				<div><img src="${pageContext.request.contextPath}/img/error.png"></div>
			</div>
		</div>
		<script type="text/javascript">
		
			var totalH=screen.height-62;
			$('.body').css('height',totalH+"px");
			var a=totalH/2-50
			$('.e').css('margin-top',a+'px');
			var msg ="${msg}"
			if(msg != undefined && msg !=""){
				if(msg == "当前用户被禁用！"){
					$(".e").append("<div>"+msg+"</div>")
				}else{
					$(".e").append("<div style='color:#000000;'>"+msg+"</div>")
					$(".body").css("background","#EBEBEB")
				}
				
			}else{
				$(".e").append("<div>网络异常</div>")
			}
									
		</script>
</html>
