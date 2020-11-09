import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../../lib/createRequestSaga";
import * as couponAPI from '../../../lib/api/admin/coupon';


const [COUPON_LIST,COUPON_LIST_SUCCESS, COUPON_LIST_FAILURE] = createRequestActionTypes('adminCoupon/COUPON_LIST')
const [COUPON_CREATE,COUPON_CREATE_SUCCESS, COUPON_CREATE_FAILURE] = createRequestActionTypes('adminCoupon/COUPON_CREATE')
const INITIALIZE  = 'adminCoupon/INITIALIZE';

export const couponList = createAction(COUPON_LIST, ({page}) => ({
    page
}))

export const couponCreate = createAction(COUPON_CREATE, ({point, count}) => ({
    point,count
}))
export const initialize = createAction(INITIALIZE, from => from);
const couponListSaga = createRequestSaga(COUPON_LIST, couponAPI.couponList);
const couponCreateSaga = createRequestSaga(COUPON_CREATE, couponAPI.couponCreate);

export function* couponSaga(){
    yield takeLatest(COUPON_LIST, couponListSaga);
    yield takeLatest(COUPON_CREATE, couponCreateSaga);
}

const initialState = {
    coupon:{
        list:null,
        page:null,
    },
    create:{
        isSuccess:false,
        error:null
    },
    error:null
};

const coupon = handleActions(
    {
        [COUPON_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.coupon.list = data.list
                draft.coupon.page = data.page
            }),
        [COUPON_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.coupon = initialState.coupon
                draft.error = error.response.data
            }),

        [COUPON_CREATE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.create.isSuccess = true
                draft.create.error = null
            }),
        [COUPON_CREATE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.create.isSuccess = false
                draft.create.error = error.response.data
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form],
        }),

    },
    initialState
);
export default coupon;