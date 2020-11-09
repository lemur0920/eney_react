import React, {Fragment, useState,useEffect} from 'react';
import {Box, Button} from "@material-ui/core";
import CustomModal from "../../common/CustomModal";
import TextField from "@material-ui/core/TextField";
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import NativeSelect from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import useStyles from './style';
import cx from "classnames/bind";
import Typography from "@material-ui/core/Typography";
import Postcode from "../../util/Postcode";


const UserAddrChangeModal = ({open, onClose, onSubmit,changeAddr,address,getAddress, error}) => {

    const classes = useStyles();

    const [show, setShow] = useState(false);
    const showMap = () =>{
        setShow(!show)
    }

    const title = "주소 변경";
    const subTitle = "";

    const modalStyle ={
        width:"700px"
    }

    const handleAddress = (data) => {
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
    }


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle} error={error} style={modalStyle}>
            <form onSubmit={(e) => {e.preventDefault(); onSubmit();}}>
                <Box className={cx(classes.addrBox)}>
                    <label className={cx(classes.label)}>주소</label>
                    <TextField className={cx(classes.addrInput)} value={address.address} name="address" variant="outlined" onChange={changeAddr} inputProps={{style: {fontSize: 14},readOnly: true}}/>

                    <label className={cx(classes.label, classes.postLabel)}>우편번호</label>
                    <TextField className={cx(classes.postCodeInput)} name="postCode" variant="outlined" value={address.postCode} onChange={changeAddr} inputProps={{style: {fontSize: 14},readOnly: true}}/>
                </Box>

                <Box className={cx(classes.addrBox)}>
                    <label className={cx(classes.detailLabel)}>상세주소</label>
                    <TextField className={cx(classes.detailAddrInput)} name="addressDetail" onBlur={changeAddr} variant="outlined" inputProps={{style: {fontSize: 14}}}/>
                    <Button className={cx(classes.searchButton)} onClick={showMap}>검색</Button>
                </Box>
                <Box className={cx(classes.modalBtnBox)}>
                    <Button className={cx(classes.btn)} type="submit" >저장</Button>
                    <Button className={cx(classes.btn)} onClick={onClose} >취소</Button>
                </Box>
            </form>
            <CustomModal open={show} onClose={showMap}>
                <Postcode getAddress={getAddress} onClose={showMap}/>
            </CustomModal>
        </CustomModal>
    );
};

export default UserAddrChangeModal;