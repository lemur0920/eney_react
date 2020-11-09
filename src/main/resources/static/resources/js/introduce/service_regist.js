$(function(){

    $(".charge_info").css("display","none");
    $('#all_chk').on('ifChecked', function(event){
        $("#idc_check").iCheck('check');
        $("#etc_check").iCheck('check');
    })

    $('#all_chk').on('ifUnchecked', function(event){
        $("#idc_check").iCheck('uncheck');
        $("#etc_check").iCheck('uncheck');
    })

    $("input[type=radio]").on("ifChanged",function(){
        var service_period = $("input[name=service_period]:checked").val();
        var service_type = $("input[name=service_type]:checked").val();
        var pay_way = $("input[name=pay_way]:checked").val();

        if(pay_way == "credit"){
            $(".pay_way_td").text("신용카드");
            $(".charge_way_td").text("신용카드");
        }else if(pay_way == "epoint"){
            $(".pay_way_td").text("포인트");
            $(".charge_way_td").text("포인트");
        }else if(pay_way == "account_transfer"){
            $(".pay_way_td").text("전자세금계산서(계좌이체)");
            $(".charge_way_td").text("전자세금계산서(당월청구)");
        }else if(pay_way == "auto_transfer"){
            $(".pay_way_td").text("전자세금계산서(자동이체)");
            $(".charge_way_td").text("전자세금계산서(당월청구)");
        }

        if(pay_way == "account_transfer" || pay_way == "auto_transfer"){
            $(".charge_info").css("display","");
        }
    })
})
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function regist_cancel(){
    if (confirm("정말 취소하시겠습니까??") == true){    //확인
        alert('취소되었습니다.');
        location.href = "/main.do";
    }else{   //취소
        return false;
    }
}

function step1tostep2(){
    var element1 = document.getElementById("regist_step1");
    var element2 = document.getElementById("regist_step2");

    if($("#idc_check").prop("checked")==true && $("#etc_check").prop("checked")==true){
        element1.classList.remove("show");
        element1.classList.add("hidden");

        element2.classList.add("show");
        element2.classList.remove("hidden");
    }else{
        alert('이용약관에 동의해주세요.');
        return false;
    }
}

function step2tostep1(){
    var element1 = document.getElementById("regist_step1");
    var element2 = document.getElementById("regist_step2");

    element2.classList.remove("show");
    element2.classList.add("hidden");

    element1.classList.add("show");
    element1.classList.remove("hidden");
}

function step3tostep2(){
    var element2 = document.getElementById("regist_step2");
    var element3 = document.getElementById("regist_step3");

    element3.classList.remove("show");
    element3.classList.add("hidden");

    element2.classList.add("show");
    element2.classList.remove("hidden");
}
function step4tostep3(){
    var element3 = document.getElementById("regist_step3");
    var element4 = document.getElementById("regist_step4");

    element4.classList.remove("show");
    element4.classList.add("hidden");

    element3.classList.add("show");
    element3.classList.remove("hidden");
}
