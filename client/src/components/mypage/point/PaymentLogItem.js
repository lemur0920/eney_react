import React,{useState,Fragment} from 'react';
import PropTypes from "prop-types";
import {makeStyles, withStyles} from "@material-ui/core/styles";
import Collapse from "@material-ui/core/Collapse";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import Typography from '@material-ui/core/Typography';
import palette from "../../../lib/styles/palette";
import Moment from 'react-moment';

const useStyles = makeStyles({
    expPaper: {
        margin:15,
        padding:20
    },
    changeButton:{
        backgroundColor:palette.cyan[0],
        color:"white",
        float:"right",
        // ['@media (max-width:514px)']: {
        //     width: '98%'
        // },
        '&:hover': {
            fontWeight: "bold",
            backgroundColor:palette.cyan[0]
        }
    },
    cell:{
        fontSize:14,
        lineHeight:1.9,
        color:"#696969",
        borderBottom: "1px solid rgba(224, 224, 224, 1)",
        ['@media (max-width:514px)']: {
            fontSize:12,
            padding:4
        },
    }
});





const PaymentLogItem = ({paymentLog}) => {

    const classes = useStyles();

    let payWay = "";

    switch(paymentLog.pay_method){
        case "credit":
            payWay = "신용카드"
            break;
        case "culture":
            payWay = "컬처랜드"
            break;
        case "happymoney":
            payWay = "신용카드"
            break;
        case "coupon":
            payWay = "쿠폰"
            break;
        default:
            payWay = paymentLog.pay_method
    }

    return (
        <Fragment>
        <TableRow>
            {/*<TableCell>{index}</TableCell>*/}
            <TableCell className={classes.cell} align="center">{paymentLog.rownum}</TableCell>
            <TableCell className={classes.cell} align="center">
                <Moment format="YYYY-MM-DD">
                    {paymentLog.charge_date}
                </Moment>
            </TableCell>
            <TableCell className={classes.cell}  align="center">
                    {paymentLog.serial_number}
            </TableCell>
            <TableCell className={classes.cell}  align="center">
                {paymentLog.amount}
            </TableCell>
            <TableCell className={classes.cell}  align="center">
                {payWay}
            </TableCell>
        </TableRow>
        </Fragment>
    );
};

export default PaymentLogItem;