import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import * as patchcallDashboardAPI from '../../lib/api/service/patchcall_dashboard';


const [GET_COMPARE_DATA,GET_COMPARE_DATA_SUCCESS, GET_COMPARE_DATA_FAILURE] = createRequestActionTypes('patchcallDashboard/GET_COMPARE_DATA')
const [GET_CALL_RATIO,GET_CALL_RATIO_SUCCESS, GET_CALL_RATIO_FAILURE] = createRequestActionTypes('patchcallDashboard/GET_CALL_RATIO')
const [GET_DATE_BY_CALL_DATA,GET_DATE_BY_CALL_DATA_SUCCESS, GET_DATE_BY_CALL_DATA_FAILURE] = createRequestActionTypes('patchcallDashboard/GET_DATE_BY_CALL_DATA')
const [GET_TIME_BY_CALL_DATA,GET_TIME_BY_CALL_DATA_SUCCESS, GET_TIME_BY_CALL_DATA_FAILURE] = createRequestActionTypes('patchcallDashboard/GET_TIME_BY_CALL_DATA')
const [GET_TIME_AVG_CALL_DATA,GET_TIME_AVG_CALL_DATA_SUCCESS, GET_TIME_AVG_CALL_DATA_FAILURE] = createRequestActionTypes('patchcallDashboard/GET_TIME_AVG_CALL_DATA')

const INITIALIZE  = 'patchcallDashboard/INITIALIZE';

// export const getCustomerCaseCate = createAction(GET_CUSTOMER_CASE_CATE)
export const getCompareData = createAction(GET_COMPARE_DATA)
export const getCallRatios = createAction(GET_CALL_RATIO)
export const getDateByCallData = createAction(GET_DATE_BY_CALL_DATA, (date) => (date))
export const getTimeByCallData = createAction(GET_TIME_BY_CALL_DATA, (date) => (date))
export const getTimeAvgCallData = createAction(GET_TIME_AVG_CALL_DATA, (date) => (date))
// export const getAgentList = createAction(GET_AGENT_LIST, ({page, cate,search_filed, search}) => ({page, cate,search_filed, search}))
// export const updateAgent = createAction(UPDATE_AGENT, (agent) => (agent))
//
// //콜로그 조회
// export const getCalllogList = createAction(GET_CALLLOG_LIST, ({page, cate,search_filed, search,startDate,endDate}) => ({page, cate,search_filed, search,startDate,endDate}))
//
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


export const initialize = createAction(INITIALIZE, from => from);

// const getCustomerCaseCateSage = createRequestSaga(GET_CUSTOMER_CASE_CATE, customerCaseAPI.getCustomerCaseCate);
const getCompareDataSage = createRequestSaga(GET_COMPARE_DATA, patchcallDashboardAPI.getCompareData);
const getCallRatiosSage = createRequestSaga(GET_CALL_RATIO, patchcallDashboardAPI.getCallRatio);
const getDateByCallDataSage = createRequestSaga(GET_DATE_BY_CALL_DATA, patchcallDashboardAPI.getDateByCallData);
const getTimeByCallDataSage = createRequestSaga(GET_TIME_BY_CALL_DATA, patchcallDashboardAPI.getTimeByCallCount);
const getTimeAvgCallDataSaga = createRequestSaga(GET_TIME_AVG_CALL_DATA, patchcallDashboardAPI.getTimeAvgCallCount);
// const getAgentListSage = createRequestSaga(GET_AGENT_LIST, patchcallAPI.getAgentList);
// const updateAgentSage = createRequestSaga(UPDATE_AGENT, patchcallAPI.updateAgent);
//
// const getCalllogListSage = createRequestSaga(GET_CALLLOG_LIST, patchcallAPI.getCalllogList);
// const getCidListSage = createRequestSaga(GET_CID_LIST, patchcallAPI.getCidList);


