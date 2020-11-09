import React,{Fragment} from 'react';
import {TextField} from "@material-ui/core";
import Box from "@material-ui/core/Box";

import useStyles from './style';
import cx from "classnames/bind";


const UserNameInfo = ({name}) => {
    const classes = useStyles();
    return (
        <Fragment>
            <label className={cx(classes.label)}>이름</label>
            <TextField className={cx(classes.fullInput)} fullWidth variant="outlined" inputProps={{style: {fontSize: 14}}} value={name} readOnly/>
        </Fragment>
    );
};

export default React.memo(UserNameInfo);