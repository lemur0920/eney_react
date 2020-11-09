import React from 'react';
import {Link,withRouter} from 'react-router-dom';
import CustomerCaseItem from "./CustomerCaseItem";
import qs from "qs";

const CustomerCaseTab = ({tabList, cate, cx}) => {

    return (
        <ul className={cx("tab_ul")}>
            <li className={cx("tab_li")}>
                <Link className={cx(`${cate === null ? "on" : ""}`)} to={`${location.pathname}`}>
                    전체
                </Link>
            </li>
            {tabList !== null && (
                tabList.map((item) => (
                        <li className={cx("tab_li")} key={item.code}>
                            <Link className={cx(`${parseInt(cate) === item.code ? "on" : ""}`)} to={`${location.pathname}?cate=${item.code}`}>
                                {item.code_name}
                            </Link>
                        </li>
                    ))
            )}

        </ul>
    );
};

export default withRouter(CustomerCaseTab);