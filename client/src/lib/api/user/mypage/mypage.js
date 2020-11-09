import client from '../../client';
import qs from "qs";

export const myinfo = () => {
    return client.get("/user/mypage/myinfo")
}


export const updatePhone = (phone) => {
    return client.put("/user/mypage/phone",phone)
}


export const updateEmail = (email) => {
    return client.put("/user/mypage/email",email)
}

export const updateCompanyKind = (companyKind) => {
    return client.put("/user/mypage/companyKind",companyKind)
}

export const updatePw = ({currentPw, newPw, newPwCheck}) => {

    const requestBody = {
        currentPw,
        newPw,
        newPwCheck
    };

    return client.put("/user/mypage/password",requestBody)
}

export const updateAddress = (address) => {

    const requestBody = {
        address:address.address,
        addressDetail:address.addressDetail,
        postCode:address.postCode
    };

    return client.put("/user/mypage/address",requestBody)
}

export const myserviceList = ({myservicePage}) => {
    const queryString = qs.stringify({
        myservicePage
    });
    return client.get(`/user/mypage/serviceList?${queryString}`)
}

export const pointChargeLogList = ({chargePage,startDate, endDate}) => {
    const queryString = qs.stringify({
        chargePage, startDate, endDate
    });
    return client.get(`/user/mypage/logCharge?${queryString}`)
}

export const pointPaymentLogList = ({paymentPage, startDate, endDate}) => {
    const queryString = qs.stringify({
        paymentPage, startDate, endDate
    });
    return client.get(`/user/mypage/logPayment?${queryString}`)
}

export const uploadLicence = () => {
    return client.get(`/user/mypage/company/license`)
}

export const deleteLicence = (idx) => {
    return client.delete(`/user/mypage/company/license/${idx}`)
}


export const apiTokenList = ({page}) => {
    const queryString = qs.stringify({
        page
    });
    return client.get(`/api/service/token/list?${queryString}`)
}

export const genToken = (token) => {
    const requestBody = {
        service:token.service,
        allocation_ip : `${token.allocationIP_01}.${token.allocationIP_02}.${token.allocationIP_03}`
    };

    return client.post(`/api/service/token`, requestBody)
}


export const registerCoupon = (couponNum) => {

    const requestBody = {
        coupon_num:couponNum
    };
    return client.post(`/user/couponUse`, requestBody)
}