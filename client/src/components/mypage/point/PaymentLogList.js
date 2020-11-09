import React from 'react';
import {TableBody} from "@material-ui/core";
import PointLogItem from "./PointLogItem";
import PaymentLogItem from "./PaymentLogItem";

const PaymentLogList = ({logList}) => {

    return (
        <TableBody>
            {logList.map((paymentLog) => (
                    <PaymentLogItem paymentLog={paymentLog} key={paymentLog.orderId}/>
                ))}
        </TableBody>
    );
};

export default PaymentLogList;