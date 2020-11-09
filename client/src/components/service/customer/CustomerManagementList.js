import React from 'react';
import CustomerManagementItem from "./CustomerManagementItem";

const CustomerManagementList = ({cx,list,showModal,handleShowInfoEditModal}) => {
    return (
        <>
        {list.map((item,index) => (
            <CustomerManagementItem key={item.idx} cx={cx} item={item} showModal={showModal} handleShowInfoEditModal={handleShowInfoEditModal}/>
        ))}
        </>
    );
};


export default CustomerManagementList;