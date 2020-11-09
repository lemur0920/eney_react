import {createAction, handleActions} from 'redux-actions';
import produce from 'immer';
import {takeLatest} from 'redux-saga/effects';
import createRequestSaga, {createRequestActionTypes} from "../lib/createRequestSaga";
import * as authAPI from '../lib/api/auth';

const LOGOUT = 'user/LOGOUT';
const CHANGE_FIELD  = 'auth/CHANGE_FIELD';
const INITIALIZE_FORM  = 'auth/INITIALIZE_FORM';

const ID_FIND_MODAL_TOGGLE  = 'auth/ID_FIND_MODAL_TOGGLE';
const PW_FIND_MODAL_TOGGLE  = 'auth/PW_FIND_MODAL_TOGGLE';
const TEMP_SET_USER = 'auth/TEMP_SET_USER';
const INIT_CERTIFY  = 'auth/INIT_CERTIFY';
const INIT_USER_TYPE  = 'auth/INIT_USER_TYPE';
const SET_AGREE  = 'auth/SET_AGREE';


const [REGISTER,REGISTER_SUCCESS, REGISTER_FAILURE] = createRequestActionTypes('auth/REGISTER')
const [LOGIN,LOGIN_SUCCESS, LOGIN_FAILURE] = createRequestActionTypes('auth/LOGIN')
const [GET_CERTIFY,GET_CERTIFY_SUCCESS, GET_CERTIFY_FAILURE] = createRequestActionTypes('auth/GET_CERTIFY')
const [ID_FIND,ID_FIND_SUCCESS, ID_FIND_FAILURE] = createRequestActionTypes('auth/ID_FIND')
const [CHECK, CHECK_SUCCESS, CHECK_FAILURE] = createRequestActionTypes('auth/CHECK');
const [ID_CHECK, ID_CHECK_SUCCESS, ID_CHECK_FAILURE] = createRequestActionTypes('auth/ID_CHECK');
const [ID_DUPLICATE_CHECK, ID_DUPLICATE_CHECK_SUCCESS, ID_DUPLICATE_CHECK_FAILURE] = createRequestActionTypes('auth/ID_DUPLICATE_CHECK');
const [PW_RECOVER, PW_RECOVER_SUCCESS, PW_RECOVER_FAILURE] = createRequestActionTypes('auth/PW_RECOVER');
const [PASSWORD_RESET, PASSWORD_RESET_SUCCESS, PASSWORD_RESET_FAILURE] = createRequestActionTypes('auth/PASSWORD_RESET');
const [AUTH_UPDATER,AUTH_UPDATER_SUCCESS, AUTH_UPDATER_FAILURE] = createRequestActionTypes('auth/AUTH_UPDATER')
const [PW_CHECK,PW_CHECK_SUCCESS, PW_CHECK_FAILURE] = createRequestActionTypes('auth/PW_CHECK')


export const changeField = createAction(
    CHANGE_FIELD,
    ({form, key, value}) => ({
        form,
        key,
        value
    }),
);

export const idFindModalToggle = createAction(ID_FIND_MODAL_TOGGLE);
export const pwFindModalToggle = createAction(PW_FIND_MODAL_TOGGLE);
export const initCertify = createAction(INIT_CERTIFY);
export const initUserType = createAction(INIT_USER_TYPE, userType => userType)
export const setAgree = createAction(SET_AGREE, agree => agree)
export const initializeForm = createAction(INITIALIZE_FORM, from => from);

export const register = createAction(REGISTER, ({form}) => ({
    form
}))

export const getCertify = createAction(GET_CERTIFY, (imp_uid, type) => ({
    imp_uid, type
}))

export const idFind = createAction(ID_FIND, (imp_uid) => ({
    imp_uid
}))

export const pwRecover = createAction(PW_RECOVER, (imp_uid, userId) => ({
    imp_uid, userId
}))

export const passwordReset = createAction(PASSWORD_RESET, (userId,newPw, newPwCheck) => ({
    userId,newPw, newPwCheck
}))

export const idCheck = createAction(ID_CHECK, userId => userId)

export const idDuplicateCheck = createAction(ID_DUPLICATE_CHECK, userId => userId)

export const login = createAction(LOGIN, ({userId,password}) => ({
    userId,
    password,
}))
export const authUpdate = createAction(AUTH_UPDATER)

export const tempSetUser = createAction(TEMP_SET_USER, user => user);
export const logout = createAction(LOGOUT);
export const check = createAction(CHECK);

export const pwCheck = createAction(PW_CHECK, (pw) => (
    pw
))


