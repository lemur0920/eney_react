import React,{useEffect,useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import {changeField, initializeForm, login} from "../../modules/auth";
import IdFindForm from '../../components/auth/LoginForm'
import {tempSetUser} from '../../modules/user/mypage/mypage';


const UserIdFind = ({history}) => {
    const [error, setError] = useState(null);
    const dispatch = useDispatch();
    const {form, auth, authError, user} = useSelector(({auth,user}) =>({
        form: auth.login,
        auth:auth.auth,
        authError:auth.authError,
        user:user.user
    }))

    useEffect(() => {
        dispatch(initializeForm('login'));
    },[dispatch])

    return (
        <IdFindForm
            type="login"
            form={form}
            onChange={onChange}
            error={error}
        />
    );

};

export default withRouter(UserIdFind);