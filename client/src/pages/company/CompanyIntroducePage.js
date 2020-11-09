import React,{Fragment} from 'react';
import CompanyIntroduceTab from "../../components/company/CompanyIntroduceTab";
import { Link, Route,withRouter } from 'react-router-dom';
import CompanyIntroduce from "../../components/company/CompanyIntroduce";
import CompanyHistory from "../../components/company/CompanyHistory";
import CompanyCi from "../../components/company/CompanyCi";
import CompanyMap from "../../components/company/CompanyMap";

const CompanyIntroducePage = ({match,location}) => {

    return (
        <Fragment>
            <div className="company_visual"></div>
            <div className="sub_container company_cont">
                {/*<CompanyIntroduceTab page={match.params.page}/>*/}
                <Route path={`/introduce/company`} render={ () => <CompanyIntroduce page={match.params.page}/>}/>
                <Route path={`/introduce/history`} render={ () => <CompanyHistory page={match.params.page}/>}/>
                <Route path={`/introduce/ci`} render={ () => <CompanyCi page={match.params.page}/>}/>
                <Route path={`/introduce/map`} render={ () => <CompanyMap page={match.params.page}/>}/>
            </div>
        </Fragment>
    );
};

export default withRouter(CompanyIntroducePage);