import {createAction, handleActions} from 'redux-actions';
import {takeLatest} from 'redux-saga/effects'
import * as paymentAPI from '../../lib/api/payment/payment'
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import produce from "immer";

const RESET  = 'payment/RESET';
const SET_FIELD  = 'payment/SET_FIELD';
// const CHANGE_FILE  = 'customerservice/CHANGE_FILE';
//
// const CHANGE_GROUP  = 'customerservice/CHANGE_GROUP';
// const CHANGE_CONDITION  = 'customerservice/CHANGE_CONDITION';


const [EPOINT_ITEM_INFO, EPOINT_ITEM_INFO_SUCCESS, EPOINT_ITEM_INFO_FAILURE] = createRequestActionTypes('payment/EPOINT_ITEM_INFO');
const [PAY_REQUEST_INFO, PAY_REQUEST_INFO_SUCCESS, PAY_REQUEST_INFO_FAILURE] = createRequestActionTypes('payment/PAY_REQUEST_INFO');
const [PAYMENT_SERVICE, PAYMENT_SERVICE_SUCCESS, PAYMENT_SERVICE_FAILURE] = createRequestActionTypes('payment/PAYMENT_SERVICE');
// const [BOARD_INFO, BOARD_INFO_SUCCESS, BOARD_INFO_FAILURE] = createRequestActionTypes('customerservice/BOARD_INFO');
//
// const [BOARD_WRITE, BOARD_WRITE_SUCCESS, BOARD_WRITE_FAILURE] = createRequestActionTypes('customerservice/BOARD_WRITE');
// const [BOARD_UPDATE, BOARD_UPDATE_SUCCESS, BOARD_UPDATE_FAILURE] = createRequestActionTypes('customerservice/BOARD_UPDATE');
// const [BOARD_DELETE, BOARD_DELETE_SUCCESS, BOARD_DELETE_FAILURE] = createRequestActionTypes('customerservice/BOARD_DELETE');
//
// const [BOARD_LIST, BOARD_LIST_SUCCESS, BOARD_LIST_FAILURE] = createRequestActionTypes('customerservice/BOARD_LIST');
//
// const [FAQ_BOARD_LIST, FAQ_BOARD_LIST_SUCCESS, FAQ_BOARD_LIST_FAILURE] = createRequestActionTypes('customerservice/FAQ_BOARD_LIST');
// const [TERMS_BOARD_LIST, TERMS_BOARD_LIST_SUCCESS, TERMS_BOARD_LIST_FAILURE] = createRequestActionTypes('customerservice/TERMS_BOARD_LIST');

export const epointItemInfo = createAction(EPOINT_ITEM_INFO);
export const payRequestInfo = createAction(PAY_REQUEST_INFO,(info) => (
    info
));

export const paymentService = createAction(PAYMENT_SERVICE,(payRequest, paymentType) => (
    {payRequest, paymentType}
));

export const reset = createAction(RESET);
//
// export const setBoardInfo = createAction(BOARD_INFO,({type}) => ({
//     type
// }));
//
// export const boardWrite = createAction(BOARD_WRITE,({boardItem,type}) => ({
//     boardItem,type
// }));
//
// export const boardUpdate = createAction(BOARD_UPDATE,({boardItem,type}) => ({
//     boardItem,type
// }));
//
// export const boardDelete = createAction(BOARD_DELETE,({boardItem}) => ({
//     boardItem
// }));
//
// export const boardList = createAction(BOARD_LIST,({type,page}) => ({
//     type,page
// }));
//
// export const faqBoardList = createAction(FAQ_BOARD_LIST,({type,page}) => ({
//     type,page
// }));
//
// export const termsBoardList = createAction(TERMS_BOARD_LIST,({type,page}) => ({
//     type,page
// }));


