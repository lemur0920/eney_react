import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import * as templateAPI from '../../lib/api/service/template';

const [GET_TEMPLATE_LIST, GET_TEMPLATE_LIST_SUCCESS, GET_TEMPLATE_LIST_FAILURE] = createRequestActionTypes('template/GET_TEMPLATE_LIST');
const [GET_TEMPLATE_DETAIL, GET_TEMPLATE_DETAIL_SUCCESS, GET_TEMPLATE_DETAIL_FAILURE] = createRequestActionTypes('template/GET_TEMPLATE_DETAIL');

const [UPDATE_TEMPLATE, UPDATE_TEMPLATE_SUCCESS, UPDATE_TEMPLATE_FAILURE] = createRequestActionTypes('template/UPDATE_TEMPLATE');
const [DELETE_TEMPLATE, DELETE_TEMPLATE_SUCCESS, DELETE_TEMPLATE_FAILURE] = createRequestActionTypes('template/DELETE_TEMPLATE');

const [CREATE_TEMPLATE, CREATE_TEMPLATE_SUCCESS, CREATE_TEMPLATE_FAILURE] = createRequestActionTypes('template/CREATE_TEMPLATE');

const CHANGE_TEMPLATE_EDIT = 'template/EDIT_UPDATE_TEMPLATE';
const SET_TEMPLATE_CONTENT = 'template/SET_TEMPLATE_CONTENT';

const INITIALIZE = 'template/INITIALIZE';
//CID 리스트
export const getTemplateList = createAction(GET_TEMPLATE_LIST, (page) => (page));
export const getTemplateDetail = createAction(GET_TEMPLATE_DETAIL, idx => idx);
// export const getCidList = createAction(GET_CID_LIST, ({page}) => ({page}))

export const editTemplateValue = createAction(
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
);

// export const updateAgent = createAction(UPDATE_AGENT, ({agent,address}) => ({agent,address}))

export const updateTemplate = createAction(UPDATE_TEMPLATE, ({idx, subject, text}) => ({idx, subject, text}));
export const deleteTemplate = createAction(DELETE_TEMPLATE, (idx) => (idx));
export const createTemplate = createAction(CREATE_TEMPLATE, ({subject, text, msg_type}) => ({subject, text, msg_type}));

// export const initialize = createAction(INITIALIZE, ());
export const initialize = createAction(INITIALIZE);

const getTemplateListSaga = createRequestSaga(GET_TEMPLATE_LIST, templateAPI.getTemplateList);
const getTemplateDetailSaga = createRequestSaga(GET_TEMPLATE_DETAIL, templateAPI.getTemplateDetail);
const updateTemplateSaga = createRequestSaga(UPDATE_TEMPLATE, templateAPI.updateTemplate);
const deleteTemplateSaga = createRequestSaga(DELETE_TEMPLATE, templateAPI.deleteTemplate);
const createTemplateSaga = createRequestSaga(CREATE_TEMPLATE, templateAPI.createTemplate);

export function* templateSaga() {
    yield takeLatest(GET_TEMPLATE_LIST, getTemplateListSaga);
    yield takeLatest(GET_TEMPLATE_DETAIL, getTemplateDetailSaga);
    yield takeLatest(UPDATE_TEMPLATE, updateTemplateSaga);
    yield takeLatest(DELETE_TEMPLATE, deleteTemplateSaga);
    yield takeLatest(CREATE_TEMPLATE, createTemplateSaga);
}

const initialState = {
    templateList: {
        list: null,
        page: null,
    },
    templateEdit: {
        subject: '',
        text: '',
        idx: '',
        msg_type: '',
    },
    error: null,
    deleteResult : null,
    templateEditResult: false,
    templateEditError: null,
    fileUploadError: null
};

const template = handleActions(
    {
        [GET_TEMPLATE_LIST_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.templateList.list = data.list
                draft.templateList.page = data.page
            }),
        [GET_TEMPLATE_LIST_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.template = initialState.template
            }),
        // [GET_TEMPLATE_DETAIL_SUCCESS]: (state, {payload: data}) => console.log(data),
        [GET_TEMPLATE_DETAIL_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.templateEdit.idx = data.idx
                draft.templateEdit.subject = data.subject
                draft.templateEdit.text = data.text
                draft.templateEdit.msg_type = data.msg_type
            }),
        [GET_TEMPLATE_DETAIL_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.template = initialState.template
            }),
        [CHANGE_TEMPLATE_EDIT]: (state, {payload: {key, value}}) =>
            produce(state, draft => {
                draft.templateEdit[key] = value
            }),
        [SET_TEMPLATE_CONTENT]: (state, {payload: {subject, text}}) =>
            produce(state, draft => {
                draft.templateEdit.subject = subject
                draft.templateEdit.text = text
            }),
        [UPDATE_TEMPLATE_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.templateEditResult = true
            }),
        [UPDATE_TEMPLATE_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.templateEditResult = false
                draft.templateEditResult = error.response.data.updateError
            }),
        [DELETE_TEMPLATE_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.deleteResult = data
            }),
        [DELETE_TEMPLATE_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.deleteResult = false
            }),
        [CREATE_TEMPLATE_SUCCESS]: (state, {payload: data}) =>
            produce(state, draft => {
                draft.templateEditResult = true
            }),
        [CREATE_TEMPLATE_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.templateEditResult = false
                draft.templateEditResult = error.response.data.updateError
            }),
        [INITIALIZE]: (state, {payload: form}) => ({
            ...initialState
        }),

    },
    initialState
    )
;
export default template;