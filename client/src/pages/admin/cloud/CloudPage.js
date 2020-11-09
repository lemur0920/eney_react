import React from 'react';

import style from '../../../asset/style/admin/cloud.module.css';
import classnames from "classnames/bind";
import Cloud from "../../../container/admin/cloud/Cloud";

const cx = classnames.bind(style);

const CloudPage = () => {
    return (
        <Cloud cx={cx}/>
    );
};

export default CloudPage;