import React from 'react';

import style from '../../../asset/style/admin/call_log.module.css';
import classnames from "classnames/bind";
import CallLog from "../../../container/admin/call_log/CallLog";

const cx = classnames.bind(style);

const CallLogPage = () => {
    return (
        <CallLog cx={cx}/>
    );
};

export default CallLogPage;