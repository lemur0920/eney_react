import React from 'react';
import PatchCallCidItem from "./PatchCallCidItem";

const PatchCallCidList = ({cx,list}) => {
    return (
        <tbody>
        {list.map((item) => (
            <PatchCallCidItem key={item.rownum} cx={cx} item={item}/>
        ))}
        </tbody>
    );
};

export default PatchCallCidList;