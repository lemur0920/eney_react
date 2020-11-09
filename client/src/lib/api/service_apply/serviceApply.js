import client from '../client';
import qs from "qs";


export const getItemList = (cate) => {
    return client.get(`/serviceApply/item/${cate}`)
}

export const registPatchcall = (service) => {

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };
    // const requestBody = {
    //     userId:userId,
    //     newPw:newPw,
    //     newPwCheck:newPwCheck,
    // };
    return client.post(`/serviceApply/patchcall/regist_patchcall`,service,config)
}