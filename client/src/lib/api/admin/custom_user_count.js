import client from '../client';

export const getCustomUserCount = () => {
    return client.get(`/customUserCount`)
}


export const updateCustomUserCount = (data) => {
    return client.put(`/admin/customUserCount`,data)
}