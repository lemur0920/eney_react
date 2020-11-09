import React, {Fragment, useState} from 'react';
import {Box, Button} from "@material-ui/core";
import CustomModal from "../../common/CustomModal";
import TextField from "@material-ui/core/TextField";
import useStyles from './style'
import cx from "classnames/bind";
import Certification from "../../util/Certification";
import Typography from "@material-ui/core/Typography";


const PasswordChageModal = ({open, onClose,onChange,onSubmit,error}) => {

    const classes = useStyles();

    // const [certifiIsOpen,setCertifiIsOpen] = useState(false);
    //
    // const toglleModal = () =>{
    //     setCertifiIsOpen(!certifiIsOpen)
    //
    // }

    const modalStyle ={
        width:"500px"
    }

    const title = "비밀번호 변경";
    const subTitle = "비밀번호 길이는 6~16자 이내로 반드시 특수문자가 1개 이상 포함되어야 합니다.";

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle} style={modalStyle}>
            <form onSubmit={ (e)=> { e.preventDefault(); onSubmit()}}>
                <Typography variant="subtitle1" color="error">
                    {error}
                </Typography>
            <Box>
                <TextField
                    label="현재 비밀번호"
                    className={cx(classes.textField)}
                    helperText="현재 비밀번호를 입력하세요"
                    name="currentPw"
                    // onChange={onChange}
                    // value={changePw.currentPw}
                    onBlur={onChange}
                />
                <TextField
                    label="비밀번호"
                    className={cx(classes.textField)}
                    helperText="비밀번호를 입력하세요"
                    name="newPw"
                    // onChange={onChange}
                    // value={changePw.newPw}
                    onBlur={onChange}
                />
                <TextField
                    label="비밀번호 확인"
                    className={cx(classes.textField)}
                    helperText="비밀번호를 다시 입력하세요"
                    name="newPwCheck"
                    // onChange={onChange}
                    // value={changePw.newPWCheck}
                    onBlur={onChange}
                />
            </Box>
            <Box>
                <Button className={cx(classes.btn)}onClick={onClose}>취소</Button>
                <Button className={cx(classes.btn)}  type="submit" >비밀번호 변경</Button>
                {/*<Button className={cx(classes.btn)} onClick={toglleModal}>본인인증</Button>*/}
            </Box>
            </form>
            {/*{certifiIsOpen && (*/}
            {/*    <Certification onClose={toglleModal} type="PW_RECOVER"/>*/}
            {/*)}*/}
        </CustomModal>
    );
};

export default React.memo(PasswordChageModal);