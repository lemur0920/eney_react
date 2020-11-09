import React,{Fragment} from 'react';
import {withRouter, Route} from 'react-router-dom';
import CustomerCase from "../../../container/admin/customer_case/CustomerCase";
import CompanyIntroduce from "../../../components/company/CompanyIntroduce";
import CustomerCaseWrite from "../../../container/admin/customer_case/CustomerCaseWrite";

import style from '../../../asset/style/admin/customer_case.module.css';
import classnames from "classnames/bind";

const cx = classnames.bind(style);

const CustomerCasePage = ({match}) => {
    return (

        <div className={cx('admin_customer_case')}>
            <Route exact path={`${match.url}`} component={CustomerCase}/>
            <Route exact path={`${match.url}/:type(write)`} component={CustomerCaseWrite} />
            <Route exact path={`${match.url}/:type(edit)`} component={CustomerCaseWrite}/>
        </div>
    //     <Route path={`/introduce/history`} render={ () => <CompanyHistory page={match.params.page}/>}/>
    // <Route path={`/introduce/ci`} render={ () => <CompanyCi page={match.params.page}/>}/>
    // <Route path={`/introduce/map`} render={ () => <CompanyMap page={match.params.page}/>}/>

        // <CustomerCase/>
    );
};

export default withRouter(CustomerCasePage);