
var content;
var picList=[];
var soundAddr;



var userId;

var token;

var refreshToken;


var baseUrl;



/**
 * 返回按钮点击事件
 */
function back(){
	window.history.go(-1);
}

/**
 * 分享按钮点击事件
 */
function share(){
	console.log("点击了分享按钮");
}
/**
 * 提交邮箱
 */
function submitEmial(){
	console.log("提交邮箱");
	var msg=$("#submit").attr('t');
	console.log("msg:"+msg);
	if(msg==1){
		console.log("取消");
		$('.explain-popumenu').hide();
	}else if(msg==0){
		var email=document.getElementById('input').value;
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(email)){ 
			alert("您的电子邮件格式不正确");
		 }else{
			putEmail(email);
			$('.explain-popumenu').hide();
		 }
		
	}
}

/**
 * 我要讲解
 */
function explainClick(){
	$('.explain-popumenu').show();
}

function putEmail(email){
	var userId = window.parent.userId
	$.ajax({
		url :"/museumController/myExplain",
		type : "post",
		data:{userId,
			email
			},
		dataType : "json",
		success : function(obj) {	
				alert(obj.code);
		},
		error : function() {
			
		}
	});
}




