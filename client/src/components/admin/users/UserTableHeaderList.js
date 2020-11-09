import React,{Fragment} from 'react';
import {TableCell} from "@material-ui/core";
import UserItem from "./UserItem";

const UserTableHeaderList = ({columns}) => {
    return (

        <Fragment>
        { columns && (
            columns.map(key =>
                <TableCell key={key} align="center">{key}</TableCell>
            )
        )}
        </Fragment>

    );
};

export default UserTableHeaderList;