import React from 'react';
import PatchCallLogItem from "./PatchCallLogItem";

const PatchCallLogList = ({list, cx,audioDownload}) => {
    return (
        <tbody>
        {list.map((item) => (
            <PatchCallLogItem key={item.rownum} cx={cx} item={item} audioDownload={audioDownload}/>
        ))}
        </tbody>
    );
};

export default PatchCallLogList;