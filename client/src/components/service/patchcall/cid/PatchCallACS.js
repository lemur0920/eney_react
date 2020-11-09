import React, {Fragment, useState} from 'react';

const PatchCallACS = ({cx,onACSModule,setCertifyNum,onCheckAcsNumber}) => {
    return (
        <Fragment>
            <tr>
                <td>인증번호</td>
                <td>
                    <input type="text" onChange={(e) => setCertifyNum(e.target.value)}/>
                    <button className={cx("btn_g","btn_g2")} onClick={() => {onCheckAcsNumber();}}><span>인증하기</span></button>
                </td>
            </tr>
        </Fragment>
    );
};

export default PatchCallACS;