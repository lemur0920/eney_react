import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../../lib/createRequestSaga";
import * as serviceApplyAPI from '../../../lib/api/admin/service_apply';


const [GET_CLOUD_SERVICE_APPLY_LIST,GET_CLOUD_SERVICE_APPLY_LIST_SUCCESS, GET_CLOUD_SERVICE_APPLY_LIST_FAILURE] = createRequestActionTypes('adminCloud/GET_CLOUD_SERVICE_APPLY_LIST')
const [EDIT_CLOUD_SERVICE_APPLY_STATUS,EDIT_CLOUD_SERVICE_APPLY_STATUS_SUCCESS, EDIT_CLOUD_SERVICE_APPLY_STATUS_FAILURE] = createRequestActionTypes('adminCloud/EDIT_CLOUD_SERVICE_APPLY_STATUS')
const [CREATE_INSTANCE,CREATE_INSTANCE_SUCCESS, CREATE_INSTANCE_FAILURE] = createRequestActionTypes('adminCloud/CREATE_INSTANCE')
const [REMOVE_INSTANCE,REMOVE_INSTANCE_SUCCESS, REMOVE_INSTANCE_FAILURE] = createRequestActionTypes('adminCloud/REMOVE_INSTANCE')
const [GET_INSTANCE_LIST,GET_INSTANCE_LIST_SUCCESS, GET_INSTANCE_LIST_FAILURE] = createRequestActionTypes('adminCloud/GET_INSTANCE_LIST')
const [INSTANCE_START,INSTANCE_START_SUCCESS, INSTANCE_START_FAILURE] = createRequestActionTypes('adminCloud/INSTANCE_START')
const [INSTANCE_STOP,INSTANCE_STOP_SUCCESS, INSTANCE_STOP_FAILURE] = createRequestActionTypes('adminCloud/INSTANCE_STOP')
const [INSTANCE_RESTART,INSTANCE_RESTART_SUCCESS, INSTANCE_RESTART_FAILURE] = createRequestActionTypes('adminCloud/INSTANCE_RESTART')
const CHANGE_FIELD  = 'adminCloud/CHANGE_FIELD';
const INITIALIZE  = 'adminCloud/INITIALIZE';

export const getCloudServiceApplyList = createAction(GET_CLOUD_SERVICE_APPLY_LIST, (page) => (page))
export const editCloudServiceApplyStatus = createAction(EDIT_CLOUD_SERVICE_APPLY_STATUS, (form) => (form))
export const createInstance = createAction(CREATE_INSTANCE, (idx) => (idx))
export const removeInstance = createAction(REMOVE_INSTANCE, (idx) => (idx))
export const getInstanceList = createAction(GET_INSTANCE_LIST)
export const instanceStart = createAction(INSTANCE_START, (id) => (id))
export const instanceStop= createAction(INSTANCE_STOP, (id) => (id))
export const instanceRestart= createAction(INSTANCE_RESTART, (id) => (id))
// export const updateCustomUserCount = createAction(UPDATE_CUSTOM_USER_COUNT, (data) => (data))

// export const changeField = createAction(
//     CHANGE_FIELD,
//     ({key, value}) => ({
//         key,
//         value
//     }),
// );

export const initialize = createAction(INITIALIZE, from => from);

const getCloudServiceApplyListSaga = createRequestSaga(GET_CLOUD_SERVICE_APPLY_LIST, serviceApplyAPI.getCloudServiceApplyList);
const editCloudServiceApplyStatusSaga = createRequestSaga(EDIT_CLOUD_SERVICE_APPLY_STATUS, serviceApplyAPI.editCloudServiceApplyStatus);
const createInstanceSaga = createRequestSaga(CREATE_INSTANCE, serviceApplyAPI.createInstance);
const removeInstanceSaga = createRequestSaga(REMOVE_INSTANCE, serviceApplyAPI.removeInstance);
const getInstanceListSaga = createRequestSaga(GET_INSTANCE_LIST, serviceApplyAPI.getInstanceList);
const instanceStartSaga = createRequestSaga(INSTANCE_START, serviceApplyAPI.instanceStart);
const instanceStopSaga = createRequestSaga(INSTANCE_STOP, serviceApplyAPI.instanceStop);
const instanceRestartSaga = createRequestSaga(INSTANCE_RESTART, serviceApplyAPI.instanceReStart);

// const updateCustomUserCountSaga = createRequestSaga(UPDATE_CUSTOM_USER_COUNT, customUserCountAPI.updateCustomUserCount);

export function* cloudSaga(){
    yield takeLatest(GET_CLOUD_SERVICE_APPLY_LIST, getCloudServiceApplyListSaga);
    yield takeLatest(EDIT_CLOUD_SERVICE_APPLY_STATUS, editCloudServiceApplyStatusSaga);
    yield takeLatest(CREATE_INSTANCE, createInstanceSaga);
    yield takeLatest(REMOVE_INSTANCE, removeInstanceSaga);
    yield takeLatest(GET_INSTANCE_LIST, getInstanceListSaga);
    yield takeLatest(INSTANCE_START, instanceStartSaga);
    yield takeLatest(INSTANCE_STOP, instanceStopSaga);
    yield takeLatest(INSTANCE_RESTART, instanceRestartSaga);

}

const initialState = {
    cloud:{
        list:null,
        page:null
    },
    instance:{
        list:null
    },
    createInstanceResult:false,
    createInstanceError:null,
    removeInstanceResult:false,
    removeInstanceError:null,
    manage:{
        editResult:false,
        editError:null,
        startResult:false,
        startError:null,
        stopResult:false,
        stopError:null,
        restartResult:false,
        restartError:null,
    },
    error:null
};

const cloud = handleActions(
    {
        [GET_CLOUD_SERVICE_APPLY_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.cloud.list = data.list
                draft.cloud.page = data.page
            }),
        [GET_CLOUD_SERVICE_APPLY_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.cloud = initialState.cloud
                draft.error = error.response.data
            }),
        [EDIT_CLOUD_SERVICE_APPLY_STATUS_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.editResult = true
                draft.editError = null
            }),
        [EDIT_CLOUD_SERVICE_APPLY_STATUS_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.editResult = false
                draft.editError = error.response.data
            }),
        [CREATE_INSTANCE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.createInstanceResult = true
                draft.createInstanceError = null
            }),
        [CREATE_INSTANCE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.createInstanceResult = false
                draft.createInstanceError = error.response.data
            }),
        [REMOVE_INSTANCE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.removeInstanceResult = true
                draft.removeInstanceError = null
            }),
        [REMOVE_INSTANCE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.removeInstanceResult = false
                draft.removeInstanceError = error.response.data
            }),
        [GET_INSTANCE_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.instance.list = data
                draft.error = null
            }),
        [GET_INSTANCE_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.error = error.response.data
            }),
        [INSTANCE_START_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.manage.startResult = true
                draft.manage.startError = null
            }),
        [INSTANCE_START_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.manage.startResult = false
                draft.manage.startError = error.response.data
            }),
        [INSTANCE_STOP_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.manage.stopResult = true
                draft.manage.stopError = null
            }),
        [INSTANCE_STOP_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.manage.stopResult = false
                draft.manage.stopError = error.response.data
            }),
        [INSTANCE_RESTART_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.manage.restartResult= true
                draft.manage.restartError = null
            }),
        [INSTANCE_RESTART_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.manage.restartResult = false
                draft.manage.restarterror = error.response.data
            }),

        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),

    },
    initialState
);
export default cloud;