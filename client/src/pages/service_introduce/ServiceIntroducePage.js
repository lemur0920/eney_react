import React,{Fragment} from 'react';
import { Link, Route } from 'react-router-dom';
import ServiceIntroduce from '../../components/service_introduce/ServiceIntroduce';
import PatchCall from '../../components/service_introduce/PatchCall';
import PatchAi from "../../components/service_introduce/PatchAi";
import PatchBi from "../../components/service_introduce/PatchBi";
import Cloud from "../../components/service_introduce/Cloud";
import BulkMessage from "../../components/service_introduce/BulkMessage";
import WebBuilder from "../../components/service_introduce/WebBuilder";


const ServiceIntroducePage = ({location, match, history}) => {

    return (

        <Fragment>
            <Route exact path={match.url} component={ServiceIntroduce} />
            <Route path={`${match.url}/patch_call`} component={PatchCall}/>
            <Route path={`${match.url}/patch_ai`} component={PatchAi}/>
            <Route path={`${match.url}/patch_bi`} component={PatchBi}/>
            <Route path={`${match.url}/cloud`} component={Cloud}/>
            <Route path={`${match.url}/message`} component={BulkMessage}/>
            <Route path={`${match.url}/builder`} component={WebBuilder}/>
            <Route path={`${match.url}/colorring`} component={PatchCall}/>
            {/*<Route path={`${match.url}/consulting`} component={PatchCall}/>*/}
        </Fragment>
    );
};

export default ServiceIntroducePage;