
import client from '../client';
import qs from "qs";

//view id리스트
export const getAnalyticsViewIdList = (page) => {
    return client.get(`/service/analytics/management?page=${page}`)
}

//view id 추가
export const insertAnalyticsViewId = (data) => {
    return client.post(`/service/analytics/management`,data)
}

//view id 수정
export const editAnalyticsViewId = (date) => {
    return client.put(`/service/analytics/management`,date)
}

//view id 삭제
export const deleteAnalyticsViewId = (idx) => {
    return client.delete(`/service/analytics/management/${idx}`)
}
