import React, {Fragment, useEffect} from 'react';
import ColoringTable from "../../../components/admin/coloring/ColoringTable";
import {useDispatch, useSelector} from "react-redux";
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import {withRouter} from 'react-router-dom';
import Grid from "@material-ui/core/Grid";
import Widget from "../../../components/admin/widget/Widget";
import CustomerCaseListTable from "../../../components/admin/customer_case/CustomerCaseListTable";
import Pagination from "../../../components/util/Pagination";
import {getColoringApplyList, initialize} from "../../../modules/admin/coloring/coloring";
import qs from "qs";


const Coloring = ({cx, location, history}) => {
    const dispatch = useDispatch();

    const {list,page,loading} = useSelector(({coloring,loading}) =>({
        list: coloring.coloring.list,
        page: coloring.coloring.page,
        error: coloring.error,
        loading: loading['coloring/GET_COLORING_APPLY_LIST'],
    }))


    useEffect(() => {

        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        dispatch(getColoringApplyList(page))

    },[])

    useEffect(() => {

        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        dispatch(getColoringApplyList(page))

    },[dispatch, location.search])


    const pageChange = data => {
        history.push(`${location.pathname}?page=${data}`)
    };


    return (
        <Fragment>
            <PageTitle title="컬러링 관리" />
            <div className={cx("tb_style_1")}>
            <Grid container spacing={4}>
                <Grid item xs={12}>
                        {list !== null && !loading &&(
                            <Fragment>
                                <ColoringTable list={list}  cx={cx}/>
                                {page !== null &&(
                                    <Pagination
                                        totalRecords={page.totalCount}
                                        pageLimit={page.pageSize}
                                        pageNeighbours={1}
                                        currentPage={page.pageNo}
                                        onPageChanged={pageChange}/>
                                )}
                            </Fragment>

                        )}
                </Grid>
            </Grid>
            </div>
        </Fragment>
    );
};

export default withRouter(Coloring);