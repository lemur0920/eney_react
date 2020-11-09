import {createAction, handleActions} from 'redux-actions';
import {takeLatest} from 'redux-saga/effects'
import * as customerAPI from '../../lib/api/service/customer'
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import produce from "immer";
import FileSaver from "file-saver";
import * as patchcallAPI from "../../lib/api/service/patchcall";

const RESET  = 'customer/RESET';
const SET_ERROR  = 'auth/SET_ERROR';
const INITIALIZE  = 'customer/INITIALIZE';
const INITIALIZE_FORM  = 'customer/INITIALIZE_FORM';


const [ADD_CUSTOMER, ADD_CUSTOMER_SUCCESS, ADD_CUSTOMER_FAILURE] = createRequestActionTypes('customer/ADD_CUSTOMER');
const [GET_CUSTOMER_INFO, GET_CUSTOMER_INFO_SUCCESS, GET_CUSTOMER_INFO_FAILURE] = createRequestActionTypes('customer/GET_CUSTOMER_INFO');
const [UPDATE_CUSTOMER_INFO, UPDATE_CUSTOMER_INFO_SUCCESS, UPDATE_CUSTOMER_INFO_FAILURE] = createRequestActionTypes('customer/UPDATE_CUSTOMER_INFO');
const [GET_CUSTOMER_LIST, GET_CUSTOMER_LIST_SUCCESS, GET_CUSTOMER_LIST_FAILURE] = createRequestActionTypes('customer/GET_CUSTOMER_LIST');
const [GET_GROUP_BY_CUSTOMER_LIST, GET_GROUP_BY_CUSTOMER_LIST_SUCCESS, GET_GROUP_BY_CUSTOMER_LIST_FAILURE] = createRequestActionTypes('customer/GET_GROUP_BY_CUSTOMER_LIST');
const [UPDATE_CUSTOMER_GROUP_MEMBER, UPDATE_CUSTOMER_GROUP_MEMBER_SUCCESS, UPDATE_CUSTOMER_GROUP_MEMBER_FAILURE] = createRequestActionTypes('customer/UPDATE_CUSTOMER_GROUP_MEMBER');

const [GET_CUSTOMER_EVENT_LIST, GET_CUSTOMER_EVENT_LIST_SUCCESS, GET_CUSTOMER_EVENT_LIST_FAILURE] = createRequestActionTypes('customer/GET_CUSTOMER_EVENT_LIST');
const [GET_CUSTOMER_GROUP_LIST, GET_CUSTOMER_GROUP_LIST_SUCCESS, GET_CUSTOMER_GROUP_LIST_FAILURE] = createRequestActionTypes('customer/GET_CUSTOMER_GROUP_LIST');
const [ADD_CUSTOMER_GROUP, ADD_CUSTOMER_GROUP_SUCCESS, ADD_CUSTOMER_GROUP_FAILURE] = createRequestActionTypes('customer/ADD_CUSTOMER_GROUP');
const [DELETE_CUSTOMER_GROUP, DELETE_CUSTOMER_GROUP_SUCCESS, DELETE_CUSTOMER_GROUP_FAILURE] = createRequestActionTypes('customer/DELETE_CUSTOMER_GROUP');
const [UPDATE_CUSTOMER_GROUP, UPDATE_CUSTOMER_GROUP_SUCCESS, UPDATE_CUSTOMER_GROUP_FAILURE] = createRequestActionTypes('customer/UPDATE_CUSTOMER_GROUP');

const [CUSTOMER_BULK_UPLOAD_SAMPLE_DOWNLOAD,CUSTOMER_BULK_UPLOAD_SAMPLE_DOWNLOAD_SUCCESS, CUSTOMER_BULK_UPLOAD_SAMPLE_DOWNLOAD_FAILURE] = createRequestActionTypes('customer/CUSTOMER_BULK_UPLOAD_SAMPLE_DOWNLOAD');
const [CUSTOMER_FILE_UPLOAD,CUSTOMER_FILE_UPLOAD_SUCCESS, CUSTOMER_FILE_UPLOAD_FAILURE] = createRequestActionTypes('customer/CUSTOMER_FILE_UPLOAD');

export const addCustomer = createAction(ADD_CUSTOMER, (data) => (data))
export const getCustomerInfo = createAction(GET_CUSTOMER_INFO, ({idx}) => ({idx}))
export const updateCustomerInfo = createAction(UPDATE_CUSTOMER_INFO, (form) => (form))
export const getCustomerList = createAction(GET_CUSTOMER_LIST, ({page, cate,search_filed, search}) => ({page, cate,search_filed, search}))
export const getGroupByCustomerList = createAction(GET_GROUP_BY_CUSTOMER_LIST, ({group_idx}) => ({group_idx}))
export const getCustomerEventList = createAction(GET_CUSTOMER_EVENT_LIST, ({page, customer_idx}) => ({page, customer_idx}))
export const getCustomerGroupList = createAction(GET_CUSTOMER_GROUP_LIST, ({page, cate,search_filed, search}) => ({page, cate,search_filed, search}))
export const addCustomerGroup = createAction(ADD_CUSTOMER_GROUP, (form) => (form))
export const updateCustomerGroupMember = createAction(UPDATE_CUSTOMER_GROUP_MEMBER, (form) => (form))
export const deleteCustomerGroup = createAction(DELETE_CUSTOMER_GROUP, (idx) => (idx))
export const updateCustomerGroup = createAction(UPDATE_CUSTOMER_GROUP, (data) => (data))

