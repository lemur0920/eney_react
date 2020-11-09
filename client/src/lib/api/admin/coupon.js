import client from '../client';
import qs from "qs";

export const couponList = ({page}) => {
    const queryString = qs.stringify({
        page
    });

    return client.get(`/admin/coupon?${queryString}`)
}


export const couponCreate = ({point, count}) => {
    return client.post(`/admin/couponCreate/${point}/${count}`)
}