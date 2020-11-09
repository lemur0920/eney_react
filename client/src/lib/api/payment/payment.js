import client from '../client';
import qs from "qs";

export const epointItemInfo = () => {
    return client.get('/payment/epoint/item')
}

export const getPayRequestInfo = (info) => {
    return client.post(`/payment/${info.payWay}/pay`,info)
}

export const paymentService = (info) => {
    const body = {
        ORDER_ID:"214123"
    }

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };
    const k = info.payRequest.keys()

    return client.post(`/payment/epoint/return`,info.payRequest, config)
}