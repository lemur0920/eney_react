import React, {Fragment, useEffect,useState} from 'react';
import Loading from "../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import qs from "qs";
import * as moment from 'moment'
import {Link} from 'react-router-dom';
import CustomerManagementTable from "../../../components/service/customer/CustomerManagementTable";
import PatchCallSearchBox from "../../../components/service/patchcall/PatchCallSearchBox";
import Pagination from "../../../components/util/Pagination";
import {getCustomerList,initialize,customerBulkUploadSampleDownLoad} from "../../../modules/service/customer";
import CustomerEventModal from "./CustomerEventModal";
import CustomerInfoEditModal from "./CustomerInfoEditModal";
import CustomerAddModal from "./CustomerAddModal";
import PatchCallManagementBulkFormModal from "../patchcall/PatchCallManagementBulkFormModal";
import CustomerBulkUploadModal from "./CustomerBulkUploadModal";
import {callManageSampleDownload} from "../../../modules/service/patchcall";
import swal from 'sweetalert';

const CustomerManagement = ({cx,location,history}) => {
    const dispatch = useDispatch();

    const [showEventModal, setShowEventModal] =  useState(false);
    const [showBulkModal, setShowBulkModal] = useState(false);
    const [showInfoEditModal, setShowInfoEditModal] =  useState(false);
    const [showAddModal, setShowAddModal] =  useState(false);
    const [selectCustomer, setSelectCustomer] =  useState(null);
    const [selectEditCustomer, setSelectEditCustomer] =  useState(null);



    const {customer,loading} = useSelector(({customer,loading}) =>({
        customer: customer.customer,
        loading: loading['customer/GET_CUSTOMER_LIST'],
    }))

    const searchList = [
        {name:"선택",value:""},
        {name:"전화번호",value:"phone_number"},
        {name:"주소",value:"address"},
        {name:"이름",value:"name"},
    ]

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

        dispatch(getCustomerList(data))

        return () => {
            dispatch(initialize('customer'));
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

        dispatch(getCustomerList(data))

    },[location.search])

    useEffect(() => {
        if(!loading){
            window.scroll(0,0);
        }
    },[loading])

    const movePage = (page) => {

        const {search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search, {
            ignoreQueryPrefix: true,
        });
        history.push(`${location.pathname}?page=${page}${`${search_filed !== null ? (`&search_filed=${search_filed}`) : ""}`}${`${search !== null ?
            (`&search=${search}`) : ""}`}${`${startDate !== null ? (`&startDate=${startDate}`) : ""}`}${`${endDate !== null ? (`&endDate=${endDate}`) : ""}`}`)

    }

    const moveCustomerSearch = (searchForm) => {
        // const {page = 1} = qs.parse(location.search,{
        //     ignoreQueryPrefix:true,
        // });

        const {search_filed = null, search = null, startDate = null, endDate = null} = searchForm;

        history.push(`${location.pathname}?page=1${`${search_filed !== null && search_filed !== "" ? (`&search_filed=${search_filed}`) : ""}`}${`${search !== null  && search !== ""  ?
            (`&search=${search}`) : ""}`}${`${startDate !== null ? (`&startDate=${moment(startDate).format('YYYYMMDD')}`) : ""}`}${`${endDate !== null ? (`&endDate=${moment(endDate).format('YYYYMMDD')}`) : ""}`}`)

    }

    const showModal = (idx) => {
        setSelectCustomer(idx);
        setShowEventModal(true)
    }

    const handleShowInfoEditModal = (idx) => {
        setSelectEditCustomer(idx)
        setShowInfoEditModal(true)
    }

    const handleShowBulkUplaodModal = () => {
        setShowBulkModal(!showBulkModal)
    }

    const sampleDownload = () => {
        dispatch(customerBulkUploadSampleDownLoad());
    }



    return (
        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>패치AI</li>
                    <li>고객관리</li>
                </ul>
            </div>

            <div className={cx("box_cont")}>
                <div className={cx("customer_list")}>
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>고객</span>관리</h1>
                    </div>
                    <div className={cx("txt_r","mb_20")}>
                        <button className={cx("btn_add")} onClick={()=> {setShowAddModal(true)}}><span>추가하기</span></button>
                    </div>
                    <div className={cx("tb_style_1")}>
                        {/*<a href="tel:05073480314" onClick={() => {gtag('event', '050번호 클릭',{'event_category' : '05073480314', 'event_label' : new Date().yyyymmddhhmmss()});}}>05073480314</a>*/}
                        <span className={cx("guide_txt_01")}>
                                {/*1: Incomming 후 전화 종료(전화 걸었다가 바로 끊을 시 발생)*/}
                                <br/>
                                {/*2: 가맹점 전화 후 전화 종료 (부재중으로 처리)*/}
                        </span>
                        <PatchCallSearchBox cx={cx} onSearch={moveCustomerSearch} searchUse={true} dateUse={true} searchList={searchList} />
                        <CustomerBulkUploadModal cx={cx} open={showBulkModal} onClose={() => setShowBulkModal(false)} sampleDownload={sampleDownload}/>
                        <button className={cx("btn_g","btn_g2","show_modal_btn")} onClick={() => {swal("서비스준비중")}}><span>데이터 강화하기</span></button>
                        <button className={cx("btn_g","btn_g2","show_modal_btn")} onClick={() => {handleShowBulkUplaodModal();}}><span>고객 일괄 등록</span></button>
                        {customer.list !== null && !loading ? (
                            <Fragment>
                                <CustomerManagementTable list={customer.list} cx={cx} showModal={showModal} handleShowInfoEditModal={handleShowInfoEditModal}/>
                                <Pagination
                                    totalRecords={customer.page.totalCount}
                                    pageLimit={customer.page.pageSize}
                                    pageNeighbours={1}
                                    currentPage={customer.page.pageNo}
                                    onPageChanged={movePage}/>
                            </Fragment>
                        ) : <Loading/>}
                    </div>

                </div>
            </div>
            {showEventModal &&
                <CustomerEventModal open={showEventModal} onClose={ () => setShowEventModal(false)} cx={cx} customerIdx={selectCustomer}/>
            }

            {showInfoEditModal &&
            <CustomerInfoEditModal open={showInfoEditModal} onClose={ () => setShowInfoEditModal(false)} cx={cx} customerIdx={selectEditCustomer}/>
            }

            {showAddModal && (
                <CustomerAddModal  open={showAddModal} onClose={ () => setShowAddModal(false)} cx={cx}/>
            )}
        </Fragment>
    );
};

export default withRouter(CustomerManagement);