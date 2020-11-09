import React, {useEffect, useState,Fragment} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from "react-router-dom";
import {couponList,couponCreate,initialize} from '../../../modules/admin/coupon/coupon'
import CouponTable from "../../../components/admin/coupon/CouponTable";
import CouponGenerator from "../../../components/admin/coupon/CouponGenerator";
import Pagination from "../../../components/util/Pagination";
import qs from "qs";
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import Widget from "../../../components/admin/widget/Widget";
import  Grid  from "@material-ui/core/Grid";
import style from '../../../asset/style/admin/coupon.module.css';
import classnames from "classnames/bind";

const cx = classnames.bind(style);

const CouponManagement = ({location, history}) => {

    const dispatch = useDispatch();

    const {list, page,createError,loading,createResult} = useSelector(({coupon,loading}) =>({
        list: coupon.coupon.list,
        page: coupon.coupon.page,
        createResult:coupon.create.isSuccess,
        createError : coupon.create.error,
        loading: loading['adminCoupon/COUPON_LIST'],
    }))

    useEffect(() => {
        if(loading){
            return;
        }

        history.push(`${location.pathname}?page=1`)
    },[])

    useEffect(() => {
        const {page} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        dispatch(couponList({page}))

    },[location.search])


    const onPageChanged = data => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const query = `?page=${data}`
        history.push(`${location.pathname}${query}`)
    };

    const onCouponCreate = ({point, count}) => {
         console.log("생성")
        dispatch(couponCreate({point, count}))
    };

    const onInitialize = (form) => {
        dispatch(initialize(form));
    };

    return (
        <div>
            {/*{!loading && list !== null && (*/}
                <Fragment>
                    <PageTitle title="쿠폰 관리" />
                    <div className={cx("tb_style_1")}>
                        <Grid container spacing={4}>
                            <Grid item xs={12}>
                                    <CouponGenerator onCouponCreate={onCouponCreate} onInitialize={onInitialize} createResult={createResult}error={createError}/>
                                    {!loading && list !== null && (
                                        <Fragment>
                                            <CouponTable list={list}/>
                                            <Pagination
                                                totalRecords={page.totalCount}
                                                pageLimit={page.pageSize}
                                                pageNeighbours={1}
                                                currentPage={page.pageNo}
                                                onPageChanged={onPageChanged}
                                            />
                                        </Fragment>
                                        )}
                        </Grid>
                    </Grid>
                    </div>
                </Fragment>


        </div>
    );
};

export default withRouter(CouponManagement);