import React, {useState,Fragment} from 'react';

const PatchCallCidNumCheck = ({cx,onNumberCheck,setNum}) => {
    return (
        <Fragment>
        <tr>
            <td>발신번호등록</td>
            <td>
                <input type="text" onChange={(e) => setNum(e.target.value)}/>
                <button className={cx("btn_g","btn_g2")} onClick={() => {onNumberCheck();}}><span>번호 확인</span></button>
            </td>
        </tr>
        <tr>
            <td>인증수단</td>
            <td>ARS</td>
        </tr>
        </Fragment>
    );
};

export default PatchCallCidNumCheck;