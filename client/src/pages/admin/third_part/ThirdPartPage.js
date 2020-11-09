import React from 'react';

import style from '../../../asset/style/admin/third_part.module.css';
import classnames from "classnames/bind";
import ThirdPart from "../../../container/admin/third_part/ThirdPart";

const cx = classnames.bind(style);

const ThirdPartPage = () => {
    return (
        <ThirdPart cx={cx}/>
    );
};

export default ThirdPartPage;