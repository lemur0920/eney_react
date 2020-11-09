import React from 'react';
import TableBody from "@material-ui/core/TableBody";
import ThirdPartServiceApplyItem from "./ThirdPartServiceApplyItem";

const ThirdPartServiceApplyList = ({cx,list,updateStatus}) => {
    return (
        <tbody>
            {list.map((item) => (
                <ThirdPartServiceApplyItem item={item}  key={item.idx} cx={cx} updateStatus={updateStatus}/>
            ))}
        </tbody>
    );
};

export default ThirdPartServiceApplyList;