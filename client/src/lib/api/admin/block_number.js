import client from '../client';
import qs from "qs";

export const getBlockNumberList = ({page}) => {
    const queryString = qs.stringify({
        page
    });
    console.log(`/service/message/blockNumber?page=${page}`);
    return client.get(`/service/message/blockNumber?page=${page}`)
    // return client.get(`/admin/service_apply/${serviceType}?page=${page}`)


}


export const updateCustomUserCount = (data) => {
    return client.put(`/admin/customUserCount`, data)
}