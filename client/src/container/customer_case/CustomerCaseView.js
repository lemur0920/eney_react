import React,{useEffect,Fragment} from 'react';
import CustomerCaseViewContent from "../../components/customer_case/CustomerCaseViewContent";
import {
    getCustomerCase,
} from "../../modules/admin/customer_case/customer_case";
import {useDispatch, useSelector} from "react-redux";
import qs from "qs";

const CustomerCaseView = ({cx}) => {
    const dispatch = useDispatch();

    const {customerCase,error} = useSelector(({customer_case,loading}) =>({
        customerCase: customer_case.customerCase.item,
        error: customer_case.error,
        // loading: loading['adminCustomUserCount/GET_CUSTOM_USER_COUNT'],
    }))

    useEffect(() => {
        const {idx} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });
        dispatch(getCustomerCase(idx))

    },[])


    return (
        <div className="main_cont">
            {customerCase !== null && (
                <CustomerCaseViewContent item={customerCase} cx={cx}/>
            )}
        </div>
    );
};

export default CustomerCaseView;