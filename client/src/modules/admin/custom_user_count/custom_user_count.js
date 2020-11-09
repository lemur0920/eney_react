import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../../lib/createRequestSaga";
import * as customUserCountAPI from '../../../lib/api/admin/custom_user_count';


const [GET_CUSTOM_USER_COUNT,GET_CUSTOM_USER_COUNT_SUCCESS, GET_CUSTOM_USER_COUNT_FAILURE] = createRequestActionTypes('adminCustomUserCount/GET_CUSTOM_USER_COUNT')
const [UPDATE_CUSTOM_USER_COUNT,UPDATE_CUSTOM_USER_COUNT_SUCCESS, UPDATE_CUSTOM_USER_COUNT_FAILURE] = createRequestActionTypes('adminCustomUserCount/UPDATE_CUSTOM_USER_COUNT')
const CHANGE_FIELD  = 'adminCustomUserCount/CHANGE_FIELD';
const INITIALIZE  = 'adminCustomUserCount/INITIALIZE';

export const getCustomUserCount = createAction(GET_CUSTOM_USER_COUNT)
export const updateCustomUserCount = createAction(UPDATE_CUSTOM_USER_COUNT, (data) => (data))

export const changeField = createAction(
    CHANGE_FIELD,
    ({key, value}) => ({
        key,
        value
    }),
);

export const initialize = createAction(INITIALIZE, from => from);

const getCustomUserCountSaga = createRequestSaga(GET_CUSTOM_USER_COUNT, customUserCountAPI.getCustomUserCount);
const updateCustomUserCountSaga = createRequestSaga(UPDATE_CUSTOM_USER_COUNT, customUserCountAPI.updateCustomUserCount);

export function* customUserCountSaga(){
    yield takeLatest(GET_CUSTOM_USER_COUNT, getCustomUserCountSaga);
    yield takeLatest(UPDATE_CUSTOM_USER_COUNT, updateCustomUserCountSaga);
}

const initialState = {
    count:{
        web_log:0,
        personal:0,
        corp:0,
        total:0,
        api:0,
        visit:0
    },
    updateResult:false,
    error:null
};

const custom_user_count = handleActions(
    {
        [GET_CUSTOM_USER_COUNT_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.count.web_log = data.customCount.web_log
                draft.count.personal = data.customCount.personal
                draft.count.corp = data.customCount.corp
                draft.count.api = data.customCount.api
                draft.count.visit = data.customCount.visit
                draft.count.total = data.total
            }),
        [GET_CUSTOM_USER_COUNT_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.count = {
                    web_log:0,
                    personal:0,
                    corp:0,
                    total:0,
                }
            }),
        [UPDATE_CUSTOM_USER_COUNT_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.updateResult = true
                draft.error = null
            }),
        [UPDATE_CUSTOM_USER_COUNT_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.updateResult = false
                draft.error = error.response.data

            }),
        [CHANGE_FIELD]: (state, {payload: {key, value}}) =>
            produce(state, draft => {
                draft.count[key] = value;
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form],
        }),

    },
    initialState
);
export default custom_user_count;