import React from 'react';
import ColoringList from "./ColoringList";
import CustomerCaseList from "../customer_case/CustomerCaseList";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const ColoringTable = ({cx, list}) => {
    return (
        <Table className={cx("customer_case_table")}>
            <TableHead>
            <TableRow>
                <TableCell align="center" scope="col">번호</TableCell>
                <TableCell align="center" scope="col">사용자</TableCell>
                <TableCell align="center" scope="col">시나리오</TableCell>
                <TableCell align="center" scope="col">목소리 톤</TableCell>
                <TableCell align="center" scope="col">배경음악</TableCell>
                <TableCell align="center" scope="col">신청</TableCell>
                <TableCell align="center" scope="col">상태</TableCell>
                <TableCell align="center" scope="col">관리</TableCell>
            </TableRow>
            </TableHead>
            <ColoringList list={list} cx={cx}/>
        </Table>
    );
};

export default ColoringTable;