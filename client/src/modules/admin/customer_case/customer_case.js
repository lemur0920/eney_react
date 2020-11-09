import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../../lib/createRequestSaga";
import * as customerCaseAPI from '../../../lib/api/admin/customer_case';


const [GET_CUSTOMER_CASE_CATE,GET_CUSTOMER_CASE_CATE_SUCCESS, GET_CUSTOMER_CASE_CATE_FAILURE] = createRequestActionTypes('customerCase/GET_CUSTOMER_CASE_CATE')
const [SUBSCRIBE_CHECK,SUBSCRIBE_CHECK_SUCCESS, SUBSCRIBE_CHECK_FAILURE] = createRequestActionTypes('customerCase/SUBSCRIBE_CHECK')


const [GET_CUSTOMER_CASE_LIST,GET_CUSTOMER_CASE_LIST_SUCCESS, GET_CUSTOMER_CASE_LIST_FAILURE] = createRequestActionTypes('customerCase/GET_CUSTOMER_CASE_LIST')
const [GET_CUSTOMER_CASE,GET_CUSTOMER_CASE_SUCCESS, GET_CUSTOMER_CASE_FAILURE] = createRequestActionTypes('customerCase/GET_CUSTOMER_CASE')
const [ADD_CUSTOMER_CASE,ADD_CUSTOMER_CASE_SUCCESS, ADD_CUSTOMER_CASE_FAILURE] = createRequestActionTypes('customerCase/ADD_CUSTOMER_CASE')
const [EDIT_CUSTOMER_CASE,EDIT_CUSTOMER_CASE_SUCCESS, EDIT_CUSTOMER_CASE_FAILURE] = createRequestActionTypes('customerCase/EDIT_CUSTOMER_CASE')
const [DELETE_CUSTOMER_CASE,DELETE_CUSTOMER_CASE_SUCCESS, DELETE_CUSTOMER_CASE_FAILURE] = createRequestActionTypes('customerCase/DELETE_CUSTOMER_CASE')
// const [UPDATE_CUSTOM_USER_COUNT,UPDATE_CUSTOM_USER_COUNT_SUCCESS, UPDATE_CUSTOM_USER_COUNT_FAILURE] = createRequestActionTypes('adminCustomUserCount/UPDATE_CUSTOM_USER_COUNT')
const CHANGE_FIELD  = 'customerCase/CHANGE_FIELD';
const INITIALIZE  = 'customerCase/INITIALIZE';

export const subscribeCheck = createAction(SUBSCRIBE_CHECK)
export const getCustomerCaseCate = createAction(GET_CUSTOMER_CASE_CATE)
export const getCustomerCase = createAction(GET_CUSTOMER_CASE, (idx) => (idx))
export const getCustomerCaseList = createAction(GET_CUSTOMER_CASE_LIST, ({page, cate}) => ({page,cate}))
export const addCustomerCase = createAction(ADD_CUSTOMER_CASE, (form) => (form))
export const editCustomerCase = createAction(EDIT_CUSTOMER_CASE, (form) => (form))
export const deleteCustomerCase = createAction(DELETE_CUSTOMER_CASE, (idx) => (idx))
// export const updateCustomUserCount = createAction(UPDATE_CUSTOM_USER_COUNT, (data) => (data))

// export const changeField = createAction(
//     CHANGE_FIELD,
//     ({key, value}) => ({
//         key,
//         value
//     }),
// );

export const initialize = createAction(INITIALIZE, from => from); 

const subscribeCheckSage = createRequestSaga(SUBSCRIBE_CHECK, customerCaseAPI.subscribeCheck);
const getCustomerCaseCateSage = createRequestSaga(GET_CUSTOMER_CASE_CATE, customerCaseAPI.getCustomerCaseCate);
const getCustomerCaseSage = createRequestSaga(GET_CUSTOMER_CASE, customerCaseAPI.getCustomerCase);
const getCustomerCaseListSage = createRequestSaga(GET_CUSTOMER_CASE_LIST, customerCaseAPI.getCustomerCaseList);
const addCustomerCaseSaga = createRequestSaga(ADD_CUSTOMER_CASE, customerCaseAPI.addCustomerCase);
const editCustomerCaseSaga = createRequestSaga(EDIT_CUSTOMER_CASE, customerCaseAPI.editCustomerCase);
const deleteCustomerCaseSaga = createRequestSaga(DELETE_CUSTOMER_CASE, customerCaseAPI.deleteCustomerCase);

// const updateCustomUserCountSaga = createRequestSaga(UPDATE_CUSTOM_USER_COUNT, customUserCountAPI.updateCustomUserCount);

export function* customerCaseSaga(){
    yield takeLatest(SUBSCRIBE_CHECK, subscribeCheckSage);
    yield takeLatest(GET_CUSTOMER_CASE_CATE, getCustomerCaseCateSage);
    yield takeLatest(GET_CUSTOMER_CASE, getCustomerCaseSage);
    yield takeLatest(GET_CUSTOMER_CASE_LIST, getCustomerCaseListSage);
    yield takeLatest(ADD_CUSTOMER_CASE, addCustomerCaseSaga);
    yield takeLatest(EDIT_CUSTOMER_CASE, editCustomerCaseSaga);
    yield takeLatest(DELETE_CUSTOMER_CASE, deleteCustomerCaseSaga);
}

const initialState = {
    subscribeCheck:null,
    customerCaseCate:null,
    customerCase:{
        item:null,
        list:null,
        page:null,
    },
    updateResult:false,
    addResult:false,
    editResult:false,
    deleteResult:false,
    error:null
};

const customer_case = handleActions(
    {
        [SUBSCRIBE_CHECK_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.subscribeCheck = true
            }),
        [SUBSCRIBE_CHECK_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.subscribeCheck = false
            }),
        [GET_CUSTOMER_CASE_CATE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.customerCaseCate = data
            }),
        [GET_CUSTOMER_CASE_CATE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.customerCaseCate = []
            }),
        [GET_CUSTOMER_CASE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.customerCase.item = data
            }),
        [GET_CUSTOMER_CASE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.customerCase = initialState.customerCase
            }),
        [GET_CUSTOMER_CASE_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.customerCase.list = data.list
                draft.customerCase.page = data.page
            }),
        [GET_CUSTOMER_CASE_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.customerCase = initialState.customerCase
            }),
        [ADD_CUSTOMER_CASE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.addResult = true
                draft.error = null
            }),
        [ADD_CUSTOMER_CASE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.addResult = false
                draft.error = error.response.data
            }),
        [EDIT_CUSTOMER_CASE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.editResult = true
                draft.error = null
            }),
        [EDIT_CUSTOMER_CASE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.editResult = false
                draft.error = error.response.data
            }),
        [DELETE_CUSTOMER_CASE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.deleteResult = true
                draft.error = null
            }),
        [DELETE_CUSTOMER_CASE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.deleteResult = false
                draft.error = error.response.data
            }),
        // [UPDATE_CUSTOM_USER_COUNT_SUCCESS]:(state, {payload: data}) =>
        //     produce(state, draft => {
        //         draft.updateResult = true
        //         draft.error = null
        //     }),
        // [UPDATE_CUSTOM_USER_COUNT_FAILURE]:(state, {payload: error}) =>
        //     produce(state, draft => {
        //         draft.updateResult = false
        //         draft.error = error.response.data
        //
        //     }),
        // [CHANGE_FIELD]: (state, {payload: {key, value}}) =>
        //     produce(state, draft => {
        //         draft.count[key] = value;
        //     }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),

    },
    initialState
);
export default customer_case;