import React from 'react';
import Moment from "react-moment";
import {withRouter} from 'react-router-dom'

const PatchcallManagementItem = ({item, cx,history}) => {

    const moveDetail = (vno) =>{
        history.push(`${location.pathname}/detail?vno=${vno}`)
    }

    return (
        <tr className={cx("active_tr")} onClick={() => {moveDetail(item.vno)}}>
            <td>
                {item.name}
            </td>
            <td>
                {item.dongName}
            </td>
            <td>
                {item.vno}
            </td>
            <td>
                {item.rcvNo}
            </td>
            <td>
                <Moment format="YYYY-MM-DD">{item.regDate}</Moment>
            </td>
            <td>
                <Moment format="YYYY-MM-DD">{item.limitDate}</Moment>
            </td>
        </tr>
    );
};

export default withRouter(PatchcallManagementItem);