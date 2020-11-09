import client from '../client';
import qs from "qs";

//050 번호
export const getCustomerGroupList = () => {
    return client.get(`/service/customer/Allgroup`)
}
export const getCustomerGroupListCount = (group_idx) => {

    const queryString = qs.stringify({
        group_idx
    });
    return client.get(`/service/customer/management/group_member_count?${queryString}`);
}

export const insertSMS = ({subject, text, msg_type, callback, group_idx, request_time, fileloc1}) => {

    let formData = new FormData();
    formData.append("subject", subject);
    formData.append("text", text);
    formData.append("msg_type", msg_type);
    formData.append("callback", callback);
    formData.append("group_idx", group_idx);
    formData.append("request_time", request_time);


    if (fileloc1 != undefined) formData.append("fileloc1", fileloc1);

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    return client.post(`/service/message/send_sms`, formData, config)
}