// const addCustomerCaseSaga = createRequestSaga(ADD_CUSTOMER_CASE, customerCaseAPI.addCustomerCase);
// const editCustomerCaseSaga = createRequestSaga(EDIT_CUSTOMER_CASE, customerCaseAPI.editCustomerCase);
// const deleteCustomerCaseSaga = createRequestSaga(DELETE_CUSTOMER_CASE, customerCaseAPI.deleteCustomerCase);

// const updateCustomUserCountSaga = createRequestSaga(UPDATE_CUSTOM_USER_COUNT, customUserCountAPI.updateCustomUserCount);

export function* patchcallDashBoardSaga(){
    yield takeLatest(GET_COMPARE_DATA, getCompareDataSage);
    yield takeLatest(GET_CALL_RATIO, getCallRatiosSage);
    yield takeLatest(GET_DATE_BY_CALL_DATA, getDateByCallDataSage);
    yield takeLatest(GET_TIME_BY_CALL_DATA, getTimeByCallDataSage);
    yield takeLatest(GET_TIME_AVG_CALL_DATA, getTimeAvgCallDataSaga);

}

const initialState = {
    compareData:{
        conversion_rate:null,
        total_call:null,
        result_call:null,
        svc_duration:null,
        total_call_change_rate: null,
        result_call_change_rate: null,
        conversion_rate_change_rate: null,
        svc_duration_change_rate: null
    },
    callRatio:null,
    dateByCallData:{
        dateList:[],
        countList:[],
    },
    timeByCallData:{
        timeList:[],
        countList:[],
        date:""
    },
    timeAvg:{
        timeList:[],
        countList:[],
        startDate:"",
        endDate:"",
    },
    visitType:{
        revisit_visit:null,
        new_visit:null,
        virtual_ani:null,
        mobile_ani:null,
        total_ani:null,
        area_ani: null

    },
    agent:{
        agentList:[]
    },
    location:{
        sidoList:[],
        countList:[],
        startDate:"",
        endDate:"",
    },
    dateDataError:false,
    timeDataError:false,
    error:null

};

const patchcall_dashboard = handleActions(
    {
        [GET_COMPARE_DATA_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.compareData = data
            }),
        [GET_COMPARE_DATA_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.compareData = initialState.compareData
            }),
        [GET_CALL_RATIO_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.callRatio = data
            }),
        [GET_CALL_RATIO_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.callRatio = initialState.callRatio
            }),
        [GET_DATE_BY_CALL_DATA_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.dateByCallData.dateList = data.dateList
                draft.dateByCallData.countList = data.countList
            }),
        [GET_DATE_BY_CALL_DATA_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.dateByCallData = initialState.dateByCallData
                // draft.compareData = initialState.compareData
            }),
        [GET_TIME_BY_CALL_DATA_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.timeByCallData.timeList = data.timeList
                draft.timeByCallData.countList = data.countList
                draft.timeByCallData.date = data.date
            }),
        [GET_TIME_BY_CALL_DATA_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.timeByCallData = initialState.timeByCallData
            }),
        [GET_TIME_AVG_CALL_DATA_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.timeAvg.timeList = data.timeAvg.timeList
                draft.timeAvg.countList = data.timeAvg.countList
                draft.timeAvg.startDate = data.timeAvg.startDate
                draft.timeAvg.endDate = data.timeAvg.endDate
                draft.visitType.revisit_visit = data.visit[0].revisit_visit
                draft.visitType.new_visit = data.visit[0].new_visit
                draft.visitType.virtual_ani = data.visit[1].virtual_ani
                draft.visitType.mobile_ani = data.visit[1].mobile_ani
                draft.visitType.total_ani = data.visit[1].total_ani
                draft.visitType.area_ani = data.visit[1].area_ani
                draft.agent.agentList = data.agent
                draft.location = data.location

            }),
        [GET_TIME_AVG_CALL_DATA_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.timeAvg = initialState.timeAvg
                draft.visitType = initialState.visitType
                draft.agent = initialState.agent
                draft.location = initialState.location
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),

    },
    initialState
);
export default patchcall_dashboard;