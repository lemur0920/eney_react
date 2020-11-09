import React,{useState} from 'react';
import Typography from "@material-ui/core/Typography";
import {Box} from "@material-ui/core";
import TextField from "@material-ui/core/TextField";
import CustomModal from "../../components/common/CustomModal";
import styled from "styled-components";
import Button from "../../components/common/Button";
import useStyles from './style'
import cx from "classnames";



const CustomButton = styled(Button)`
    display: inline-block;
    margin:5px;
    float: right;
    padding: 9px 16px;
`;

const PasswordCheckModal = ({open, onClose,onSubmit,error}) => {
    const modalStyle ={
        width:"500px"
    }


    const classes = useStyles();
    const [pw, setPw] = useState();

    const title = "비밀번호 확인";
    const subTitle = "비밀번호 길이는 6~16자 이내로 반드시 특수문자가 1개 이상 포함되어야 합니다.";

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle} style={modalStyle}>
            <form onSubmit={ (e)=> { e.preventDefault(); onSubmit(pw);}}>
                <Typography variant="subtitle1" color="error">
                    {error}
                </Typography>
                <Box>
                    <TextField
                        fullWidth={true}
                        label="현재 비밀번호"
                        // className={cx(classes.textField)}
                        helperText="현재 비밀번호를 입력하세요"
                        name="currentPw"
                        onChange={(e) => setPw(e.target.value)}
                        type='password'
                        // value={changePw.currentPw}
                    />
                </Box>
                <Box>
                    <CustomButton eney type="button" onClick={onClose}>취소</CustomButton>
                    <CustomButton eney  type="submit" >확인 후 신청</CustomButton>
                    {/*<Button className={cx(classes.btn)} onClick={toglleModal}>본인인증</Button>*/}
                </Box>
            </form>
            {/*{certifiIsOpen && (*/}
            {/*    <Certification onClose={toglleModal} type="PW_RECOVER"/>*/}
            {/*)}*/}
        </CustomModal>
    );
};

export default PasswordCheckModal;