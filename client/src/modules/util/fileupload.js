import {createAction, handleActions} from 'redux-actions';
import {takeLatest, call} from 'redux-saga/effects'
import * as fileuploadAPI from '../../lib/api/util/fileupload'
import createRequestSaga, {createRequestActionTypes} from "../../lib/createRequestSaga";
import produce from "immer";

const RESET  = 'fileupload/RESET';
const [IMAGE_UPLOAD, IMAGE_UPLOAD_SUCCESS, IMAGE_UPLOAD_FAILURE] = createRequestActionTypes('fileupload/IMAGE_UPLOAD');
const [LICENSE_UPLOAD, LICENSE_UPLOAD_SUCCESS, LICENSE_UPLOAD_FAILURE] = createRequestActionTypes('fileupload/LICENSE_UPLOAD');

export const imageUpload = createAction(IMAGE_UPLOAD,(image) => (
    image
));

export const lecenseUpload = createAction(LICENSE_UPLOAD,(image) => (
    image
));

export const resetFile = createAction(RESET);

const imageUploadSaga = createRequestSaga(IMAGE_UPLOAD, fileuploadAPI.imageUpload);
const licenseUploadSaga = createRequestSaga(LICENSE_UPLOAD, fileuploadAPI.licenseUpload);

export function* fileuploadSaga(){
    yield takeLatest(IMAGE_UPLOAD, imageUploadSaga);
    yield takeLatest(LICENSE_UPLOAD, licenseUploadSaga);
}

const initialState = {
    file:null,
    imageUrl:null,
    error:null,
    license:{
        result:false,
        error:null
    }
}

const fileupload = handleActions(
    {
        [IMAGE_UPLOAD_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.imageUrl = data;
            }),
        [IMAGE_UPLOAD_FAILURE]:(state,{payload:error}) =>
            produce(state, draft => {
                draft.imageUrl = null;
                draft.error = error;
            }),
        [LICENSE_UPLOAD_SUCCESS]:(state, {payload: data}) =>
            produce(state, draft => {
                draft.license.result = true;
                draft.license.error = null;
            }),
        [LICENSE_UPLOAD_FAILURE]:(state,{payload:error}) =>
            produce(state, draft => {
                draft.license.result = false;
                draft.license.error = error.response.data;
            }),
        [RESET]: (state) => (
            initialState
        ),
    },
    initialState
)


export default fileupload;
