import React from 'react';
import CloudServiceApplyItem from "./CloudServiceApplyItem";
import TableBody from "@material-ui/core/TableBody";

const CloudServiceApplyList = ({list,cx,handleCreate,handleRemove }) => {
    return (
        <tbody>
        {list.map((item) => (
            <CloudServiceApplyItem item={item}  key={item.idx} cx={cx} handleCreate={handleCreate} handleRemove={handleRemove}/>
        ))}
        </tbody>
    );
};

export default CloudServiceApplyList;