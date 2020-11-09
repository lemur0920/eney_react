import React from 'react';
import CustomerCaseItem from "./CustomerCaseItem";

const CustomerCaseList = ({list,moveEdit,handleDelete,cx }) => {
    return (
        <tbody>
        {list.map((item) => (
            <CustomerCaseItem item={item}  key={item.idx} moveEdit={moveEdit} handleDelete={handleDelete} cx={cx}/>
        ))}
        </tbody>
    );
};

export default CustomerCaseList;