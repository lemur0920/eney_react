import React, {Fragment, useEffect} from 'react';
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import {withRouter} from "react-router-dom";
import Grid from "@material-ui/core/Grid";
import Widget from "../../../components/admin/widget/Widget";
import Pagination from "../../../components/util/Pagination";
import {useDispatch, useSelector} from "react-redux";
import {getCloudServiceApplyList,createInstance,removeInstance,editCloudServiceApplyStatus, initialize} from "../../../modules/admin/service_apply/cloud";
import qs from "qs";
import CloudServiceApplyListTable from "../../../components/admin/cloud/CloudServiceApplyListTable";
import swal from 'sweetalert';

const Cloud = ({history,match,cx,location}) => {


    const dispatch = useDispatch();

    const {list,page,editResult,error,createInstanceResult,createInstanceError,createLoading,deleteLoading,  loading} = useSelector(({cloud,loading}) =>({
        list: cloud.cloud.list,
        page: cloud.cloud.page,
        editResult: cloud.editResult,
        error: cloud.error,
        loading: loading['adminCloud/GET_CLOUD_SERVICE_APPLY_LIST'],
        createInstanceResult:cloud.createInstanceResult,
        createInstanceError:cloud.createInstanceError,
        createLoading: loading['adminCloud/CREATE_INSTANCE'],
        deleteLoading: loading['adminCloud/REMOVE_INSTANCE'],
    }))

    useEffect(() => {

        if(!loading && list !== null){
            dispatch(getCloudServiceApplyList(1))
        }

    },[])

    useEffect(() => {

        if(!createLoading && createInstanceResult && createInstanceError === null){
            dispatch(initialize());

            const {page = 1} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            dispatch(getCloudServiceApplyList(page))

        }

    },[dispatch, createInstanceResult, createInstanceError, createLoading,deleteLoading])

    useEffect(() => {

        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        dispatch(getCloudServiceApplyList(page))

    },[dispatch, location.search])



    const pageChange = page => {
        history.push(`${location.pathname}?page=${page}`)
    };

    const handleCreate = (idx) => {
        swal({
            text: "생성하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(createInstance(idx));
            }
        });

        // const result = confirm("생성하시겠습니까?");
        // if(result){
        //     dispatch(createInstance(idx));
        // }
    }

    const handleRemove = (idx) => {

        swal({
            text: "삭제하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(removeInstance(idx));
            }
        });

        // const result = confirm("삭제하시겠습니까?");
        // if(result){
        //     dispatch(removeInstance(idx))
        // }
    }

    return (
        <Fragment>
            <PageTitle title="클라우드 신청 관리" />
            <div className={cx("tb_style_1")}>
            <Grid container spacing={4}>
                <Grid item xs={12}>
                        {list !== null && !loading &&(
                            <Fragment>
                                <CloudServiceApplyListTable list={list}  cx={cx} handleCreate={handleCreate} handleRemove={handleRemove}/>
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

export default withRouter(Cloud);