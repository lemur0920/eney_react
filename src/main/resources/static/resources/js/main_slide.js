var browserSize = window.innerWidth;
var browserHeight = window.innerHeight - 200;
var width = browserSize - 200;

if(browserSize < 720){width = browserSize - 100;}
else if(browserSize > 1720){width = browserSize - 400;}


var options = {
    id : 264568147,
    width:width,
    loop : true
};

function layerOpen(){
    $(".dim").css("display","block");
    $(".ny_close").css("display","block");
    $("#made-in-ny").css("display","block");

    var player = new Vimeo.Player('made-in-ny', options);


    $("#made-in-ny").css("position","fixed");
    $("#made-in-ny").css("top", Math.max(0, (($(window).height() - browserHeight) / 2) + $(window).scrollTop()) + "px");
    $("#made-in-ny").css("left", Math.max(0, (($(window).width() - width) / 2) + $(window).scrollLeft()) + "px");

    player.play(1);
    player.setVolume(100);

    $(".ny_close").on("click",function(){
        $(".dim").css("display","none");
        $("#made-in-ny").css("display","none");
        player.pause();

    })
}
