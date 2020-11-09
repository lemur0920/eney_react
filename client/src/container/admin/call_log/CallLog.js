import React, {Fragment, useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import Grid from "@material-ui/core/Grid";
import PatchCallSearchBox from "../../../components/service/patchcall/PatchCallSearchBox";
import Pagination from "../../../components/util/Pagination";
import qs from "qs";
import {
    adminGetCallLogAudioDownload,
    adminGetCalllogList,
    adminGetCallLogListDownload,
    initialize
} from "../../../modules/service/patchcall";
import * as moment from 'moment'
import PatchCallLogTable from "../../../components/service/patchcall/calllog/PatchCallLogTable";
import Loading from "../../../components/util/Loading";

const CallLog = ({location,history,cx}) => {
    const searchList = [
        {name:"선택",value:""},
        {name:"아이디",value:"user_id"},
        {name:"가맹점명",value:"agent_name"},
        {name:"발신지명",value:"dong_name"},
        {name:"발신번호",value:"ani"},
        {name:"착신번호",value:"call_no"},
        {name:"050번호",value:"dn"},
    ]


    const dispatch = useDispatch();

    const {callLog, loading,downLoading} = useSelector(({patchcall,loading}) =>({
        callLog: patchcall.callLog,
        loading: loading['patchcall/ADMIN_GET_CALLLOG_LIST'],
        downLoading: loading['patchcall/ADMIN_GET_CALLLOG_LIST_DOWNLOAD'],
    }))

    useEffect(() => {
        // const {user_id = null,page = 1,search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
        //     ignoreQueryPrefix:true,
        // });
        //
        // const data = {
        //     user_id:user_id,
        //     page: page,
        //     search_filed : search_filed,
        //     search : search,
        //     startDate: startDate,
        //     endDate : endDate
        // }
        //
        // dispatch(adminGetCalllogList(data))

        return () => {
            dispatch(initialize());
        }

    },[])

    useEffect(() => {
        const {user_id = null,page = 1,search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            user_id:user_id,
            page: page,
            search_filed : search_filed,
            search : search,
            startDate: startDate,
            endDate : endDate
        }

        dispatch(adminGetCalllogList(data))

    },[location.search])


    const movePage = (page) => {

        const {user_id = null,search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        history.push(`${location.pathname}?page=${page}${`${user_id !== null && user_id !== "" ? (`&user_id=${user_id}`) : ""}`}${`${search_filed !== null && search_filed !== "" ? (`&search_filed=${search_filed}`) : ""}`}${`${search !== null  && search !== ""  ?
            (`&search=${search}`) : ""}`}${`${startDate !== null ? (`&startDate=${moment(startDate).format('YYYYMMDD')}`) : ""}`}${`${endDate !== null ? (`&endDate=${moment(endDate).format('YYYYMMDD')}`) : ""}`}`)
    }

    const moveCallLogSearch = (searchForm) => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const {user_id = null,search_filed = null, search = null, startDate = null, endDate = null} = searchForm;

        history.push(`${location.pathname}?page=1${`${user_id !== null && user_id !== "" ? (`&user_id=${user_id}`) : ""}`}${`${search_filed !== null && search_filed !== "" ? (`&search_filed=${search_filed}`) : ""}`}${`${search !== null  && search !== ""  ?
            (`&search=${search}`) : ""}`}${`${startDate !== null ? (`&startDate=${moment(startDate).format('YYYYMMDD')}`) : ""}`}${`${endDate !== null ? (`&endDate=${moment(endDate).format('YYYYMMDD')}`) : ""}`}`)

    }

    const excelDownload = () =>{

        const {user_id = null,page = 1,search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            user_id: user_id,
            page: page,
            search_filed : search_filed,
            search : search,
            startDate: startDate,
            endDate : endDate
        }

        dispatch(adminGetCallLogListDownload(data));

    }

    const audioDownload = (month, fileName) => {
        const data = {
            month:month,
            fileName:fileName
        }
        dispatch(adminGetCallLogAudioDownload(data));
    }



    return (
        <>
            <PageTitle title="콜로그 조회" />
            <div className={cx("tb_style_1")}>
                <Grid container spacing={4}>
                    <Grid item xs={12}>
                            <>
                                <PatchCallSearchBox cx={cx} onSearch={moveCallLogSearch} searchUse={true} dateUse={true} searchList={searchList} excelDownload={excelDownload}/>
                                <button className={cx("btn_download")} onClick={() => {excelDownload()}}><span>Excel 다운로드</span></button>
                                {callLog.list !== null && !loading  && !downLoading ? (
                                    <>
                                        <PatchCallLogTable list={callLog.list} cx={cx} audioDownload={audioDownload}/>
                                    </>
                                ) : <Loading/>}
                                {!loading && callLog.page !== null &&
                                    <>
                                        <Pagination
                                        totalRecords={callLog.page.totalCount}
                                        pageLimit={callLog.page.pageSize}
                                        pageNeighbours={1}
                                        currentPage={callLog.page.pageNo}
                                        onPageChanged={movePage}/>
                                    </>
                                }
                            </>
                    </Grid>
                </Grid>
            </div>
        </>
    );
};

export default withRouter(CallLog);