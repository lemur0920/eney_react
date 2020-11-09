import React,{useEffect,Fragment} from 'react';
import {Route, Redirect} from 'react-router-dom';
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import {check} from "../modules/auth";
import swal from 'sweetalert';

const PrivateRoute = ({history,component: Component, ...rest}) => {

    const dispatch = useDispatch();
    const {user,checkError,loading} = useSelector(({auth,loading}) =>({
        user:auth.user,
        checkError: auth.checkError,
        loading:loading['auth/CHECK']
    }))

    useEffect(() => {
        dispatch(check());
    },[])



    const data = {...rest};

    const exclusionArray = [
        '/admin',
        '/admin/dashboard',
        '/admin/users',
        '/admin/coupon',
        '/admin/customUserCount',
        // '/auth/login',
    ]

    // if(exclusionArray.indexOf(location.pathname) !== -1){
    //     return null;
    // }

    // console.log(a)

    return (
        !loading  &&(
            <Route
                {...rest}
                render={props => {
                    if(user === null){
                        swal("로그인 후 이용해주세요").then(() => {
                            history.push("/auth/login")
                        })
                    }else if(exclusionArray.indexOf(data.path) !== -1 && !user || user.role ==='user'){
                        swal("관리자만 가능합니다").then(() => {
                            history.push("/auth/login")
                        })
                    }else{
                        return <Component {...props} />
                    }
                }}
            />
        )
    );
};

export default withRouter(PrivateRoute);