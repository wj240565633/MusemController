/**
 * 博物馆详情界面
 */
/**
 * 是否监听滑动
 */
var  b=true;
/**
 * 窗体滑动事件
 */
window.onscroll = function () {
	
	/**
      * 判断滑动方向
      */
     scroll(function(direction) { 
     	if (direction=="down") {
     		if (getScrollHeight()-(getScrollTop() + getClientHeight() )<20 &&b) {
		        console.log("到底了");
		        loading();
		       
		    }
     	}
     	
     });
    
}


//获取滚动条当前的位置
function getScrollTop() {
    var scrollTop = 0;
    if (document.documentElement && document.documentElement.scrollTop) {
        scrollTop = document.documentElement.scrollTop;
    }
    else if (document.body) {
        scrollTop = document.body.scrollTop;
    }
    return scrollTop;
}
//获取当前可视范围的高度
function getClientHeight() {
    var clientHeight = 0;
    if (document.body.clientHeight && document.documentElement.clientHeight) {
        clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight);
    }
    else {
        clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight);
    }
    return clientHeight;
}

//获取文档完整的宽度
function getScrollHeight() {
    return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
}


/**
 * 判断滑动方向
 * @param {Object} fn
 */
function scroll( fn ) {
    var beforeScrollTop = document.body.scrollTop
        fn = fn || function() {};
    window.addEventListener("scroll", function() {
        var afterScrollTop = document.body.scrollTop,
            delta = afterScrollTop - beforeScrollTop;
        if( delta === 0 ) return false;
        fn( delta > 0 ? "down" : "up" );
        beforeScrollTop = afterScrollTop;
    }, false);
}




//----------------------功能部分---------------------\\\\\\\\\\\\


//--------变量部分---------------------

//当前初始语言

var nomalLanguageId;
var mCurrentLanguage;
var mLanguageId;
var mLanguageList=[];
var mLanguageIdList=[];

//图片集合
var mPicList=[];

//博物馆介绍
var mMuseumDesc;

//语音地址
var mSoundPath;
//博物馆名字
var mMuseumName;
//博物馆id
var mMuseumId;

//博物馆语音介绍
var soundAddress;




//用户信息

var userId;


var baseUrl;



var isFirst=true;
window.onload=function(){	
	
}


/**
 * 获取数据成功
 */
function loadDataSuccess(){
	b=true;
	
//	获取数据成功
}
/**
 * 获取数据失败
 */
function loadDataError(){
	b=true;
	
	//	获取数据失败
}

/**
 * 开始读取后台数据
 */
var item_offset=15;
var search_limit=15;

var isSearch=false;
function loading(){
	b=false; 
	if(isSearch){
		$.ajax({
			url :"/museumController/heritageSearch/"+window.parent.value+"/"+window.parent.mLanguageId+"/"+window.parent.userId+"/"+window.parent.search_limit+"/15",
			type : "get",
			dataType : "json",
			success : function(obj) {
				var lgglt=obj.searchList;
				if(lgglt.length==0){
					noMoreData();
				}else{
					showData(lgglt);
					search_limit+=15;
					loadDataSuccess();
				}
				
			},
			error : function() {
				loadDataError();
				
			}
		});
		
	}else{
		$.ajax({
			url :"/museumController/historicalRelicTabulation/"+window.parent.mMuseumId+"/"+window.parent.mLanguageId+"/"+window.parent.userId+"/"+window.parent.item_offset+"/15",
			type : "get",
			dataType : "json",
			success : function(obj) {
				var lgglt=obj.historicalList;
				if(lgglt.length==0){
					noMoreData();
				}else{
					showData(lgglt);
					item_offset+=15;
					loadDataSuccess();
				}
				
					
			},
			error : function() {
				loadDataError();
				
			}
		});
	}
	
    

}

/**
 * 文物列表
 */
function showData(lgglt) {
	if(lgglt=="undefined"){
		mui.toast('查询的文物不存在',{ duration:'short', type:'div' });
		return;
	}
	for(var i =0;i<lgglt.length;i++){
		var id=lgglt[i].historicalRelicId;
		var name=lgglt[i].historicalRelicName;
		var time=lgglt[i].dynasty;
		var type=lgglt[i].level;
		var pic=window.parent.baseUrl+lgglt[i].pictureAddress;
		var isBuy=lgglt[i].buy;
		$(".app-context-body-iframe").contents().find(".divbody").append("<div id='"+id+"' class='item' onclick='onItemClickListener(this.id)'>"+
				"<img src="+pic+">"+
				"<div class='item-bg'></div>"+
				"<div class='info'>"+
				"<ul>"+
				"<li class='one'>"+name+"</li>"+
				"<li class='other'>"+type+"</li>"+
				"<li class='other'>"+time+"</li>"+
				"</ul>"+
				"</div>"+
				"<div class='kst' id='kst"+id+"'></div>"+
				"<div class='mf' id='mf"+id+"'></div>"+
				"</div>");
		if(isBuy=="NO"){
			$(".app-context-body-iframe").contents().find('#kst'+id).attr('hidden',true);
		}
		if(isBuy=="OK"){
			$(".app-context-body-iframe").contents().find('#mf'+id).attr('hidden',false);
		}else{
			$(".app-context-body-iframe").contents().find('#mf'+id).attr('hidden',true);
		}
	}
	
}


