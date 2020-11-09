import React, {useEffect,Fragment} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import {withRouter} from "react-router-dom";
import ServiceInfoTable from "../../../components/mypage/serviceInfo/ServiceInfoTable";
import {useDispatch, useSelector} from "react-redux";
import {usePreloader} from "../../../lib/PreloaderContext";
import {myserviceList, pointChargeLogList} from "../../../modules/user/mypage/mypage";
import Pagination from "../../../components/util/Pagination";
import qs from "qs";

const useStyles = makeStyles({
    root: {
        width: '100%',
        overflowX: 'auto',
        boxShadow: "none",
    },
    table: {
        minWidth: 650,
    },
});

const ServiceInfo = ({history,location}) => {

    const dispatch = useDispatch();
    const classes = useStyles();

    const {serviceList,serviceListPage, loading} = useSelector(({mypage,loading }) =>({
        serviceList: mypage.serviceList,
        serviceListPage:mypage.serviceListPage,
        loading: loading['mypage/MYSERVICE_LIST'],
    }))

    // usePreloader(() => dispatch(myserviceList()))

    // useEffect(() => {
    //     // console.log("탐")
    //     // if(loading && serviceList !== null){
    //     //     return;
    //     // }
    //     dispatch(myserviceList())
    // },[])

    useEffect(() => {
        const {myservicePage} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        dispatch(myserviceList({myservicePage}))

    },[location.search])

    const onPageChanged = data => {
        const {myservicePage = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        history.push(`${location.pathname}?myservicePage=${data}`)
    };


    return (
        <Paper className={classes.root}>
            {!loading && serviceList && (
                <Fragment>
                    <ServiceInfoTable serviceList={serviceList}/>

                    <Pagination
                    totalRecords={serviceListPage.totalCount}
                    pageLimit={serviceListPage.pageSize}
                    pageNeighbours={1}
                    currentPage={serviceListPage.pageNo}
                    onPageChanged={onPageChanged}/>
                </Fragment>
            )}
        </Paper>
    );
};

export default withRouter(ServiceInfo);