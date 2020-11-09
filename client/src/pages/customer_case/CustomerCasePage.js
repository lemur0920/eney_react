import React,{Fragment} from 'react';
import {Link,Route} from "react-router-dom";
import patch from "../../asset/image/service_introduce/patch.png";
import patchAi from "../../asset/image/service_introduce/patch_ai.png";
import patchBi from "../../asset/image/service_introduce/patch_bi.png";
import cloud from "../../asset/image/service_introduce/cloud.png";
import message from "../../asset/image/service_introduce/message.png";
import builder from "../../asset/image/service_introduce/builder.png";
import web from "../../asset/image/service_introduce/web.png";
import colorring from "../../asset/image/service_introduce/colorring.png";
import consulting from "../../asset/image/service_introduce/consulting.png";
import CustomerCase from "../../container/customer_case/CustomerCase";
import CustomerCaseView from "../../container/customer_case/CustomerCaseView";
import style from '../../asset/style/customer_case/customer_case.module.css';
import classnames from "classnames/bind";
import CompanyIntroduce from "../../components/company/CompanyIntroduce";
import CompanyHistory from "../../components/company/CompanyHistory";

const cx = classnames.bind(style);

const CustomerCasePage = ({location,match}) => {
    return (
        <Fragment>
            <section className="sub_container">
                <div className="main_container">
                    <Route exact path={`${match.url}`} render={ () => <CustomerCase cx={cx}/>}/>
                    <Route path={`${match.url}/view`} render={ () =>  <CustomerCaseView cx={cx}/>}/>
                </div>
            </section>
        </Fragment>
    );
};

export default CustomerCasePage;