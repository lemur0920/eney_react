import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import * as patchcallAPI from '../../lib/api/service/patchcall';
import FileSaver from 'file-saver';


// const [GET_CUSTOMER_CASE_CATE,GET_CUSTOMER_CASE_CATE_SUCCESS, GET_CUSTOMER_CASE_CATE_FAILURE] = createRequestActionTypes('service/GET_CUSTOMER_CASE_CATE')

const [GET_AGENT, GET_AGENT_SUCCESS, GET_AGENT_FAILURE] = createRequestActionTypes('patchcall/GET_AGENT')
const [UPDATE_AGENT, UPDATE_AGENT_SUCCESS, UPDATE_AGENT_FAILURE] = createRequestActionTypes('patchcall/UPDATE_AGENT')
const [GET_AGENT_LIST, GET_AGENT_LIST_SUCCESS, GET_AGENT_LIST_FAILURE] = createRequestActionTypes('patchcall/GET_AGENT_LIST')

const [GET_CALLLOG_LIST, GET_CALLLOG_LIST_SUCCESS, GET_CALLLOG_LIST_FAILURE] = createRequestActionTypes('patchcall/GET_CALLLOG_LIST')
const [GET_CALLLOG_LIST_DOWNLOAD, GET_CALLLOG_LIST_DOWNLOAD_SUCCESS, GET_CALLLOG_LIST_DOWNLOAD_FAILURE] = createRequestActionTypes('patchcall/GET_CALLLOG_LIST_DOWNLOAD')
const [GET_CALLLOG_AUDIO_DOWNLOAD, GET_CALLLOG_AUDIO_DOWNLOAD_SUCCESS, GET_CALLLOG_AUDIO_DOWNLOAD_FAILURE] = createRequestActionTypes('patchcall/GET_CALLLOG_AUDIO_DOWNLOAD')
const [CALL_MANAGE_SAMPLE_DOWNLOAD, CALL_MANAGE_SAMPLE_DOWNLOAD_SUCCESS, CALL_MANAGE_SAMPLE_DOWNLOAD_FAILURE] = createRequestActionTypes('patchcall/CALL_MANAGE_SAMPLE_DOWNLOAD');

const [ADMIN_GET_CALLLOG_LIST, ADMIN_GET_CALLLOG_LIST_SUCCESS, ADMIN_GET_CALLLOG_LIST_FAILURE] = createRequestActionTypes('patchcall/ADMIN_GET_CALLLOG_LIST')
const [ADMIN_GET_CALLLOG_LIST_DOWNLOAD, ADMIN_GET_CALLLOG_LIST_DOWNLOAD_SUCCESS, ADMIN_GET_CALLLOG_LIST_DOWNLOAD_FAILURE] = createRequestActionTypes('patchcall/ADMIN_GET_CALLLOG_LIST_DOWNLOAD')
const [ADMIN_GET_CALLLOG_AUDIO_DOWNLOAD, ADMIN_GET_CALLLOG_AUDIO_DOWNLOAD_SUCCESS, ADMIN_GET_CALLLOG_AUDIO_DOWNLOAD_FAILURE] = createRequestActionTypes('patchcall/ADMIN_GET_CALLLOG_AUDIO_DOWNLOAD')

const [NUM_MANAGE_FILE_UPLOAD, NUM_MANAGE_FILE_UPLOAD_SUCCESS, NUM_MANAGE_FILE_UPLOAD_FAILURE] = createRequestActionTypes('patchcall/NUM_MANAGE_FILE_UPLOAD');


const [GET_CID_LIST, GET_CID_LIST_SUCCESS, GET_CID_LIST_FAILURE] = createRequestActionTypes('patchcall/GET_CID_LIST')
const [GET_ALL_CID_LIST, GET_ALL_CID_LIST_SUCCESS, GET_ALL_CID_LIST_FAILURE] = createRequestActionTypes('patchcall/GET_ALL_CID_LIST')

