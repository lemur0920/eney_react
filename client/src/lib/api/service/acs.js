import client from '../client';
import qs from "qs";

//발신번호 체크
export const numberCheck = (num) => {
    return client.post(`/service/acs/number_check`,num)
}


//인증번호 인증
export const acsSubmit = (num) => {
    return client.post(`/service/acs/acsSubmit`,num)
}


//
export const checkAcsNumber = (data) => {
    return client.post(`/service/acs/checkAcsNumber`,data)
}