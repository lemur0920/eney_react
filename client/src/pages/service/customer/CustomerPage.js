import React from 'react';
import {Switch, Route,Redirect} from 'react-router-dom';
import style from '../service.module.css';
import classnames from "classnames/bind";
import CustomerManagement from "../../../container/service/customer/CustomerManagement";
import CustomerGroupManagement from "../../../container/service/customer/group/CustomerGroupManagement";

const cx = classnames.bind(style);

const CustomerPage = () => {
    return (
        <section className={cx("cont")}>
            <Switch>
                <Route exact path="/service/customer/management" render={(props) => <CustomerManagement {...props} cx={cx} />}/>
                <Route exact path="/service/customer/group/management" render={(props) => <CustomerGroupManagement {...props} cx={cx} />}/>
                <Route
                    exact
                    path="/service/customer"
                    render={() => <Redirect to="/service/customer/management" />}
                />
            </Switch>
        </section>
    );
};

export default CustomerPage;