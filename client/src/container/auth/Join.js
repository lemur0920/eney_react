import React, {Fragment, useEffect,useRef} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {changeField, initializeForm,register,getCertify,idDuplicateCheck} from "../../modules/auth";
import JoinForm from "../../components/auth/JoinForm";
import {check} from '../../modules/auth';
import {withRouter} from 'react-router-dom'
import axios from 'axios';

const Join = ({history,location}) => {

    const userCertifyForm = useRef();

    const dispatch = useDispatch();
    const {certifyForm, auth, authError,user,registerCheck,certifySuccessNum, certifySuccessName, userType,registerSuccess} = useSelector(({auth}) => ({
        certifyForm:auth.certifyForm,
        auth:auth.auth,
        userType:auth.userType,
        authError: auth.authError,
        registerCheck:auth.registerCheck,
        user:auth.user,
        certifySuccessNum:auth.certifySuccessNum,
        certifySuccessName:auth.certifySuccessName,
        registerSuccess:auth.registerCheck.registerSuccess,

    }))
    const maxLengthCheck = (object) => {
        if (object.target.value.length > object.target.maxLength) {
            object.target.value = object.target.value.slice(0, object.target.maxLength)
        }
    }

    //
    // const onChange = e => {
    //     const {value, name} = e.target;
    //     dispatch(
    //         changeField({
    //             form:'register',
    //             key:name,
    //             value
    //         })
    //     )
    // };

    const onSubmit = form => {
        const {password, password_check} = form;

        if(password !== password_check){
            return;
        }

        form.corporate_number = `${form.corporate_number01}-${form.corporate_number02}-${form.corporate_number03}`
        if(registerCheck.agree.isMarketing === true){
            form.marketing_agree = true;
        }

        dispatch(register({form}))
    }

    const onDuplicateCheck = (userId) => {
        dispatch(idDuplicateCheck(userId));
    }

    const moveBack = () => {
        history.goBack();
    }


    useEffect(() => {
        // if(location.userType == null){
        //     history.push('/auth/user_type')
        // }
        // dispatch(initializeForm('registerCheck'));
        // dispatch(getCertify());
        dispatch(check());
        //유저 타입 선택없이 진입
        if(userType === null || userType === ""){
            history.push("/auth/user_type");
        }

        return () => {
            dispatch(initializeForm('registerCheck'));
        };

    },[dispatch])

    useEffect(() =>{

        if(registerSuccess){
            history.push("/auth/join_success")
        }

    }, [dispatch,registerSuccess])

    useEffect(() => {
        if(user){
            history.push('/')
        }
    }, [user])

    useEffect(() => {
        if(user){
            history.push('/')
            try{
                localStorage.setItem("user",JSON.stringify(user));
            }catch(e){
                console.log("localStorage is not working")
            }
        }
    },[history, user])



    return (
        <Fragment>
            <JoinForm certifyForm={certifyForm} onSubmit={onSubmit} maxLengthCheck={maxLengthCheck} certifySuccessName={certifySuccessName} onDuplicateCheck={onDuplicateCheck} certifySuccessNum={certifySuccessNum} authError={authError} registerCheck={registerCheck} userType={userType} moveBack={moveBack}/>
            {/*{userCertify !== null && (<UserCertifyFrom  userCertify={userCertify} ref={userCertifyForm} /> )}*/}
        </Fragment>
    );
};

export default withRouter(Join);