import React from 'react';
import CloudManagementItem from "./CloudManagementItem";

const CloudManagementList = ({list,cx,start, stop, restart}) => {
    return (
        <tbody>
        {list.map((item,index) => (
            <CloudManagementItem index={index} key={item.instance_id} cx={cx} item={item} start={start} stop={stop} restart={restart}/>
        ))}
        </tbody>
    );
};

export default CloudManagementList;