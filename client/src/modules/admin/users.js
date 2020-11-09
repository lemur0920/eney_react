import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import * as authAPI from '../../lib/api/users';


const [USER_LIST,USER_LIST_SUCCESS, USER_LIST_FAILURE] = createRequestActionTypes('users/USER_LIST')
const [GET_USER_INFO,GET_USER_INFO_SUCCESS, GET_USER_INFO_FAILURE] = createRequestActionTypes('users/GET_USER_INFO')
const [UPDATE_USER_INFO,UPDATE_USER_INFO_SUCCESS, UPDATE_USER_INFO_FAILURE] = createRequestActionTypes('users/UPDAATE_USER_INFO')
const INITIALIZE_FORM  = 'auth/INITIALIZE_FORM';

export const userList = createAction(USER_LIST, (data) => (data))
export const getUserInfo = createAction(GET_USER_INFO, ({idx}) => ({idx}))
export const updateUserInfo = createAction(UPDATE_USER_INFO, (userInfo) => (userInfo))
export const initializeForm = createAction(INITIALIZE_FORM, from => from);

const userListSaga = createRequestSaga(USER_LIST, authAPI.userList);
const getUserInfoSaga = createRequestSaga(GET_USER_INFO, authAPI.getUserInfo);
const updateUserInfoSaga = createRequestSaga(UPDATE_USER_INFO, authAPI.updateUserInfo);

export function* usersSaga(){
    yield takeLatest(USER_LIST, userListSaga);
    yield takeLatest(GET_USER_INFO, getUserInfoSaga);
    yield takeLatest(UPDATE_USER_INFO, updateUserInfoSaga);
}

const initialState = {
    users:{
        page:null,
        list:null
    },
    error:null,
    pageInfo:null,
    userInfo:{
        license:null,
        info:null,
        result:false,
        error:null,
    },
    userInfoUpdate:{
        result:false,
        error:null
    }
};

const users = handleActions(
    {
        [USER_LIST_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.users.list = data.list
                draft.users.page = data.page
            }),
        [USER_LIST_FAILURE]:(state, {payload: error}) =>({
            ...state,
            error
        }),
        [GET_USER_INFO_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.userInfo.license = data.license
                draft.userInfo.info = data.userInfo
                draft.userInfo.result = true
                draft.userInfo.error = null
            }),
        [GET_USER_INFO_FAILURE]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.userInfo.license = null
                draft.userInfo.info = null
                draft.userInfo.result = false
                draft.userInfo.error = error.response.data
            }),
        [UPDATE_USER_INFO_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.userInfoUpdate.result = true
                draft.userInfoUpdate.error = null
            }),
        [UPDATE_USER_INFO_FAILURE]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.userInfoUpdate.error = error.response.data
                draft.userInfoUpdate.result = false
            }),
        [INITIALIZE_FORM]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form]
        }),


    },
    initialState
);
export default users;