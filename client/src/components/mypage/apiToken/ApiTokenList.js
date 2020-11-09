import React from 'react';
import {TableBody} from "@material-ui/core";
import ApiTokenItem from "./ApiTokenItem";

const ApiTokenList = ({list}) => {
    return (
        <TableBody>
            {list.map((token) => (
                    <ApiTokenItem token={token} key={token.token_key}/>
                ))}
        </TableBody>
    );
};

export default ApiTokenList;