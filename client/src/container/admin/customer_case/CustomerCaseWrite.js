import React, {Fragment, useEffect} from 'react';
import {withRouter} from 'react-router-dom'
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import Grid from "@material-ui/core/Grid";
import Widget from "../../../components/admin/widget/Widget";
import CustomerCaseEditor from "../../../components/admin/customer_case/CustomerCaseEditor";
import {useDispatch, useSelector} from "react-redux";
import {getCustomerCaseCate,addCustomerCase,editCustomerCase,getCustomerCase,initialize} from "../../../modules/admin/customer_case/customer_case";
import Button from "@material-ui/core/Button";
import qs from "qs";
import swal from 'sweetalert';

const CustomerCaseWrite = ({history, match, location}) => {

    const dispatch = useDispatch();

    const {customerCaseCate,error, addResult,editResult, item} = useSelector(({customer_case,loading}) =>({
        customerCaseCate: customer_case.customerCaseCate,
        item: customer_case.customerCase.item,
        error: customer_case.error,
        addResult: customer_case.addResult,
        editResult: customer_case.editResult,
        // loading: loading['adminCustomUserCount/GET_CUSTOM_USER_COUNT'],
    }))

    useEffect(() => {
        dispatch(getCustomerCaseCate());
        if(match.params.type === "edit"){
            const {idx} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            dispatch(getCustomerCase(idx));
        }
        return () => {
            dispatch(initialize());
        }
    },[])

    useEffect(() => {
        if(addResult && error === null){
            swal("등록이 완료되었습니다");
            dispatch(initialize());
            history.push("/admin/customer_case")
        }
    },[addResult])

    useEffect(() => {
        if(editResult && error === null){
            swal("수정이 완료되었습니다");
            dispatch(initialize());
            history.push("/admin/customer_case")
        }
    },[editResult])


    const onSave = () => {

        let form = new FormData(document.customerCaseForm);
        dispatch(addCustomerCase(form))
    }

    const onUpdate = () => {

        let form = new FormData(document.customerCaseForm);
        dispatch(editCustomerCase(form))
    }


    return (
        <Fragment>
            <PageTitle title="교육/컨설팅 추가" />
            <Grid container spacing={4}>
                <Grid item xs={12}>
                    <Widget title="" upperTitle noBodyPadding disableWidgetMenu={true}>
                        <CustomerCaseEditor cateList={customerCaseCate} onSubmit={match.params.type === "write"? onSave : onUpdate} item={item} type={match.params.type}/>
                    </Widget>
                </Grid>
            </Grid>
        </Fragment>
    );
};

export default withRouter(CustomerCaseWrite);