import React from 'react';
import ServiceHeader from "./layout/ServiceHeader";
import ServiceSideMenu from "./layout/ServiceSideMenu";
import {Switch,Route} from 'react-router-dom';
import style from './service.module.css';
import classnames from "classnames/bind";
import {withRouter} from 'react-router-dom';

import loadable from "@loadable/component";

const PatchCallPage = loadable(() => import('./patchcall/PatchCallPage'))
const MessagePage = loadable(() => import('./message/MessagePage'))
const CloudPage = loadable(() => import('./cloud/CloudPage'))
const Swagger = loadable(() => import('../../components/service/api_doc/Swagger'))
const CustomerPage = loadable(() => import('./customer/CustomerPage'))

const cx = classnames.bind(style);

const ServicePage = ({history,location}) => {
    return (
        <div className={cx("service_container")}>
        <div id="wrap">
                <ServiceHeader cx={cx}/>
                <div className={cx('container', 'clfx')}>
                    <ServiceSideMenu cx={cx} history={history} location={location}/>
                    <Switch>
                        <Route path="/service/patchcall" component={PatchCallPage}/>
                        <Route path="/service/customer" component={CustomerPage}/>
                        <Route path="/service/message" component={MessagePage}/>
                        <Route path="/service/cloud" component={CloudPage}/>
                        <Route path="/service/api" component={Swagger}/>
                    </Switch>
                </div>
            </div>
        </div>
    );
};

export default withRouter(ServicePage);