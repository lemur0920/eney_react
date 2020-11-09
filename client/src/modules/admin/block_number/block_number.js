import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../../lib/createRequestSaga";
import * as blockNumberAPI from '../../../lib/api/admin/block_number';



const [GET_BLOCK_NUMBER, GET_BLOCK_NUMBER_SUCCESS, GET_BLOCK_NUMBER_FAILURE] = createRequestActionTypes('adminBlockNumber/GET_BLOCK_NUMBER')


const CHANGE_FIELD = 'adminBlockNumber/CHANGE_FIELD';
const INITIALIZE = 'adminBlockNumber/INITIALIZE';


export const getBlockNumber = createAction(GET_BLOCK_NUMBER, ({page}) => ({page}));

export const changeField = createAction(
    CHANGE_FIELD,
    ({key, value}) => ({
        key,
        value
    }),
);

export const initialize = createAction(INITIALIZE, from => from);

const getBlockNumberSaga = createRequestSaga(GET_BLOCK_NUMBER, blockNumberAPI.getBlockNumberList);

export function* blockNumberSaga() {
    yield takeLatest(GET_BLOCK_NUMBER, getBlockNumberSaga);
}

const initialState = {
    blockNumber: {
        list: null,
        page: null,
    },
    updateResult: false,
    error: null
};

const block_number = handleActions(
    {
        [GET_BLOCK_NUMBER_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.blockNumber.page = data.page
                draft.blockNumber.list = data.list
            }),
        [GET_BLOCK_NUMBER_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.blockNumber = initialState.blockNumber
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
export default block_number;