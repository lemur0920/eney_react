import React, {Fragment, useEffect} from 'react';
import Pagination from "../../../components/util/Pagination";
import Loading from "../../../components/util/Loading";
import {getCalllogList,getCallLogListDownload,getCallLogAudioDownload, initialize} from "../../../modules/service/patchcall";
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import qs from "qs";
import PatchCallLogTable from "../../../components/service/patchcall/calllog/PatchCallLogTable";
import PatchCallSearchBox from "../../../components/service/patchcall/PatchCallSearchBox";
import * as moment from 'moment'
import swal from "sweetalert";

const PatchCallLog = ({cx,location,history}) => {
    const dispatch = useDispatch();

    const searchList = [
        {name:"선택",value:""},
        {name:"가맹점명",value:"agent_name"},
        {name:"발신지명",value:"dong_name"},
        {name:"발신번호",value:"ani"},
        {name:"착신번호",value:"call_no"},
        {name:"050번호",value:"dn"},
    ]

    const {callLog, loading,downLoading} = useSelector(({patchcall,loading}) =>({
        callLog: patchcall.callLog,
        loading: loading['patchcall/GET_CALLLOG_LIST'],
        downLoading: loading['patchcall/GET_CALLLOG_LIST_DOWNLOAD'],
    }))

    useEffect(() => {
        const {page = 1,search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            page: page,
            search_filed : search_filed,
            search : search,
            startDate: startDate,
            endDate : endDate
        }

        dispatch(getCalllogList(data))

        return () => {
            dispatch(initialize());
        }

    },[])

    useEffect(() => {
        const {page = 1,search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            page: page,
            search_filed : search_filed,
            search : search,
            startDate: startDate,
            endDate : endDate
        }

        dispatch(getCalllogList(data))

    },[location.search])

    useEffect(() => {
        if(!loading){
            window.scroll(0,0);
        }
    },[loading])

    const movePage = (page) => {

        const {search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });
        history.push(`${location.pathname}?page=${page}${`${search_filed !== null ? (`&search_filed=${search_filed}`) : ""}`}${`${search !== null ? 
            (`&search=${search}`) : ""}`}${`${startDate !== null ? (`&startDate=${startDate}`) : ""}`}${`${endDate !== null ? (`&endDate=${endDate}`) : ""}`}`)
    }

    const moveCallLogSearch = (searchForm) => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const {search_filed = null, search = null, startDate = null, endDate = null} = searchForm;

        history.push(`${location.pathname}?page=1${`${search_filed !== null && search_filed !== "" ? (`&search_filed=${search_filed}`) : ""}`}${`${search !== null  && search !== ""  ?
            (`&search=${search}`) : ""}`}${`${startDate !== null ? (`&startDate=${moment(startDate).format('YYYYMMDD')}`) : ""}`}${`${endDate !== null ? (`&endDate=${moment(endDate).format('YYYYMMDD')}`) : ""}`}`)

    }

    const excelDownload = () =>{

        const {search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if((startDate == null || endDate == null) && (search_filed == null || startDate == null)){
            swal("검색 결과만 다운로드 가능합니다");
        }else{
            const data = {
                search_filed : search_filed,
                search : search,
                startDate: startDate,
                endDate : endDate
            }

            dispatch(getCallLogListDownload(data));
        }

    }

    const audioDownload = (month, fileName) => {
        const data = {
            month:month,
            fileName:fileName
        }
        // console.log(data);
        dispatch(getCallLogAudioDownload(data));
    }

    return (
        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>패치콜</li>
                    <li>수신내역</li>
                </ul>
            </div>

            <div className={cx("box_cont","calllog_content")}>
                <div className="">
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>수신</span>내역</h1>
                    </div>
                    <div className={cx("tb_style_1")}>
                            <span className={cx("guide_txt_01")}>
                                1: Incomming 후 전화 종료(전화 걸었다가 바로 끊을 시 발생)
                                <br/>
                                2: 가맹점 전화 후 전화 종료 (부재중으로 처리)
                            </span>
                            <PatchCallSearchBox cx={cx} onSearch={moveCallLogSearch} searchUse={true} dateUse={true} searchList={searchList}/>
                        <button className={cx("btn_download")} onClick={() => {excelDownload()}}><span>Excel 다운로드</span></button>
                            {callLog.list !== null && !loading  && !downLoading ? (
                                <Fragment>
                                    <PatchCallLogTable list={callLog.list} cx={cx} audioDownload={audioDownload}/>
                                    <Pagination
                                        totalRecords={callLog.page.totalCount}
                                        pageLimit={callLog.page.pageSize}
                                        pageNeighbours={4}
                                        currentPage={callLog.page.pageNo}
                                        onPageChanged={movePage}/>
                                </Fragment>
                            ) : <Loading/>}
                    </div>
                </div>
            </div>
        </Fragment>
    );
};

export default withRouter(PatchCallLog);