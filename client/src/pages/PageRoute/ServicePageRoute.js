import React from 'react';
import loadable from "@loadable/component";
import {useSelector} from "react-redux";
import FullLoading from "../../components/util/FullLoading";
const PrivateRoute = loadable(() => import('../PrivateRoute'))
const ServicePage = loadable(() => import('../service/ServicePage'))
const ServicePageRoute = () => {
    const {loading} = useSelector(({loading}) =>({
        loading:loading.loading
    }))
    return (
        <>
            {loading && <FullLoading/>}
            <PrivateRoute path="/service" component={ServicePage} />
        </>
    );
};

export default ServicePageRoute;