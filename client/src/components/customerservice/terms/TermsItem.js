import React from 'react';
import { Link,withRouter } from 'react-router-dom';
import Moment from "react-moment";

const TermsItem = ({item,group, condition, location}) => {

    return (
        <tr>
            <td>{item.rownum}</td>
            <td className="txt_l">
                <Link to={`${location.pathname}${location.search}&id=${item.content_id}`}>{item.title}</Link>
            </td>
            <td align="center">{group[item.group_number]}</td>
        {/*<td>*/}
        {/*    <Moment format="YYYY-MM-DD">{item.write_date}</Moment>*/}
        {/*</td>*/}
    </tr>
    );
};

export default withRouter(TermsItem);