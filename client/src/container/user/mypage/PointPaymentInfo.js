import React, {forwardRef, Fragment, useEffect, useState} from 'react';
import PaymentTable from "../../../components/mypage/point/PaymentLogTable";
import Pagination from "../../../components/util/Pagination";
import qs from "qs";
import {withRouter} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {pointChargeLogList, pointPaymentLogList} from "../../../modules/user/mypage/mypage";

import Moment from 'moment';

import DatePicker from "react-datepicker";

import calendalIcon from '../../../asset/image/calendal_icon.png'

import "react-datepicker/dist/react-datepicker.css";
import styled from "styled-components";
import Button from "../../../components/common/Button";
import Flatpickr from "react-flatpickr";
import { Korean } from "flatpickr/dist/l10n/ko.js"
import Loading from "../../../components/util/Loading";

const CustomButton = styled(Button)`
  padding: 4px 5px;
  margin:2px;
  margin:10px 0 0 10px;
`;

const PointPaymentInfo = ({location, history}) => {

    const option = {
        mode:"range",
        maxDate: "today",
        locale:Korean,
        dateFormat: "Y-m-d",
    }

    const dispatch = useDispatch();
    const [searchDate, setSearchDate] = useState({
        startDate:null,
        endDate:null
    })

    const {chargeLogList, chargePageInfo, chargeLoading, paymentLogList, paymentPageInfo, paymentLoading,epoint} = useSelector(({mypage,loading,auth}) =>({
        chargeLogList: mypage.pointChargeLogList,
        chargePageInfo: mypage.pointChargeLogPage,
        chargeLoading: loading['mypage/POINT_CHARGE_LOG_LIST'],
        paymentLogList: mypage.pointPaymentLogList,
        paymentPageInfo: mypage.pointPaymentLogPage,
        paymentLoading: loading['mypage/POINT_PAYMENT_LOG_LIST'],
        epoint: mypage.user.epoint,
    }))

    // useEffect(() => {
    //     const {chargePage, paymentPage} = qs.parse(location.search,{
    //         ignoreQueryPrefix:true,
    //     });
    //     dispatch(pointPaymentLogList({paymentPage}))
    // },)

    useEffect(() => {
        const {chargePage = 1, paymentPage, startDate, endDate} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(chargeLoading || paymentLoading){
            return;
        }

        history.push(`${location.pathname}?chargePage=1&paymentPage=1`)

    },[])


    useEffect(() => {
        const {chargePage, paymentPage,startDate, endDate} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        // if(chargeLoading || paymentLoading){
        //     return;
        // }

        // console.log(chargePage)
        // if(chargePageInfo !== null && chargePageInfo.pageNo !== parseInt(chargePage)){
        //     dispatch(pointChargeLogList({chargePage}))
        //     return;
        // }else if(paymentPageInfo !== null && paymentPageInfo.pageNo !== parseInt(paymentPage)){
        //     dispatch(pointPaymentLogList({paymentPage}))
        //     return;
        // }else{
        //     dispatch(pointPaymentLogList({paymentPage}))
        //     dispatch(pointChargeLogList({chargePage}))
        // }
        dispatch(pointPaymentLogList({paymentPage,startDate, endDate}))
        // dispatch(pointChargeLogList({chargePage}))

    },[location.search])



    const onPaymentPageChanged = data => {
        const {chargePage = 1, startDate, endDate} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const query = `?chargePage=${chargePage}&paymentPage=${data}${startDate !== undefined ?(`&startDate=${startDate}&endDate=${endDate}`):('')}`
        history.push(`${location.pathname}${query}`)

    };

    const onSearch = () => {
        const {paymentPage = 1, chargePage = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const sDate = Moment(searchDate.startDate).format(Moment.HTML5_FMT.DATE)
        const eDate = Moment(searchDate.endDate).format(Moment.HTML5_FMT.DATE)

        const query = `?&startDate=${sDate}&endDate=${eDate}`
        history.push(`${location.pathname}${query}`)
    }

    const setDate = (e) => {
        setSearchDate({
            startDate: e[0],
            endDate: e[1],
        })
    }

    return (
        <Fragment>
            {!paymentLoading && paymentLogList ? (
                <Fragment>
                    <div className='date_select_box'>
                        <form onSubmit={ (e) => {e.preventDefault(); onSearch();} }>
                            <Flatpickr options={option}
                                       onClose={(e)=>  {setDate(e)}}
                                       disabled={false}
                                       placeholder="날짜를 선택하세요"
                            />
                            <img src={calendalIcon}/>
                            {/*<DatePicker*/}
                            {/*    selected={startDate}*/}
                            {/*    onChange={changeStartDate}*/}
                            {/*    dateFormat="yyyy-MM-dd"*/}
                            {/*    customInput={<CustomDateInput />}*/}
                            {/*/>*/}
                            {/*~*/}
                            {/*<DatePicker*/}
                            {/*    selected={endDate}*/}
                            {/*    onChange={changeEndDate}*/}
                            {/*    dateFormat="yyyy-MM-dd"*/}
                            {/*    minDate={startDate}*/}
                            {/*    customInput={<CustomDateInput />}*/}
                            {/*/>*/}
                            <CustomButton  type="submit" eney>조회</CustomButton>
                        </form>
                    </div>
                    <PaymentTable logList={paymentLogList}/>
                    <Pagination
                        totalRecords={paymentPageInfo.totalCount}
                        pageLimit={paymentPageInfo.pageSize}
                        pageNeighbours={1}
                        currentPage={paymentPageInfo.pageNo}
                        onPageChanged={onPaymentPageChanged}

                    />
                </Fragment>
                ):(<Loading/>)}
        </Fragment>
    );
};

export default withRouter(PointPaymentInfo) ;