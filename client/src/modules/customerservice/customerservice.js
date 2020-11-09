import {createAction, handleActions} from 'redux-actions';
import {takeLatest} from 'redux-saga/effects'
import * as customerserviceAPI from '../../lib/api/customerservice/customerservice'
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import produce from "immer";

const RESET  = 'customerservice/RESET';
const CHANGE_FIELD  = 'customerservice/CHANGE_FIELD';
const CHANGE_FILE  = 'customerservice/CHANGE_FILE';

const CHANGE_GROUP  = 'customerservice/CHANGE_GROUP';
const CHANGE_CONDITION  = 'customerservice/CHANGE_CONDITION';


const [BOARD, BOARD_SUCCESS, BOARD_FAILURE] = createRequestActionTypes('customerservice/BOARD');
const [BOARD_INFO, BOARD_INFO_SUCCESS, BOARD_INFO_FAILURE] = createRequestActionTypes('customerservice/BOARD_INFO');

const [BOARD_WRITE, BOARD_WRITE_SUCCESS, BOARD_WRITE_FAILURE] = createRequestActionTypes('customerservice/BOARD_WRITE');
const [BOARD_UPDATE, BOARD_UPDATE_SUCCESS, BOARD_UPDATE_FAILURE] = createRequestActionTypes('customerservice/BOARD_UPDATE');
const [BOARD_DELETE, BOARD_DELETE_SUCCESS, BOARD_DELETE_FAILURE] = createRequestActionTypes('customerservice/BOARD_DELETE');

const [BOARD_LIST, BOARD_LIST_SUCCESS, BOARD_LIST_FAILURE] = createRequestActionTypes('customerservice/BOARD_LIST');

const [FAQ_BOARD_LIST, FAQ_BOARD_LIST_SUCCESS, FAQ_BOARD_LIST_FAILURE] = createRequestActionTypes('customerservice/FAQ_BOARD_LIST');
const [TERMS_BOARD_LIST, TERMS_BOARD_LIST_SUCCESS, TERMS_BOARD_LIST_FAILURE] = createRequestActionTypes('customerservice/TERMS_BOARD_LIST');

export const reset = createAction(RESET);

export const board = createAction(BOARD,({type,id}) => ({
    type,id
}));

export const setBoardInfo = createAction(BOARD_INFO,({type}) => ({
    type
}));

export const boardWrite = createAction(BOARD_WRITE,({boardItem,content,type}) => ({
    boardItem,content,type
}));

export const boardUpdate = createAction(BOARD_UPDATE,({boardItem,content,type}) => ({
    boardItem,content,type
}));

export const boardDelete = createAction(BOARD_DELETE,({boardItem}) => ({
    boardItem
}));

export const boardList = createAction(BOARD_LIST,({type,page}) => ({
    type,page
}));

export const faqBoardList = createAction(FAQ_BOARD_LIST,({type,page}) => ({
    type,page
}));

export const termsBoardList = createAction(TERMS_BOARD_LIST,({type,page}) => ({
    type,page
}));


export const changeField = createAction(
    CHANGE_FIELD,
    ({key, value}) => ({
        key,
        value
    }),
);

export const changeFile = createAction(
    CHANGE_FILE,
    (files) => (
        files
    ),
);

export const changeGroup = createAction(
    CHANGE_GROUP,
    (value) => (
        value
    ),
);

export const changeCondition = createAction(
    CHANGE_CONDITION,
    (value) => (
        value
    ),
);


const boardSaga = createRequestSaga(BOARD, customerserviceAPI.board);
const boardInfoSaga = createRequestSaga(BOARD_INFO, customerserviceAPI.boardInfo);

const boardWriteSaga = createRequestSaga(BOARD_WRITE, customerserviceAPI.write);
const boardUpdateSaga = createRequestSaga(BOARD_UPDATE, customerserviceAPI.update);
const boardDeleteSaga = createRequestSaga(BOARD_DELETE, customerserviceAPI.boardDelete);

const boardListSaga = createRequestSaga(BOARD_LIST, customerserviceAPI.boardList);

const faqBoardListSaga = createRequestSaga(FAQ_BOARD_LIST, customerserviceAPI.boardList);
const termsBoardListSaga = createRequestSaga(TERMS_BOARD_LIST, customerserviceAPI.boardList);

export function* customerserviceSaga(){
    yield takeLatest(BOARD, boardSaga);
    yield takeLatest(BOARD_INFO, boardInfoSaga);
    yield takeLatest(BOARD_WRITE, boardWriteSaga);
    yield takeLatest(BOARD_UPDATE, boardUpdateSaga);
    yield takeLatest(BOARD_DELETE, boardDeleteSaga);
    yield takeLatest(BOARD_LIST, boardListSaga);
    yield takeLatest(FAQ_BOARD_LIST, faqBoardListSaga);
    yield takeLatest(TERMS_BOARD_LIST, termsBoardListSaga);
}

