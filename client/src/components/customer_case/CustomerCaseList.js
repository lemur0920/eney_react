import React from 'react';
import CustomerCaseItem from "./CustomerCaseItem";

const CustomerCaseList = ({list,cx,viewContent}) => {
    return (
        <ul className={cx("customer_case_ul")}>
            {list.map((item) => (
                <CustomerCaseItem item={item}  key={item.idx} cx={cx} viewContent={viewContent}/>
            ))}
        </ul>
    );
};

export default CustomerCaseList;