import React from 'react';
import {Switch, Route,Redirect} from 'react-router-dom';
import style from '../service.module.css';
import classnames from "classnames/bind";
import CloudManagement from "../../../container/service/cloud/CloudManagement";

const cx = classnames.bind(style);

const CloudPage = () => {
    return (
        <section className={cx("cont")}>
            <Switch>
                <Route exact path="/service/cloud/management" render={(props) => <CloudManagement {...props} cx={cx} />}/>
                <Route
                    exact
                    path="/service/cloud"
                    render={() => <Redirect to="/service/cloud/management" />}
                />
            </Switch>
        </section>
    );
};

export default CloudPage;