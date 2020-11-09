$(function(){
	var link = document.location.href.split("/");
	var name = link[link.length -1];
	
	$(".line_1").css("display","none");
	$(".line_2").css("display","none");
	$(".line_3").css("display","none");
	$(".line_4").css("display","none");
	
	if(name == "profile.do"){
		$(".line_1").css("display","");
		$(".profile").addClass("on");
	}else if(name == "pw_change.do"){
		$(".line_2").css("display","");
		$(".pw_change").addClass("on");
	}else if(name == "certification.do"){
		$(".line_3").css("display","");
		$(".certification").addClass("on");
	}else if(name == "event.do"){
		$(".line_4").css("display","");
		$(".event").addClass("on");
	}
})