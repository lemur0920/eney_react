import React, {Fragment, useState, useEffect} from 'react';
import useStyles from './style';
import cx from "classnames/bind";
import {TextField} from "@material-ui/core";

import UserPhoneChageModal from "./UserPhoneChageModal";
import {useDispatch, useSelector} from "react-redux";
import {getCertify,initCertify} from "../../../modules/auth";
import {updatePhone} from "../../../modules/user/mypage/mypage";
import CustomAlertModal from "../../common/CustomAlertModal";
import Button from "../../common/Button";
import styled from "styled-components";
import swal from 'sweetalert';

const CustomButton = styled(Button)`
    display: inline-block;
    margin:0;
    padding: 9px 16px;
`;

const UserPhoneInfo = () => {

    const classes = useStyles()
    const {phone, isSuccess, isCertify,certifySuccessNum,loading} = useSelector(({auth,mypage,loading}) =>({
        phone: mypage.user.phone_number,
        isSuccess:mypage.isSuccess,
        isCertify:auth.certifyForm.isCertify,
        certifySuccessNum:auth.certifyForm.certifySuccessNum,
        loading:loading["mypage/UPDATE_PHONE"]
    }))
    const dispatch = useDispatch();

    const [changePboneIsOpen, setChangePhoneIsOpen] = useState(false);

    const toggleModal = () =>{
        setChangePhoneIsOpen(!changePboneIsOpen)
        dispatch(initCertify());
    }

    const onSubmit = () =>{
        dispatch(updatePhone(certifySuccessNum))
    }

    useEffect(() => {

        if(!loading){
            return
        }

        if(isSuccess.phoneNumber === true){
            setChangePhoneIsOpen(false)
            swal("핸드폰번호가 변경되었습니다");
        }

    },[isSuccess.phoneNumber])

    return (
        <Fragment>
            <label className={cx(classes.label)}>핸드폰 번호</label>
            <TextField className={cx(classes.divisionInput)} value={phone} variant="outlined" inputProps={{style: {fontSize: 14}}}/>
            <CustomButton eney className={cx(classes.changeButton)} onClick={toggleModal}>핸드폰번호 변경</CustomButton>
            <UserPhoneChageModal open={changePboneIsOpen} onClose={toggleModal} onSubmit={onSubmit} isSuccess={isSuccess} isCertify={isCertify} certifySuccessNum={certifySuccessNum}/>

        </Fragment>
    );
};

export default React.memo(UserPhoneInfo);