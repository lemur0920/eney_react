import client from '../client';
import qs from "qs";
import {createAction} from "redux-actions";

export const getColoringApplyList = (page) => {
    // const queryString = qs.stringify(
    //     page
    // );

    return client.get(`/admin/coloring?page=${page}`)
}

export const updateColoringApply = ({id,status}) => {
    const data = {
        id:id,
        status:status
    }
    return client.put(`/admin/coloring`,data)
}

export const soundUpload = (form) => {

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    return client.post(`/admin/upload/sound`,form, config)
}

export const registerColoringApply = (form) => {
    return client.post(`/serviceApply/coloring`,form)
}

// export const deleteCustomerCase = (idx) => {
//     return client.delete(`/customer_case/${idx}`)
// }

// export const updateColoringApply = createAction(UPDATE_COLORING_APPLY, ({idx, status}) => ({idx, status}))
// export const getColoringApplyList = createAction(GET_COLORING_APPLY_LIST, ({page, cate}) => ({page,cate}))
// export const registerColoringApply = createAction(REGISTER_COLORING_APPLY, (form) => (form))