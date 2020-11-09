import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../../lib/createRequestSaga";
import * as coloringAPI from '../../../lib/api/admin/coloring';

const [GET_COLORING_APPLY_LIST, GET_COLORING_APPLY_LIST_SUCCESS, GET_COLORING_APPLY_LIST_FAILURE] = createRequestActionTypes('coloring/GET_COLORING_APPLY_LIST')
const [GET_COLORING_APPLY, GET_COLORING_APPLY_SUCCESS, GET_COLORING_APPLY_FAILURE] = createRequestActionTypes('coloring/GET_COLORING_APPLY')
const [REGISTER_COLORING_APPLY, REGISTER_COLORING_APPLY_SUCCESS, REGISTER_COLORING_APPLY_FAILURE] = createRequestActionTypes('coloring/REGISTER_COLORING_APPLY')
const [UPDATE_COLORING_APPLY,UPDATE_COLORING_APPLY_SUCCESS, UPDATE_COLORING_APPLY_FAILURE] = createRequestActionTypes('coloring/UPDATE_COLORING_APPLY')
const [SOUND_UPLOAD,SOUND_UPLOAD_SUCCESS, SOUND_UPLOAD_FAILURE] = createRequestActionTypes('coloring/SOUND_UPLOAD')
// const [DELETE_CUSTOMER_CASE,DELETE_CUSTOMER_CASE_SUCCESS, DELETE_CUSTOMER_CASE_FAILURE] = createRequestActionTypes('coloring/DELETE_CUSTOMER_CASE')
// const [UPDATE_CUSTOM_USER_COUNT,UPDATE_CUSTOM_USER_COUNT_SUCCESS, UPDATE_CUSTOM_USER_COUNT_FAILURE] = createRequestActionTypes('adminCustomUserCount/UPDATE_CUSTOM_USER_COUNT')
// const CHANGE_FIELD  = 'customerCase/CHANGE_FIELD';
const INITIALIZE  = 'coloring/INITIALIZE';

// export const getColoringApplyList = createAction(GET_COLORING_APPLY_LIST)
export const updateColoringApply = createAction(UPDATE_COLORING_APPLY, ({idx, status}) => ({idx, status}))
export const getColoringApplyList = createAction(GET_COLORING_APPLY_LIST, (page) => (page))
export const soundUpload = createAction(SOUND_UPLOAD, (form) => (form))
export const registerColoringApply = createAction(REGISTER_COLORING_APPLY, (form) => (form))
// export const editCustomerCase = createAction(EDIT_CUSTOMER_CASE, (form) => (form))
// export const deleteCustomerCase = createAction(DELETE_CUSTOMER_CASE, (idx) => (idx))
// export const updateCustomUserCount = createAction(UPDATE_CUSTOM_USER_COUNT, (data) => (data))

// export const changeField = createAction(
//     CHANGE_FIELD,
//     ({key, value}) => ({
//         key,
//         value
//     }),
// );

export const initialize = createAction(INITIALIZE);

const getColoringApplyListSaga = createRequestSaga(GET_COLORING_APPLY_LIST, coloringAPI.getColoringApplyList);
const soundUploadSaga = createRequestSaga(SOUND_UPLOAD, coloringAPI.soundUpload);
const registerColoringApplySaga = createRequestSaga(REGISTER_COLORING_APPLY, coloringAPI.registerColoringApply);
const updateColoringApplySaga = createRequestSaga(UPDATE_COLORING_APPLY, coloringAPI.updateColoringApply);
// const addCustomerCaseSaga = createRequestSaga(ADD_CUSTOMER_CASE, customerCaseAPI.addCustomerCase);
// const editCustomerCaseSaga = createRequestSaga(EDIT_CUSTOMER_CASE, customerCaseAPI.editCustomerCase);
// const deleteCustomerCaseSaga = createRequestSaga(DELETE_CUSTOMER_CASE, customerCaseAPI.deleteCustomerCase);

// const updateCustomUserCountSaga = createRequestSaga(UPDATE_CUSTOM_USER_COUNT, customUserCountAPI.updateCustomUserCount);

export function* coloringSaga(){
    yield takeLatest(GET_COLORING_APPLY_LIST, getColoringApplyListSaga);
    yield takeLatest(SOUND_UPLOAD, soundUploadSaga);
    yield takeLatest(REGISTER_COLORING_APPLY, registerColoringApplySaga);
    yield takeLatest(UPDATE_COLORING_APPLY, updateColoringApplySaga);
}

const initialState = {
    coloring:{
        list:null,
        page:null,
    },
    uploadResult:{
        result:false,
        error:null
    },
    updateResult:false,
    registerResult:false,
    error:null
};

const coloring = handleActions(
    {
        [GET_COLORING_APPLY_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.coloring = data
            }),
        [GET_COLORING_APPLY_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.coloring = initialState.coloring
            }),
        [REGISTER_COLORING_APPLY_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.registerResult = true
                draft.error = null
            }),
        [REGISTER_COLORING_APPLY_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.registerResult = false
                draft.error = error.response.data
            }),
        [UPDATE_COLORING_APPLY_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.updateResult = true
            }),
        [UPDATE_COLORING_APPLY_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.updateResult = false
                draft.error = error.response.data
            }),
        [SOUND_UPLOAD_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.uploadResult.result = true
                draft.uploadResult.error = null
            }),
        [SOUND_UPLOAD_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.uploadResult.result = false
                draft.uploadResult.error = error.response.data
            }),
        [INITIALIZE]: (state) => ({
            ...initialState
        }),

    },
    initialState
);
export default coloring;