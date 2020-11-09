import React from 'react';
import Moment from "react-moment";

const CustomerManagementItem = ({item,cx,showModal,handleShowInfoEditModal}) => {
    return (
        <tr className={cx("active_tr")} onClick={() => showModal(item.idx)}>
            <td>{item.rownum}</td>
            <td>
                {item.name === null || item.name === "" ? "미지정" : item.name}
                <button style={{marginLeft:3}} onClick={(e) => { e.stopPropagation();handleShowInfoEditModal(item.idx);}}><img src={require("../../../asset/image/service/icon_modify.png")} alt="수정하기" /></button>
            </td>
            <td>
                {item.phone_number}
            </td>
            <td>{item.gender === "M" && "남자"}{item.gender === "W" && "여자"}</td>
            <td>{item.team_name}</td>
            <td>{item.address}</td>
            <td>{item.email}</td>
            <td>
                <Moment format="YYYY-MM-DD hh:mm">{item.reg_date}</Moment>
            </td>
        </tr>
    );
};

export default CustomerManagementItem;