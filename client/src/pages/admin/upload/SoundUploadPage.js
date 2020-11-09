import React from 'react';

import style from '../../../asset/style/admin/sound_upload.module.css';
import classnames from "classnames/bind";
import SoundUpload from "../../../container/admin/upload/SoundUpload";

const cx = classnames.bind(style);

const SoundUploadPage = () => {
    return (
        <SoundUpload cx={cx}/>
    );
};

export default SoundUploadPage;