var map={};
function showPLSTData(lgglt) {
	if(lgglt=="undefined"){
		mui.toast('查询的文物不存在',{ duration:'short', type:'div' });
		return;
	}
	for(var i =0;i<lgglt.length;i++){
		var id=lgglt[i].historicalRelicId;
		var name=lgglt[i].historicalRelicName;
		var time=lgglt[i].dynasty;
		var type=lgglt[i].level;
		var pic=window.parent.baseUrl+lgglt[i].pictureAddress;
	
		
		$(".app-context-body-iframe").contents().find(".divbody").append("<div id='"+id+"' class='item' onclick='onItemClickListener(this.id)'>"+
				"<img src='"+pic+"' />"+
				"<div class='item-bg'></div>"+
				"<div class='info'>"+
				"<ul>"+
				"<li class='one'>"+name+"</li>"+
				"<li class='other'>"+type+"</li>"+
				"<li class='other'>"+time+"</li>"+
				"</ul>"+
				"</div>"+
				"<input type='checkbox' id='cb"+id+"' />"+
				
				"</div>");	
				
			}
	$.each(map, function(i) {     
	    delete map[i];
	});
	console.log(map);
	$(".app-context-body-iframe").contents().find(".divbody").append("<div style='width: 100%; height: 48px;'></div>");
	$(".app-context-body-iframe").contents().find('.check').empty();
	$(".app-context-body-iframe").contents().find(".check").append("<input type='checkbox' id='checkAll' onclick='checkAllClick()'/>");
	$(".app-context-body-iframe").contents().find(".check").append("<div class='check-value'>全选</div>");
	$(".app-context-body-iframe").contents().find(".check").append("<div class='check-num'>共选0个</div>");
	$(".app-context-body-iframe").contents().find(".check").append("<div class='check-pay' onclick='pay()'>确认</div>");
	
}

/**
 * 提示用户没有更多数据了
 */
function noMoreData(){
	b=true;
	
	
}


/**
 * 移除内容区域所有内容
 */

function removeAllItem(){
	$(".app-context-body-iframe").contents().find(".divbody").empty();
}



var isGoplst=false;

/**
 * 返回按钮点击事件
 */
function back(){
	console.log(isGoplst)
	if(isGoplst){
		$('.app-context-body-iframe').attr('src',"/museumController/historicalRelicTabulation/"+window.parent.mMuseumId+"/"
				+window.parent.mLanguageId+"/"+window.parent.userId+"/0/15");	
		isGoplst=false;
	}else{
		//location.href="../index.html";
		window.history.go(-1);
	}
	
}


/**
 * 切换语言事件
 */
function onlanguagechange(){
	var select=document.getElementById('app-header-se');
	var item=select.value;
	if(mLanguageId!=item){
		mLanguageId=item;
		for(var i=0;i<mLanguageIdList.length;i++){
			if(mLanguageId==mLanguageIdList[i]){
				mCurrentLanguage=mLanguageList[i]
			}
		}
		reLoad();
	}
	
	
}

/**
 * 切换语言更新数据
 */
function reLoad() {
	if(window.parent.single){
		//单个文物收听选择语言
		$.ajax({
			url :"/museumController/historicalRelicTabulation/"+window.parent.mMuseumId+"/"+window.parent.mLanguageId+"/"+window.parent.userId+"/0/15",
			type : "get",
			dataType : "json",
			success : function(obj) {
				var lgglt=obj.historicalList;
				if(lgglt.length==0){
					mui.toast('此语种信息无数据',{ duration:'short', type:'div' });
					window.parent.mLanguageId=window.parent.nomalLanguageId;
					var se=document.getElementById("app-header-se");
					se.value=window.parent.mLanguageId;
					var text=se.options[se.selectedIndex].text;
					window.parent.mCurrentLanguage=text;
				}else{
					window.parent.nomalLanguageId=window.parent.mLanguageId;
					removeAllItem();
					showData(lgglt);
					isSearch=false;	
				}
				
					
			},
			error : function() {
				loadDataError();
			}
		});
	}else{
		//批量收听切换语言
		$.ajax({
			url :"/museumController/batchDataFetch/"+window.parent.mMuseumId+"/"+window.parent.mLanguageId+"/"+window.parent.userId,
			type : "get",
			dataType : "json",
			success : function(obj) {
				var lgglt=obj.json;
				if(lgglt.length==0){
					mui.toast('此语种信息无数据',{ duration:'short', type:'div' });
					window.parent.mLanguageId=window.parent.nomalLanguageId;
					var se=document.getElementById("app-header-se");
					se.value=window.parent.mLanguageId;
					var text=se.options[se.selectedIndex].text;
					window.parent.mCurrentLanguage=text;
				}else{
					window.parent.nomalLanguageId=window.parent.mLanguageId;
					removeAllItem();
					showPLSTData(lgglt);
					isSearch=false;	
				}
				
					
			},
			error : function() {
				loadDataError();
			}
		});
	}
	
}

