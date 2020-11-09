import React,{Fragment,useEffect} from 'react';
import {Route,Switch} from 'react-router-dom';
import ChannelTalk from "../../components/channel/ChannelTalk";
import loadable from "@loadable/component";
import Wrapper from "../Wrapper";

const Header = loadable(() => import('../../components/header/Header'))
const Footer = loadable(() => import('../../components/footer/Footer'))
const MainPage = loadable(() => import('../home/MainPage.js'))
const LoginPage = loadable(() => import('../auth/LoginPage'))
const JoinPage = loadable(() => import('../auth/JoinPage'))
const JoinSuccessPage = loadable(() => import('../auth/JoinSuccessPage'))
const UserTypePage = loadable(() => import('../auth/UserTypePage'))
const AgreementCheckPage = loadable(() => import('../auth/AgreementCheckPage'))
// const PrivateRoute = loadable(() => import('../PrivateRoute'))
import PrivateRoute from '../PrivateRoute';
const MyPage = loadable(() => import('../user/MyPage'))
const ServiceApplyPage = loadable(() => import('../service_apply/ServiceApplyPage'))
const ColoringApplyPage = loadable(() => import('../coloring_apply/ColoringApplyPage'));
const PointChargePage = loadable(() => import('../user/PointChargePage'))
const ServiceIntroducePage = loadable(() => import('../service_introduce/ServiceIntroducePage'))
const CompanyIntroduce = loadable(() => import('../company/CompanyIntroducePage'))
const CustomerCasePage = loadable(() => import('../customer_case/CustomerCasePage'))

const CustomerServicePage = loadable(() => import('../customerService/CustomerServicePage'))
const PricePage = loadable(() => import('../price/PricePage'))
const HomePageRoute = (props) => {


    return (
        <Fragment>
        <Route component={Header} />
            <Wrapper>
                <Switch>
                    <Route exact path="/" component={MainPage}/>
                    <Route path="/introduce/:page" component={CompanyIntroduce} />
                    <PrivateRoute exact path="/mypage" component={MyPage} />
                    <PrivateRoute exact path="/pointCharge" component={PointChargePage} />
                    <Route path="/customerservice" component={CustomerServicePage} />
                    <Route exact path="/auth/login" component={LoginPage} />
                    <Route exact path="/auth/join" component={JoinPage} />
                    <Route exact path="/auth/user_type" component={UserTypePage} />
                    <Route exact path="/auth/agreement_check" component={AgreementCheckPage} />
                    <Route exact path="/auth/join_success" component={JoinSuccessPage} />
                    <Route path="/service_introduce" component={ServiceIntroducePage} />
                    <Route path="/customer_case" component={CustomerCasePage} />
                    <Route path="/price" component={PricePage} />
                    {/*<Route path="/service_apply/" component={ServiceApplyPage} />*/}
                    <PrivateRoute path="/service_apply" component={ServiceApplyPage} />
                    <PrivateRoute path="/coloring_apply" component={ColoringApplyPage} />
                    {/*<Route component={ChannelTalk} />*/}
                </Switch>
                <ChannelTalk />
            </Wrapper>
        <Route component={Footer} />
        </Fragment>
    );
};

export default React.memo(HomePageRoute);