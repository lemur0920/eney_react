import React, {Fragment, useEffect} from 'react';
import {withRouter} from "react-router-dom";
import Grid from "@material-ui/core/Grid";
import Widget from "../../../components/admin/widget/Widget";
import Pagination from "../../../components/util/Pagination";
import {useDispatch, useSelector} from "react-redux";
import {
    editServiceApplyStatus,
    getServiceApplyList,
    initialize
} from "../../../modules/admin/service_apply/service_apply_manage";
import qs from "qs";
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import PatchCallServiceApplyTable from '../../../components/admin/patchcall/PatchCallServiceApplyTable'
import swal from 'sweetalert';

const PatchCall = ({history,location,cx}) => {

    const dispatch = useDispatch();

    const {list,page,editInfo,loading} = useSelector(({service_apply_manage,loading}) =>({
        list: service_apply_manage.serviceApply.list,
        page: service_apply_manage.serviceApply.page,
        error: service_apply_manage.error,
        editInfo:service_apply_manage.editInfo,
        loading: loading['adminServiceApply/GET_SERVICE_APPLY_LIST'],
    }))

    useEffect(() => {

        if(!loading && list !== null){
            dispatch(getServiceApplyList(1,"patchcall"))
        }

    },[])

    useEffect(() => {

        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            page:page,
            serviceType:"patchcall"
        }

        dispatch(getServiceApplyList(data))

    },[dispatch, location.search])


    const pageChange = page => {
        history.push(`${location.pathname}?page=${page}`)
    };

    useEffect(() => {

        if(editInfo.result && editInfo.error === null){
            const {page = 1} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            const data = {
                page:page,
                serviceType:"patchcall"
            }

            swal("상태 변경이 완료되었습니다");
            dispatch(getServiceApplyList(data))
        }

        if(!editInfo.result && editInfo.error !== null){
            swal("상태 변경 중 에러가 발생했습니다");
        }

    },[dispatch, editInfo])


    const updateStatus = (data) => {
        data.serviceType = 'patchcall';
        swal({
            text: "상태를 저장하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(editServiceApplyStatus(data))
            }
        });
    }


    return (
        <Fragment>
            <PageTitle title="패치콜 신청 관리" />
            <div className={cx("tb_style_1")}>
            <Grid container spacing={4}>
                <Grid item xs={12}>
                        {list !== null && !loading &&(
                            <Fragment>
                                <PatchCallServiceApplyTable list={list}  cx={cx} updateStatus={updateStatus}/>
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

export default withRouter(PatchCall);