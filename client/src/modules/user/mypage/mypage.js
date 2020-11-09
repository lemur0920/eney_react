import {createAction, handleActions} from 'redux-actions';
import {takeLatest, call} from 'redux-saga/effects'
import * as mypageAPI from '../../../lib/api/user/mypage/mypage'
import createRequestSaga, {createRequestActionTypes} from "../../../lib/createRequestSaga";
import produce from "immer";

const [MYINFO, MYINFO_SUCCESS, MYINFO_FAILURE] = createRequestActionTypes('mypage/MYINFO');
// const [EPOINT_PAY_INFO, EPOINT_PAY_INFO_SUCCESS, EPOINT_PAY_INFO_FAILURE] = createRequestActionTypes('mypage/EPOINT_PAY_INFO');
const CHANGE_FIELD  = 'mypage/CHANGE_FIELD';
const INITIALIZE  = 'mypage/INITIALIZE';
const [UPDATE_EMAIL, UPDATE_EMAIL_SUCCESS, UPDATE_EMAIL_FAILURE] = createRequestActionTypes('mypage/UPDATE_EMAIL');
const [UPDATE_PHONE, UPDATE_PHONE_SUCCESS, UPDATE_PHONE_FAILURE] = createRequestActionTypes('mypage/UPDATE_PHONE');
const [UPDATE_COMPANY_KIND, UPDATE_COMPANY_KIND_SUCCESS, UPDATE_COMPANY_KIND_FAILURE] = createRequestActionTypes('mypage/UPDATE_COMPANY_KIND');
const [UPDATE_PW, UPDATE_PW_SUCCESS, UPDATE_PW_FAILURE] = createRequestActionTypes('mypage/UPDATE_PW');
const [UPDATE_ADDRESS, UPDATE_ADDRESS_SUCCESS, UPDATE_ADDRESS_FAILURE] = createRequestActionTypes('mypage/UPDATE_ADDRESS');
const [MYSERVICE_LIST, MYSERVICE_LIST_SUCCESS, MYSERVICE_LIST_FAILURE] = createRequestActionTypes('mypage/MYSERVICE_LIST');
const [POINT_CHARGE_LOG_LIST, POINT_CHARGE_LOG_LIST_SUCCESS, POINT_CHARGE_LOG_LIST_FAILURE] = createRequestActionTypes('mypage/POINT_CHARGE_LOG_LIST');
const [POINT_PAYMENT_LOG_LIST, POINT_PAYMENT_LOG_LIST_SUCCESS, POINT_PAYMENT_LOG_LIST_FAILURE] = createRequestActionTypes('mypage/POINT_PAYMENT_LOG_LIST');
const [API_TOKEN_LIST, API_TOKEN_LIST_SUCCESS, API_TOKEN_LIST_FAILURE] = createRequestActionTypes('mypage/API_TOKEN_LIST');
const [GEN_TOKEN, GEN_TOKEN_SUCCESS, GEN_TOKEN_FAILURE] = createRequestActionTypes('mypage/GEN_TOKEN');
const [UPLOAD_LICENSE, UPLOAD_LICENSE_SUCCESS, UPLOAD_LICENSE_FAILURE] = createRequestActionTypes('mypage/UPLOAD_LICENSE');
const [DELETE_LICENSE, DELETE_LICENSE_SUCCESS, DELETE_LICENSE_FAILURE] = createRequestActionTypes('mypage/DELETE_LICENSE');

const [REGISTER_COUPON, REGISTER_COUPON_SUCCESS, REGISTER_COUPON_FAILURE] = createRequestActionTypes('mypage/REGISTER_COUPON');

const INIT_CERTIFY  = 'mypage/INIT_CERTIFY';

export const myinfo = createAction(MYINFO);
// export const epointPayInfo = createAction(EPOINT_PAY_INFO);
export const updateEmail = createAction(UPDATE_EMAIL,(email) => (
    email
));

export const initialize = createAction(INITIALIZE, from => from);
export const updatePhone = createAction(UPDATE_PHONE,(phone) => (
    phone
));

export const updateCompanyKind = createAction(UPDATE_COMPANY_KIND,(companyKind) => (
    companyKind
));

export const updatePw = createAction(UPDATE_PW,({currentPw, newPw, newPwCheck}) => (
    {currentPw, newPw, newPwCheck}
));

export const updateAddress = createAction(UPDATE_ADDRESS,(address) => (
    address
));

export const myserviceList = createAction(MYSERVICE_LIST, ({myservicePage}) =>({
    myservicePage
}));
export const pointChargeLogList = createAction(POINT_CHARGE_LOG_LIST,({chargePage, startDate, endDate}) => ({
    chargePage, startDate, endDate
}));

