$(function(){
	var link = document.location.href.split("/");
	var name = link[link.length -2];
	$(".customer_board").css("display","none");
    $(".ir_board").css("display","none");
	$(".line_1").css("display","none");
	$(".line_2").css("display","none");
	$(".line_3").css("display","none");
	$(".line_4").css("display","none");
    $(".line_5").css("display","none");
    $(".line_01").css("display","none");
    $(".line_02").css("display","none");
	
	if(name == "notice"){
        $(".customer_board").css("display","");
		$(".line_1").css("display","");
		$(".notice").addClass("on");
	}else if(name == "help"){
        $(".customer_board").css("display","");
		$(".line_2").css("display","");
		$(".help").addClass("on");
	}else if(name == "recruit"){
        $(".customer_board").css("display","");
		$(".line_3").css("display","");
		$(".recruit").addClass("on");
	}else if(name == "event"){
        $(".customer_board").css("display","");
		$(".line_4").css("display","");
		$(".event").addClass("on");
	}else if(name == "ir_disclosure"){
        $(".ir_board").css("display","");
        $(".line_01").css("display","");
        $(".ir_disclosure").addClass("on");
	}else if(name == "ir_news"){
        $(".ir_board").css("display","");
        $(".line_02").css("display","");
        $(".ir_news").addClass("on");
	}
})