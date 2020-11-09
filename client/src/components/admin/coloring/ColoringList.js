import React from 'react';
import ColoringItem from "./ColoringItem";
import CustomerCaseItem from "../customer_case/CustomerCaseItem";
import TableBody from "@material-ui/core/TableBody";

const ColoringList = ({cx,list}) => {
    return (
        <TableBody>
        {list.map((item) => (
            <ColoringItem cx={cx} item={item} key={item.id}/>
        ))}
        </TableBody>
    );
};

export default ColoringList;