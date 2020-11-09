import React, {useState} from 'react';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import {Select} from "@material-ui/core";
import MenuItem from "@material-ui/core/MenuItem";
import Button from "../../common/Button";

const PatchCallServiceApplyItem = ({item,cx,updateStatus}) => {
    const[isManage, setIsManage] = useState(false);
    const[status, setStatus] = useState({
        idx:item.patchcall_idx,
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
            <TableRow className={cx("cloud_tr")}>
                <TableCell align="center">
                    {item.rownum}
                </TableCell>
                <TableCell align="center">
                    {item.userid}
                </TableCell>
                <TableCell align="center">
                    {item.service_type}
                </TableCell>
                <TableCell align="center">
                    {item.service_amount}
                </TableCell>
                <TableCell align="center">
                    {item.pay_way}
                </TableCell>
                <TableCell align="center">
                    {item.reg_date}
                </TableCell>
                <TableCell align="center">
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
                </TableCell>
                <TableCell align="center">
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
                </TableCell>
                <TableCell align="center">
                    {isManage ? (
                            <>
                                <Button type="button" style={{margin:3}} onClick={() => updateStatus(status)}>저장</Button>
                                <Button type="button" onClick={() => setIsManage(false)}>취소</Button>
                            </>
                        ) :
                        (
                            <Button type="button" onClick={() => setIsManage(!isManage)}>관리</Button>
                        )}
                </TableCell>
            </TableRow>
        </>
    );
};

export default PatchCallServiceApplyItem;