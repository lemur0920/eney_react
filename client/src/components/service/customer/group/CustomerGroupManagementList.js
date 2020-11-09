import React from 'react';
import CustomerGroupManagementItem from "./CustomerGroupManagementItem";

const CustomerGroupManagementList = ({cx,list,handleDeleteCustomerGroup,handleUpdateCustomerGroup,handleGroupMemberModal}) => {
    return (
        <>
            {list.map((item,index) => (
                <CustomerGroupManagementItem key={item.idx} cx={cx} item={item} handleDeleteCustomerGroup={handleDeleteCustomerGroup} handleUpdateCustomerGroup={handleUpdateCustomerGroup} handleGroupMemberModal={handleGroupMemberModal}/>
            ))}
        </>
    );
};

export default CustomerGroupManagementList;