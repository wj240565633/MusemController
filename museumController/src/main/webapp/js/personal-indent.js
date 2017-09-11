

var userId;
function searchThing(){
	var text= document.getElementById('search-text').value;
	document.getElementById('search-text').value="";
	if(text.length>1){
		if(needLoad(userId,text)){
			$('.child-item-body').attr('src',"/museumController/purchaseRecordSearch/"+userId+"/"+text);
		}
		
	}else{
		if(needLoad(userId,"All")){
			$('.child-item-body').attr('src',"/museumController/purchaseRecordSearch/"+userId+"/All");
		}
	}
	
	
}

function onTypeChanged(){
	var select=document.getElementById("app-header-select");
	var item=select.value;
	var src="/museumController/purchaseRecordContext/"+userId+"/"+item;
	$('.child-item-body').attr("src",src);
}

function needLoad(userId,text){
	var need = false;
	$.ajax({
		url :"/museumController/hasRecordSearch/"+userId+"/"+text,
		type : "get",
		dataType : "json",
		async: false,
		success : function(obj) {
			if(100 == obj){
				need = true;
			}else{
				need = false;
			}
		},
		error : function() {
			need = false;
		}
	});
	return need;
}
