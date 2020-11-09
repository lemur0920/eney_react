import React,{Fragment} from 'react';
import DaumPostcode from 'react-daum-postcode';
import styled from "styled-components";
import Button from "../common/Button";

const CustomButton = styled(Button)`
    padding: 4px 8px;
    margin-top: 5px;
    font-size:12px;
    //@media (max-width: 768px) {
    //margin-top: 5px;
    //}
    
`;

const Postcode = ({onClose,getAddress}) => {

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
        getAddress(data)

        onClose();
    }


    return (
        <div>
            <DaumPostcode onComplete={handleAddress} autoClose height={500}width={400}/>
            <div style={{textAlign:"center"}}>
                <CustomButton onClick={() => onClose()}>닫기</CustomButton>
            </div>
        </div>
    );
};

export default Postcode;