import client from '../client';
import qs from "qs";

export const getCustomerCaseCate = () => {
    return client.get(`/customer_case/customer_type`)
}

export const subscribeCheck = () => {
    return client.get(`/customer_case/subscribe_check`)
}

export const getCustomerCase = (idx) => {
    return client.get(`/customer_case/selectOne?idx=${idx}`)
}
export const getCustomerCaseList = ({page,cate}) => {
    const queryString = qs.stringify({
        page, cate
    });

    return client.get(`/customer_case?${queryString}`)
}


export const addCustomerCase = (form) => {
    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    return client.post(`/customer_case`,form,config)
}

export const editCustomerCase = (form) => {

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    return client.post(`/customer_case/edit`,form,config)
}

export const deleteCustomerCase = (idx) => {
    return client.delete(`/customer_case/${idx}`)
}
// export const updateCustomUserCount = (data) => {
//     return client.put(`/admin/customUserCount`,data)
// }