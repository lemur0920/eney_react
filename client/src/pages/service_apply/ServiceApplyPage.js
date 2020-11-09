import React, {Fragment} from 'react';
import PatchCallApply from "../../container/service_apply/patchcall/PatchCallApply";
import ServiceIntroduce from "../../components/service_introduce/ServiceIntroduce";
import {Route } from 'react-router-dom';

const ServiceApplyPage = ({match}) => {
    return (
        <section className={`sub_container`}>
            <div className="title_cont">
                <h1 className="title_style_5">서비스 신청</h1>
            </div>
            <Fragment>
                <Route exact path={match.url} component={ServiceIntroduce} />
                <Route path={`${match.url}/:service/:type`} component={PatchCallApply}/>
            </Fragment>
        </section>

    );
};

export default ServiceApplyPage;