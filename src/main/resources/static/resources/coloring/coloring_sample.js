$(function(){
	//처음 로드
	var cate = '${sampleList[0].category}';
	var len = $(".sample-wrap[category='"+cate+"']").length;
	var height = (len%2 == 0) ? len/2*240 + 180 : (len/2+1)*240 + 180
	var cate = '${param.category}';
	if(cate=='')
		cate = 'education';
	$('.category-wrap').find('.'+cate).addClass('on');
	$(".listen-wrap").css('height',height)
	$(".sample-wrap").css('display','none');
	$(".sample-wrap[category='"+cate+"']").css('display','');
	
	$(".category").click(function(){
		var cate = $(this).text().trim();
		var len = $(".sample-wrap[category='"+cate+"']").length;
		var height = (len%2 == 0) ? len/2*200 + 300 : (len/2+1)*200 + 100
		$(".category-wrap").find(".on").removeClass("on");
		$(this).addClass("on");
		$(".listen-wrap").css('height',height)
		$(".sample-wrap").css('display','none');
		$(".sample-wrap[category='"+cate+"']").css('display','');
	})
	
	$.each($("audio"),function(){
		$(this).mediaelementplayer({
		    alwaysShowControls: true,
		    features: ['playpause','progress','volume'],
		    audioVolume: 'horizontal',
		    audioWidth: 497,
		    audioHeight: 70,
		    iPadUseNativeControls: true,
		    iPhoneUseNativeControls: true,
		    AndroidUseNativeControls: true
		});
	})
})