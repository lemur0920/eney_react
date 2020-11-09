import React from 'react';
import { Route } from 'react-router-dom';
import LoginTemplate from "../../components/auth/LoginTemplate";
import Login from "../../container/auth/Login";
import UserIdFind from "../../container/auth/UserIdFind";
import IamPortScript from "../../components/util/IamPortScript";

const LoginPage = ({match}) => {
    return (
        <LoginTemplate>
            <Login/>
            {/*<Route exact path={`${match.url}/login`} component={Login}/>*/}
            {/*<Route path={`${match.url}/idFind`} component={UserIdFind}/>*/}
            {/*<Route path={`${match.url}/pwFind`} component={Login}/>*/}
            <IamPortScript/>
        </LoginTemplate>
    );
};

export default LoginPage;