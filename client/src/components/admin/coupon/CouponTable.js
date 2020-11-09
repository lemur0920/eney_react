import React from 'react';
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core";
import CouponList from "./CouponList";

const useStyles = makeStyles({
    root: {
        width: '100%',
        overflowX: 'auto',
    },
    table: {
        borderTop: "1px solid rgba(224, 224, 224, 1)",
        minWidth: 650,
    },
    cell:{
        fontSize:14,
        lineHeight:1.9,
        borderBottom: "1px solid rgba(224, 224, 224, 1)",
    }
});



const CouponTable = ({list}) => {

    const classes = useStyles();

    return (
        <Table className="mb-0" aria-label="simple table">
            <TableHead>
                <TableRow>
                    <TableCell align="center">No</TableCell>
                    <TableCell align="center">쿠폰 번호</TableCell>
                    <TableCell align="center">생성일</TableCell>
                    <TableCell align="center">사용여부</TableCell>
                    <TableCell align="center">포인트</TableCell>
                    <TableCell align="center">사용일</TableCell>
                    <TableCell align="center">사용자</TableCell>
                </TableRow>
            </TableHead>
            <CouponList list={list}/>
        </Table>
    );
};

export default CouponTable;