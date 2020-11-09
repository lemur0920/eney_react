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
        borderBottom: "1px solid rgba(224, 224, 224, 1)",
    }
});





const CouponItem = ({coupon}) => {

    const classes = useStyles();

    // const [isOpen, openToggle] = useState(false);


    return (
        <Fragment>
        <TableRow>
            {/*<TableCell>{index}</TableCell>*/}
            <TableCell align="center">{coupon.idx}</TableCell>
            <TableCell align="center">
                {coupon.coupon_num}
            </TableCell>
            <TableCell  align="center">
                {coupon.create_date !== null ? (
                    <Moment format="YYYY-MM-DD HH:mm">
                        {coupon.create_date}
                    </Moment>
                ):('')}
            </TableCell>
            <TableCell  align="center">
                {coupon.used === true ? ('사용'):('미사용')}
            </TableCell>
            <TableCell align="center">
                {coupon.point}
            </TableCell>
            <TableCell align="center">
                {coupon.used_date !== null ? (
                    <Moment format="YYYY-MM-DD HH:mm">
                        {coupon.used_date}
                    </Moment>
                ):('')}
            </TableCell>
            <TableCell align="center">
                {coupon.used_user}
            </TableCell>
        </TableRow>
        </Fragment>
    );
};

export default CouponItem;