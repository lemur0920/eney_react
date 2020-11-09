import React, {Fragment, useState,useEffect} from 'react';
import {Box, Button} from "@material-ui/core";
import CustomModal from "../../common/CustomModal";
import TextField from "@material-ui/core/TextField";
import useStyles from './style'
import cx from "classnames/bind";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";


const UserCompanyKindChageModal = ({open, onClose,companyKindList,onSubmit}) => {

    const classes = useStyles();

    const [textIsOpen, setTextIsOpen] = useState(false);
    const [companyKind, setCompanyKind] = useState("");
    const [newCompanyKind, setNewCompanyKind] = useState("");

    const handleCompanyKind = (e) =>{
        const kind = e.target.value;

        setCompanyKind(kind)
        if(kind === 'custom'){
            setNewCompanyKind("")
        }else{
            setNewCompanyKind(kind)
        }

        // if(kind === 0){
        //
        // }
    }

    const title = "업태/업종 변경";
    const subTitle = "변경하실 업태/업종을 입력하세요";

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <form onSubmit={(e) => {e.preventDefault(); onSubmit(newCompanyKind)}}>
            <Box>
                {companyKindList && (
                    <FormControl color="secondary" className={cx(classes.select)} >

                        {companyKind === 'custom' && (
                            <TextField
                                value={newCompanyKind}
                                className={cx(classes.textField)}
                                onChange={(e) => setNewCompanyKind(e.target.value)}
                                helperText="업종을 입력하세요"
                                required={true}
                            />
                        )}

                        <InputLabel color="secondary" >업종 선택</InputLabel>
                        <Select
                            required={true}
                            value={companyKind}
                            onChange={handleCompanyKind}
                        >
                            {companyKindList.map((item, index) => (
                                <MenuItem  key={item.kind} value={item.kind}>{item.kind}</MenuItem>
                            ))
                            }
                           <MenuItem value="custom">직접 입력</MenuItem>
                        </Select>
                    </FormControl>
                )}
            </Box>
            <Box>
                <Button className={cx(classes.btn)}onClick={onClose}>취소</Button>
                <Button className={cx(classes.btn)} type="submit">업태/업종 변경</Button>
            </Box>
            </form>
        </CustomModal>
    );
};

export default UserCompanyKindChageModal;