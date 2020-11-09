import React from 'react';

import loadable from "@loadable/component";
import FullLoading from "../../components/util/FullLoading";
import {useSelector} from "react-redux";
const PrivateRoute = loadable(() => import('../PrivateRoute'))
const AdminPage = loadable(() => import('../admin/AdminPage'))

const AdminPageRoute = () => {
    const {loading} = useSelector(({loading}) =>({
        loading:loading.loading
    }))
    return (
        <>
            <PrivateRoute path="/admin" component={AdminPage} />
        </>
    );
};

export default AdminPageRoute;