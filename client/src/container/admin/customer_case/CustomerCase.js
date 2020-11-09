import React, {Fragment,useEffect,useState} from 'react';
import {withRouter} from "react-router-dom";
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import Grid from "@material-ui/core/Grid";
import Widget from "../../../components/admin/widget/Widget";
import {useDispatch, useSelector} from "react-redux";
import {getCustomerCaseList, deleteCustomerCase, initialize} from "../../../modules/admin/customer_case/customer_case";
import Button from "../../../components/common/Button";
import styled from "styled-components";
import 'react-image-lightbox/style.css';
import qs from "qs";
import Pagination from "../../../components/util/Pagination";
import CustomerCaseListTable from "../../../components/admin/customer_case/CustomerCaseListTable";
import swal from 'sweetalert';
import style from '../../../asset/style/admin/customer_case.module.css';
import classnames from "classnames/bind";

const cx = classnames.bind(style);

const CustomerAddBtn = styled(Button)`
  float: right;
  margin: 30px;
`;

const CustomerCase = ({history,match,location}) => {

    const dispatch = useDispatch();

    const {list,page,deleteResult,error,loading} = useSelector(({customer_case,loading}) =>({
        list: customer_case.customerCase.list,
        page: customer_case.customerCase.page,
        // customerCase: coloring.customerCase,
        deleteResult: customer_case.deleteResult,
        error: customer_case.error,
        loading: loading['customerCase/GET_CUSTOMER_CASE_LIST'],
    }))

    useEffect(() => {

        const data = {
            page: 1,
            cate: null
        }

        dispatch(getCustomerCaseList(data))

    },[])

    useEffect(() => {

        const {page = 1, cate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            page: page,
            cate: cate
        }

        dispatch(getCustomerCaseList(data))

    },[dispatch, location.search])

    useEffect(() => {
        if(deleteResult && error === null){
            swal("삭제가 완료되었습니다");
            dispatch(initialize());
            const {page = 1, cate = null} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            const data = {
                page: page,
                cate: cate
            }

            dispatch(getCustomerCaseList(data))

        }
    },[deleteResult])

    const moveWrite = () => {
        history.push(`${location.pathname}/write`)
    }

    const moveEdit = (idx) => {
        history.push(`${location.pathname}/edit?idx=${idx}`)
    }

    const handleDelete = (idx) => {

        swal({
            text: "삭제하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(deleteCustomerCase(idx));
            }
        });

    }


    const pageChange = data => {
        const {type, page} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });
        history.push(`${location.pathname}?page=${data}`)
    };


    return (
        <Fragment>
            <PageTitle title="교육/컨설팅 관리" />
            <div className={cx("tb_style_1")}>
            <Grid container spacing={4}>
                <Grid item xs={12}>
                        <form>
                            <CustomerAddBtn onClick={() => moveWrite()} eney >교육/컨설팅 추가</CustomerAddBtn>
                        </form>
                        {list !== null && !loading &&(
                            <Fragment>
                                <CustomerCaseListTable list={list} moveEdit={moveEdit} handleDelete={handleDelete} />
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

export default withRouter(CustomerCase);