// const [EDIT_CUSTOMER_CASE,EDIT_CUSTOMER_CASE_SUCCESS, EDIT_CUSTOMER_CASE_FAILURE] = createRequestActionTypes('customerCase/EDIT_CUSTOMER_CASE')
// const [DELETE_CUSTOMER_CASE,DELETE_CUSTOMER_CASE_SUCCESS, DELETE_CUSTOMER_CASE_FAILURE] = createRequestActionTypes('customerCase/DELETE_CUSTOMER_CASE')
// const [UPDATE_CUSTOM_USER_COUNT,UPDATE_CUSTOM_USER_COUNT_SUCCESS, UPDATE_CUSTOM_USER_COUNT_FAILURE] = createRequestActionTypes('adminCustomUserCount/UPDATE_CUSTOM_USER_COUNT')
const DISABLE_CALLBACK = 'patchcall/DISABLE_CALLBACK';
const CHANGE_AGENT_FIELD = 'patchcall/CHANGE_AGENT_FIELD';
const INITIALIZE = 'patchcall/INITIALIZE';
const INITIALIZE_FORM = 'patchcall/INITIALIZE_FORM';

// export const getCustomerCaseCate = createAction(GET_CUSTOMER_CASE_CATE)
export const getAgent = createAction(GET_AGENT, (vno) => (vno))
export const getAgentList = createAction(GET_AGENT_LIST, ({page, cate, search_filed, search}) => ({
    page,
    cate,
    search_filed,
    search
}))
export const updateAgent = createAction(UPDATE_AGENT, ({agent, address}) => ({agent, address}))

//콜로그 조회
export const getCalllogList = createAction(GET_CALLLOG_LIST, ({page, cate, search_filed, search, startDate, endDate,user_id}) => ({
    user_id,
    page,
    cate,
    search_filed,
    search,
    startDate,
    endDate
}))

export const getCallLogListDownload = createAction(GET_CALLLOG_LIST_DOWNLOAD, ({search_filed, search, startDate, endDate}) => ({
    search_filed,
    search,
    startDate,
    endDate
}))
export const getCallLogAudioDownload = createAction(GET_CALLLOG_AUDIO_DOWNLOAD, (fileName) => (fileName))
//관리자 콜로그 조회
export const adminGetCalllogList = createAction(ADMIN_GET_CALLLOG_LIST, ({page, cate, search_filed, search, startDate, endDate,user_id}) => ({
    user_id,
    page,
    cate,
    search_filed,
    search,
    startDate,
    endDate
}))

export const adminGetCallLogListDownload = createAction(ADMIN_GET_CALLLOG_LIST_DOWNLOAD, ({user_id,page, cate, search_filed, search, startDate, endDate}) => ({
    user_id,
    page,
    cate,
    search_filed,
    search,
    startDate,
    endDate
}))
export const adminGetCallLogAudioDownload = createAction(ADMIN_GET_CALLLOG_AUDIO_DOWNLOAD, (fileName) => (fileName))
export const callManageSampleDownload = createAction(CALL_MANAGE_SAMPLE_DOWNLOAD)
export const numManamgeFileUpload = createAction(NUM_MANAGE_FILE_UPLOAD, (excelFile) => (excelFile))

//CID 리스트
export const getCidList = createAction(GET_CID_LIST, ({page}) => ({page}))
export const getAllCidList = createAction(GET_ALL_CID_LIST)


export const changeAgentField = createAction(
    CHANGE_AGENT_FIELD,
    ({key, value}) => ({
        key,
        value
    }),
);


export const disableCallback = createAction(DISABLE_CALLBACK);


export const initialize = createAction(INITIALIZE, form => form);
export const initializeForm = createAction(INITIALIZE_FORM, form => form);


