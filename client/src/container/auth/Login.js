import React,{useEffect,useState,useCallback} from 'react';
import {useDispatch, useSelector,shallowEqual} from "react-redux";
import {withRouter} from 'react-router-dom';
import {changeField, initializeForm, login,idFindModalToggle,pwFindModalToggle} from "../../modules/auth";
import LoginForm from '../../components/auth/LoginForm'
import {tempSetUser} from '../../modules/auth';
import LoginFormFooter from './LoginFormFooter';


const Login = ({history}) => {
    const [error, setError] = useState(null);
    const dispatch = useDispatch();
    const {form, auth, authError, user, authFind} = useSelector(({auth}) =>({
        form: auth.login,
        auth:auth.auth,
        authError:auth.authError,
        user:auth.user,
        authFind: auth.authFind
    }),shallowEqual)

    const onChange = useCallback(e =>{
        const {value, name} = e.target;
        dispatch(
            changeField({
                form:'login',
                key:name,
                value
            })
        )
    },[])

    const onSubmit = e => {
        e.preventDefault();
        const {userId, password} = form;

        if(userId == "" || password == ""){
            setError("아이디 / 패스워드를 입력하세요")
            return;
        }

        dispatch(login({userId,password}))
    }

    const openIdFindModal = e =>{
        dispatch(idFindModalToggle())
    }

    const openPwFindModal = e =>{
        dispatch(pwFindModalToggle())
    }

    useEffect(() => {
        dispatch(initializeForm('login'));
    },[dispatch])

    useEffect(()=>{
        if(authError){
            setError("아이디 / 패스워드가 일치하지않습니다")
            return;
        }
        if(auth){
            dispatch(tempSetUser(auth));
        }
    },[auth, authError, dispatch]);

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
        <LoginForm
            type="login"
            form={form}
            onChange={onChange}
            onSubmit={onSubmit}
            error={error}
        >
            <LoginFormFooter openIdFindModal={openIdFindModal} openPwFindModal={openPwFindModal} authFind={authFind} />

        </LoginForm>
    );

};

export default React.memo(withRouter(Login));