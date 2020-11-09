import React,{useState} from 'react';
import TextField from "@material-ui/core/TextField";
import { makeStyles } from '@material-ui/core/styles';
import styled from "styled-components";
import Button from "../../common/Button";
import AlertModal from "../../common/AlertModal";

const CustomButton = styled(Button)`
    display: inline-block;
    margin: 30px 0 30px 30px;
    
`;

const useStyles = makeStyles(theme => ({
    input: {
        margin:"30px 0 30px 30px"
    },
}));
const CouponGenerator = ({onCouponCreate ,onInitialize,createResult,error}) => {
    const classes = useStyles();

    const [point, setPoint] = useState(0);
    const [count, setCount] = useState(0);

    const [showModal, setShowModal] = useState(false);

    const modalText = {
        title:"쿠폰을 생성 하시겠습니까?",
        content:`${point}원 쿠폰 ${count}개를 생성합니다`
    }

    const handlePoint = (e) =>{
        setPoint(e.target.value)

    }
    const handleCount = (e) =>{
        setCount(e.target.value)
    }

    const onShowModal = () => {
        setShowModal(!showModal)
        onInitialize('create')
    }

    return (
        <div>
            <form>
                <TextField
                    label="포인트"
                    type="number"
                    value={point}
                    onChange={handlePoint}
                    className={classes.input}
                    InputLabelProps={{
                        shrink: true,
                        inputprops: { min: 1}
                    }}
                    required={true}
                />
                <TextField
                    label="수량"
                    type="number"
                    value={count}
                    onChange={handleCount}
                    className={classes.input}
                    InputLabelProps={{
                        shrink: true,
                    }}
                    required={true}
                />
                <CustomButton eney type='button' onClick={onShowModal}>쿠폰 생성</CustomButton>
                <AlertModal open={showModal} onClose={onShowModal} onClick={() => onCouponCreate({point, count})} title={modalText.title} content={modalText.content} result={createResult}error={error}/>
            </form>
        </div>
    );
};

export default CouponGenerator;