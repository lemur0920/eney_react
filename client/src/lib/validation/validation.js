import swal from "sweetalert";

export const isEmail = email => {
    const emailRegex = /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

    return emailRegex.test(email);
};

export const isPassword = password => {



    let num = password.search(/[0-9]/g);

    let eng = password.search(/[a-z]/ig);

    let spe = password.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);




    if(password.length < 8 || password.length > 16){

        // alert("6자리 ~ 16자리 이내로 입력해주세요.");

        return "8자리 ~ 16자리 이내로 입력해주세요.";

    }

    if(password.search(/₩s/) != -1){

        // alert("비밀번호는 공백업이 입력해주세요.");

        return "비밀번호는 공백업이 입력해주세요.";

    }

    if(spe < 0 ){

        // alert("특수문자를 혼합하여 입력해주세요.");

        return "특수문자를 혼합하여 입력해주세요.";

    }


    return true;


};

export const isEqual = (str1, str2) => {
    if(str1 === str2){
        return true;
    }else{
        return "입력값이 일치하지않습니다.";
    }

};


export const getByte = (str) => {
    return str
        .split('')
        .map(s => s.charCodeAt(0))
        .reduce((prev, c) => (prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1))), 0);
}


export const isUrl = (url, msg)=> {
    var re = /^(http[s]?:\/\/){0,1}(www\.){0,1}[a-zA-Z0-9\.\-]+\.[a-zA-Z]{2,5}[\.]{0,1}/;
    if (!re.test(url)) {
        swal('버튼 타입은 5개까지 등록 가능합니다. ');
        return false;
    }
}