/**
 * 搜索按钮点击事件
 */
var value;

var single;
function search(){
	window.parent.value=document.getElementById("app-context-search-input").value
	if(window.parent.single){
		//单个文物购买界面搜索
		
		if(value.length==0){
			
			$.ajax({
				url :"/museumController/historicalRelicTabulation/"+window.parent.mMuseumId+"/"+window.parent.mLanguageId+"/"+window.parent.userId+"/0/15",
				type : "get",
				dataType : "json",
				success : function(obj) {
					var lgglt=obj.historicalList;
					
					if(lgglt.length==0){
						mui.toast('搜索的信息不存在',{ duration:'short', type:'div' });
					}else{
						removeAllItem();
						showData(lgglt);
						isSearch=false;	
					}
					
						
				},
				error : function() {
					loadDataError();
					mui.toast('获取数据失败',{ duration:'short', type:'div' }); 
				}
			});
			
		}else{
			$.ajax({
				url :"/museumController/heritageSearch/"+window.parent.mMuseumId+"/"+window.parent.value+"/"+window.parent.mLanguageId+"/"+window.parent.userId+"/0/15",
				type : "get",
				dataType : "json",
				success : function(obj) {
					var lgglt=obj.searchList;
					if(obj.status=="NO"){
						mui.toast('检索的结果不存在',{ duration:'short', type:'div' });
						return;
					}else if(obj.status=="YES"){
						removeAllItem();
						showData(lgglt);
						isSearch=true;	
					}
					
				},
				error : function() {
					loadDataError();
					mui.toast('获取数据失败',{ duration:'short', type:'div' }); 
				}
			});
		}
		
	}else{
		//批量购买文物界面
		plgmUI(window.parent.value);
		
	}
	
	document.getElementById("app-context-search-input").value="";
	
}
//批量购买界面搜索
function plgmUI(value){
	if(value.length<1){
		$.ajax({
			url :"/museumController/batchDataFetch/"+window.parent.mMuseumId+"/"+window.parent.mLanguageId+"/"+window.parent.userId,
			type : "get",
			dataType : "json",
			success : function(obj) {
				var lgglt=obj.json;
				if(lgglt.length==0){
					mui.toast('搜索的信息不存在',{ duration:'short', type:'div' });
				}else{
					removeAllItem();
					showPLSTData(lgglt);
					isSearch=false;	
				}
				
					
			},
			error : function() {
				loadDataError();
				mui.toast('获取数据失败',{ duration:'short', type:'div' }); 
			}
		});
	}else{
		//搜索批量收听口没写
		$.ajax({
			url :"/museumController/batchDataFetchSearch/"+value+"/"+window.parent.mMuseumId+"/"+window.parent.mLanguageId+"/"+window.parent.userId,
			type : "get",
			dataType : "json",
			success : function(obj) {
				var lgglt=obj.json;
				if(lgglt.length==0){
					mui.toast('搜索的信息不存在',{ duration:'short', type:'div' });
				}else{
					removeAllItem();
					showPLSTData(lgglt);
					isSearch=false;	
				}
				
					
			},
			error : function() {
				loadDataError();
				mui.toast('获取数据失败',{ duration:'short', type:'div' }); 
			}
		});
	}
	
}

/**
 * 文物条目点击事件
 */
var cancelPayMask=false;
var payMask;

var itemCanClick=false;
function onItemClickListener(itemId){
	if(payMask!=null){
		if(payMask._show){
			cancelPayMask=true;
			payMask.close();
			payMask=null;
		}
	}else{
		payMask= mui.createMask(function(){
		return cancelPayMask;
	});			
	payMask.show(); 
	var masks=document.getElementsByClassName('mui-backdrop');
	console.log(masks)
	var payM=masks[0];
	payM.id="payMask";
	payM.style.bottom="0px";
	console.log(screen.height);
	var t=screen.height-200;
	payM.style.top=t+"px";
//	payM.style.zIndex=999;
//	payM.style.position='absolute'
	payM.style.height="150px";
	payM.style.backgroundColor="#FF0000";
	}	
}

function goBuy(){
	console.log("批量支付");
	window.parent.isGoplst=true;
	location.href="/museumController/batchDataFetch/"+window.parent.mMuseumId+"/"+window.parent.mLanguageId+"/"+window.parent.userId;
}
