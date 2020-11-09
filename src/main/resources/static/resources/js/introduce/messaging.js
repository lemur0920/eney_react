$(function(){
	var link = document.location.href.split("/");
	var name = link[link.length -1];
	
	$(".line_1").css("display","none");
	$(".line_2").css("display","none");
	
	if(name == "big_message.do"){
		$(".line_1").css("display","");
	}else if(name == "big_price.do"){
		$(".line_2").css("display","");
	}
})