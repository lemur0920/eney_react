import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Tooltip from "@material-ui/core/Tooltip/Tooltip";



const DashBoardInfoTooltip = ({text,style}) => {

    const useStyles = makeStyles(theme => ({
        customTooltip: {
            backgroundColor: "gray",
            fontSize:13,
            padding:10
        },
    }))

    const classes = useStyles();

    return (
        <Tooltip title={text} placement="top-start" classes={{tooltip: classes.customTooltip}}>
            <img src={require("../../asset/image/i_icon.png")} style={style}/>
        </Tooltip>
    );
};

export default DashBoardInfoTooltip;