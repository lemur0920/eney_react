import React, {Fragment, useCallback, useState,useEffect} from 'react';
import {TextField} from "@material-ui/core";
import Box from "@material-ui/core/Box";
import useStyles from './style'
import cx from "classnames";
import PasswordChageModal from "./PasswordChageModal";
import {useDispatch, useSelector} from "react-redux";
import {isPassword,isEqual} from '../../../lib/validation/validation';
import {changeField, updatePw} from "../../../modules/user/mypage/mypage";
import CustomAlertModal from "../../common/CustomAlertModal";
import Button from "../../common/Button";
import styled from "styled-components";
import swal from 'sweetalert';


const CustomButton = styled(Button)`
    display: inline-block;
    margin:0;
    padding: 9px 16px;
`;

const PasswordChange = () => {
    const [pwError, setPwError] = useState(null);

    const classes = useStyles();
    const dispatch = useDispatch();
    const {updateDate,changePw,error,loading} = useSelector(({mypage,loading}) =>({
        updateDate:mypage.user.password_last_update,
        changePw:mypage.changePw,
        error:mypage.changePw.error,
        loading:loading['mypage/UPDATE_PW']
    }))

    const [changePwIsOpen, setChangePwIsOpen] = useState(false);

    const toggleModal = () =>{
        setChangePwIsOpen(!changePwIsOpen)
    }

    const onChange = useCallback((e) =>{

        const {name, value} = e.target;

        dispatch(changeField({
            form:"changePw",
            key:name,
            value
        }))

    },[]);

    const onSubmit = () => {
        let error = null;
        const result1 = isPassword(changePw.newPw)
        if(result1 !== true){
            error = result1;
        }
        const result2 = isPassword(changePw.newPwCheck)
        if(result2 !== true){
            error = result2;
        }
        const result3 = isEqual(changePw.newPw, changePw.newPwCheck)
        if(result3 !== true){
            error = result3;
        }

        if(changePw.currentPw === "" || changePw.currentPw === null){
            error = "현재 패스워드를 입력하세요."
        }

        if(error !== null){
            dispatch(changeField({
                form:"changePw",
                key:"error",
                value:error
            }))

            return;

        }else{
            dispatch(updatePw(changePw))
        }
    }

    useEffect( () =>{

        if(loading !== true){
            return
        }
        setChangePwIsOpen(false);
        swal("패스워드가 변경되었습니다");

    },[updateDate])

    return (
        <Fragment>
            <label className={cx(classes.label)}>비밀번호</label>
            <TextField className={cx(classes.divisionInput)} value={updateDate === null ? "최종 변경일 : 없음" : (`최종 변경일 : ${updateDate}`)} variant="outlined" inputProps={{style: {fontSize: 14}}}/>
            <CustomButton eney className={cx(classes.changeButton)} onClick={toggleModal}>비밀번호 변경</CustomButton>
            <PasswordChageModal open={changePwIsOpen} onClose={toggleModal} onChange={onChange} onSubmit={onSubmit} error={error}/>

            {/*<CustomAlertModal open={alertIsOpen} onClose={() => setAlertIsOpen(!alertIsOpen)} text="패스워드가 변경되었습니다"/>*/}
        </Fragment>
    );
};

export default React.memo(PasswordChange);