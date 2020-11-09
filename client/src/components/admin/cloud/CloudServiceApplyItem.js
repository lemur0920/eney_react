import React from 'react';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const CloudServiceApplyItem = ({item, cx,handleCreate,handleRemove}) => {
    return (
        <>
            <tr className={cx("cloud_tr")}>
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
                    {item.pay_state}
                </td>
                <td align="center">
                    {item.status}
                </td>
                <td align="center">
                    {item.pay_state === "standby" ? <button onClick={() => {handleCreate(item.idx);}}>인스턴스 생성</button> :
                        (<button onClick={() => {handleRemove(item.idx);}}>인스턴스 삭제</button>)}
                </td>
            </tr>
        </>
    );
};

export default CloudServiceApplyItem;