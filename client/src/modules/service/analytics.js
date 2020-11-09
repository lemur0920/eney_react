import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import * as analyticsAPI from '../../lib/api/service/analytics';

const [GET_VIEW_ID_LIST,GET_VIEW_ID_LIST_SUCCESS, GET_VIEW_ID_LIST_FAILURE] = createRequestActionTypes('analytics/GET_VIEW_ID_LIST')
const [ADD_VIEW_ID,ADD_VIEW_ID_SUCCESS, ADD_VIEW_ID_FAILURE] = createRequestActionTypes('analytics/ADD_VIEW_ID')
const [EDIT_VIEW_ID,EDIT_VIEW_ID_SUCCESS, EDIT_VIEW_ID_FAILURE] = createRequestActionTypes('analytics/EDIT_VIEW_ID')
const [DELETE_VIEW_ID,DELETE_VIEW_ID_SUCCESS, DELETE_VIEW_ID_FAILURE] = createRequestActionTypes('analytics/DELETE_VIEW_ID')
const INITIALIZE  = 'analytics/INITIALIZE';

export const getViewIdList = createAction(GET_VIEW_ID_LIST, (page) => (page))
export const addViewId = createAction(ADD_VIEW_ID, (form) => (form))
export const editViewId = createAction(EDIT_VIEW_ID, (form) => (form))
export const deleteViewId = createAction(DELETE_VIEW_ID, (idx) => (idx))
// export const updateCustomUserCount = createAction(UPDATE_CUSTOM_USER_COUNT, (data) => (data))

// export const changeField = createAction(
//     CHANGE_FIELD,
//     ({key, value}) => ({
//         key,
//         value
//     }),
// );

export const initialize = createAction(INITIALIZE, from => from);

const getViewIdListSage = createRequestSaga(GET_VIEW_ID_LIST, analyticsAPI.getAnalyticsViewIdList);
const addViewIdSaga = createRequestSaga(ADD_VIEW_ID, analyticsAPI.insertAnalyticsViewId);
const editViewIdSaga = createRequestSaga(EDIT_VIEW_ID, analyticsAPI.editAnalyticsViewId);
const deleteViewIdSaga = createRequestSaga(DELETE_VIEW_ID, analyticsAPI.deleteAnalyticsViewId);

// const updateCustomUserCountSaga = createRequestSaga(UPDATE_CUSTOM_USER_COUNT, customUserCountAPI.updateCustomUserCount);

export function* analyticsSaga(){ 
    yield takeLatest(GET_VIEW_ID_LIST, getViewIdListSage);
    yield takeLatest(ADD_VIEW_ID, addViewIdSaga);
    yield takeLatest(EDIT_VIEW_ID, editViewIdSaga);
    yield takeLatest(DELETE_VIEW_ID, deleteViewIdSaga);
}

const initialState = {
    viewId:{
        page:null,
        list:null
    },
    addResult:false,
    editResult:false,
    deleteResult:false,
    error:null
};

const analytics = handleActions(
    {
        [GET_VIEW_ID_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.viewId.page = data.page
                draft.viewId.list = data.list
                draft.error = null
            }),
        [GET_VIEW_ID_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.viewId = initialize.viewId
                draft.error = error.response.data
            }),
        [ADD_VIEW_ID_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.addResult = true
                draft.error = null
            }),
        [ADD_VIEW_ID_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.addResult = false
                draft.error = error.response.data
            }),
        [EDIT_VIEW_ID_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.editResult = true
                draft.error = null
            }),
        [EDIT_VIEW_ID_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.editResult = false
                draft.error = error.response.data
            }),
        [DELETE_VIEW_ID_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.deleteResult = true
                draft.error = null
            }),
        [DELETE_VIEW_ID_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.deleteResult = false
                draft.error = error.response.data
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),

    },
    initialState
);
export default analytics;