function checkFailureSaga(){
    try{
        localStorage.removeItem("user");
    }catch(e){
        console.log("localStorage is not working")
    }
}
const idDuplicateCheckSaga = createRequestSaga(ID_DUPLICATE_CHECK, authAPI.idDuplicateCheck);
const registerSaga = createRequestSaga(REGISTER, authAPI.register);
const loginSaga = createRequestSaga(LOGIN, authAPI.login)
const getCertifySaga = createRequestSaga(GET_CERTIFY, authAPI.getCertify)
const pwRecoverSaga = createRequestSaga(PW_RECOVER, authAPI.pwRecover)
const idFindSaga = createRequestSaga(ID_FIND, authAPI.idFind)
const checkSaga = createRequestSaga(CHECK, authAPI.check);
const idCheckSaga = createRequestSaga(ID_CHECK, authAPI.idCheck);
const passwordResetSaga = createRequestSaga(PASSWORD_RESET, authAPI.passwordReset);
const authUpdateSaga = createRequestSaga(AUTH_UPDATER, authAPI.authUpdate);
const pwCheckSaga = createRequestSaga(PW_CHECK, authAPI.pwCheck);

export function* authSaga(){
    yield takeLatest(REGISTER, registerSaga);
    yield takeLatest(LOGIN, loginSaga);
    yield takeLatest(GET_CERTIFY, getCertifySaga);
    yield takeLatest(PW_RECOVER, pwRecoverSaga);
    yield takeLatest(ID_FIND, idFindSaga);
    yield takeLatest(CHECK, checkSaga);
    yield takeLatest(CHECK_FAILURE, checkFailureSaga);
    yield takeLatest(ID_DUPLICATE_CHECK, idDuplicateCheckSaga);
    yield takeLatest(ID_CHECK, idCheckSaga);
    yield takeLatest(PASSWORD_RESET, passwordResetSaga);
    yield takeLatest(AUTH_UPDATER, authUpdateSaga);
    yield takeLatest(PW_CHECK, pwCheckSaga);

}

const initialState = {
    user:null,
    checkError:null,
    certifyForm:{
        certifySuccessNum:"",
        certifySuccessName:"",
        ci:"",
        di:"",
        birthday:"",
        authError: "",
        isCertify: false,
    },
    registerCheck:{
        check:{
            result : false,
            msg:{
                passwordError:"",
                userIdError:"",
                phoneNumError:"",
                emailError:"",
                certifyError:"",
                addressError:"",
                corporateError:""
            }
        },
        agree:{
            isTerms:false,
            isPersonal:false,
            isMarketing:false,
        },
        registerSuccess:false,
    },
    userType:'',
    isCertify:false,
    login:{
        userId:'',
        password:'',
    },
    idFind:{
        result:false,
        userId:""
    },
    pwFind:{
        success:false,
        userId:"",
        validateError:"",
        isCertify: false,
        result:false,
    },
    pwCheck:{
      isSuccess: false,
      error:null
    },
    phoneNumCheck:false,
    corporateNumCheck:false,
    auth:null,
    authError:null,
    authFind:{
        // isOpen:{
        //     findIdModalIsOpen:false,
        //     findPwModalIsOpen:false,
        // },
        // userId:"",
    },
};

