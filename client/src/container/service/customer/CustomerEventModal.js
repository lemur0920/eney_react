import React, {useEffect, useState} from 'react';
import {Box} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import CustomModal from "../../../components/common/CustomModal";
import {getCustomerEventList,initialize} from "../../../modules/service/customer";
import {useDispatch, useSelector} from "react-redux";
import qs from "qs";
import CustomerEventTable from "../../../components/service/customer/CustomerEventTable";
import {getCallLogAudioDownload} from "../../../modules/service/patchcall";

const CustomerEventModal = ({open, onClose,cx,customerIdx}) => {
    const title = "";
    const subTitle = "";

    const dispatch = useDispatch();

    const {customerEvent,loading} = useSelector(({customer,loading}) =>({
        customerEvent: customer.customerEvent,
        loading: loading['customer/GET_CUSTOMER_EVENT_LIST'],
    }))

    useEffect(() => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            page: 1,
            customer_idx:customerIdx
        }

        dispatch(getCustomerEventList(data))

        return () => {
            dispatch(initialize("customerEvent"));
        }

    },[])

    useEffect(() => {
        if(!loading && customerEvent === null){

        }

    },[loading, customerEvent])

    const audioDownload = (month, fileName) => {
        const data = {
            month:month,
            fileName:fileName
        }
        dispatch(getCallLogAudioDownload(data));
    }


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <div className={cx("popup","customer_list_popup")}>
                <div className={cx("popup-content")}>
                    <h1>고객관리</h1>
                    <div className={cx("inner")}>
                        <div className={cx("tb_scroll")}>
                            <div className={cx("tb_style_2")}>
                                <CustomerEventTable eventList={customerEvent.list} cx={cx} audioDownload={audioDownload}/>
                            </div>
                        </div>
                        <div className={cx("popup_close")}>
                            <button onClick={() => onClose()}><span></span></button>
                        </div>
                    </div>
                </div>
            </div>
        </CustomModal>
    );
};

export default CustomerEventModal;