export const setField = createAction(
    SET_FIELD,
    ({key, value}) => ({
        key,
        value
    }),
);
//
// export const changeFile = createAction(
//     CHANGE_FILE,
//     (files) => (
//         files
//     ),
// );
//
// export const changeGroup = createAction(
//     CHANGE_GROUP,
//     (value) => (
//         value
//     ),
// );
//
// export const changeCondition = createAction(
//     CHANGE_CONDITION,
//     (value) => (
//         value
//     ),
// );


const epointItemInfoSaga = createRequestSaga(EPOINT_ITEM_INFO, paymentAPI.epointItemInfo);
const payRequestInfoSaga = createRequestSaga(PAY_REQUEST_INFO, paymentAPI.getPayRequestInfo);
const paymentServiceSaga = createRequestSaga(PAYMENT_SERVICE, paymentAPI.paymentService);


//
// const boardWriteSaga = createRequestSaga(BOARD_WRITE, customerserviceAPI.write);
// const boardUpdateSaga = createRequestSaga(BOARD_UPDATE, customerserviceAPI.update);
// const boardDeleteSaga = createRequestSaga(BOARD_DELETE, customerserviceAPI.boardDelete);
//
// const boardListSaga = createRequestSaga(BOARD_LIST, customerserviceAPI.boardList);
//
// const faqBoardListSaga = createRequestSaga(FAQ_BOARD_LIST, customerserviceAPI.boardList);
// const termsBoardListSaga = createRequestSaga(TERMS_BOARD_LIST, customerserviceAPI.boardList);

export function* paymentSaga(){
    yield takeLatest(EPOINT_ITEM_INFO, epointItemInfoSaga);
    yield takeLatest(PAY_REQUEST_INFO, payRequestInfoSaga);
    yield takeLatest(PAYMENT_SERVICE, paymentServiceSaga);

    // yield takeLatest(BOARD_INFO, boardInfoSaga);
    // yield takeLatest(BOARD_WRITE, boardWriteSaga);
    // yield takeLatest(BOARD_UPDATE, boardUpdateSaga);
    // yield takeLatest(BOARD_DELETE, boardDeleteSaga);
    // yield takeLatest(BOARD_LIST, boardListSaga);
    // yield takeLatest(FAQ_BOARD_LIST, faqBoardListSaga);
    // yield takeLatest(TERMS_BOARD_LIST, termsBoardListSaga);
}

const initialState = {
    item:{
        epoint:null,
    },
    payRequest:null,
    payMethod:null,
    error:null
}

