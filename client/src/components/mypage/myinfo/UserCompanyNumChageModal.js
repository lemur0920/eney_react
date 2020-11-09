import React, {Fragment, useState} from 'react';
import {Box, Button} from "@material-ui/core";
import CustomModal from "../../common/CustomModal";
import TextField from "@material-ui/core/TextField";
import useStyles from './style';
import cx from "classnames/bind";


const UserCompanyNumChageModal = ({open, onClose}) => {

    const classes = useStyles();

    const title = "사업자등록 번호 변경";
    const subTitle = "사업자등록번호를 입력하세요";

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <Box>
                <TextField
                    defaultValue=""
                    className={cx(classes.num)}
                    InputProps={{
                        readOnly: true,
                    }}
                />
                <TextField
                    defaultValue=""
                    className={cx(classes.num)}
                    InputProps={{
                        readOnly: true,
                    }}
                />
                <TextField
                    defaultValue=""
                    className={cx(classes.num)}
                    InputProps={{
                        readOnly: true,
                    }}
                />
            </Box>
            <Box>
                <Button className={cx(classes.btn)} onClick={onClose}>취소</Button>
                <Button className={cx(classes.btn)} disabled={true}>사업자등록번호 변경</Button>
            </Box>
        </CustomModal>
    );
};

export default UserCompanyNumChageModal;