import React,{Fragment}  from 'react';
import {withRouter,} from "react-router-dom";
import Layout from "../../components/admin/layout/Layout";
import Helmet from 'react-helmet';

const AdminPage = () => {

    return (

        <Fragment>
            <Helmet>
                <title>관리자 페이지</title>
            </Helmet>
            <Layout/>
        </Fragment>
    );
};

export default withRouter(AdminPage);