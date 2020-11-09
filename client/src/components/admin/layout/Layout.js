import React from 'react';
import {withRouter, Switch,Route} from 'react-router-dom';
import {useSelector} from "react-redux";
import AdminHeader from "../header/AdminHeader";
import Sidebar from "../sidebar/Sidebar";
import useStyles from "./styles";
import UsersPage from "../../../pages/admin/users/UsersPage";
import CouponPage from "../../../pages/admin/coupon/CouponPage";
import CustomUserCountPage from "../../../pages/admin/custom_user_count/CustomUserCountPage";
import CustomerCasePage from "../../../pages/admin/customer_case/CustomerCasePage";
import ColoringPage from "../../../pages/admin/coloring/ColoringPage";
import SoundUploadPage from "../../../pages/admin/upload/SoundUploadPage";
import CloudPage from "../../../pages/admin/cloud/CloudPage";

import classnames from "classnames";
import CallLogPage from "../../../pages/admin/call_log/CallLogPage";
import PatchCallPage from "../../../pages/admin/patchcall/PatchCallPage";
import PatchIntelligencePage from "../../../pages/admin/patch_Intelligence/PatchIntelligencePage";
import ThirdPartPage from "../../../pages/admin/third_part/ThirdPartPage";
import BlockNumberPage from "../../../pages/admin/block_number/BlockNumberPage";

const Layout = () => {

    const {isSidebarOpened} = useSelector(({layout}) =>({
        isSidebarOpened : layout.isSidebarOpened
    }))

    var classes = useStyles();


    return (
        <div className={classes.root}>
            <AdminHeader/>
            <Sidebar />
            <div
                className={classnames(classes.content, {
                    [classes.contentShift]: isSidebarOpened,
                })}
            >
                <div className={classes.fakeToolbar} />

            <Switch>
                <Route path="/admin/call_log" component={CallLogPage} />
                <Route path="/admin/users" component={UsersPage} />
                <Route path="/admin/coupon" component={CouponPage} />
                <Route path="/admin/customUserCount" component={CustomUserCountPage} />
                <Route path="/admin/cloud" component={CloudPage} />
                <Route path="/admin/customer_case" component={CustomerCasePage} />
                <Route path="/admin/coloring" component={ColoringPage} />
                <Route path="/admin/sound_upload" component={SoundUploadPage} />
                <Route path="/admin/patch_call" component={PatchCallPage} />
                <Route path="/admin/patch_intelligence" component={PatchIntelligencePage} />
                <Route path="/admin/third_part" component={ThirdPartPage} />
                <Route path="/admin/block-number" component={BlockNumberPage} />


            </Switch>
            </div>
        </div>
    );
};

export default withRouter(Layout);