/**
 * @author 刘雄飞
 * @description  index.html and app-context.html
 */
/**
 * 是否监听滑动
 */
var  b=true;


/**
 * 当前显示的内容为搜索的结果
 */
var showSearch=false;
var nomalLanguageId;
var mLanguages;
var mLanguageId;
var mLanguageType;



/**
 *用户id  
 */
var userId;
var openId;

/**
 * 窗体滑动事件
 */
window.onscroll = function () {
	
	/**
      * 判断滑动方向
      */
     scroll(function(direction) { 
     	if(direction=="right"){
     		if (getScrollWidth()-(getScrollLeft() + getClientWidth() )<20 &&b) {
	        console.log("到底了");
	        loading();
     	}
     	
       
    }
     	
     });
    
}


//获取滚动条当前的位置
function getScrollLeft() {
    var scrollLeft = 0;
    if (document.documentElement && document.documentElement.scrollLeft) {
        scrollLeft = document.documentElement.scrollLeft;
    }
    else if (document.body) {
        scrollLeft = document.body.scrollLeft;
    }
    return scrollLeft;
}
//获取当前可视范围的高度
function getClientWidth() {
    var clientWidth = 0;
    if (document.body.clientWidth && document.documentElement.clientWidth) {
        clientWidth = Math.min(document.body.clientWidth, document.documentElement.clientWidth);
    }
    else {
        clientWidth = Math.max(document.body.clientWidth, document.documentElement.clientWidth);
    }
    return clientWidth;
}

//获取文档完整的宽度
function getScrollWidth() {
    return Math.max(document.body.scrollWidth, document.documentElement.scrollWidth);
}


/**
 * 判断滑动方向
 * @param {Object} fn
 */
function scroll( fn ) {
    var beforeScrollLeft = document.body.scrollLeft
        fn = fn || function() {};
    window.addEventListener("scroll", function() {
        var afterScrollLeft = document.body.scrollLeft,
            delta = afterScrollLeft - beforeScrollLeft;
        if( delta === 0 ) return false;
        fn( delta > 0 ? "right" : "left" );
        beforeScrollLeft = afterScrollLeft;
    }, false);
}


window.onload=function(){
	//初始化界面数据
	/**
	 * 初始化语种
	 */
	$.ajax({
		url :"/museumController/museumLangage",
		type : "get",
		dataType : "json",
		success : function(obj) {
			var lgglt=obj;
			/**
			 * 初始化默认语种和语种id
			 */
			mLanguages=lgglt;
			mLanguageId=lgglt[0].languageId;
			nomalLanguageId=lgglt[0].languageId;
			mLanguageType=lgglt[0].languageType;
			$(".app-context-body-iframe").attr("src", "/museumController/museumHomePage/"+mLanguageId+"/0/15");
			for(var i =0;i<lgglt.length;i++){
				var id=lgglt[i].languageId;
				var type=lgglt[i].languageType;
				$('#app-header-select').append("<option value="+id+">"+type+"</option>");
			}	
				
		},
		error : function() {
			
		}
	});
	
}
var baseUrl;

/**
 * 获取数据成功
 * @param {Object} data
 */
//博物馆搜索方法
function loadDataSuccess(data,limit){
	b=true;
//	获取数据成功
	if(data[0].museumId==null){
		setTimeout(function() {
			$(".mui-toast-container").remove();
		}, 2000);
		return;
	}
	for(var i =0;i<data.length;i++){
	
		var url=window.parent.baseUrl+data[i].pictureAddress;
		var title=data[i].museumName;
		var id=data[i].museumId;
		$(".app-context-body-iframe").contents().find(".mui-scroll").append("<div onclick='onItemClickListener(this.id) ' class='mui-scroll-item' id="+id+">"+
		"<img src="+url+">"+
		"<div class='bg'></div>"+
		"<div class='title' >"+title+"</div>"+
		"</div>");
		var itemName="#"+id;
		var offset=(limit+i)*36;
		console.log(offset);
		var sh=screen.height;
		var statusBarHeight=72;
		var context_body_height=sh-statusBarHeight-96;

		$(".app-context-body-iframe").contents().find(itemName).css('left',offset+"%");
		$(".app-context-body-iframe").contents().find(itemName).css('height',context_body_height+"px");
	
	}
	
	
}
/**
 * 获取数据失败
 * @param {Object} error
 */
function loadDataError(){
	b=true;
	
	//	获取数据失败
}

/**
 * 开始读取后台数据
 */
