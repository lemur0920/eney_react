import React from 'react';
import Moment from "react-moment";
import {withRouter} from 'react-router-dom'

const MessageResultItem = ({item, cx, history}) => {

    const msg_type = {
        1: 'WEB SMS',
        3: 'WEB LMS',
        6: '알림톡',
        7: '친구톡',
    }

    return (
        <tr>
            <td>
                {item.subject == ' ' ? '제목없음' : item.subject}
            </td>
            <td>
                {/*{item.send_time.substr(0, 16)}*/}
                <Moment format="YYYY-MM-DD hh:mm">{item.request_time}</Moment>
            </td>
            <td>
                {
                    item.opt_post == null ? '기타' :
                        item.filecnt != 0 ? 'WEB MMS' : msg_type[item.msg_Type]
                }
            </td>
            {/*<td>
                {item.count}
            </td>*/}
            <td>
                {item.success}
            </td>
            <td>
                {item.fail}
            </td>
        </tr>
    );
};

export default withRouter(MessageResultItem);