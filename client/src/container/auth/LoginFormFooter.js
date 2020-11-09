import React,{Fragment,useState,useEffect} from 'react';
import styled from "styled-components";
import palette from "../../lib/styles/palette";
import {Link} from "react-router-dom";
import { makeStyles } from '@material-ui/core/styles';
import {TextField,Box} from '@material-ui/core';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import {initializeForm,idCheck,passwordReset} from '../../modules/auth'
import CustomModal from "../../components/common/CustomModal";
import Button from "../../components/common/Button";
import Certification from "../../components/util/Certification";
import UserIdFindModal from "../../components/auth/UserIdFindModal";
import {useDispatch, useSelector} from "react-redux";
import UserPwFindModal from "../../components/auth/UserPwFindModal";
import CustomAlertModal from "../../components/common/CustomAlertModal";

const useStyles = makeStyles(theme => ({
    button:{
        float:'right',
        marginTop: 24,
        marginBottom: 8,
        marginLeft: 13,
        marginRight: 13,
        backgroundColor: palette.cyan[0],
        color:'white',
        "&:hover": {
            backgroundColor: palette.cyan[1]
        }
    },
    textField:{
        marginTop: 16,
        marginBottom: 6,
        marginRight: 10,
    }
}));


const LoginFormFooterBox = styled.div`
    margin-top:2rem;
    text-align:center;
    a{
      margin:1rem;
      color:${palette.gray[6]};
      text-decoration: underline;
      &:hover{
        color: ${palette.gray[9]}
      }
    }
`;

const UserId = styled(TextField)({
    width:250,
    marginRight:8,
    // float:'left'
});

const UserPw = styled(TextField)({
    width:250,
    marginRight:8,
    float:'left'
});

const UserPwCheck = styled(TextField)({
    width:250,
    marginRight:8,
    float:'left'
});

const RequestButton = styled(Button)({
    float:'left',
    marginTop: 24,
    marginBottom: 8,
    marginLeft: 10,
    marginRight: 10,
});

const CancelButton = styled(Button)({
    float:'left',
    marginTop: 24,
    marginBottom: 8,
    marginLeft: 10,
    marginRight: 10,
});

const CustomButton = styled(Button)`
  float:right;
`;



const LoginFormFooter = () => {
    const classes = useStyles();

    const {userId,pwFind} = useSelector(({auth}) =>({
        userId:auth.idFind.userId,
        pwFind:auth.pwFind
        // loading:loading["mypage/UPDATE_PHONE"]
    }))
    const dispatch = useDispatch();

    const [findIdIsOpen, setFindIdIsOpen] = useState(false);
    const [findPwIsOpen, setFindPwIsOpen] = useState(false);

    const findIdTitle = "아이디 찾기";
    const findIdSubTitle = "본인 인증 후 아이디를 확인하세요";
    const findPwTitle = "비밀번호 찾기";
    const findPwSubTitle = "본인 인증 후 비밀번호를 변경하세요";

    const [certifiIsOpen,setCertifiIsOpen] = useState(false);
    const [alertIsOpen, setAlertIsOpen] = useState(false);

    const toglleModal = () =>{
        setCertifiIsOpen(!certifiIsOpen)
    }

    const onIdCheck = (userId) => {
        dispatch(idCheck(userId));
    }

    const onRecoverPw = (userId, newPw, newPwCheck) =>{

        dispatch(passwordReset(userId,newPw, newPwCheck));
    }

    useEffect( () => {
        dispatch(initializeForm('idFind'));

    },[findIdIsOpen])

    useEffect( () => {
        dispatch(initializeForm('pwFind'));

    },[findPwIsOpen])

    useEffect( () => {
        if(pwFind.result === true){
            setFindPwIsOpen(false);
            setAlertIsOpen(true)
        }
    },[pwFind.result])


    return (
        <Fragment>
            <LoginFormFooterBox>
                <Link to="#" onClick={() => setFindIdIsOpen(!findIdIsOpen)}> 아이디 찾기</Link>
                <Link to="#" onClick={() => setFindPwIsOpen(!findPwIsOpen)}> 비밀번호 찾기</Link>
                <Link to="/auth/user_type">회원가입</Link>
            </LoginFormFooterBox>
            <UserIdFindModal userId={userId} findIdIsOpen={findIdIsOpen} setFindIdIsOpen={setFindIdIsOpen} findIdSubTitle={findIdSubTitle} findIdTitle={findIdTitle}/>
            <UserPwFindModal pwFind={pwFind} onIdCheck={onIdCheck} onRecoverPw={onRecoverPw} findPwIsOpen={findPwIsOpen} setFindPwIsOpen={setFindPwIsOpen} findPwSubTitle={findPwSubTitle} findPwTitle={findPwTitle}/>
        {/*    <CustomModal open={findPwIsOpen} onClose={()=> setFindPwIsOpen(!findPwIsOpen)} title={findPwTitle} subTitle={findPwSubTitle}>*/}
        {/*            <Box>*/}
        {/*                <UserPw*/}
        {/*                    id="pw-find-read-only-input"*/}
        {/*                    label="비밀번호"*/}
        {/*                    defaultValue=""*/}
        {/*                    className={classes.textField}*/}
        {/*                    InputProps={{*/}
        {/*                        readOnly: true,*/}
        {/*                    }}*/}
        {/*                />*/}
        {/*            </Box>*/}
        {/*            <Box>*/}
        {/*                <UserPwCheck*/}
        {/*                    id="pw-find-check-read-only-input"*/}
        {/*                    label="비밀번호 확인"*/}
        {/*                    defaultValue=""*/}
        {/*                    className={classes.textField}*/}
        {/*                    InputProps={{*/}
        {/*                        readOnly: true,*/}
        {/*                    }}*/}
        {/*                />*/}
        {/*            </Box>*/}
        {/*            <Box>*/}
        {/*                <CustomButton eney className={classes.button} onClick={()=> setFindPwIsOpen(!findPwIsOpen)}>취소</CustomButton>*/}
        {/*                <CustomButton eney className={classes.button} disabled={true}>변경</CustomButton>*/}
        {/*                <CustomButton eney className={classes.button}>본인 인증</CustomButton>*/}
        {/*            </Box>*/}
        {/*</CustomModal>*/}
        {/*    {certifiIsOpen && (*/}
        {/*        <Certification onClose={toglleModal} type="PW_RECOVER"/>*/}
        {/*    )}*/}
            <CustomAlertModal open={alertIsOpen} onClose={() => setAlertIsOpen(!alertIsOpen)} text="패스워드가 변경되었습니다"/>
        </Fragment>




    );
};

export default LoginFormFooter;