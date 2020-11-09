import React from 'react';
import CustomerGroupManagementList from "./CustomerGroupManagementList";

const CustomerGroupManagementTable = ({cx,list,handleDeleteCustomerGroup,handleUpdateCustomerGroup,handleGroupMemberModal}) => {
    return (
        <table>
            <colgroup>
                <col style={{width:"6.9%"}}/>
                <col style={{width:"9%"}}/>
                <col style={{width:"12.76%"}}/>
                <col style={{width:"11.7%"}}/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">No.</th>
                <th scope="col">그룹 이름</th>
                <th scope="col">등록일</th>
                <th scope="col">관리</th>
            </tr>
            </thead>
            <tbody>
            <CustomerGroupManagementList cx={cx} list={list} handleDeleteCustomerGroup={handleDeleteCustomerGroup} handleUpdateCustomerGroup={handleUpdateCustomerGroup} handleGroupMemberModal={handleGroupMemberModal}/>
            </tbody>
        </table>
    );
};

export default CustomerGroupManagementTable;