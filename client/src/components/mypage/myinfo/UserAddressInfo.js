import React, {Fragment, useEffect, useState} from 'react';
import useStyles from './style'
import cx from "classnames";
import {TextField} from "@material-ui/core";
import Box from "@material-ui/core/Box";
import swal from 'sweetalert';
import UserAddrChangeModal from "./UserAddrChangeModal";
import {changeField, updateAddress} from "../../../modules/user/mypage/mypage";
import {useSelector,useDispatch} from "react-redux";
import Button from "../../common/Button";
import styled from "styled-components";

const CustomButton = styled(Button)`
    display: inline-block;
    margin:0;
    padding: 9px 16px;
    margin-top: 5px;
    //@media (max-width: 768px) {
    //margin-top: 5px;
    //}
    
`;

const UserAddressInfo = () => {

    const classes = useStyles();
    const dispatch = useDispatch();

    const [changeAddrIsOpen, setChangeAddrIsOpen] = useState(false);
    const [newAddress, setNewAddress] = useState({
        postCode:"",
        address:"",
        addressDetail:""
    });

    const {user,address,loading,error} = useSelector(({mypage,loading}) =>({
        user: mypage.user,
        loading:loading['mypage/UPDATE_ADDRESS'],
        error: mypage.checkError,
        address:mypage.address,
    }))

    useEffect(() => {

        if(user && error === null){
            setChangeAddrIsOpen(false)
        }

    },[user, error ,dispatch])

    useEffect( () =>{

        if(!loading){
            return
        }
        setChangeAddrIsOpen(false);
        swal("주소가 변경되었습니다");

    },[loading])

    const toggleModal = () =>{
        setNewAddress({
            postCode:"",
            address:"",
            addressDetail:""
        })
        setChangeAddrIsOpen(!changeAddrIsOpen)
    }

    const changeAddr = (e) =>{

        const {value, name} = e.target;

        setNewAddress({
            ...newAddress,
            [name]:value
        })
    }

    const onSubmit = () => {

        if(address == ""  || address === undefined){
        }

        dispatch(updateAddress(newAddress))
    }

    const getAddress = (data) => {
        let fullAddress = data.address;
        let extraAddress = '';

        if (data.addressType === 'R') {
            if (data.bname !== '') {
                extraAddress += data.bname;
            }
            if (data.buildingName !== '') {
                extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
            }
            fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
        }

        setNewAddress({
            ...newAddress,
            address:fullAddress,
            postCode:data.postcode
        })

    }

    return (
        <Fragment>
            <Box className={cx(classes.addrBox)}>
                <label className={cx(classes.label)}>주소</label>
                <TextField className={cx(classes.addrInput)} value={(user.address ? user.address : "")} variant="outlined" inputProps={{style: {fontSize: 14},readOnly: true}}/>
                <label className={cx(classes.label, classes.postLabel)}>우편번호</label>
                <TextField className={cx(classes.postCodeInput)} value={(user.postCode ? user.postCode : "")} variant="outlined" inputProps={{style: {fontSize: 14},readOnly: true}}/>
            </Box>

            <Box className={cx(classes.addrBox)}>
                <label className={cx(classes.detailLabel)}>상세주소</label>
                <TextField className={cx(classes.detailAddrInput)} value={(user.addressDetail ? user.addressDetail : "")} variant="outlined" inputProps={{style: {fontSize: 14},readOnly: true}}/>
                <CustomButton eney className={cx(classes.changeButton)} onClick={toggleModal}>주소 변경</CustomButton>
            </Box>
            <UserAddrChangeModal open={changeAddrIsOpen} onClose={toggleModal} getAddress={getAddress} onSubmit={onSubmit} address={newAddress} setAddress={setNewAddress} changeAddr={changeAddr}/>
        </Fragment>
    );
};

export default React.memo(UserAddressInfo);