export const customerBulkUploadSampleDownLoad = createAction(CUSTOMER_BULK_UPLOAD_SAMPLE_DOWNLOAD)
export const customerFileUpload = createAction(CUSTOMER_FILE_UPLOAD, (excelFile) => (excelFile))

export const initialize = createAction(INITIALIZE, form => form);
export const initializeForm = createAction(INITIALIZE_FORM, form => form);

export const setError = createAction(
    SET_ERROR,
    ({key, value}) => ({
        key,
        value
    }),
);



const addCustomerSaga = createRequestSaga(ADD_CUSTOMER, customerAPI.addCustomer);
const getCustomerInfoSaga = createRequestSaga(GET_CUSTOMER_INFO, customerAPI.getCustomerInfo);
const updateCustomerInfoSaga = createRequestSaga(UPDATE_CUSTOMER_INFO, customerAPI.updateCustomerInfo);
const getCustomerListSaga = createRequestSaga(GET_CUSTOMER_LIST, customerAPI.getCustomerList);
const getGroupByCustomerListSaga = createRequestSaga(GET_GROUP_BY_CUSTOMER_LIST, customerAPI.getGroupByCustomerList);
const getCustomerEventListSaga = createRequestSaga(GET_CUSTOMER_EVENT_LIST, customerAPI.getCustomerEventList);
const getCustomerGroupListSaga = createRequestSaga(GET_CUSTOMER_GROUP_LIST, customerAPI.getCustomerGroupList);
const addCustomerGroupSaga = createRequestSaga(ADD_CUSTOMER_GROUP, customerAPI.addCustomerGroup);
const deleteCustomerGroupSaga = createRequestSaga(DELETE_CUSTOMER_GROUP, customerAPI.deleteCustomerGroup);
const updateCustomerGroupSaga = createRequestSaga(UPDATE_CUSTOMER_GROUP, customerAPI.updateCustomerGroup);
const updateCustomerGroupMemberSaga = createRequestSaga(UPDATE_CUSTOMER_GROUP_MEMBER, customerAPI.updateCustomerGroupMember);

const customerBulkUploadSampleDownLoadSaga = createRequestSaga(CUSTOMER_BULK_UPLOAD_SAMPLE_DOWNLOAD, customerAPI.customerBulkUploadSampleDownload);
const customerFileUploadSaga = createRequestSaga(CUSTOMER_FILE_UPLOAD, customerAPI.customerFileUpload);
// const payRequestInfoSaga = createRequestSaga(PAY_REQUEST_INFO, paymentAPI.getPayRequestInfo);
//
// const boardWriteSaga = createRequestSaga(BOARD_WRITE, customerserviceAPI.write);
// const boardUpdateSaga = createRequestSaga(BOARD_UPDATE, customerserviceAPI.update);
// const boardDeleteSaga = createRequestSaga(BOARD_DELETE, customerserviceAPI.boardDelete);
//
// const boardListSaga = createRequestSaga(BOARD_LIST, customerserviceAPI.boardList);
//
// const faqBoardListSaga = createRequestSaga(FAQ_BOARD_LIST, customerserviceAPI.boardList);
// const termsBoardListSaga = createRequestSaga(TERMS_BOARD_LIST, customerserviceAPI.boardList);

export function* customerSaga(){
    yield takeLatest(ADD_CUSTOMER, addCustomerSaga);
    yield takeLatest(GET_CUSTOMER_INFO, getCustomerInfoSaga);
    yield takeLatest(UPDATE_CUSTOMER_INFO, updateCustomerInfoSaga);
    yield takeLatest(GET_CUSTOMER_LIST, getCustomerListSaga);
    yield takeLatest(GET_GROUP_BY_CUSTOMER_LIST, getGroupByCustomerListSaga);
    yield takeLatest(GET_CUSTOMER_EVENT_LIST, getCustomerEventListSaga);
    yield takeLatest(GET_CUSTOMER_GROUP_LIST, getCustomerGroupListSaga);
    yield takeLatest(ADD_CUSTOMER_GROUP, addCustomerGroupSaga);
    yield takeLatest(DELETE_CUSTOMER_GROUP, deleteCustomerGroupSaga);
    yield takeLatest(UPDATE_CUSTOMER_GROUP, updateCustomerGroupSaga);
    yield takeLatest(UPDATE_CUSTOMER_GROUP_MEMBER, updateCustomerGroupMemberSaga);
    yield takeLatest(CUSTOMER_BULK_UPLOAD_SAMPLE_DOWNLOAD, customerBulkUploadSampleDownLoadSaga);
    yield takeLatest(CUSTOMER_FILE_UPLOAD, customerFileUploadSaga);
}

