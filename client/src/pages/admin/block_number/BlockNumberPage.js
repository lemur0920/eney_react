import React from 'react';

import style from '../../../asset/style/admin/third_part.module.css';
import classnames from "classnames/bind";
import ThirdPart from "../../../container/admin/third_part/ThirdPart";
import BlockNumber from "../../../container/admin/block_number/BlockNumber";

const cx = classnames.bind(style);

const BlockNumberPage = () => {
    return (
        <BlockNumber cx={cx}/>
    );
};

export default BlockNumberPage;