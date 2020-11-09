import React, {Fragment, useEffect,useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import qs from "qs";
import {getAgentList,callManageSampleDownload,initialize} from "../../../modules/service/patchcall";
import PatchcallManagementTable from "../../../components/service/patchcall/PatchcallManagementTable";
import Pagination from "../../../components/util/Pagination";
import Loading from "../../../components/util/Loading";
import Button from "../../../components/common/Button";
import styled from "styled-components";
import PatchCallSearchBox from "../../../components/service/patchcall/PatchCallSearchBox";
import * as moment from "moment";
import AnalyticsViewIdAddModal from "./AnalyticsViewIdAddModal";
import PatchCallManagementBulkFormModal from "./PatchCallManagementBulkFormModal";


const PatchCallManagement = ({cx,location,history}) => {
    const dispatch = useDispatch();

    const [isShowModal, setIsShowModal] = useState(false);

    const searchList = [
        {name:"선택",value:""},
        {name:"가맹점명",value:"name"},
        {name:"발신지명",value:"dong_name"},
        {name:"착신번호",value:"rcv_no"},
        {name:"050번호",value:"vno"},
    ]


    const {agentList, loading} = useSelector(({patchcall,loading}) =>({
        agentList: patchcall.agentList,
        loading: loading['patchcall/GET_AGENT_LIST'],
    }))

    useEffect(() => {
        // const {page = 1,search_filed = null, search = null} = qs.parse(location.search,{
        //     ignoreQueryPrefix:true,
        // });
        //
        // const data = {
        //     page: page,
        //     search_filed : search_filed,
        //     search : search,
        // }
        //
        // dispatch(getAgentList(data))

        return () => {
            dispatch(initialize());
        }

    },[])

    useEffect(() => {

        if(!loading){
            const {page = 1,search_filed = null, search = null} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            const data = {
                page: page,
                search_filed : search_filed,
                search : search
            }

            dispatch(getAgentList(data))
        }

    },[location.search])

    useEffect(() => {
        if(!loading){
            window.scroll(0,0);
        }
    },[loading])

    const movePage = (page) => {

        const {search_filed = null, search = null,startDate = null, endDate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        history.push(`${location.pathname}?page=${page}${`${search_filed !== null ? (`'&search_filed='${search_filed}`) : ""}`}${`${search !== null ? (`'&search='${search}`) : ""}`}${`${startDate !== null ? (`&startDate=${moment(startDate).format('YYYYMMDD')}`) : ""}`}${`${endDate !== null ? (`&endDate=${moment(endDate).format('YYYYMMDD')}`) : ""}`}`)
    }

    const moveAgentSearch = (searchForm) => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const {search_filed = null, search = null, startDate = null, endDate = null} = searchForm;

        history.push(`${location.pathname}?page=1${`${search_filed !== null && search_filed !== "" ? (`&search_filed=${search_filed}`) : ""}`}${`${search !== null  && search !== ""  ?
            (`&search=${search}`) : ""}`}${`${startDate !== null ? (`&startDate=${moment(startDate).format('YYYYMMDD')}`) : ""}`}${`${endDate !== null ? (`&endDate=${moment(endDate).format('YYYYMMDD')}`) : ""}`}`)

    }


    const onHandleModal = () => {
        setIsShowModal(!isShowModal);
    }

    const sampleDownload = () => {
        dispatch(callManageSampleDownload());
    }


    return (
        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>패치콜</li>
                    <li>번호관리</li>
                </ul>
            </div>

            <div className={cx("box_cont","patchcall_management_content")}>
                <div className="">
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>번호</span>관리</h1>
                    </div>
                    <div className={cx("tb_style_1")}>
                        <div>
                        <PatchCallSearchBox cx={cx} dateUse={false} searchUse={true} onSearch={moveAgentSearch} searchList={searchList}/>
                        <PatchCallManagementBulkFormModal cx={cx} open={isShowModal} onClose={() => onHandleModal()} sampleDownload={sampleDownload}/>
                        <button className={cx("btn_g","btn_g2","show_modal_btn")} onClick={() => {onHandleModal();}}><span>번호 일괄 수정</span></button>
                        </div>
                        {agentList.list !== null && !loading ? (
                            <Fragment>
                                <PatchcallManagementTable list={agentList.list} cx={cx}/>
                                <Pagination
                                totalRecords={agentList.page.totalCount}
                                pageLimit={agentList.page.pageSize}
                                pageNeighbours={4}
                                currentPage={agentList.page.pageNo}
                                onPageChanged={movePage}/>
                            </Fragment>
                        ) : <Loading/>}

                    </div>
                </div>
            </div>
        </Fragment>
    );
};

export default withRouter(PatchCallManagement);