import {combineReducers} from 'redux';
import {all} from 'redux-saga/effects';
import auth, {authSaga} from './auth';
import loading from './loading';
// import user,{userSaga} from "./user";
import layout from "./admin/layout";
import users, {usersSaga} from "./admin/users";
import custom_user_count, {customUserCountSaga} from "./admin/custom_user_count/custom_user_count";
import service_apply_manage, {serviceApplyManageSaga} from "./admin/service_apply/service_apply_manage";
import customer_case, {customerCaseSaga} from "./admin/customer_case/customer_case";
import cloud, {cloudSaga} from "./admin/service_apply/cloud";
import coupon, {couponSaga} from "./admin/coupon/coupon";
import coloring, {coloringSaga} from "./admin/coloring/coloring";
import mypage, {mypageSaga} from "./user/mypage/mypage";
import customerservice, {customerserviceSaga} from "./customerservice/customerservice";
import payment, {paymentSaga} from "./payment/payment";
import serviceApply, {serviceApplySaga} from "./service_apply/serviceAppy";
import fileupload, {fileuploadSaga} from "./util/fileupload";

import patchcall, {patchcallSaga} from "./service/patchcall";
import template, {templateSaga} from "./service/template";
import message_result, {messageResultSaga} from "./service/message_result";
import message, {messageSaga} from "./service/message";
import kakao, {kakaoSaga} from "./service/kakao";
import block_number, {blockNumberSaga} from "./admin/block_number/block_number";

import customer, {customerSaga} from "./service/customer";
import patchcall_dashboard, {patchcallDashBoardSaga} from "./service/patchcall_dashboard";
import acs, {acsSaga} from "./service/acs";
import analytics, {analyticsSaga} from "./service/analytics";


const rootReducer = combineReducers({
    auth,
    loading,
    // user,
    layout,
    users,
    custom_user_count,
    customer_case,
    service_apply_manage,
    coupon,
    coloring,
    block_number,
    mypage,
    payment,
    customerservice,
    fileupload,
    serviceApply,
    patchcall,
    patchcall_dashboard,
    acs,
    analytics,
    cloud,
    template,
    customer,
    message_result,
    message,
    kakao,
})

export function* rootSaga() {
    yield all([authSaga(), usersSaga(), serviceApplyManageSaga(), mypageSaga(), customerserviceSaga(),
        fileuploadSaga(), paymentSaga(), couponSaga(), coloringSaga(), blockNumberSaga(),
        serviceApplySaga(), customUserCountSaga(), customerCaseSaga(), patchcallSaga(), acsSaga(), patchcallDashBoardSaga(), analyticsSaga(),
        cloudSaga(), customerSaga(), templateSaga(), messageResultSaga(), messageSaga(), kakaoSaga()]);
}

export default rootReducer;