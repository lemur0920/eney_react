import React,{useState} from 'react';
import {Box} from "@material-ui/core/index";
import TextField from "@material-ui/core/TextField/index";
import FormControl from "@material-ui/core/FormControl/index";
import NativeSelect from "@material-ui/core/Select/Select";
import MenuItem from "@material-ui/core/MenuItem/index";
import Typography from "@material-ui/core/Typography/index";
import CustomModal from "../../common/CustomModal";
import styled from "styled-components";
import Button from "../../common/Button";
import InputLabel from "@material-ui/core/InputLabel/index";
import Select from '@material-ui/core/Select/index';
import useStyles from './style'
import cx from "classnames";
import ConfirmModal from "../../common/ConfirmModal";

const CustomButton = styled(Button)`
    display: inline-block;
    float:right;
    margin:0;
    height:70%;
    width: 160px;
    margin: 15px 0;
    @media (max-width: 768px) {
      width:100%;
    }
    
`;
const CustomCloseButton = styled(Button)`
    display: inline-block;
    float:right;
    margin:0;
    height:70%;
    width: 160px;
    margin: 15px 5px;
    background-color: white;
    color:#37afe5;
    border: 1px solid #37afe5;
     &:hover{
      background: white;
    }
    @media (max-width: 768px) {
      width:100%;
    }
    
`;


const TokenGenModal = ({open, onClose, onSubmit, error,isSuccess}) => {

    const classes = useStyles();

    const serviceArray = ["PATCHCALL","MESSAGE"]

    const [token, setToken] = useState({
        service:serviceArray[0],
        allocationIP_01:"",
        allocationIP_02:"",
        allocationIP_03:""
    });

    const title = "";
    const subTitle = ""

    const style = {
        maxWidth:'1000px',
        padding: '40px'
    }

    const onChange = (e) =>{
        let {name, value} = e.target
        // e.target.value = Math.max(0, parseInt(e.target.value) ).toString().slice(0,3)

        if(name !== 'service' && value > 255){
            value = 0
        }
        setToken({
            ...token,
            [name]:value
        })

    }

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle} style={style}>
            <form onSubmit={(e) => {e.preventDefault(); onSubmit(token)}}>
                <Box className={cx(classes.box)}>
                    <table className={cx(classes.table)}>
                        <tbody>
                            <tr>
                                <td className={cx(classes.th)}>
                                    서비스
                                </td>
                                <td className={cx(classes.td)}>
                                    <FormControl color="secondary" >
                                        <Select
                                            name='service'
                                            value={token.service}
                                            onChange={onChange}
                                        >
                                            {serviceArray.map((item, index) => (
                                                <MenuItem  key={item} value={item}>{item}</MenuItem>
                                            ))
                                            }
                                        </Select>
                                    </FormControl>
                                </td>
                            </tr>
                            <tr >
                                <td className={cx(classes.th)}>
                                    연동 IP
                                </td>
                                <td className={cx(classes.td)}>
                                    <TextField
                                        className={cx(classes.allocationIP)}
                                        name="allocationIP_01"
                                        type="number"
                                        value={token.allocationIP_01}
                                        onChange={onChange}
                                        InputLabelProps={{
                                            shrink: true,
                                        }}
                                        rowsMax={2}
                                        required={true}
                                    />
                                    <TextField
                                        className={cx(classes.allocationIP)}
                                        name="allocationIP_02"
                                        type="number"
                                        value={token.allocationIP_02}
                                        onChange={onChange}
                                        InputLabelProps={{
                                            shrink: true,
                                            inputprops: { min: 1}
                                        }}
                                        required={true}
                                    />
                                    <TextField
                                        className={cx(classes.allocationIP)}
                                        name="allocationIP_03"
                                        type="number"
                                        value={token.allocationIP_03}
                                        onChange={onChange}
                                        InputLabelProps={{
                                            shrink: true,
                                            inputprops: { min: 1}
                                        }}
                                        required={true}
                                    />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <CustomCloseButton type="button" onClick={() => {onClose();}}>닫기</CustomCloseButton>
                    <CustomButton eney type="submit">토큰 생성</CustomButton>
                </Box>
            </form>

            <ConfirmModal open={isSuccess} onClose={onClose}>
                토큰 생성 완료
            </ConfirmModal>
        </CustomModal>
    );
};

export default TokenGenModal;