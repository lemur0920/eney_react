import React from 'react';
import {TableBody} from "@material-ui/core";
import CouponItem from "./CouponItem";

const CouponList = ({list}) => {
    return (
        <TableBody>
            {list.map((coupon) => (
                    <CouponItem coupon={coupon} key={coupon.idx}/>
                ))}
        </TableBody>
    );
};

export default CouponList;