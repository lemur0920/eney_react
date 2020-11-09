import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import * as acsAPI from '../../lib/api/service/acs';


// const [GET_CUSTOMER_CASE_CATE,GET_CUSTOMER_CASE_CATE_SUCCESS, GET_CUSTOMER_CASE_CATE_FAILURE] = createRequestActionTypes('service/GET_CUSTOMER_CASE_CATE')

const [NUMBER_CHECK,NUMBER_CHECK_SUCCESS, NUMBER_CHECK_FAILURE] = createRequestActionTypes('acs/NUMBER_CHECK')
const [ACS_SUBMIT,ACS_SUBMIT_SUCCESS, ACS_SUBMIT_FAILURE] = createRequestActionTypes('acs/ACS_SUBMIT')

const [CHECK_ACS_NUMBER,CHECK_ACS_NUMBER_SUCCESS, CHECK_ACS_NUMBER_FAILURE] = createRequestActionTypes('acs/CHECK_ACS_NUMBER')

// const [UPDATE_AGENT,UPDATE_AGENT_SUCCESS, UPDATE_AGENT_FAILURE] = createRequestActionTypes('patchcall/UPDATE_AGENT')
// const [GET_AGENT_LIST,GET_AGENT_LIST_SUCCESS, GET_AGENT_LIST_FAILURE] = createRequestActionTypes('patchcall/GET_AGENT_LIST')
//
// const [GET_CALLLOG_LIST,GET_CALLLOG_LIST_SUCCESS, GET_CALLLOG_LIST_FAILURE] = createRequestActionTypes('patchcall/GET_CALLLOG_LIST')
//
// const [GET_CID_LIST,GET_CID_LIST_SUCCESS, GET_CID_LIST_FAILURE] = createRequestActionTypes('patchcall/GET_CID_LIST')

// const [EDIT_CUSTOMER_CASE,EDIT_CUSTOMER_CASE_SUCCESS, EDIT_CUSTOMER_CASE_FAILURE] = createRequestActionTypes('customerCase/EDIT_CUSTOMER_CASE')
// const [DELETE_CUSTOMER_CASE,DELETE_CUSTOMER_CASE_SUCCESS, DELETE_CUSTOMER_CASE_FAILURE] = createRequestActionTypes('customerCase/DELETE_CUSTOMER_CASE')
// const [UPDATE_CUSTOM_USER_COUNT,UPDATE_CUSTOM_USER_COUNT_SUCCESS, UPDATE_CUSTOM_USER_COUNT_FAILURE] = createRequestActionTypes('adminCustomUserCount/UPDATE_CUSTOM_USER_COUNT')
// const DISABLE_CALLBACK  = 'patchcall/DISABLE_CALLBACK';
// const CHANGE_AGENT_FIELD  = 'patchcall/CHANGE_AGENT_FIELD';
const INITIALIZE  = 'acs/INITIALIZE';

// export const getCustomerCaseCate = createAction(GET_CUSTOMER_CASE_CATE)
// export const getAgent = createAction(GET_AGENT, (vno) => (vno))
// export const getAgentList = createAction(GET_AGENT_LIST, ({page, cate,search_filed, search}) => ({page, cate,search_filed, search}))
// export const updateAgent = createAction(UPDATE_AGENT, (agent) => (agent))

//발신번호 체크
export const numberCheck = createAction(NUMBER_CHECK, (num) => (num))
export const acsSubmit = createAction(ACS_SUBMIT, (num) => (num))
export const checkAcsNumber = createAction(CHECK_ACS_NUMBER, (data) => (data))

// //CID 리스트
// export const getCidList = createAction(GET_CID_LIST, ({page}) => ({page}))
//
//
// export const changeAgentField = createAction(
//     CHANGE_AGENT_FIELD,
//     ({key, value}) => ({
//         key,
//         value
//     }),
// );

// export const disableCallback = createAction(DISABLE_CALLBACK);
//
//
export const initialize = createAction(INITIALIZE, from => from);

// const getCustomerCaseCateSage = createRequestSaga(GET_CUSTOMER_CASE_CATE, customerCaseAPI.getCustomerCaseCate);
const numberCheckSaga = createRequestSaga(NUMBER_CHECK, acsAPI.numberCheck);
const acsSubmitkSaga = createRequestSaga(ACS_SUBMIT, acsAPI.acsSubmit);
const checkAcsNumberSaga = createRequestSaga(CHECK_ACS_NUMBER, acsAPI.checkAcsNumber);


// const addCustomerCaseSaga = createRequestSaga(ADD_CUSTOMER_CASE, customerCaseAPI.addCustomerCase);
// const editCustomerCaseSaga = createRequestSaga(EDIT_CUSTOMER_CASE, customerCaseAPI.editCustomerCase);
// const deleteCustomerCaseSaga = createRequestSaga(DELETE_CUSTOMER_CASE, customerCaseAPI.deleteCustomerCase);

// const updateCustomUserCountSaga = createRequestSaga(UPDATE_CUSTOM_USER_COUNT, customUserCountAPI.updateCustomUserCount);

export function* acsSaga(){
    yield takeLatest(NUMBER_CHECK, numberCheckSaga);
    yield takeLatest(ACS_SUBMIT, acsSubmitkSaga);
    yield takeLatest(CHECK_ACS_NUMBER, checkAcsNumberSaga);
}

const initialState = {
    acs:null,
    check:{
        numberCheck:false,
        acsSubmitCheck:false,
        checkAcsNumberCheck:false
    },
    error:{
        numberCheck:null,
        acsSubmitCheck:null,
        checkAcsNumberCheck:null
    }
};

const acs = handleActions(
    {
        [NUMBER_CHECK_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.check.numberCheck = true
                draft.error.numberCheck = null
            }),
        [NUMBER_CHECK_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.check.numberCheck = false
                draft.error.numberCheck = error.response.data
            }),
        [ACS_SUBMIT_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.acs = data
                draft.check.numberCheck = true
                draft.error.acsSubmitCheck = null
            }),
        [ACS_SUBMIT_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.check.numberCheck = false
                draft.error.acsSubmitCheck = error.response.data
            }),
        [CHECK_ACS_NUMBER_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.check.checkAcsNumberCheck = true
                draft.error.checkAcsNumberCheck = null
            }),
        [CHECK_ACS_NUMBER_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.check.checkAcsNumberCheck = false
                draft.error.checkAcsNumberCheck = error.response.data
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),

    },
    initialState
);
export default acs;