// const getCustomerCaseCateSage = createRequestSaga(GET_CUSTOMER_CASE_CATE, customerCaseAPI.getCustomerCaseCate);
const getAgentSage = createRequestSaga(GET_AGENT, patchcallAPI.getAgent);
const getAgentListSage = createRequestSaga(GET_AGENT_LIST, patchcallAPI.getAgentList);
const updateAgentSage = createRequestSaga(UPDATE_AGENT, patchcallAPI.updateAgent);

const getCalllogListSage = createRequestSaga(GET_CALLLOG_LIST, patchcallAPI.getCalllogList);
const getCallLogListDownloadSage = createRequestSaga(GET_CALLLOG_LIST_DOWNLOAD, patchcallAPI.getCallLogListDownload);
const getCallLogAudioDownloadSage = createRequestSaga(GET_CALLLOG_AUDIO_DOWNLOAD, patchcallAPI.getCallLogAudioDownload);
const adminGetCalllogListSage = createRequestSaga(ADMIN_GET_CALLLOG_LIST, patchcallAPI.adminGetCalllogList);
const adminGetCallLogListDownloadSage = createRequestSaga(ADMIN_GET_CALLLOG_LIST_DOWNLOAD, patchcallAPI.adminGetCallLogListDownload);
const adminGetCallLogAudioDownloadSage = createRequestSaga(ADMIN_GET_CALLLOG_AUDIO_DOWNLOAD, patchcallAPI.adminGetCallLogAudioDownload);
const callManageSampleDownloadSaga = createRequestSaga(CALL_MANAGE_SAMPLE_DOWNLOAD, patchcallAPI.callManageSampleDownload);
const numManamgeFileUploadSaga = createRequestSaga(NUM_MANAGE_FILE_UPLOAD, patchcallAPI.numManageFileUpload);


const getCidListSage = createRequestSaga(GET_CID_LIST, patchcallAPI.getCidList);
const getAllCidListSage = createRequestSaga(GET_ALL_CID_LIST, patchcallAPI.getAllCidList);


// const addCustomerCaseSaga = createRequestSaga(ADD_CUSTOMER_CASE, customerCaseAPI.addCustomerCase);
// const editCustomerCaseSaga = createRequestSaga(EDIT_CUSTOMER_CASE, customerCaseAPI.editCustomerCase);
// const deleteCustomerCaseSaga = createRequestSaga(DELETE_CUSTOMER_CASE, customerCaseAPI.deleteCustomerCase);

// const updateCustomUserCountSaga = createRequestSaga(UPDATE_CUSTOM_USER_COUNT, customUserCountAPI.updateCustomUserCount);

export function* patchcallSaga() {
    yield takeLatest(GET_AGENT, getAgentSage);
    yield takeLatest(UPDATE_AGENT, updateAgentSage);
    yield takeLatest(GET_AGENT_LIST, getAgentListSage);
    yield takeLatest(GET_CALLLOG_LIST, getCalllogListSage);
    yield takeLatest(GET_CALLLOG_LIST_DOWNLOAD, getCallLogListDownloadSage);
    yield takeLatest(GET_CALLLOG_AUDIO_DOWNLOAD, getCallLogAudioDownloadSage);
    yield takeLatest(ADMIN_GET_CALLLOG_LIST, adminGetCalllogListSage);
    yield takeLatest(ADMIN_GET_CALLLOG_LIST_DOWNLOAD, adminGetCallLogListDownloadSage);
    yield takeLatest(ADMIN_GET_CALLLOG_AUDIO_DOWNLOAD, adminGetCallLogAudioDownloadSage);
    yield takeLatest(GET_CID_LIST, getCidListSage);
    yield takeLatest(GET_ALL_CID_LIST, getAllCidListSage);
    yield takeLatest(CALL_MANAGE_SAMPLE_DOWNLOAD, callManageSampleDownloadSaga);
    yield takeLatest(NUM_MANAGE_FILE_UPLOAD, numManamgeFileUploadSaga);

}