const initialState = {
    customerInfo:null,
    customer:{
        list:null,
        page:null,
        error:null
    },
    customerEvent:{
        list:null,
        page:null,
        error:null
    },
    group:{
        list:null,
        page:null,
        error:null
    },
    groupByList:{
        usedGroup:null,
        unusedGroup:null
    },
    bulkUpload:{
        result:false,
        error:null
    },
    fileUploadError:null,
    downloadError:null,
    error:null,
    customerAddError:null,
    addError:null,
    deleteError:null,
    updateError:null,
    groupUpdateError:null,
    customerAddResult:false,
    addResult:false,
    deleteResult:false,
    updateResult:false,
    groupUpdateResult:false,
    infoEditResult:false
}

const customer = handleActions(
    {
        [ADD_CUSTOMER_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.customerAddError = null
                draft.customerAddResult = true
            }),
        [ADD_CUSTOMER_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.customerAddError = error.response.data
                draft.customerAddResult = initialState.customerAddResult
            }),
        [GET_CUSTOMER_INFO_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.customerInfo = data
                draft.customer.error = data.null
            }),
        [GET_CUSTOMER_INFO_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.customerInfo = initialState.customerInfo
                draft.customer.error = error.response.data
            }),
        [UPDATE_CUSTOMER_INFO_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.infoEditResult = true
                draft.error = data.null
            }),
        [UPDATE_CUSTOMER_INFO_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.infoEditResult = initialState.infoEditResult
                draft.error = error.response.data
            }),
        [GET_CUSTOMER_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.customer.list = data.list
                draft.customer.page = data.page
                draft.customer.error = data.null
            }),
        [GET_CUSTOMER_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.customer = initialState.customer
                draft.customer.error = error.response.data
            }),
        [GET_GROUP_BY_CUSTOMER_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.groupByList.usedGroup = data.usedGroup
                draft.groupByList.unusedGroup = data.unusedGroup
                draft.customer.error = data.null
            }),
        [GET_GROUP_BY_CUSTOMER_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.groupByList = initialState.groupByList
                draft.customer.error = error.response.data
            }),
        [GET_CUSTOMER_EVENT_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.customerEvent.list = data.list
                draft.customerEvent.page = data.page
                draft.customerEvent.error = data.null
            }),
        [GET_CUSTOMER_EVENT_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.customerEvent = initialState.customer
                draft.customerEvent.error = error.response.data
            }),
        [GET_CUSTOMER_GROUP_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.group.list = data.list
                draft.group.page = data.page
                draft.group.error = data.null
            }),
        [GET_CUSTOMER_GROUP_LIST_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.group = initialState.group
                draft.group.error = error.response.data
            }),
        [ADD_CUSTOMER_GROUP_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.addResult = true
                draft.addError = null
            }),
        [ADD_CUSTOMER_GROUP_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.addResult = false
                draft.addError = error.response.data
            }),
        [DELETE_CUSTOMER_GROUP_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.deleteResult = true
                draft.deleteError = null
            }),
        [DELETE_CUSTOMER_GROUP_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.deleteResult = false
                draft.deleteError = error.response.data
            }),
        [UPDATE_CUSTOMER_GROUP_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.updateResult = true
                draft.updateError = null
            }),
        [UPDATE_CUSTOMER_GROUP_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.updateResult = false
                draft.updateError = error.response.data
            }),
        [UPDATE_CUSTOMER_GROUP_MEMBER_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.groupUpdateResult = true
                draft.groupUpdateError = null
            }),
        [UPDATE_CUSTOMER_GROUP_MEMBER_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.groupUpdateResult = false
                draft.groupUpdateError = error.response.data
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form],
        }),
        [SET_ERROR]: (state, {payload: {key, value}}) =>
            produce(state, draft => {
                draft[key] = value;
            }),
        [INITIALIZE_FORM]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form],
        }),
        [CUSTOMER_FILE_UPLOAD_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.bulkUpload.result = true
                draft.bulkUpload.error = null

            }),
        [CUSTOMER_FILE_UPLOAD_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.bulkUpload.result = false
                draft.bulkUpload.error = error.response.data
            }),
        [CUSTOMER_BULK_UPLOAD_SAMPLE_DOWNLOAD_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                let blob = new Blob([data], { type: 'application/vnd.ms-excel'});
                FileSaver.saveAs(blob, '고객등록샘플.xls');
                draft.downloadError = null
            }),
    },
    initialState
)


export default customer;