const initialState = {
    board:{
        content_id:0,
        title:"",
        content:"",
        files:null,
        group_number:0,
        condition_number:0,
    },
    boardInfo:{
        group:"",
        condition:"",
    },
    boardList:[],
    faqBoardList:[],
    termsBoardList:[],
    page:null,
    faqPage:null,
    termsPage:null,
    checkError:null,
    result:null
}

const customerservice = handleActions(
    {
        [BOARD_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.board = data.board
                draft.board.files = data.files
                draft.boardInfo = data.boardInfo
                draft.boardInfo.group = (data.boardInfo.group ? data.boardInfo.group.split(",") : "")
                draft.boardInfo.condition = (data.boardInfo.condition ? data.boardInfo.condition.split(",") : "")
                draft.checkError = null
            }),
        [BOARD_FAILURE]:(state,{payload:error}) => ({
            ...state,
            result : false,
            board:initialState.board,
            checkError:error
        }),
        [BOARD_INFO_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.boardInfo = data
                draft.boardInfo.group = (data.group ? data.group.split(",") : "")
                draft.boardInfo.condition = (data.condition ? data.condition.split(",") : "")
            }),
        [BOARD_INFO_FAILURE]:(state,{payload:error}) => ({
            ...state,
            boardInfo:initialState.boardInfo,
            checkError:error
        }),
        [BOARD_WRITE_SUCCESS]:(state) =>
            produce(state, draft => {
                draft.board = initialState.board
                draft.result = true
                draft.checkError = null
            }),
        [BOARD_WRITE_FAILURE]:(state,{payload:error}) => ({
            ...state,
            result : false,
            checkError:error
        }),
        [BOARD_UPDATE_SUCCESS]:(state) =>
            produce(state, draft => {
                draft.result = true
                draft.checkError = null
                draft.board = initialState.board
            }),
        [BOARD_UPDATE_FAILURE]:(state,{payload:error}) => ({
            ...state,
            result : false,
            checkError:error
        }),
        [BOARD_DELETE_SUCCESS]:(state) =>
            produce(state, draft => {
                draft.result = true
                draft.checkError = null
                draft.board = initialState.board
            }),
        [BOARD_DELETE_FAILURE]:(state,{payload:error}) => ({
            ...state,
            result : false,
            checkError:error
        }),
        [BOARD_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.boardList = data.boardList
                draft.boardInfo = data.boardInfo
                draft.boardInfo.group = (data.boardInfo.group ? data.boardInfo.group.split(",") : "")
                draft.boardInfo.condition = (data.boardInfo.condition ? data.boardInfo.condition.split(",") : "")
                draft.page = data.page
                draft.result = false
            }),
        [BOARD_LIST_FAILURE]:(state,{payload:error}) => ({
            ...state,
            boardList:null,
            page:null,
            checkError:error
        }),
        [FAQ_BOARD_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.faqBoardList = data.boardList
                draft.faqPage = data.page
                draft.board.files = data.files
                draft.boardInfo = data.boardInfo
                draft.boardInfo.group = (data.boardInfo.group ? data.boardInfo.group.split(",") : "")
            }),
        [FAQ_BOARD_LIST_FAILURE]:(state,{payload:error}) => ({
            ...state,
            faqBoardList:null,
            faqPage:null,
            checkError:error
        }),
        [TERMS_BOARD_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.termsBoardList = data.boardList
                draft.termsPage = data.page
                draft.board.files = data.files
                draft.boardInfo = data.boardInfo
                draft.boardInfo.group = (data.boardInfo.group ? data.boardInfo.group.split(",") : "")
            }),
        [TERMS_BOARD_LIST_FAILURE]:(state,{payload:error}) => ({
            ...state,
            termsBoardList:null,
            termsPage:null,
            checkError:error
        }),
        [CHANGE_FIELD]: (state, {payload: {key, value}}) =>
            produce(state, draft => {
                draft.board[key] = value;
            }),
        [CHANGE_FILE]: (state, {payload: files}) =>
            produce(state, draft => {
                draft.board.files = files;
            }),
        [CHANGE_GROUP]: (state, {payload: value}) =>
            produce(state, draft => {
                draft.board.group_number = value;
            }),
        [CHANGE_CONDITION]: (state, {payload: value}) =>
            produce(state, draft => {
                draft.board.condition_number = value;
            }),
        [RESET]: (state) => ({
            ...state,
            board:initialState.board,
            boardInfo:initialState.boardInfo,
            result:initialState.result
        })
            // produce(state, draft => {
            //     state = initialState;
            // }),
    },
    initialState
)


export default customerservice;
