import React from 'react';
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core";
import PointLogList from "./PointLogList";
import PaymentLogList from "./PaymentLogList";

const useStyles = makeStyles({
    root: {
        width: '100%',
        overflowX: 'auto',
    },
    table: {
        borderTop: "1px solid rgba(224, 224, 224, 1)",
        // minWidth: 650,
    },
    cell:{
        fontSize:14,
        lineHeight:1.9,
        borderBottom: "1px solid rgba(224, 224, 224, 1)",
    }
});



const PaymentTable = ({logList}) => {

    const classes = useStyles();

    return (
        <Table className={classes.table} aria-label="simple table">
            <TableHead>
                <TableRow>
                    <TableCell className={classes.cell} align="center">No</TableCell>
                    <TableCell className={classes.cell} align="center">날짜</TableCell>
                    <TableCell className={classes.cell} align="center">주문번호</TableCell>
                    <TableCell className={classes.cell} align="center">결제금</TableCell>
                    <TableCell className={classes.cell} align="center">결제방법</TableCell>
                </TableRow>
            </TableHead>
            <PaymentLogList logList={logList}/>
        </Table>
    );
};

export default PaymentTable;