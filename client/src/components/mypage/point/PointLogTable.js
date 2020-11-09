import React from 'react';
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core";
import PointLogList from "./PointLogList";

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
        color:"#696969",
        borderBottom: "1px solid rgba(224, 224, 224, 1)",
        ['@media (max-width:768px)']: {
            fontSize:12
        }
    },

});



const PointLogTable = ({logList}) => {

    const classes = useStyles();

    return (
        <Table className={classes.table} aria-label="simple table">
            <TableHead>
                <TableRow>
                    <TableCell className={classes.cell} align="center">No</TableCell>
                    <TableCell className={classes.cell} align="center">내용</TableCell>
                    <TableCell className={classes.cell} align="center">일자</TableCell>
                    <TableCell className={classes.cell} align="center">구분</TableCell>
                    <TableCell className={classes.cell} align="center">사용 포인트</TableCell>
                    <TableCell className={classes.cell} align="center">잔여 포인트</TableCell>
                </TableRow>
            </TableHead>
            <PointLogList logList={logList}/>
        </Table>
    );
};

export default PointLogTable;