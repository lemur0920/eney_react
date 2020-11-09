import React from 'react';
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core";
import ServiceList from "./ServiceList";

const useStyles = makeStyles({
    root: {
        width: '100%',
        overflowX: 'auto',
    },
    table: {
        borderTop: "1px solid rgba(224, 224, 224, 1)",
    },
    cell:{
        fontSize:14,
        lineHeight:1.9,
        color:"#696969",
        borderBottom: "1px solid rgba(224, 224, 224, 1)",
        ['@media (max-width:514px)']: {
            fontSize:12,
            padding:4,
        },
    }
});



const ServiceInfoTable = ({serviceList}) => {

    const classes = useStyles();

    return (
        <Table className={classes.table} aria-label="simple table">
            <TableHead>
                <TableRow>
                    {/*<TableCell>선택</TableCell>*/}
                    <TableCell className={classes.cell} align="center">서비스</TableCell>
                    <TableCell className={classes.cell} align="center">가입일</TableCell>
                    <TableCell className={classes.cell} align="center">만기일</TableCell>
                    <TableCell className={classes.cell} align="center">상태</TableCell>
                    <TableCell className={classes.cell} align="center">결제방식</TableCell>
                </TableRow>
            </TableHead>
            <ServiceList serviceList={serviceList}/>
        </Table>
    );
};

export default ServiceInfoTable;