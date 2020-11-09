import {call, put} from 'redux-saga/effects'
import {startLoading, finishLoading} from "../modules/loading";
import swal from 'sweetalert';
import React from "react";
import { push } from 'react-router-redux';
import { takeEvery, getContext } from 'redux-saga/effects';

export const createRequestActionTypes = type => {
    const SUCCESS = `${type}_SUCCESS`;
    const FAILURE = `${type}_FAILURE`;
    return [type, SUCCESS, FAILURE]
}

export default function createRequestSaga(type, request) {

    const SUCCESS = `${type}_SUCCESS`;
    const FAILURE = `${type}_FAILURE`;

    return function*(action){
        yield put(startLoading(type));
        try{
            const response = yield call(request, action.payload)
            // console.log("====request 성공=====")
            // console.log(type)
            // console.log(response)
            // console.log("====request 성공=====")
            yield put({
                type:SUCCESS,
                payload:response.data,
                mate: response
            })
        }catch(e){
            if(e.response.status === 403){
                const history = yield getContext('history');
                swal("서비스 신청 후 이용가능합니다").then(() => {
                    history.push('/');
                })
            }
            yield put({
                type:FAILURE,
                payload:e,
                error:true,
            })

            // if(e.response.status === 401){
            //     swal("로그인 후 이용해주세요!");
            //     history.push('/auth/login');
            // }
        }
        yield put(finishLoading(type))
    }
}