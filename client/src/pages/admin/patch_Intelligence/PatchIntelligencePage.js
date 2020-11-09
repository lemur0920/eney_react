import React from 'react';

import style from '../../../asset/style/admin/patch_intelligence.module.css';
import classnames from "classnames/bind";
import PatchIntelligence from "../../../container/admin/patch_intelligence/PatchIntelligence";

const cx = classnames.bind(style);

const PatchIntelligencePage = () => {
    return (
        <PatchIntelligence cx={cx}/>
    );
};

export default PatchIntelligencePage;