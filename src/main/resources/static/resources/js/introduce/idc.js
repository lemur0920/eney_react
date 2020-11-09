$(function(){
	var link = document.location.href.split("/");
	var name = link[link.length -1];
	
	$(".line_1").css("display","none");
	$(".line_2").css("display","none");
	$(".line_3").css("display","none");
	$(".line_4").css("display","none");
	
	if(name == "idc.do"){
		$(".line_1").css("display","");
	}else if(name == "vpn.do"){
		$(".line_2").css("display","");
	}else if(name == "idc_price.do"){
		$(".line_3").css("display","");
	}
})