export const pointPaymentLogList = createAction(POINT_PAYMENT_LOG_LIST,({paymentPage, startDate, endDate}) => ({
    paymentPage, startDate, endDate
}));

export const apiTokenList = createAction(API_TOKEN_LIST,({page}) => ({
    page
}));

export const genToken = createAction(GEN_TOKEN,(token) => (
    token
));

export const registerCoupon = createAction(REGISTER_COUPON,(couponNum) => (
    couponNum
));

export const uploadLicense = createAction(UPLOAD_LICENSE);

export const deleteLicense = createAction(DELETE_LICENSE,(idx) => (
    idx
));

export const changeField = createAction(
    CHANGE_FIELD,
    ({form, key, value}) => ({
        form,
        key,
        value
    }),
);

const myinfoSaga = createRequestSaga(MYINFO, mypageAPI.myinfo);
const updateEmailSaga = createRequestSaga(UPDATE_EMAIL, mypageAPI.updateEmail);
const updatePhoneSaga = createRequestSaga(UPDATE_PHONE, mypageAPI.updatePhone);
const updateCompanyKindSaga = createRequestSaga(UPDATE_COMPANY_KIND, mypageAPI.updateCompanyKind);
const updatePwSaga = createRequestSaga(UPDATE_PW, mypageAPI.updatePw);
const updateAddressSaga = createRequestSaga(UPDATE_ADDRESS, mypageAPI.updateAddress);
const myserviceListSaga = createRequestSaga(MYSERVICE_LIST, mypageAPI.myserviceList);
const pointChargeLogSaga = createRequestSaga(POINT_CHARGE_LOG_LIST, mypageAPI.pointChargeLogList);
const pointPaymentLogSaga = createRequestSaga(POINT_PAYMENT_LOG_LIST, mypageAPI.pointPaymentLogList);
const apiTokenListSaga = createRequestSaga(API_TOKEN_LIST, mypageAPI.apiTokenList);
const genTokenSaga = createRequestSaga(GEN_TOKEN, mypageAPI.genToken);
const registerCouponSaga = createRequestSaga(REGISTER_COUPON, mypageAPI.registerCoupon);
const uploadLicenseSaga = createRequestSaga(UPLOAD_LICENSE, mypageAPI.uploadLicence);
const deleteLicenseSaga = createRequestSaga(DELETE_LICENSE, mypageAPI.deleteLicence);

export function* mypageSaga(){
    yield takeLatest(MYINFO, myinfoSaga);
    yield takeLatest(UPDATE_EMAIL, updateEmailSaga);
    yield takeLatest(UPDATE_PHONE, updatePhoneSaga);
    yield takeLatest(UPDATE_ADDRESS, updateAddressSaga);
    yield takeLatest(MYSERVICE_LIST, myserviceListSaga);
    yield takeLatest(POINT_CHARGE_LOG_LIST, pointChargeLogSaga);
    yield takeLatest(POINT_PAYMENT_LOG_LIST, pointPaymentLogSaga);
    yield takeLatest(API_TOKEN_LIST, apiTokenListSaga);
    yield takeLatest(GEN_TOKEN, genTokenSaga);
    yield takeLatest(UPDATE_PW, updatePwSaga);
    yield takeLatest(UPDATE_COMPANY_KIND, updateCompanyKindSaga);
    yield takeLatest(REGISTER_COUPON, registerCouponSaga);
    yield takeLatest(UPLOAD_LICENSE, uploadLicenseSaga);
    yield takeLatest(DELETE_LICENSE, deleteLicenseSaga);
}

const initialState = {
    user:{
        name: "",
        userid: "",
        birth_day: "",
        password: "",
        company_name:"",
        company_kind:"",
        email: "",
        phone_number: "",
        epoint:"",
        postCode:"",
        address:"",
        addressDetail:"",
        password_last_update:""
    },
    uploadLicense:{
        list:null,
        error:null
    },
    deleteLicense:{
        result:false,
        error:null
    },
    isSuccess:{
        phoneNumber:false
    },
    companyKind:[],
    address:{
        postCode:"",
        address:"",
        addressDetail:""
    },
    changePw:{
        currentPw:"",
        newPw:"",
        newPwCheck:"",
        error:null
    },
    coupon:{
        registerResult:null,
        error:null,
    },
    genToken:{
        isSuccess:false,
        error:null,
    },
    authCheck:null,
    serviceList:null,
    serviceListPage:null,
    pointChargeLogList:null,
    pointChargeLogPage:null,
    pointPaymentLogList:null,
    pointPaymentLogPage:null,
    apiTokenList:null,
    apiTokenPage:null,
    checkError:null
}