const auth = handleActions(
    {
        [TEMP_SET_USER]: (state, {payload:user}) =>({
            ...state,
            user,
        }),
        [CHECK_SUCCESS]: (state, {payload:user}) =>({
            ...state,
            user: JSON.parse(localStorage.getItem("user")),
            checkError: null
        }),
        [CHECK_FAILURE]:(state,{payload:error}) => ({
            ...state,
            user:null,
            checkError:error.response.data
        }),
        [LOGOUT]: state => (
            initialState
        ),
        [CHANGE_FIELD]: (state, {payload: {form, key, value}}) =>
            produce(state, draft => {
                draft[form][key] = value;
            }),
        [INITIALIZE_FORM]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form],
            // registerCheck:{
            //     ...state.registerCheck,
            //     check: initialState.registerCheck.check
            // },
            isCertify:false,
        }),
        [REGISTER_SUCCESS]: (state, {payload: data}) => ({
            ...state,
            registerCheck:{
                ...state.registerCheck,
                check:{
                    ...state.registerCheck.check,
                    result : true,
                    msg: initialState.registerCheck.check.msg
                },
                registerSuccess:true,
            },
        }),
        [REGISTER_FAILURE]: (state, {payload: error}) => ({
            ...state,
            registerCheck:{
                ...state.registerCheck,
                check:{
                    ...state.registerCheck.check,
                    result : false,
                    msg:error.response.data
                },
                registerSuccess:false,
            },
        }),
        [LOGIN_SUCCESS]: (state, {payload: user}) => ({
            ...state,
            authError: null,
            user,
            ["login"]: initialState["login"]
        }),
        [LOGIN_FAILURE]: (state, {payload: error}) => ({
            ...state,
            authError: error
        }),
        [AUTH_UPDATER_SUCCESS]: (state, {payload: user}) => ({
            ...state,
            authError: null,
            user,
            ["login"]: initialState["login"]
        }),
        [AUTH_UPDATER_FAILURE]: (state, {payload: error}) => ({
            ...state,
            authError: error
        }),
        [GET_CERTIFY_SUCCESS]:(state, {payload:data}) => ({
            ...state,
            certifyForm: {
                ...state.certifyForm,
                certifySuccessNum: data.phone,
                certifySuccessName: data.name,
                ci: data.ci,
                di: data.di,
                birth_day: data.birthday,
                isCertify: true,
                authError: ""
            }
        }),
        [GET_CERTIFY_FAILURE]:(state, {payload:error}) => ({
            ...state,
            certifyForm: {
                ...state.certifyForm,
                isCertify: false,
                certifySuccessNum: "",
                certifySuccessName: "",
                ci: "",
                di: "",
                birth_day: "",
                authError: error.response.data
            }
        }),
        [ID_FIND_SUCCESS]:(state, {payload:data}) => ({
            ...state,
            idFind: {
                ...state.idFind,
                result:true,
                userId:data
            }
        }),
        [ID_FIND_FAILURE]:(state, {payload:error}) => ({
            ...state,
            idFind: {
                ...state.idFind,
                result:false,
                userId:""
            }
        }),
        [PW_RECOVER_SUCCESS]:(state, {payload:data}) => ({
            ...state,
            pwFind: {
                ...state.pwFind,
                validateError:"",
                isCertify:true,
                success:true,
            }
        }),
        [PW_RECOVER_FAILURE]:(state, {payload:error}) => ({
            ...state,
            pwFind: {
                ...state.pwFind,
                validateError:error.response.data,
                isCertify:false,
                success:true,
            }
        }),
        [ID_CHECK_SUCCESS]:(state, {payload:data}) => ({
            ...state,
            pwFind: {
                ...state.pwFind,
                success:true,
                userId:data
            }
        }),
        [ID_CHECK_FAILURE]:(state, {payload:error}) => ({
            ...state,
            pwFind: {
                ...state.pwFind,
                success:false,
                error:error.response.data,
                userId:""
            }
        }),
        [PASSWORD_RESET_SUCCESS]:(state, {payload:data}) => ({
            ...state,
            pwFind: {
                ...state.pwFind,
                success:true,
                result:true
            }
        }),
        [PASSWORD_RESET_FAILURE]:(state, {payload:error}) => ({
            ...state,
            pwFind: {
                ...state.pwFind,
                success:false,
                result:false,
                error:error.response.data,
            }
        }),
        [INIT_CERTIFY]:(state) => ({
            ...state,
            certifyForm:{
                ...state.certifyForm,
                certifySuccessNum:"",
                certifySuccessName:"",
                ci:"",
                di:"",
                birth_day:"",
                isCertify : false,
            }
        }),
        [INIT_USER_TYPE]:(state, {payload: userType}) =>
            produce(state, draft => {
                draft.userType = userType
            }),
        [SET_AGREE]:(state, {payload:agree}) => ({
            ...state,
            registerCheck:{
                ...state.registerCheck,
                agree:agree
            }
        }),
        [ID_DUPLICATE_CHECK_SUCCESS]:(state,{payload:data}) => ({
            ...state,
            registerCheck:{
                ...state.registerCheck,
                check:{
                    ...state.registerCheck.check,
                    result:true,
                    msg:{
                        ...state.registerCheck.check.msg,
                        userIdError:""
                    },
                    isDuplicate:false,
                }
            }
        }),
        [ID_DUPLICATE_CHECK_FAILURE]: (state, {payload:error}) =>({
            ...state,
            registerCheck:{
                ...state.registerCheck,
                check:{
                    ...state.registerCheck.check,
                    result:false,
                    msg:{
                        ...state.registerCheck.check.msg,
                        userIdError:error.response.data
                    },
                    isDuplicate:true,
                }
            }
        }),
        [PW_CHECK_SUCCESS]: (state, {payload: result}) =>
            produce(state, draft => {
                draft.pwCheck.isSuccess = true;
                draft.pwCheck.error = null;
            }),
        [PW_CHECK_FAILURE]: (state, {payload: error}) =>
            produce(state, draft => {
                draft.pwCheck.isSuccess = false;
                draft.pwCheck.error = error.response.data;
            }),
    }
    ,
    initialState
);
export default auth;