const initialState = {
    agent: null,
    address: null,
    agentList: {
        list: null,
        page: null,
    },
    callLog: {
        page: null,
        list: null
    },
    cid: {
        page: null,
        list: null
    },
    updateResult: false,
    updateError: null,
    fileUploadError: null,
    downloadError: null,
    bulkUpload: {
        result: false,
        error: null
    },
    error: null
};

const patchcall = handleActions(
    {
        [GET_AGENT_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.agent = data
                draft.address = data.address
            }),
        [GET_AGENT_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.agent = initialState.agent
            }),
        [UPDATE_AGENT_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.updateResult = true
                draft.updateError = null
                draft.fileUploadError = null
            }),
        [UPDATE_AGENT_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.updateResult = false
                draft.updateError = error.response.data.updateError
                draft.fileUploadError = error.response.data.fileUploadError
            }),
        [GET_AGENT_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.agentList.list = data.list
                draft.agentList.page = data.page
            }),
        [GET_AGENT_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.agentList = initialState.agentList
            }),
        [CHANGE_AGENT_FIELD]: (state, {payload: {key, value}}) =>
            produce(state, draft => {
                draft.agent.agentVO[key] = value;
            }),
        [DISABLE_CALLBACK]: (state) =>
            produce(state, draft => {
                draft.agent.agentVO.smsYn = "N"
                draft.agent.agentVO.outSmsYn = "N"
            }),
        [GET_CALLLOG_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.callLog.list = data.list
                draft.callLog.page = data.page
            }),
        [GET_CALLLOG_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.callLog = initialState.callLog
            }),
        [GET_CID_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.cid.list = data.list
                draft.cid.page = data.page
            }),
        [GET_CID_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.cid = initialState.cid
            }),
        [GET_ALL_CID_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.cid.list = data.list
            }),
        [GET_ALL_CID_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.cid = initialState.cid
            }),
        [GET_CALLLOG_LIST_DOWNLOAD_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                let blob = new Blob([data], {type: 'application/vnd.ms-excel'});
                FileSaver.saveAs(blob, '콜로그 수신내역.xls');
                draft.downloadError = null
            }),
        [GET_CALLLOG_AUDIO_DOWNLOAD_SUCCESS]: (state, {payload: data, mate: mate}) =>
            produce(state, draft => {
                console.log(data)
                let blob = new Blob([data], {type: 'audio/wav'});
                FileSaver.saveAs(blob, FileSaver.saveAs(blob, JSON.parse(mate.config.data).fileName));
                draft.downloadError = null
            }),
        [CALL_MANAGE_SAMPLE_DOWNLOAD_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                let blob = new Blob([data], {type: 'application/vnd.ms-excel'});
                FileSaver.saveAs(blob, '번호관리샘플.xls');
                draft.downloadError = null
            }),

        [NUM_MANAGE_FILE_UPLOAD_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.bulkUpload.result = true
                draft.bulkUpload.error = null

            }),
        [NUM_MANAGE_FILE_UPLOAD_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.bulkUpload.result = false
                draft.bulkUpload.error = error.response.data
            }),
        [INITIALIZE_FORM]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form],
        }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),
        [ADMIN_GET_CALLLOG_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.callLog.list = data.list
                draft.callLog.page = data.page
            }),
        [ADMIN_GET_CALLLOG_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.callLog = initialState.callLog
            }),
        [ADMIN_GET_CALLLOG_LIST_DOWNLOAD_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                let blob = new Blob([data], {type: 'application/vnd.ms-excel'});
                FileSaver.saveAs(blob, '콜로그 수신내역.xls');
                draft.downloadError = null
            }),
        [ADMIN_GET_CALLLOG_AUDIO_DOWNLOAD_SUCCESS]: (state, {payload: data, mate: mate}) =>
            produce(state, draft => {
                let blob = new Blob([data], {type: 'audio/wav'});
                FileSaver.saveAs(blob, FileSaver.saveAs(blob, JSON.parse(mate.config.data).fileName));
                draft.downloadError = null
            }),

    },
    initialState
);
export default patchcall;