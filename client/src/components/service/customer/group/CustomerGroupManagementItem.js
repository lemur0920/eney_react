import React from 'react';
import Moment from "react-moment";

const CustomerGroupManagementItem = ({item, cx,handleDeleteCustomerGroup,handleUpdateCustomerGroup,handleGroupMemberModal}) => {
    return (
        <tr className={cx("active_tr")} >
            <td>{item.rownum}</td>
            <td>
                {item.group_name}
                <button style={{marginLeft:3}} onClick={() => {handleUpdateCustomerGroup(item.idx, item.group_name)}}><img src={require("../../../../asset/image/service/icon_modify.png")} alt="수정하기" /></button>
            </td>
            <td>
                <Moment format="YYYY-MM-DD hh:mm">{item.reg_date}</Moment>
            </td>
            <td>
                <button className={cx("btn")} onClick={() => {handleGroupMemberModal(item.idx, item.group_name)}}>고객 관리</button>
                <button className={cx("btn")} onClick={() => {handleDeleteCustomerGroup(item.idx)}}>삭제</button>
            </td>
        </tr>
    );
};

export default CustomerGroupManagementItem;