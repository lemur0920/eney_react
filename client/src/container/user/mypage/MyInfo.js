import React, {useEffect,Fragment,memo,useCallback,useState} from 'react';
import Box from "@material-ui/core/Box";
import {useDispatch, useSelector} from "react-redux";
import {myinfo,updateEmail,updateAddress} from "../../../modules/user/mypage/mypage";
import PasswordChange from "../../../components/mypage/myinfo/PasswordChange";
import UserIdInfo from "../../../components/mypage/myinfo/UserIdInfo";
import UserNameInfo from "../../../components/mypage/myinfo/UserNameInfo";
import UserPhoneInfo from "../../../components/mypage/myinfo/UserPhoneInfo";
import UserCompanyInfo from "../../../components/mypage/myinfo/UserCompanyInfo";
import UserAddressInfo from "../../../components/mypage/myinfo/UserAddressInfo";
import UserEmailInfo from "../../../components/mypage/myinfo/UserEmailInfo";
import {changeField} from "../../../modules/user/mypage/mypage";
import useStyles from '../../../components/mypage/myinfo/style'
import cx from "classnames";



const MyInfo = () => {

    const classes = useStyles();

    const dispatch = useDispatch();

    const [newPw, setNewPw] = useState({
        currentPw:"",
        newPw:"",
        newPwCheck:""
    });


    const {user,address,loading,error,changePw} = useSelector(({mypage,loading}) =>({
        user: mypage.user,
        loading:loading['mypage/MYINFO'],
        error: mypage.checkError,
        address:mypage.address,
        changePw:mypage.changePw
    }))

    useEffect(() => {

        if(loading){
            return;
        }

        dispatch(myinfo())

    },[])

    const changeEmail = (email) => {
        const newEmail = `${email.id}@${email.domain}`

        if(email == ""  || email === undefined){
            console.log("email없음")
        }

        dispatch(updateEmail(newEmail))
    }

    const changeAddress = (address) => {

        if(address == ""  || address === undefined){
        }

        dispatch(updateAddress(address))
    }

    const onChangePW = useCallback((e) =>{
        const {name, value} = e.target;
        //
        dispatch(changeField({
            form:"changePw",
            key:name,
            value
        }))
    },[]);



    return (

        <Fragment>
        {!loading && (
        <Box className={cx(classes.tabContent)}>
            <Box className={cx(classes.division)}>
                <Box>
                    <UserIdInfo id={user.userid}/>
                </Box>
                <Box>
                    <PasswordChange/>
                </Box>
            </Box>
            <Box className={cx(classes.division)}>
                <Box>
                    <UserNameInfo name={user.name}/>
                </Box>
                <Box>
                    <UserPhoneInfo/>
                    {/*<label className={classes.label}>핸드폰번호</label>*/}
                    {/*<TextField className={classes.divisionInput} variant="outlined" inputProps={{style: {fontSize: 14}}} value={user.phone_number}/>*/}
                    {/*<Button className={classes.changeButton}>번호 변경</Button>*/}
                </Box>
            </Box>
            <Box className={cx(classes.division)}>
                <Fragment>
                {user.type === "COMPANY" && (
                    <UserCompanyInfo name={user.company_name} number={user.corporate_number} type={""}/>
                )}
                <Box>
                    <UserAddressInfo user={user} address={address} error={error}/>
                    {/*<label className={classes.label}>주소</label>*/}
                    {/*<TextField className={classes.fullInput} fullWidth value="소상인" variant="outlined" inputProps={{style: {fontSize: 14}}}/>*/}
                    {/*<TextField className={classes.detailAddrInput} value="상세주소오" fullWidth variant="outlined" inputProps={{style: {fontSize: 14}}}/>*/}
                    {/*<Button className={classes.changeButton}>주소 변경</Button>*/}
                </Box>
                <Box>
                    <UserEmailInfo email={user.email} onSubmit={changeEmail} error={error}/>
                    {/*<label className={classes.label}>세금계산서처리메일</label>*/}
                    {/*<TextField className={classes.divisionInput} value="asdads@naver.com" variant="outlined" inputProps={{style: {fontSize: 14}}}/>*/}
                    {/*<Button className={classes.changeButton}>메일 변경</Button>*/}
                </Box>
                </Fragment>
            </Box>
        </Box>
    )}
        </Fragment>
    );
};

export default React.memo(MyInfo);