import React,{useState} from 'react';
import {Box} from "@material-ui/core";
import TextField from "@material-ui/core/TextField";
import FormControl from "@material-ui/core/FormControl";
import NativeSelect from "@material-ui/core/Select/Select";
import MenuItem from "@material-ui/core/MenuItem";
import Typography from "@material-ui/core/Typography";
import CustomModal from "../../common/CustomModal";
import styled from "styled-components";
import Button from "../../../components/common/Button";

const CustomButton = styled(Button)`
    display: inline-block;
    float:right;
    margin:5px;
    margin-top:20px;
    
`;

const CouponRegisterModal = ({open, onClose, onSubmit, error,couponResult}) => {

    const [couponNum, setCouponNum] = useState("");

    const title = "쿠폰 등록";
    const subTitle = "쿠폰 번호를 입력하세요"

    const style = {
        width:'350px'
    }
    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle} style={style}>
            <form onSubmit={(e) => {e.preventDefault();onSubmit(couponNum);}}>
                <Box>
                    <TextField
                        fullWidth={true}
                        name="couponNum"
                        // className={cx(classes.email)}
                        value={couponNum}
                        InputProps={{
                        }}
                        onChange={(e) => setCouponNum(e.target.value)}
                        required={true}
                    />
                </Box>
                <Box>
                    <Typography color="error">{error}</Typography>
                    <Typography color="primary">{couponResult === true && ('쿠폰 등록 성공')}</Typography>
                    <CustomButton eney type="button" onClick={onClose}>취소</CustomButton>
                    <CustomButton eney type="submit">쿠폰 등록</CustomButton>
                </Box>
            </form>
        </CustomModal>
    );
};

export default CouponRegisterModal;