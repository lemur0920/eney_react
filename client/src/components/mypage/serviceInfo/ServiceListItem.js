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
import Typography from '@material-ui/core/Typography';
import palette from "../../../lib/styles/palette";
import Moment from 'react-moment';
import Button from "../../common/Button";
import styled from "styled-components";
const CustomButton = styled(Button)`
  padding: 4px 5px;
  margin:0;
  @media (max-width: 768px) {
    min-width: 44px;
    font-size: 12px;
  }
    
`;

const useStyles = makeStyles({
    expPaper: {
        margin:15,
        padding:20
    },
    changeButton:{
        color:"white",
        float:"right",
        // ['@media (max-width:514px)']: {
        //     width: '98%'
        // },
    },
    cell:{
        fontSize:14,
        lineHeight:1.9,
        color:"#696969",
        padding:4,
        borderBottom: "1px solid rgba(224, 224, 224, 1)",
        ['@media (max-width:514px)']: {
            fontSize:12,
            wordBreak: "break-all"
        },
    }
});





const ServiceListItem = ({service,index}) => {

    const classes = useStyles();

    // const [isOpen, openToggle] = useState(false);

    return (
        <Fragment>
        <TableRow>
            {/*<TableCell>{index}</TableCell>*/}
            <TableCell className={classes.cell} align="left">{service.service_type}</TableCell>
            <TableCell className={classes.cell}  align="center">
                <Moment format="YYYY-MM-DD">
                    {service.reg_date}
                </Moment>
            </TableCell>
            <TableCell className={classes.cell}  align="center">
                <Moment format="YYYY-MM-DD">
                    {service.end_date}
                </Moment>
            </TableCell>
            <TableCell className={classes.cell}  align="center">
                {service.status === "using" ? "사용" : "만료"}
            </TableCell>
            <TableCell className={classes.cell}  align="center">
                <span>{service.pay_way}</span>
                <CustomButton eney className={classes.changeButton}>변경</CustomButton>
            </TableCell>
        </TableRow>
        {/*<TableRow style={{display: isOpen ? "table-row": "none"}}>*/}
        {/*<TableCell padding={"none"} colSpan={5}>*/}
        {/*    <Collapse hidden={!isOpen} in={isOpen}>*/}
        {/*        <Paper className={classes.expPaper}>*/}
        {/*            sdsdjkldfalfhdsfksjlfjlsjfkladsfklsjflkdsflsdf*/}
        {/*        </Paper>*/}
        {/*    </Collapse>*/}
        {/*</TableCell>*/}
        {/*</TableRow>*/}
        </Fragment>
    );
};

export default ServiceListItem;