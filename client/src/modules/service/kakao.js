import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import * as kakaoAPI from '../../lib/api/service/kakao';

const [GET_CATEGORY_LIST, GET_CATEGORY_LIST_SUCCESS, GET_CATEGORY_LIST_FAILURE] = createRequestActionTypes('kakao/GET_CATEGORY_LIST');
const [GET_AUTH_TOKEN, GET_AUTH_TOKEN_SUCCESS, GET_AUTH_TOKEN_FAILURE] = createRequestActionTypes('kakao/GET_AUTH_TOKEN');
const [CREATE_FRIEND, CREATE_FRIEND_SUCCESS, CREATE_FRIEND_FAILURE] = createRequestActionTypes('kakao/CREATE_FRIEND');
const [DELETE_FRIEND, DELETE_FRIEND_SUCCESS, DELETE_FRIEND_FAILURE] = createRequestActionTypes('kakao/DELETE_FRIEND');

const [DELETE_KAKAO_TEMPLATE, DELETE_KAKAO_TEMPLATE_SUCCESS, DELETE_KAKAO_TEMPLATE_FAILURE] = createRequestActionTypes('kakao/DELETE_KAKAO_TEMPLATE');
const [CREATE_KAKAO_TEMPLATE, CREATE_KAKAO_TEMPLATE_SUCCESS, CREATE_KAKAO_TEMPLATE_FAILURE] = createRequestActionTypes('kakao/CREATE_KAKAO_TEMPLATE');
const [UPDATE_KAKAO_TEMPLATE, UPDATE_KAKAO_TEMPLATE_SUCCESS, UPDATE_KAKAO_TEMPLATE_FAILURE] = createRequestActionTypes('kakao/UPDATE_KAKAO_TEMPLATE');

const [GET_KAKAO_TEMPLATE_LIST, GET_KAKAO_TEMPLATE_LIST_SUCCESS, GET_KAKAO_TEMPLATE_LIST_FAILURE] = createRequestActionTypes('kakao/GET_KAKAO_TEMPLATE_LIST');
const [GET_KAKAO_TEMPLATE, GET_KAKAO_TEMPLATE_SUCCESS, GET_KAKAO_TEMPLATE_FAILURE] = createRequestActionTypes('kakao/GET_KAKAO_TEMPLATE');


const [GET_SENDER_PROFILE_LIST, GET_SENDER_PROFILE_LIST_SUCCESS, GET_SENDER_PROFILE_LIST_FAILURE] = createRequestActionTypes('kakao/GET_SENDER_PROFILE_LIST');

// const [GET_SENDER_PROFILE_LIST, GET_SENDER_PROFILE_LIST_SUCCESS, GET_SENDER_PROFILE_LIST_FAILURE] = createRequestActionTypes('kakao/GET_SENDER_PROFILE_LIST');


// const GET_AUTH_TOKEN = 'kakao/AUTH_TOKEN';
const SET_TEMPLATE_CONTENT = 'template/SET_TEMPLATE_CONTENT';

const INITIALIZE = 'kakao/INITIALIZE';
//CID 리스트

/*export const editTemplateValue = createAction(
    CHANGE_TEMPLATE_EDIT,
    ({key, value}) => ({
        key,
        value,
    })
);
export const setTemplateContent = createAction(
    SET_TEMPLATE_CONTENT,
    ({subject, text}) => ({
        subject,
        text,
    })
);*/

// export const updateAgent = createAction(UPDATE_AGENT, ({agent,address}) => ({agent,address}))

export const getCategoryList = createAction(GET_CATEGORY_LIST);
export const getKakaoAuthToken = createAction(GET_AUTH_TOKEN, ({yellowId, phoneNumber}) => ({yellowId, phoneNumber}));
export const createPlusFriend = createAction(CREATE_FRIEND, ({yellowId, phoneNumber, token, categoryCode}) => ({
    yellowId,
    phoneNumber,
    token,
    categoryCode
}));
export const deletePlusFriend = createAction(DELETE_FRIEND, (senderKey) => (senderKey));
export const createKakaoTemplate = createAction(CREATE_KAKAO_TEMPLATE, ({senderKey, uuid, name, templateName, templateContent, buttons}) => ({
    senderKey,
    uuid,
    name,
    templateName,
    templateContent,
    buttons
}));

export const updateKakaoTemplate = createAction(UPDATE_KAKAO_TEMPLATE, ({senderKey, templateCode, uuid, name, templateName, templateContent, buttons}) => ({
    senderKey,
    templateCode,
    uuid,
    name,
    templateName,
    templateContent,
    buttons
}));

export const deleteKakaoTemplate = createAction(DELETE_KAKAO_TEMPLATE, ({senderKey, templateCode}) => ({
    senderKey,
    templateCode
}));
export const getSenderProfileList = createAction(GET_SENDER_PROFILE_LIST, ({page}) => ({page}));
export const getKakaoTemplateList = createAction(GET_KAKAO_TEMPLATE_LIST, ({page}) => ({page}));
export const getKakaoTemplate = createAction(GET_KAKAO_TEMPLATE, ({senderKey, templateCode}) => ({
    senderKey,
    templateCode
}));


