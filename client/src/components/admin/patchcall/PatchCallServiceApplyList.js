import React from 'react';
import TableBody from "@material-ui/core/TableBody";
import PatchCallServiceApplyItem from "./PatchCallServiceApplyItem";

const PatchCallServiceApplyList = ({cx,list,updateStatus}) => {
    return (
        <TableBody>
            {list.map((item) => (
                <PatchCallServiceApplyItem item={item}  key={item.idx} cx={cx} updateStatus={updateStatus}/>
            ))}
        </TableBody>
    );
};

export default PatchCallServiceApplyList;