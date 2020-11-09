import React from 'react';
import styled from "styled-components";
import Button from "../../common/Button";


const BlockNumberItem = ({cx, item}) => {
    console.log(item);
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
                    {item.pay_state}
                </td>
                <td align="center">
                    {item.status}
                </td>
                <td align="center">
                    {item.generate_yn}
                </td>
                <td align="center">
                    {item.tel_num}
                </td>
                <td align="center">
                    <Button type="button">관리</Button>
                </td>
            </tr>
        </>
    );
};

export default BlockNumberItem;