// export const initialize = createAction(INITIALIZE, ());
export const initialize = createAction(INITIALIZE, from => from);

const getCategoryListSaga = createRequestSaga(GET_CATEGORY_LIST, kakaoAPI.getCategoryList);
const getAuthTokenSaga = createRequestSaga(GET_AUTH_TOKEN, kakaoAPI.getAuthToken);
const createPlusFriendSaga = createRequestSaga(CREATE_FRIEND, kakaoAPI.createPlusFriend);
const deletePlusFriendSaga = createRequestSaga(DELETE_FRIEND, kakaoAPI.deletePlusFriend);
const createKakaoTemplateSaga = createRequestSaga(CREATE_KAKAO_TEMPLATE, kakaoAPI.createKakaoTemplate);
const updateKakaoTemplateSaga = createRequestSaga(UPDATE_KAKAO_TEMPLATE, kakaoAPI.updateKakaoTemplate);
const deleteKakaoTemplateSaga = createRequestSaga(DELETE_KAKAO_TEMPLATE, kakaoAPI.deleteKakaoTemplate);
const getSenderProfileListSaga = createRequestSaga(GET_SENDER_PROFILE_LIST, kakaoAPI.getSenderProfileList);
const getKakaoTemplateListSaga = createRequestSaga(GET_KAKAO_TEMPLATE_LIST, kakaoAPI.getKakaoTemplateList);
const getKakaoTemplateSaga = createRequestSaga(GET_KAKAO_TEMPLATE, kakaoAPI.getKakaoTemplate);

export function* kakaoSaga() {
    yield takeLatest(GET_CATEGORY_LIST, getCategoryListSaga);
    yield takeLatest(GET_AUTH_TOKEN, getAuthTokenSaga);
    yield takeLatest(CREATE_FRIEND, createPlusFriendSaga);
    yield takeLatest(DELETE_FRIEND, deletePlusFriendSaga);
    yield takeLatest(CREATE_KAKAO_TEMPLATE, createKakaoTemplateSaga);
    yield takeLatest(UPDATE_KAKAO_TEMPLATE, updateKakaoTemplateSaga);
    yield takeLatest(DELETE_KAKAO_TEMPLATE, deleteKakaoTemplateSaga);
    yield takeLatest(GET_SENDER_PROFILE_LIST, getSenderProfileListSaga);
    yield takeLatest(GET_KAKAO_TEMPLATE_LIST, getKakaoTemplateListSaga);
    yield takeLatest(GET_KAKAO_TEMPLATE, getKakaoTemplateSaga);
}

const initialState = {
    category_list: {},
    sender_list: {
        list: null,
        page: null,
    },
    template_list: {
        list: null,
        page: null,
    },

    code_auth: {code: null, message: null},
    code_delete_profile: {code: null, message: null},
    code_create_friend: {code: null, message: null},

    code_create_template: {code: null, message: null},
    code_delete_template: {code: null, message: null},
    detail_kakao_template: {code: null, data: {}},
    error: null,
};

const kakao = handleActions(
    {
        [GET_CATEGORY_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.category_list = data;
            }),
        [GET_CATEGORY_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.category_list = initialState.category_list
            }),
        [GET_AUTH_TOKEN_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.code_auth = data;
            }),
        [GET_AUTH_TOKEN_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.code_auth = initialState.code_auth
            }),
        [CREATE_FRIEND_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.code_create_friend = data;
            }),
        [CREATE_FRIEND_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.code_create_friend = error
            }),
        [DELETE_FRIEND_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.code_delete_profile = data;
            }),
        [DELETE_FRIEND_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.code_delete_profile = error
            }),
        [CREATE_KAKAO_TEMPLATE_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.code_create_template = data;
            }),
        [CREATE_KAKAO_TEMPLATE_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.code_create_template = initialState.code_create_template
            }),
        [UPDATE_KAKAO_TEMPLATE_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.code_create_template = data;
            }),
        [UPDATE_KAKAO_TEMPLATE_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.code_create_template = initialState.code_create_template
            }),
        [DELETE_KAKAO_TEMPLATE_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.code_delete_template = data;
            }),
        [DELETE_KAKAO_TEMPLATE_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.code_delete_template = initialState.code_delete_template
            }),
        [GET_SENDER_PROFILE_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.sender_list.list = data.list;
                draft.sender_list.page = data.page;
            }),
        [GET_SENDER_PROFILE_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.sender_list = initialState.sender_list
            }),
        [GET_KAKAO_TEMPLATE_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.template_list.list = data.list;
                draft.template_list.page = data.page;
            }),
        [GET_KAKAO_TEMPLATE_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.template_list = initialState.template_list
            }),
        [GET_KAKAO_TEMPLATE_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.detail_kakao_template = data;
            }),
        [GET_KAKAO_TEMPLATE_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.detail_kakao_template = initialState.detail_kakao_template
            }),
        // [GET_TEMPLATE_DETAIL_SUCCESS]: (state, {payload: data}) => console.log(data),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),

    },
    initialState
    )
;
export default kakao;