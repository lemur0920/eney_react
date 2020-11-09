import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../../lib/createRequestSaga";
import * as serviceApplyAPI from '../../../lib/api/admin/service_apply';


const [GET_SERVICE_APPLY_LIST,GET_SERVICE_APPLY_LIST_SUCCESS, GET_SERVICE_APPLY_LIST_FAILURE] = createRequestActionTypes('adminServiceApply/GET_SERVICE_APPLY_LIST')
const [EDIT_SERVICE_APPLY_STATUS,EDIT_SERVICE_APPLY_STATUS_SUCCESS, EDIT_SERVICE_APPLY_STATUS_FAILURE] = createRequestActionTypes('adminServiceApply/EDIT_SERVICE_APPLY_STATUS')
const INITIALIZE  = 'adminServiceApply/INITIALIZE';
const INITIALIZE_FORM = 'adminServiceApply/INITIALIZE_FORM';

export const getServiceApplyList = createAction(GET_SERVICE_APPLY_LIST, ({page,serviceType}) => ({page,serviceType}))
export const editServiceApplyStatus = createAction(EDIT_SERVICE_APPLY_STATUS, (data) => (data))
export const initialize = createAction(INITIALIZE, from => from);
export const initializeForm = createAction(INITIALIZE_FORM, form => form);

const getServiceApplyListSaga = createRequestSaga(GET_SERVICE_APPLY_LIST, serviceApplyAPI.getServiceApplyList);
const editServiceApplyStatusSaga = createRequestSaga(EDIT_SERVICE_APPLY_STATUS, serviceApplyAPI.editServiceApplyStatus);
// const editCloudServiceApplyStatusSaga = createRequestSaga(EDIT_SERVICE_APPLY_STATUS, serviceApplyAPI.editCloudServiceApplyStatus);

// const updateCustomUserCountSaga = createRequestSaga(UPDATE_CUSTOM_USER_COUNT, customUserCountAPI.updateCustomUserCount);

export function* serviceApplyManageSaga(){
    yield takeLatest(GET_SERVICE_APPLY_LIST, getServiceApplyListSaga);
    yield takeLatest(EDIT_SERVICE_APPLY_STATUS, editServiceApplyStatusSaga);

}

const initialState = {
    serviceApply:{
        list:null,
        page:null
    },
    editInfo:{
        result:false,
        error:null
    },
    error:null
};

const service_apply_manage = handleActions(
    {
        [GET_SERVICE_APPLY_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.serviceApply.list = data.list
                draft.serviceApply.page = data.page
            }),
        [GET_SERVICE_APPLY_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.serviceApply = initialState.serviceApply
                draft.error = error.response.data
            }),
        [EDIT_SERVICE_APPLY_STATUS_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.editInfo.result = true
                draft.editInfo.error = null
            }),
        [EDIT_SERVICE_APPLY_STATUS_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.editInfo.result = false
                draft.editInfo.error = error.response.data
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),
        [INITIALIZE_FORM]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form],
        }),

    },
    initialState
);
export default service_apply_manage;