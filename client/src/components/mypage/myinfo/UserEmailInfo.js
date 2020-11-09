import React, {Fragment, useState,useEffect} from 'react';
import useStyles from './style'
import cx from "classnames";
import {TextField} from "@material-ui/core";
import Box from "@material-ui/core/Box";
import UserEmailChageModal from "./UserEmailChageModal";
import {useDispatch, useSelector} from "react-redux";
import swal from 'sweetalert';
import Button from "../../common/Button";
import styled from "styled-components";


const CustomButton = styled(Button)`
    display: inline-block;
    margin:0;
    padding: 9px 16px;
    margin-top: 3px;
    //@media (max-width: 768px) {
    //margin-top: 3px;
    //}
`;

const UserEmailInfo = ({onSubmit}) => {

    const classes = useStyles();
    const dispatch = useDispatch();

    const [changeEmailIsOpen, setChangeEmailIsOpen] = useState(false);
    const [domain, setDomain] = useState("custom");
    const [address, setAddress] = useState({
        id: "",
        domain:""
    })

    const {email,error,loading} = useSelector(({mypage,loading}) =>({
        email: mypage.user.email,
        error: mypage.checkError,
        loading:loading["mypage/UPDATE_EMAIL"]
    }))

    useEffect(() => {

        if(!loading){
            return
        }

        setChangeEmailIsOpen(false)
        swal("E-MAIL이 변경되었습니다");


    },[email])

    const toggleModal = () =>{
        setAddress({id: "", domain:""})
        setChangeEmailIsOpen(!changeEmailIsOpen)
    }

    const handleSelect = event => {

        const value = event.target.value;

        setDomain(value);
        if(value !== "custom"){
            setAddress({
                ...address,
                domain:value
            })
        }
    };

    const handleChange = (e) =>{
        const {name, value}  = e.target

        setAddress({
            ...address,
            [name] : value
        })

    }



    return (
        <Fragment>
            <label className={cx(classes.label)}>세금계산서처리메일</label>
            <TextField className={cx(classes.divisionInput)} value={email} variant="outlined" inputProps={{style: {fontSize: 14}}}/>
            <CustomButton eney className={cx(classes.changeButton)} onClick={toggleModal}>메일 변경</CustomButton>
            <UserEmailChageModal open={changeEmailIsOpen} onClose={toggleModal} onSubmit={onSubmit} domain={domain} address={address} handleChange={handleChange} handleSelect={handleSelect} error={error}/>
        </Fragment>
    );
};

export default React.memo(UserEmailInfo);