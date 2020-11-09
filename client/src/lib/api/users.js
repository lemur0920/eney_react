import qs from 'qs';
import client from './client';

export const userList = ({page, cate, search_filed, search}) => {
    const queryString = qs.stringify({
        page,cate,search_filed,search
    });
    return client.get(`/admin/users?${queryString}`)
}

export const getUserInfo = ({idx}) => {
    const queryString = qs.stringify({
        idx
    });
    return client.get(`/admin/user/info?${queryString}`)
}

export const updateUserInfo = (updateUserInfo) => {
    return client.put(`/admin/user/info`,updateUserInfo)
}