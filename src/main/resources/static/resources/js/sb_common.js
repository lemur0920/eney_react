$(window).load(function(){
var doc_h = $(document).height();
var win_w = $(window).width();

	$('.custom_input input').iCheck({
		checkboxClass: 'icheckbox_minimal', 
		radioClass: 'iradio_minimal',
		increaseArea: '20%' 
	});

	$("#all_chk").on("click",function(){
		//var a = $("input[name='chk']").is(":checked");
		var chk = $(this).is(":checked");
		if(chk) $("input[name='chk']").prop('checked', true);
		else  $("input[name='chk']").prop('checked', false);
	});

	$(".btn_top").on("click",function(){
		$("body,html").animate({"scrollTop":0},400);
		return false;
	});
	$('.main_visual').flexslider({
		/*
		slideshow : false,
		before : function () {
			$('.main_visual .slides li').animate({"background-size":"auto 130%"},700);
			$('.main_visual .slides li.flex-active-slide').animate({"background-size":"auto 100%"},700);
		},
		
		start : function () {
			$('.main_visual .slides li:first-child').css("opacity",1)
		}
		*/
		
	});


/*------ 웹 ------*/
if (win_w >1140 ){

	/*gnb*/
	$(".gnb>li").on("mouseenter",function(){
		$(".gnb_cover").show();
		$(".header").addClass("on");
		var a = $(this).find(".s_gnb").css("display");
		if (a == "none"){
			$(".s_gnb").hide();
			$(this).find(".s_gnb").stop().fadeIn(150);
		}
		
	})
	$(".gnb_area").on("mouseleave",function(){
		$(".s_gnb").hide();
		$(".header").removeClass("on");
		$(".gnb_cover").fadeOut(100);
	});



/*------ 태블릿 ------*/
} else if (win_w <= 1140 && win_w >=768){

	/*gnb*/
	$(".gnb>li").on("mouseenter",function(){
		$(".gnb_cover").show();
		$(".header").addClass("on");
		var a = $(this).find(".s_gnb").css("display");
		if (a == "none"){
			$(".s_gnb").hide();
			$(this).find(".s_gnb").stop().fadeIn(150);
		}
		
	})
	$(".gnb_area").on("mouseleave",function(){
		$(".s_gnb").hide();
		$(".header").removeClass("on");
		$(".gnb_cover").fadeOut(100);

	});


/*모바일*/
} else if (win_w <= 768){

	/*gnb*/
	var gnb = $(".gnb>li>a");
	var sgnb_all = gnb.next(".s_gnb");
	gnb.on("click",function(){
		var sgnb = $(this).next(":hidden");
		sgnb_all.slideUp("fast");
		sgnb.slideDown("fast");
		return false;
	});

	var dep2_btn = $(".s_gnb>ul>li>.dep2_title");
	var dep3_cont_all = dep2_btn.next();
	dep2_btn.on("click",function(){
		var dep3_cont = $(this).next(":hidden");
		dep3_cont_all.slideUp("fast");
		dep3_cont.slideDown("fast");
		return false;
	});

	$(".gnb_open").on("click",function(){
		$(".gnb_area").stop().fadeIn("100");
		return false;
	});
	$(".gnb_cover").on("click",function(){
		$(".gnb_area").stop().fadeOut("100");
		return false;
	});
	$(".gnb_close").on("click",function(){
		$(".gnb_area").stop().fadeOut("100");
		return false;
	});

}

$(window).resize(function(){
	var doc_h = $(document).height();
	var win_w = $(window).width();

	/*------ 웹 ------*/
	if (win_w >1140 ){
		$(".gnb>li").unbind("mouseenter");
		$(".gnb_area").unbind("mouseleave");
		$(".gnb>li>a").unbind("click");
		$(".s_gnb>ul>li>.dep2_title").unbind("click");
		$(".gnb_cover").unbind("click");
		$(".gnb_area").show();
		$(".gnb>li>a").show();
		$(".s_gnb").hide();
		$(".dep3_menu").show();
		$(".gnb_cover").hide();

		/*gnb*/
		$(".gnb>li").on("mouseenter",function(){
			$(".gnb_cover").show();
			$(".header").addClass("on");
			var a = $(this).find(".s_gnb").css("display");
			if (a == "none"){
				$(".s_gnb").hide();
				$(this).find(".s_gnb").stop().fadeIn(150);
			}
			
		})
		$(".gnb_area").on("mouseleave",function(){
			$(".s_gnb").hide();
			$(".header").removeClass("on");
			$(".gnb_cover").fadeOut(100);
		});



	/*------ 태블릿 ------*/
	} else if (win_w <= 1140 && win_w >=768){
		$(".gnb>li").unbind("mouseenter");
		$(".gnb_area").unbind("mouseleave");
		$(".gnb>li>a").unbind("click");
		$(".s_gnb>ul>li>.dep2_title").unbind("click");
		$(".gnb_cover").unbind("click");
		$(".gnb_area").show();
		$(".gnb>li>a").show();
		$(".s_gnb").hide();
		$(".dep3_menu").show();
		$(".gnb_cover").hide();

		/*gnb*/
		$(".gnb>li").on("mouseenter",function(){
			$(".gnb_cover").show();
			$(".header").addClass("on");
			var a = $(this).find(".s_gnb").css("display");
			if (a == "none"){
				$(".s_gnb").hide();
				$(this).find(".s_gnb").stop().fadeIn(150);
			}
		})
		$(".gnb_area").on("mouseleave",function(){
			$(".s_gnb").hide();
			$(".header").removeClass("on");
			$(".gnb_cover").fadeOut(100);
		});


	/*모바일*/
	} else if (win_w <= 768){
		$(".gnb>li").unbind("mouseenter");
		$(".gnb_area").unbind("mouseleave");
		$(".gnb>li>a").unbind("click");
		$(".s_gnb>ul>li>.dep2_title").unbind("click");
		$(".gnb_cover").unbind("click");
		$(".gnb_area").hide();
		$(".gnb>li>a").show();
		$(".s_gnb").hide();
		$(".dep3_menu").hide();
		$(".gnb_cover").show();
		$(".header").removeClass("on");


		/*gnb*/
		var gnb = $(".gnb>li>a");
		var sgnb_all = gnb.next(".s_gnb");
		gnb.on("click",function(){
			var sgnb = $(this).next(":hidden");
			sgnb_all.slideUp("fast");
			sgnb.slideDown("fast");
			return false;
		});

		var dep2_btn = $(".s_gnb>ul>li>.dep2_title");
		var dep3_cont_all = dep2_btn.next();
		dep2_btn.on("click",function(){
			var dep3_cont = $(this).next(":hidden");
			dep3_cont_all.slideUp("fast");
			dep3_cont.slideDown("fast");
			return false;
		});

		$(".gnb_open").on("click",function(){
			$(".gnb_area").stop().fadeIn("100");
			return false;
		});
		$(".gnb_cover").on("click",function(){
			$(".gnb_area").stop().fadeOut("100");
			return false;
		});
		$(".gnb_close").on("click",function(){
			$(".gnb_area").stop().fadeOut("100");
			return false;
		});

	}
});

/*main*/
var controller = new ScrollMagic.Controller();

/*
var section1 =  new TimelineMax().add([
					TweenMax.staggerFromTo(".main_cont_2 .txt_1", 2.2, {left:-30, opacity:0}, {left: 0, opacity:1}),
					TweenMax.staggerFromTo(".main_cont_2 h1", 2, {left:-50, opacity:0}, {left: 0, opacity:1}),
					]);

var section1_start = new ScrollMagic.Scene({triggerElement: ".main_cont_1"})
				.setTween(section1)
				//.addIndicators() 
				.addTo(controller);
*/

var section2 =  new TimelineMax().add([
					TweenMax.staggerFromTo(".main_cont_2 li:nth-child(even)", 1.2, {top:-40, opacity:0}, {top: 0, opacity:1}),
					TweenMax.staggerFromTo(".main_cont_2 li:nth-child(odd)", 1.2, {bottom:-40, opacity:0}, {bottom: 0, opacity:1}),
					]);

var section2_start = new ScrollMagic.Scene({triggerElement: ".main_cont_1"})
				.setTween(section2)
				//.addIndicators() 
				.addTo(controller);

var section3 =  new TimelineMax().add([
					TweenMax.staggerFromTo(".main_cont_4 p", 2.2, {left:-30, opacity:0}, {left: 0, opacity:1}),
					TweenMax.staggerFromTo(".main_cont_4 h1", 2, {left:-50, opacity:0}, {left: 0, opacity:1}),
					]);

var section3_start = new ScrollMagic.Scene({triggerElement: ".main_cont_3"})
				.setTween(section3)
				//.addIndicators() 
				.addTo(controller);

var section4 =  new TimelineMax().add([
					TweenMax.staggerFromTo(".main_cont_5 li:nth-child(1)", 1.2, {top:-40, opacity:0}, {top: 0, opacity:1}),
					TweenMax.staggerFromTo(".main_cont_5 li:nth-child(2)", 1.2, {top:-40, opacity:0}, {top: 0, opacity:1, delay:0.4}),
					TweenMax.staggerFromTo(".main_cont_5 li:nth-child(3)", 1.2, {top:-40, opacity:0}, {top: 0, opacity:1, delay:0.8}),
					]);

var section4_start = new ScrollMagic.Scene({triggerElement: ".m_partners"})
				.setTween(section4)
				//.addIndicators() 
				.addTo(controller);


});