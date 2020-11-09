import React,{Fragment} from 'react';
import {Route,Switch} from 'react-router-dom';
import loadable from "@loadable/component";
import {useSelector} from "react-redux";
import FullLoading from "../../components/util/FullLoading";
const LoginPage = loadable(() => import('../auth/LoginPage'))
const JoinPage = loadable(() => import('../auth/JoinPage'))
const JoinSuccessPage = loadable(() => import('../auth/JoinSuccessPage'))
const UserTypePage = loadable(() => import('../auth/UserTypePage'))
const AgreementCheckPage = loadable(() => import('../auth/AgreementCheckPage'))
const Header = loadable(() => import('../../components/header/Header'))
const Footer = loadable(() => import('../../components/footer/Footer'))

const AuthPageRoute = ({location, history}) => {
    const {loading} = useSelector(({loading}) =>({
        loading:loading.loading
    }))

    return (
        <Fragment>
            {loading && <FullLoading/>}
            {location.pathname === "/auth/login" ? (
                <Fragment>
                    <Route exact path="/auth/login" component={LoginPage} />
                </Fragment>): (
                <Fragment>
                    <Route component={Header} />
                    <Switch>
                        <Route exact path="/auth/join" component={JoinPage} />
                        <Route exact path="/auth/user_type" component={UserTypePage} />
                        <Route exact path="/auth/agreement_check" component={AgreementCheckPage} />
                        <Route exact path="/auth/join_success" component={JoinSuccessPage} />
                    </Switch>
                    <Route component={Footer} />
                </Fragment> )
            }
        </Fragment>

    );
};

export default AuthPageRoute;