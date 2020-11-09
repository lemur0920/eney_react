
import client from '../client';
import qs from "qs";

//customer info
export const getCustomerInfo = ({idx}) => {
    const queryString = qs.stringify({
        idx
    });
    return client.get(`/service/customer/info?${queryString}`)
}

//customer info 수정
export const updateCustomerInfo = (form) => {

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    return client.put(`/service/customer/info`,form, config)
}

//customer info 추가
export const addCustomer = (data) => {

    return client.post(`/service/customer/info`,data)
}

//customer 리스트
export const getCustomerList = ({page,cate, search_filed, search}) => {
    const queryString = qs.stringify({
        page, cate,search_filed, search
    });

    return client.get(`/service/customer/management?${queryString}`)
}

export const getGroupByCustomerList = ({group_idx}) => {
    const queryString = qs.stringify({
        group_idx
    });

    return client.get(`/service/customer/management/group_member?${queryString}`)
}

//customer evenet 리스트
export const getCustomerEventList = ({page,customer_idx}) => {
    const queryString = qs.stringify({
        page, customer_idx
    });

    return client.get(`/service/customer/management/event?${queryString}`)
}

//customer Group 리스트
export const getCustomerGroupList = ({page,cate, search_filed, search}) => {
    const queryString = qs.stringify({
        page, cate,search_filed, search
    });

    return client.get(`/service/customer/group?${queryString}`)
}

//customer Group 삭제
export const deleteCustomerGroup = (idx) => {

    return client.delete(`/service/customer/group?idx=${idx}`)
}

//customer Group 수정
export const updateCustomerGroup = (form) => {

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    return client.put(`/service/customer/group`,form, config)
}

export const addCustomerGroup = (form) => {

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };


    return client.post(`/service/customer/group`,form, config)
}


export const updateCustomerGroupMember = (data) => {

    let formData = new FormData();
    formData.append("idx", data.group_idx);
    formData.append("use_customer_idx_list", data.use_customer_idx_list);
    formData.append("unused_customer_idx_list", data.unused_customer_idx_list);

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    return client.put(`/service/customer/management/group`,formData, config)
}

//고객 관리 일괄 등록 샘플
export const customerBulkUploadSampleDownload = () => {
    return client.get(`/service/customer/sample_download`,{ responseType: 'arraybuffer' })
}

//번호 관리 파일 업로드
export const customerFileUpload = (excelFile) => {

    let formData = new FormData();
    formData.append("file", excelFile);

    const config = {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };

    return client.post(`/service/customer/excel_upload`,formData,config)
}