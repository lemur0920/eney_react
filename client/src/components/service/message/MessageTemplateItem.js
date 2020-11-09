import React from 'react';
import Moment from "react-moment";
import {withRouter} from 'react-router-dom'

const MessageTemplateItem = ({item, cx, handleDeleteTemplate, history}) => {

    const moveDetail = (vno) => {
        history.push(`${location.pathname}/detail?idx=${vno}`)
    };

    const msg_type = {
        1: 'SMS',
        3: 'LMS',
        6: '알림톡',
        7: '친구톡',
    }

    return (
        <tr>
            <td>
                {msg_type[item.msg_type]}
            </td>
            <td>
                {item.subject}
            </td>

            <td>
                <button className={cx("btn")} onClick={() => {
                    moveDetail(item.idx)
                }}>수정
                </button>
                <button className={cx("btn")} onClick={() => {
                    handleDeleteTemplate(item.idx)
                }}>삭제
                </button>
            </td>
        </tr>
    );
};

export default withRouter(MessageTemplateItem);