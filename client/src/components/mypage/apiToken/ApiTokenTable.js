import React from 'react';
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core";
import ApiTokenList from "./ApiTokenList";

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
        ['@media (max-width:514px)']: {
            fontSize:12,
            padding:4,
        },
    }
});



const ApiTokenTable = ({list}) => {

    const classes = useStyles();

    return (
        <Table className={classes.table} aria-label="simple table">
            <TableHead>
                <TableRow>
                    <TableCell className={classes.cell} align="center">No</TableCell>
                    <TableCell className={classes.cell} align="center">API Key</TableCell>
                    <TableCell className={classes.cell} align="center">연동 고정 IP</TableCell>
                    <TableCell className={classes.cell} align="center">서비스</TableCell>
                </TableRow>
            </TableHead>
            <ApiTokenList list={list}/>
        </Table>
    );
};

export default ApiTokenTable;