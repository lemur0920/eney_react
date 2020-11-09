import React from 'react';

import style from '../../../asset/style/admin/coloring.module.css';
import classnames from "classnames/bind";
import Coloring from "../../../container/admin/coloring/Coloring";

const cx = classnames.bind(style);

const ColoringPage = () => {
    return (
        <Coloring cx={cx}/>
    );
};

export default ColoringPage;