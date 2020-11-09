import React,{useState,useEffect} from 'react';
import Moment from "react-moment";

const PatchCallCidItem = ({cx,item}) => {

    const [stateText, setStateText] = useState("");

    useEffect(() => {
        switch (item.status) {
            case "A":
                setStateText("완료");
                break;
            case "R":
                setStateText("등록");
                break;
            case "I":
                setStateText("심사중");
                break;
            case "C":
                setStateText("반려");
                break;

        }

    },[])
    return (
        <tr>
            <td>
                {item.tel_num}
            </td>
            <td>
                {item.reg_date}
            </td>
            <td>
                {stateText}
            </td>
        </tr>
    );
};

export default PatchCallCidItem;