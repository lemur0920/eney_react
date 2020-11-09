import React from 'react';
import {Switch, Route,Redirect} from 'react-router-dom';
import PatchCallManagement from "../../../container/service/patchcall/PatchCallManagement";
import style from '../service.module.css';
import classnames from "classnames/bind";
import PatchCallManagementDetail from "../../../container/service/patchcall/PatchCallManagementDetail";
import PatchCallLog from "../../../container/service/patchcall/PatchCallLog";
import PatchCallCid from "../../../container/service/patchcall/PatchCallCid";
import PatchCallDashboard from "../../../container/service/patchcall/PatchCallDashboard";
import AnalyticsManage from "../../../container/service/patchcall/AnalyticsManage";

const cx = classnames.bind(style);

const PatchCallPage = () => {
    return (
        <section className={cx("cont")}>
            <Switch>
                <Route exact path="/service/patchcall/dashboard" render={(props) => <PatchCallDashboard {...props} cx={cx} />}/>
                <Route exact path="/service/patchcall/management" render={(props) => <PatchCallManagement {...props} cx={cx} />}/>
                <Route exact path="/service/patchcall/management/detail" render={(props) => <PatchCallManagementDetail {...props} cx={cx} />}/>
                <Route exact path="/service/patchcall/call_log" render={(props) => <PatchCallLog {...props} cx={cx} />}/>
                <Route exact path="/service/patchcall/cid" render={(props) => <PatchCallCid {...props} cx={cx} />}/>
                <Route exact path="/service/patchcall/analytics_manage" render={(props) => <AnalyticsManage {...props} cx={cx} />}/>
                <Route
                    exact
                    path="/service/patchcall"
                    render={() => <Redirect to="/service/patchcall/dashboard" />}
                />
            </Switch>
        </section>
    );
};

export default PatchCallPage;