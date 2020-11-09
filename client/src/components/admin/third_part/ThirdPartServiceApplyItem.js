import React,{useState} from 'react';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import Button from "../../common/Button";
import {Select} from "@material-ui/core";
import MenuItem from "@material-ui/core/MenuItem";

const ThirdPartServiceApplyItem = ({item,cx,updateStatus}) => {

    const[isManage, setIsManage] = useState(false);
    const[status, setStatus] = useState({
        idx:item.idx,
        useStatus:item.status,
        payStatus:item.pay_state
    });

    const handleStatus = (e) =>{
        const {name,value} = e.target;

        setStatus({
            ...status,
            [name]:value
        })

    }


    return (
        <>
            <tr>
                <td align="center">
                    {item.rownum}
                </td>
                <td align="center">
                    {item.userid}
                </td>
                <td align="center">
                    {item.service_type}
                </td>
                <td align="center">
                    {item.service_amount}
                </td>
                <td align="center">
                    {item.pay_way}
                </td>
                <td align="center">
                    {item.reg_date}
                </td>
                <td align="center">
                    {isManage ? (
                        <Select
                            name='payStatus'
                            value={status.payStatus}
                            onChange={handleStatus}
                        >
                            <MenuItem value="standby">대기</MenuItem>
                            <MenuItem value="approve">완료</MenuItem>
                            }
                        </Select>
                    ) : (item.pay_state)}
                </td>
                <td align="center">
                    {isManage ? (
                        <Select
                            name='useStatus'
                            value={status.useStatus}
                            onChange={handleStatus}
                        >
                            <MenuItem value="waiting">대기</MenuItem>
                            <MenuItem value="using">사용중</MenuItem>
                            <MenuItem value="deleted">삭제</MenuItem>
                            }
                        </Select>
                    ) : (item.status)}
                </td>
                <td align="center">
                    {isManage ? (
                        <>
                            <Button type="button" style={{margin:3}} onClick={() => updateStatus(status)}>저장</Button>
                            <Button type="button" onClick={() => setIsManage(false)}>취소</Button>
                        </>
                    ) :
                        (
                            <Button type="button" onClick={() => setIsManage(!isManage)}>관리</Button>
                        )}
                </td>
            </tr>
        </>
    );
};

export default ThirdPartServiceApplyItem;