const mypage = handleActions(
    {
        [MYINFO_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.user = data.user
                draft.companyKind = data.companyKind
                draft.checkError = null
            }),
        [MYINFO_FAILURE]:(state,{payload:error}) => ({
            ...state,
            user:initialState.user,
            checkError:error
        }),
        [UPDATE_EMAIL_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.user.email = data
                draft.checkError = null
            }),
        [UPDATE_EMAIL_FAILURE]:(state,{payload:error}) => ({
            ...state,
            checkError:error.response.data
        }),
        [UPDATE_PHONE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.user.phone = data
                draft.checkError = null
                draft.isSuccess.phoneNumber = true
            }),
        [UPDATE_PHONE_FAILURE]:(state,{payload:error}) => ({
            ...state,
            isSuccess:{
                ...state.isSuccess,
                phoneNumber:false,
            },
            checkError:error.response.data
        }),
        [UPDATE_COMPANY_KIND_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.user.company_kind = data
                draft.checkError = null
            }),
        [UPDATE_COMPANY_KIND_FAILURE]:(state,{payload:error}) => ({
            ...state,
            checkError:error.response.data
        }),
        [UPDATE_PW_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.user.password_last_update = data
                draft.changePw = initialState.changePw
            }),
        [UPDATE_PW_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.changePw.error = error.response.data
            }),
        [UPDATE_ADDRESS_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.user.address = data.address
                draft.user.addressDetail = data.addressDetail
                draft.user.postCode = data.postCode
                draft.checkError = null
            }),
        [UPDATE_ADDRESS_FAILURE]:(state,{payload:error}) => ({
            ...state,
            checkError:error.response.data
        }),
        [MYSERVICE_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.serviceList = data.list
                draft.serviceListPage = data.page
                draft.checkError = null
            }),
        [MYSERVICE_LIST_FAILURE]:(state,{payload:error}) => ({
            ...state,
            serviceList:null,
            serviceListPage : null,
            checkError:error
        }),
        [POINT_CHARGE_LOG_LIST_SUCCESS]:(state, {payload: data}) =>
        produce(state, draft => {
            draft.pointChargeLogList = data.logList
            draft.pointChargeLogPage = data.page
            draft.checkError = null
        }),
        [POINT_CHARGE_LOG_LIST_FAILURE]:(state,{payload:error}) => ({
            ...state,
            pointChargeLogList:null,
            pointChargeLogPage:null,
            checkError:error
        }),
        [POINT_PAYMENT_LOG_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.pointPaymentLogList = data.logList
                draft.pointPaymentLogPage = data.page
                draft.checkError = null
            }),
        [POINT_PAYMENT_LOG_LIST_FAILURE]:(state,{payload:error}) => ({
            ...state,
            pointPaymentLogList:null,
            pointPaymentLogPage:null,
            checkError:error
        }),
        [API_TOKEN_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.apiTokenList = data.tokenList
                draft.apiTokenPage = data.page
                draft.checkError = null
            }),
        [API_TOKEN_LIST_FAILURE]:(state,{payload:error}) => ({
            ...state,
            apiTokenList:null,
            apiTokenPage:null,
            checkError:error.response.data
        }),
        [GEN_TOKEN_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.genToken.isSuccess = true
                draft.genToken.error = null
            }),
        [GEN_TOKEN_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.genToken.isSuccess = true
                draft.genToken.error = error.response.data
            }),
        [CHANGE_FIELD]: (state, {payload: {form, key, value}}) =>
            produce(state, draft => {
                draft[form][key] = value;
            }),
        [REGISTER_COUPON_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.coupon.registerResult = data
                draft.coupon.error = null
            }),
        [REGISTER_COUPON_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.coupon.registerResult = false
                draft.coupon.error = error.response.data
            }),
        [UPLOAD_LICENSE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.uploadLicense.list = data
                draft.uploadLicense.error = null
            }),
        [UPLOAD_LICENSE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.uploadLicense.list = null
                draft.uploadLicense.error = error.response.data
            }),
        [DELETE_LICENSE_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.deleteLicense.result = true
                draft.deleteLicense.error = null
            }),
        [DELETE_LICENSE_FAILURE]:(state, {payload: error}) =>
            produce(state, draft => {
                draft.deleteLicense.result = false
                draft.deleteLicense.error = error.response.data
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form],
        }),
    },
    initialState
)


export default mypage;