var nomal_limit=15;
function loading(){
	b=false;   
	/**
	 * 分页查询
	 */
	if(showSearch){//加载的是搜索获取的分页数据
		
		$.ajax({
			url :"/museumController/museumSearch/"+window.parent.search_value+"/"+mLanguageId+"/"+search_limit+"/15",
			type : "get",
			dataType : "json",
			success : function(obj) {
				var lgglt=obj.modelList;
				if(lgglt.length<1){
					noMoreData();
				}else{
					search_limit+=15;
					loadDataSuccess(lgglt,search_limit);
				}
					
			},
			error : function() {
				loadDataError();
			}
		});
	}else{//加载的是初始化数据的分页数据
		var url="/museumController/museumHomePage/"+window.parent.mLanguageId+"/"+nomal_limit+"/15";
		$.ajax({
			url :url,
			type : "get",
			dataType : "json",
			success : function(obj) {
				console.log(obj.length);
				if(obj.length=="undefined"){
					console.log("没数据了");
					console.log(obj[0].museumId);
				}
				var lgglt=obj.museumList;
				if(lgglt.length<1){
					noMoreData();
				}else{
					nomal_limit+=15;
					loadDataSuccess(lgglt,nomal_limit);
				}
						
					
			},
			error : function() {
				loadDataError();
			}
		});
		
	}
	

}
/**
 * 提示用户没有更多数据了
 */
function noMoreData(){
	b=true;
//	mui.toast('没有更多数据了',{ duration:'short', type:'div' });
//	
//	mui.toast('此语种信息无数据',{ duration:'short', type:'div' });
	
}


/**
 * 移除内容区域所有内容
 */

function removeAllItem(){
	$(".app-context-body-iframe").contents().find(".mui-scroll").empty();
}

/**
 * 切换语言事件
 */
function onlanguagechange(){
	var select=document.getElementById('app-header-select');
	var item=select.selectedIndex;
	if(mLanguageId!=mLanguages[item].languageId){
		mLanguageId=mLanguages[item].languageId;
		window.parent.mLanguageType=mLanguages[item].languageType;
		reLoad();
	}
	
	
}

/**
 * 改变语言刷新数据
 */
function reLoad( ) {
	var url="/museumController/museumHomePage/"+mLanguageId+"/0/15";
	showSearch=false;
	$.ajax({
		url :url,
		type : "get",
		dataType : "json",
		success : function(obj) {
			var lgglt=obj.museumList;
			if(lgglt.length<1){
				mui.toast('此语种信息无数据',{ duration:'short', type:'div' });
				mLanguageId=nomalLanguageId;
				var se=document.getElementById("app-header-select");
				se.value=mLanguageId;
				var text=se.options[se.selectedIndex].text;
				window.parent.mLanguageType=text;
			}else{
				nomalLanguageId=mLanguageId;
				//清除以前的所有条目
				removeAllItem();
				loadDataSuccess(lgglt,0);
			}
					
				
		},
		error : function() {
			loadDataError();
		}
	});
	
}

/**
 * 搜索按钮点击事件
 */
var search_limit=1;
var search_value;
function search(){
	var search_input=document.getElementById("app-context-search-input");
	search_value=search_input.value;
	if(search_value.length==0){//当前搜索的内容为空
		showSearch=false;
		var url="/museumController/museumHomePage/"+mLanguageId+"/0/15";
		$.ajax({
			url :url,
			type : "get",
			dataType : "json",
			success : function(obj) {
				nomal_limit=nomal_limit+15;
				var lgglt=obj.museumList;
				
				//清除以前的所有条目
				removeAllItem();
				loadDataSuccess(lgglt,0);
				
						
					
			},
			error : function() {
				loadDataError();
			}
		});
		
	}else{
		showSearch=true;
		//当前搜索的内容部位空
		$.ajax({
			url :"/museumController/museumSearch/"+search_value+"/"+mLanguageId+"/0/15",
			type : "get",
			dataType : "json",
			async: false,
			success : function(obj) {
				document.getElementById("app-context-search-input").value="";
				//清除以前的所有条目
				
				if(obj.modelList.length==0||obj.modelList[0].museumId==null){
					mui.toast('检索的博物馆不存在',{ duration:'long', type:'div' }); 
					return;
				}
				removeAllItem();
				var lgglt=obj.modelList;
				loadDataSuccess(lgglt,0);		
					
			},
			error : function() {
				loadDataError();
			}
		});
	}
	
	
}
/**
 * 子页面条目点击事件
 */
function onItemClickListener(itemName){
	//login();
	var url="/museumController/museumIntroduce?id="+
	itemName+"&languageId="+window.parent.mLanguageId+"&languageName="+window.parent.mLanguageType+"&userId="+window.parent.userId+"&type=100&openid="+window.parent.openId+"&status=1";
	//top.location.href=window.parent.url;
	var u=encodeURI(url);
	top.location.href=u;
	
}

var url;
function login(){
	$.ajax({
		url :"/museumController/wechatCode",
		type : "get",
		dataType : "text",
		async: false,
		success : function(obj) {		
			window.parent.url=obj;	
		},
		error : function() {
	
		}
	});
}
