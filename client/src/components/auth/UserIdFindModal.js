import React, {useEffect, useState} from 'react';
import {Box, TextField, Typography} from "@material-ui/core";
import CustomModal from "../common/CustomModal";
import styled from "styled-components";
import Button from "../common/Button";
import Certification from "../util/Certification";
import {initializeForm} from "../../modules/auth";
import {useDispatch} from "react-redux";
import palette from "../../lib/styles/palette";

const UserId = styled(Typography)({
    padding:10,
    color:palette.cyan[0],
    // float:'left'
});

const CustomButton = styled(Button)`
  float:right;
`;

const UserIdFindModal = ({userId, findIdIsOpen, setFindIdIsOpen,findIdTitle, findIdSubTitle}) => {

    const dispatch = useDispatch();

    const [certifiIsOpen,setCertifiIsOpen] = useState(false);

    const toggleModal = () =>{
        setCertifiIsOpen(!certifiIsOpen)
    }


    // useEffect( () => {
    //     initializeForm('idFind');
    //
    // },[])


    return (
        <CustomModal open={findIdIsOpen} onClose={() => setFindIdIsOpen(!findIdIsOpen)} title={findIdTitle} subTitle={findIdSubTitle}>
            <Box>
                {/*<UserId*/}
                {/*    id="id-find-read-only-input"*/}
                {/*    label="아이디"*/}
                {/*    placeholder="본인 인증을 완료하세요"*/}
                {/*    // className={classes.textField}*/}
                {/*    InputProps={{*/}
                {/*        readOnly: true,*/}
                {/*    }}*/}
                {/*/>*/}


                <UserId>{userId}</UserId>
            </Box>
            <Box>
                <CustomButton eney  onClick={() => setFindIdIsOpen(!findIdIsOpen)}>{userId !== null ? ("확인") : ("취소")}</CustomButton>
                <CustomButton eney onClick={toggleModal}>본인 인증</CustomButton>
            </Box>
            {certifiIsOpen && (
                <Certification onClose={toggleModal} type="ID_FIND"/>
            )}
        </CustomModal>
    );
};

export default UserIdFindModal;