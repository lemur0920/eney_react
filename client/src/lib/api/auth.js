import client from './client';
const qs = require('querystring');

//로그인
export const login = ({userId, password}) =>{

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };
    const requestBody = {
        userId:userId,
        password:password
    };

    return client.post('/user/login', requestBody);
}

export const authUpdate = () =>{


    return client.post('/user/authUpdate');
}

//회원가입
export const register = ({form}) =>{

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    return client.post('/join/auth/join', form);
}

export const getCertify = ({imp_uid, type}) => {
    const requestBody = {
        impUid:imp_uid,
        type:type
    };
    return client.post('/userCertify/authCheck', requestBody);
}

export const idFind = ({imp_uid}) => {
    const requestBody = {
        impUid:imp_uid
    };
    return client.post('/userCertify/idFind', requestBody);
}

export const pwRecover = ({imp_uid, userId}) => {
    const requestBody = {
        impUid:imp_uid,
        userId:userId,
    };
    return client.post('/userCertify/pwFind', requestBody);
}

export const passwordReset = ({userId,newPw, newPwCheck}) => {
    const requestBody = {
        userId:userId,
        newPw:newPw,
        newPwCheck:newPwCheck,
    };
    return client.post('/user/passwordReset', requestBody);
}

export const idCheck = (userId) => {
    return client.post('/user/idCheck', userId);
}


export const idDuplicateCheck = (userId) => {
    // const requestBody = {
    //     impUid:imp_uid,
    //     type:type
    // };
    return client.post('/join/duplicateCheck', userId);
}

export const pwCheck = (pw) => {
    const requestBody = {
        password:pw
    };
    return client.post('/userCertify/pwCheck', requestBody);
}

//로그인 상태 확인
export const check = () => client.post('/user/authCheck')

//로그아웃
export const logout = () => client.post("/logout")