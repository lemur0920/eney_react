import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import * as messageAPI from '../../lib/api/service/message';

const [GET_CUSTOMER_GROUP_LIST, GET_CUSTOMER_GROUP_LIST_SUCCESS, GET_CUSTOMER_GROUP_LIST_FAILURE] = createRequestActionTypes('message/GET_CUSTOMER_GROUP_LIST');
const [GET_CUSTOMER_GROUP_LIST_COUNT, GET_CUSTOMER_GROUP_LIST_COUNT_SUCCESS, GET_CUSTOMER_GROUP_LIST_COUNT_FAILURE] = createRequestActionTypes('message/GET_CUSTOMER_GROUP_LIST_COUNT');
const [INSERT_SMS, INSERT_SMS_SUCCESS, INSERT_SMS_FAILURE] = createRequestActionTypes('message/INSERT_SMS');
const CHANGE_CUSTOMER_GROUP = 'message/CHANGE_CUSTOMER_GROUP';
const CHANGE_CALLBACK = 'message/CHANGE_CALLBACK';
const [GET_RESULT_TABLE_LIST, GET_RESULT_TABLE_LIST_SUCCESS, GET_RESULT_TABLE_LIST_FAILURE] = createRequestActionTypes('message/GET_RESULT_TABLE_LIST');

const INITIALIZE = 'template/INITIALIZE';

//CID 리스트
export const getCustomerGroupList = createAction(GET_CUSTOMER_GROUP_LIST);
export const getCustomerGroupListCount = createAction(GET_CUSTOMER_GROUP_LIST_COUNT, (group_idx) => (group_idx));

export const insertSMS = createAction(INSERT_SMS, ({subject, text, msg_type, callback, group_idx, request_time, fileloc1}) => ({
    subject,
    text,
    msg_type,
    callback,
    group_idx,
    request_time,
    fileloc1
}));

// 결과테이블 리스트 조회

export const message_initialize = createAction(INITIALIZE, from => from);

export const editCustomerGroup = createAction(
    CHANGE_CUSTOMER_GROUP,
    (value) => (value)
);

export const editCallback = createAction(
    CHANGE_CALLBACK,
    (value) => (value)
);


const getCustomerGroupListSaga = createRequestSaga(GET_CUSTOMER_GROUP_LIST, messageAPI.getCustomerGroupList);
const getCustomerGroupListCountSaga = createRequestSaga(GET_CUSTOMER_GROUP_LIST_COUNT, messageAPI.getCustomerGroupListCount);
const insertSMSSaga = createRequestSaga(INSERT_SMS, messageAPI.insertSMS);

export function* messageSaga() {
    yield takeLatest(GET_CUSTOMER_GROUP_LIST, getCustomerGroupListSaga);
    yield takeLatest(GET_CUSTOMER_GROUP_LIST_COUNT, getCustomerGroupListCountSaga);
    yield takeLatest(INSERT_SMS, insertSMSSaga);
}

const initialState = {
    resultList: null,
    resultTableList: null,
    cGroupList: null,
    cGroupCustomerList: null,
    selectedCustomerGroup: null,
    selectedCallback: null,
    cGroupCount: 0,
    error: null,
    sendResult : null,

};

const message = handleActions(
    {
        [GET_CUSTOMER_GROUP_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.cGroupList = data
            }),
        [GET_CUSTOMER_GROUP_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.cGroupList = initialState.cGroupList
            }),
        [GET_CUSTOMER_GROUP_LIST_COUNT_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.cGroupCount = data
            }),
        [GET_CUSTOMER_GROUP_LIST_COUNT_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.cGroupCount = initialState.cGroupCount
            }),
        [INSERT_SMS_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.sendResult = data
            }),
        [INSERT_SMS_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.sendResult = initialState.sendResult
            }),
        [CHANGE_CUSTOMER_GROUP]: (state, {payload: value}) =>
            produce(state, draft => {
                draft.selectedCustomerGroup = value
            }),
        [CHANGE_CALLBACK]: (state, {payload: value}) =>
            produce(state, draft => {
                draft.selectedCallback = value
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),

    },
    initialState
    )
;
export default message;