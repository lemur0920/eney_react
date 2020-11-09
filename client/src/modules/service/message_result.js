import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import * as msg_resultAPI from '../../lib/api/service/message_result';

const [GET_RESULT_LIST, GET_RESULT_LIST_SUCCESS, GET_RESULT_LIST_FAILURE] = createRequestActionTypes('message/GET_RESULT_LIST');
const [GET_RESULT_TABLE_LIST, GET_RESULT_TABLE_LIST_SUCCESS, GET_RESULT_TABLE_LIST_FAILURE] = createRequestActionTypes('message/GET_RESULT_TABLE_LIST');

const INITIALIZE = 'template/INITIALIZE';

//CID 리스트
export const getTemplateList = createAction(GET_RESULT_LIST, ({page}) => ({page}));

// 결과테이블 리스트 조회
export const getResultTableList = createAction(GET_RESULT_TABLE_LIST);
export const getResultList = createAction(GET_RESULT_LIST, ({page, year, month, startYear, startMonth, startDay, endYear, endMonth, endDay}) => ({page, year, month, startYear, startMonth, startDay, endYear, endMonth, endDay}));

export const initialize = createAction(INITIALIZE, from => from);

const getResultListSaga = createRequestSaga(GET_RESULT_LIST, msg_resultAPI.getResultList);

export function* messageResultSaga() {
    yield takeLatest(GET_RESULT_LIST, getResultListSaga);
}

const initialState = {
    resultList: null,
    resultTableList: null,
    resultSearch: {},

    error: null,
};

const message_result = handleActions(
    {
        [GET_RESULT_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.resultList = data
            }),
        [GET_RESULT_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.resultList = initialState.resultTableList
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),

    },
    initialState
    )
;
export default message_result;