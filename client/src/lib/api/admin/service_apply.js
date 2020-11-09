import client from '../client';
import qs from "qs";

export const getCloudServiceApplyList = (page) => {

    return client.get(`/admin/cloud/service_apply?page=${page}`)
}

export const getServiceApplyList = ({page,serviceType}) => {

    return client.get(`/admin/service_apply/${serviceType}?page=${page}`)
}

export const editServiceApplyStatus = (data) => {

    let body = {
        idx:data.idx,
        pay_state:data.payStatus,
        status:data.useStatus,

    }

    let formData = new FormData();
    formData.append("idx", data.idx);
    formData.append("pay_state", data.payStatus);
    formData.append("status", data.useStatus);

    const config = {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };
    return client.put(`/admin/service_apply/${data.serviceType}`,body)
}

export const editCloudServiceApplyStatus = ({serviceType,payStatus,useStatus}) => {
    const data = {
        state: useStatus,
        pay_state:payStatus
    }

    return client.post(`/admin/service_apply/${serviceType}`,data)
}


export const createInstance = (idx) => {
    const data = {
        applyIdx:idx,
    }

    return client.post(`/service/cloud/instance_create`,idx)
}

export const removeInstance = (idx) => {
    return client.post(`/service/cloud/instance_remove`,idx)
}

export const getInstanceList = () => {
    return client.get(`/service/cloud/instance`)
}

export const instanceStart = (id) => {
    return client.post(`/service/cloud/instance_start`,id)
}

export const instanceStop = (id) => {
    return client.post(`/service/cloud/instance_stop`,id)
}

export const instanceReStart = (id) => {
    return client.post(`/service/cloud/instance_restart`,id)
}