import React, {Fragment, useState} from 'react';
import {Box, Button} from "@material-ui/core";
import CustomModal from "../../common/CustomModal";
import TextField from "@material-ui/core/TextField";
import useStyles from './style'
import cx from "classnames/bind";


const UserCompanyNameChageModal = ({open, onClose}) => {

    const classes = useStyles();

    const title = "기업명 변경";
    const subTitle = "변경하실 기업명을 입력하세요";

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <Box>
                <TextField
                    label="기업명"
                    defaultValue=""
                    className={cx(classes.textField)}
                    helperText="기업명을 입력하세요"
                />
            </Box>
            <Box>
                <Button className={cx(classes.btn)}onClick={onClose}>취소</Button>
                <Button className={cx(classes.btn)}>비밀번호 변경</Button>
            </Box>
        </CustomModal>
    );
};

export default UserCompanyNameChageModal;