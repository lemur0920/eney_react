import React, {useState,Fragment} from 'react';
import {Box, TextField} from "@material-ui/core";
import CustomModal from "../common/CustomModal";
import Certification from "../util/Certification";
import styled from "styled-components";
import Button from "../common/Button";


const UserIdInput = styled(TextField)({
    width:220,
    margin:15,
    float:'left'
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

const CustomButton = styled(Button)`
  //float:right;
  margin:12px;
  margin-top:40px;
`;

const UserPwFindModal = ({pwFind,onIdCheck,onRecoverPw, findPwIsOpen, setFindPwIsOpen,findPwTitle, findPwSubTitle}) => {
    const [certifiIsOpen,setCertifiIsOpen] = useState(false);

    const [newPw,setNewPw] = useState("");
    const [newPwCheck,setNewPwCheck] = useState("");

    const [userId,setUserId] = useState("");

    const onChangeId = (e) => {
        setUserId(e.target.value);

    }

    const toggleModal = () =>{
        setCertifiIsOpen(!certifiIsOpen)
    }

    return (
        <CustomModal open={findPwIsOpen} onClose={()=> setFindPwIsOpen(!findPwIsOpen)} title={findPwTitle} subTitle={findPwSubTitle}>
            {pwFind.userId === "" &&  pwFind.success !== true ? (
                <Box>
                    <UserIdInput
                        label="아이디"
                        onChange={onChangeId}
                        placeholder="아이디를 입력하세요"
                        // defaultValue=""
                        // InputProps={{
                        //     fals
                        // }}
                    />
                    <p className="alert_validation">{pwFind.error}</p>
                    <Box>
                        <CustomButton eney onClick={() => {onIdCheck(userId)}}>다음 단계</CustomButton>
                        <CustomButton eney  onClick={()=> setFindPwIsOpen(!findPwIsOpen)}>취소</CustomButton>
                    </Box>
                </Box>
            ):(
                <Fragment>
                    {pwFind.userId !== "" &&  pwFind.success === true && pwFind.isCertify === true &&(
                        <Fragment>
                            <Box>
                                <UserPw
                                    id="pw-find-read-only-input"
                                    label="비밀번호"
                                    onChange={(e) => setNewPw(e.target.value)}
                                    type="password"
                                    // defaultValue=""
                                    // InputProps={{
                                    //     readOnly: true,
                                    // }}
                                />
                            </Box>
                            <Box>
                                <UserPwCheck
                                    id="pw-find-check-read-only-input"
                                    label="비밀번호 확인"
                                    onChange={(e) => setNewPwCheck(e.target.value)}
                                    type="password"
                                    // defaultValue=""
                                    // InputProps={{readOnly: true,}}
                                />
                            </Box>
                        </Fragment>
                        )}
                    <p className="alert_validation">{pwFind.validateError}</p>
                </Fragment>
            )}
                {pwFind.userId !== "" &&  pwFind.success === true &&(
                    <Box>
                        {pwFind.userId !== "" &&  pwFind.success === true && pwFind.isCertify === true &&(
                            <CustomButton eney  disabled={!pwFind.isCertify} onClick={()=> onRecoverPw(pwFind.userId, newPw,newPwCheck)}>변경</CustomButton>
                        )}
                        {pwFind.userId !== "" &&  pwFind.success === true && pwFind.isCertify === true ?(
                            null
                        ):(<CustomButton eney  onClick={toggleModal}>본인 인증</CustomButton>)}
                        <CustomButton eney  onClick={()=> setFindPwIsOpen(!findPwIsOpen)}>취소</CustomButton>
                    </Box>
                )}
            {certifiIsOpen && (
                <Certification onClose={toggleModal} type="PW_RECOVER"/>
            )}
        </CustomModal>
    );
};

export default UserPwFindModal;