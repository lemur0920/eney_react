import React from 'react';
import AnalyticsManageItem from "./AnalyticsManageItem";

const AnalyticsManageList = ({cx,list,onDelete, onEdit}) => {
    return (
        <tbody>
        {list.map((item) => (
            <AnalyticsManageItem key={item.rownum} cx={cx} item={item} onDelete={onDelete} onEdit={onEdit}/>
        ))}
        </tbody>
    );
};

export default AnalyticsManageList;