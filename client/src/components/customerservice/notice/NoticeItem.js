import React from 'react';
import { Link,withRouter } from 'react-router-dom';
import Moment from "react-moment";

const NoticeItem = ({item,group, condition, location}) => {

    // const groupArray = group.split(",");
    //
    //
    // let conditionArray = [];
    // if(condition){
    //     conditionArray = condition.split(",");
    // }

    return (
        <tr>
            <td>{item.rownum}</td>
            <td className="txt_l">
                <Link to={`${location.pathname}${location.search}&id=${item.content_id}`}>{item.title}</Link>
            </td>
            <td align="center">{group[item.group_number]}</td>
            <td className="banner_line" align="center">
                <div className="banner complete">{condition[item.condition_number]}</div>
            </td>
        <td>
            <Moment format="YYYY-MM-DD">{item.write_date}</Moment>
        </td>
    </tr>
    );
};

export default withRouter(NoticeItem);