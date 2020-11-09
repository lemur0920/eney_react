$(function(){
	var link = document.location.href.split("/");
	var name = link[link.length -1];
	
	$(".line_1").css("display","none");
	$(".line_2").css("display","none");
	$(".line_3").css("display","none");
	$(".line_4").css("display","none");
	
	if(name == "patchcall.do"){
		$(".line_1").css("display","");
	}else if(name == "coloring.do"){
		$(".line_2").css("display","");
	}else if(name == "price_info.do"){
		$(".line_3").css("display","");
	}
})