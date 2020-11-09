import React, {Fragment, useState} from 'react';
import {Box} from "@material-ui/core";
import CustomModal from "../../common/CustomModal";
import TextField from "@material-ui/core/TextField";
import useStyles from './style';
import cx from "classnames/bind";
import Postcode from "../../util/Postcode";
import Certification from "../../util/Certification";
import styled from "styled-components";
import Button from "../../common/Button";

const CustomButton = styled(Button)`
  float:right;
`;


const UserPhoneChageModal = ({open, onClose, onSubmit, isCertify,isSuccess,certifySuccessNum}) => {
    const classes = useStyles();

    const [certifiIsOpen,setCertifiIsOpen] = useState(false);

    const toggleModal = () =>{
        setCertifiIsOpen(!certifiIsOpen)
    }

    const title = "핸드폰 번호 변경";
    const subTitle = "본인 인증을 완료해주세요";

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <form onSubmit={ (e) => {e.preventDefault(); onSubmit();}}>
            <Box>
                <TextField
                    // defaultValue=""
                    value={certifySuccessNum === null ? ("") : certifySuccessNum}
                    className={cx(classes.num)}
                    InputProps={{
                        readOnly: true,
                    }}
                />
                {/*<TextField*/}
                {/*    defaultValue=""*/}
                {/*    className={cx(classes.num)}*/}
                {/*    InputProps={{*/}
                {/*        readOnly: true,*/}
                {/*    }}*/}
                {/*/>*/}
                {/*<TextField*/}
                {/*    defaultValue=""*/}
                {/*    className={cx(classes.num)}*/}
                {/*    InputProps={{*/}
                {/*        readOnly: true,*/}
                {/*    }}*/}
                {/*/>*/}
            </Box>
            <Box>
                <CustomButton eney  onClick={onClose}>취소</CustomButton>
                {isCertify ?(
                    <CustomButton eney  type="submit" disabled={!isCertify}>핸드폰번호 변경</CustomButton>
                ):(
                    <CustomButton eney onClick={toggleModal} >본인 인증 </CustomButton>
                )}
            </Box>
            {/*<CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle} error={error} style={modalStyle}>*/}
            {certifiIsOpen && (
                <Certification onClose={toggleModal} type="PHONE_CHANGE"/>
            )}
            {/*</CustomModal>*/}
            </form>
        </CustomModal>
    );
};

export default UserPhoneChageModal;