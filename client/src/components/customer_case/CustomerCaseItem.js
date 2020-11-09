import React from 'react';
import {Link} from 'react-router-dom';
import * as clientLib from '../../lib/api/client';

const CustomerCaseItem = ({item,cx, viewContent}) => {
    return (
        <li className={cx("gall_li","col-gn-4" )}>
            <div className={cx("gall_chk")}>
					{/*			<span className="sound_only">*/}
					{/*60				</span>*/}
            </div>
            <div className={cx("img_area","item-filter")}>
                <div className={cx("overlay-hover")}>
                    <a onClick={() => viewContent(item.idx)}></a>
                    <div className={cx("hover-background")}></div>
                    <div className={cx("hover-line-x")}></div>
                    <div className={cx("hover-line-y")}></div>
                    {/*<div className={cx("hover-text")}>*/}
                    {/*    <h5 className={cx("text-preview")}>Preview</h5></div>*/}
                </div>
                <Link to={`/customer_case/view?idx=${item.idx}`} className="inner">
                    <img
                        src={`${clientLib.getUrl()}/customer_case/tumbnail/${item.tumbnail_file}`}
                        alt=""/>
                </Link>
            </div>
            <div className={cx("txt_area")}>
                <a onClick={() => viewContent(item.idx)} className="clfx">
                    <div className={cx("clients")}>
                        <strong>강의명</strong>
                        {item.clients}
                    </div>
                    <div className={cx("project")}>
                        <strong>강사명</strong>
                        {item.project}
                    </div>
                </a>
            </div>
        </li>
    );
};

export default CustomerCaseItem;