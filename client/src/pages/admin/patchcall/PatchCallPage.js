import React from 'react';
import style from '../../../asset/style/admin/patchcall.module.css';
import classnames from "classnames/bind";
import PatchCall from "../../../container/admin/patchcall/PatchCall";

const cx = classnames.bind(style);

const PatchCallPage = () => {
    return (
        <PatchCall cx={cx}/>
    );
};

export default PatchCallPage;