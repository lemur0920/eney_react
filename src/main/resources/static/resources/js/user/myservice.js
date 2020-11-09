$(function(){
	var link = document.location.href.split("/");
	var name = link[link.length -1];
	var paging = name.split("?");
	var own_name = paging[paging.length-2];
	
	$(".line_1").css("display","none");
	$(".line_2").css("display","none");
	$(".line_3").css("display","none");
	$(".line_4").css("display","none");
    $(".line_5").css("display","none");
    $(".line_6").css("display","none");
	
	if(name == "management.do"){
		$(".line_1").css("display","");
		$(".management").addClass("on");
	}else if(name == "bill.do"){
		$(".line_2").css("display","");
		$(".bill").addClass("on");
	}else if(name == "payment_history.do"){
		$(".line_3").css("display","");
		$(".payment_history").addClass("on");
	}else if(name == "point.do"){
		$(".line_4").css("display","");
		$(".point").addClass("on");
	}else if(name == "log_charge.do" || own_name == "log_charge.do"){
        $(".line_6").css("display","");
        $(".point").addClass("on");
	}else if(name == "log_pay.do" || own_name == "log_pay.do"){
        $(".line_5").css("display","");
        $(".point").addClass("on");
    };

})