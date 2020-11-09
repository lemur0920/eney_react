import React from 'react';
import CustomerManagementItem from "./CustomerManagementItem";
import CustomerEventItem from "./CustomerEventItem";

const CustomerEventList = ({eventList,cx,audioDownload}) => {
    return (
        <>
            {eventList.map((item,index) => (
                <CustomerEventItem key={item.idx} cx={cx} item={item} audioDownload={audioDownload}/>
            ))}
        </>
    );
};

export default CustomerEventList;