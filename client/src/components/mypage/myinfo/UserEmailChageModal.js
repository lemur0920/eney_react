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


const UserEmailChageModal = ({open, onClose, onSubmit,domain, address, handleChange, handleSelect, error}) => {

    const classes = useStyles();

    // const [domain, setDomain] = useState("custom");
    // const [address, setAddress] = useState({
    //     id: "",
    //     domain:""
    // })

    // useEffect(() => {
    //     return () => {
    //         setAddress({id: "", domain:""})
    //     }
    // }, [])

    const title = "E-MAIL 변경";
    const subTitle = "세금계산서가 발행 될 E-MAIL입니다.";

    // const handleSelect = event => {
    //
    //     const value = event.target.value;
    //
    //     setDomain(value);
    //     if(value !== "custom"){
    //         setAddress({
    //             ...address,
    //             domain:value
    //         })
    //     }
    // };
    //
    // const handleChange = (e) =>{
    //     const {name, value}  = e.target
    //
    //     console.log(name, value)
    //
    //     setAddress({
    //         ...address,
    //         [name] : value
    //     })
    //
    // }


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle} error={error}>
            <form onSubmit={(e) => {e.preventDefault(); onSubmit(address);}}>
                <Box>
                    <TextField
                        name="id"
                        className={cx(classes.email)}
                        value={address.id}
                        InputProps={{
                        }}
                        onChange={handleChange}
                        required={true}
                    />
                    <span className={cx(classes.at)}>@</span>
                    {domain === "custom" &&(
                        <TextField
                            name="domain"
                            value={address.domain}
                            className={cx(classes.email)}
                            InputProps={{
                                readOnly: domain !== "custom",
                            }}
                            onChange={handleChange}
                        />
                    )}
                    <FormControl>
                        <NativeSelect
                            className={cx(classes.domainSelect)}
                            value={domain}
                            onChange={handleSelect}
                        >
                            <MenuItem value="custom">직접 입력</MenuItem>
                            <MenuItem value="naver.com">naver.com</MenuItem>
                            <MenuItem value="google.com">google.com</MenuItem>
                        </NativeSelect>
                    </FormControl>
                    <Typography color="error">{error}</Typography>
                </Box>
                <Box>
                    <Button className={cx(classes.btn)} type="button" onClick={onClose}>취소</Button>
                    <Button className={cx(classes.btn)} type="submit">EMAIL 변경</Button>
                </Box>
            </form>
        </CustomModal>
    );
};

export default UserEmailChageModal;