const payment = handleActions(
    {
        [EPOINT_ITEM_INFO_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.item.epoint = data
            }),
        [EPOINT_ITEM_INFO_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.item.epoint = []
                draft.error = error
            }),
        [PAY_REQUEST_INFO_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.payRequest = data.payRequest
                draft.payMethod = data.payMethod
                // draft.payRequest.payMethod = data.payMethod
                // draft.payRequest.itemParam = data.itemParam
                // draft.payRequest.paymentRequestUrl = data.paymentRequestUrl
            }),
        [PAY_REQUEST_INFO_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.payRequest = null
                draft.payMethod = null
                draft.error = error.response.data
            }),
        /*[SET_FIELD]: (state, {payload: {key, value}}) =>
            produce(state, draft => {
                draft.payRequest[key] = value;
            }),*/
        [SET_FIELD]:(state,{payload:{key, value}}) => ({
            ...state,
            payRequest:{
                ...state.payRequest,
                [key]: value
            }
        }),
        [RESET]:(state) => (
            initialState
            ),

        // [BOARD_INFO_SUCCESS]:(state, {payload: data}) =>
        //     produce(state, draft => {
        //         draft.boardInfo = data
        //         draft.boardInfo.group = (data.group ? data.group.split(",") : "")
        //         draft.boardInfo.condition = (data.condition ? data.condition.split(",") : "")
        //     }),
        // [BOARD_INFO_FAILURE]:(state,{payload:error}) => ({
        //     ...state,
        //     boardInfo:initialState.boardInfo,
        //     checkError:error
        // }),
        // [BOARD_WRITE_SUCCESS]:(state) =>
        //     produce(state, draft => {
        //         draft.board = initialState.board
        //         draft.result = true
        //         draft.checkError = null
        //     }),
        // [BOARD_WRITE_FAILURE]:(state,{payload:error}) => ({
        //     ...state,
        //     result : false,
        //     checkError:error
        // }),
        // [BOARD_UPDATE_SUCCESS]:(state) =>
        //     produce(state, draft => {
        //         draft.result = true
        //         draft.checkError = null
        //         draft.board = initialState.board
        //     }),
        // [BOARD_UPDATE_FAILURE]:(state,{payload:error}) => ({
        //     ...state,
        //     result : false,
        //     checkError:error
        // }),
        // [BOARD_DELETE_SUCCESS]:(state) =>
        //     produce(state, draft => {
        //         draft.result = true
        //         draft.checkError = null
        //         draft.board = initialState.board
        //     }),
        // [BOARD_DELETE_FAILURE]:(state,{payload:error}) => ({
        //     ...state,
        //     result : false,
        //     checkError:error
        // }),
        // [BOARD_LIST_SUCCESS]:(state, {payload: data}) =>
        //     produce(state, draft => {
        //         draft.boardList = data.boardList
        //         draft.boardInfo = data.boardInfo
        //         draft.boardInfo.group = (data.boardInfo.group ? data.boardInfo.group.split(",") : "")
        //         draft.boardInfo.condition = (data.boardInfo.condition ? data.boardInfo.condition.split(",") : "")
        //         draft.page = data.page
        //         draft.result = false
        //     }),
        // [BOARD_LIST_FAILURE]:(state,{payload:error}) => ({
        //     ...state,
        //     boardList:null,
        //     page:null,
        //     checkError:error
        // }),
        // [FAQ_BOARD_LIST_SUCCESS]:(state, {payload: data}) =>
        //     produce(state, draft => {
        //         draft.faqBoardList = data.boardList
        //         draft.faqPage = data.page
        //         draft.board.files = data.files
        //         draft.boardInfo = data.boardInfo
        //         draft.boardInfo.group = (data.boardInfo.group ? data.boardInfo.group.split(",") : "")
        //     }),
        // [FAQ_BOARD_LIST_FAILURE]:(state,{payload:error}) => ({
        //     ...state,
        //     faqBoardList:null,
        //     faqPage:null,
        //     checkError:error
        // }),
        // [TERMS_BOARD_LIST_SUCCESS]:(state, {payload: data}) =>
        //     produce(state, draft => {
        //         draft.termsBoardList = data.boardList
        //         draft.termsPage = data.page
        //         draft.board.files = data.files
        //         draft.boardInfo = data.boardInfo
        //         draft.boardInfo.group = (data.boardInfo.group ? data.boardInfo.group.split(",") : "")
        //     }),
        // [TERMS_BOARD_LIST_FAILURE]:(state,{payload:error}) => ({
        //     ...state,
        //     termsBoardList:null,
        //     termsPage:null,
        //     checkError:error
        // }),
        // [CHANGE_FIELD]: (state, {payload: {key, value}}) =>
        //     produce(state, draft => {
        //         draft.board[key] = value;
        //     }),
        // [CHANGE_FILE]: (state, {payload: files}) =>
        //     produce(state, draft => {
        //         draft.board.files = files;
        //     }),
        // [CHANGE_GROUP]: (state, {payload: value}) =>
        //     produce(state, draft => {
        //         draft.board.group_number = value;
        //     }),
        // [CHANGE_CONDITION]: (state, {payload: value}) =>
        //     produce(state, draft => {
        //         draft.board.condition_number = value;
        //     }),
        // [RESET]: (state) => ({
        //     ...state,
        //     board:initialState.board,
        //     boardInfo:initialState.boardInfo,
        //     result:initialState.result
        // })
        // produce(state, draft => {
        //     state = initialState;
        // }),
    },
    initialState
)


export default payment;
