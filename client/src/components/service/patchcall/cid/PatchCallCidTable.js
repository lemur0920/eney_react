import React from 'react';
import PatchCallCidList from "../cid/PatchCallCidList";

const PatchCallCidTable = ({cx, list}) => {
    return (
        <table>
            <thead>
            <tr>
                <th scope="col">발신번호</th>
                <th scope="col">등록일자</th>
                <th scope="col">상태</th>
            </tr>
            </thead>
            <PatchCallCidList list={list} cx={cx}/>
        </table>
    );
};

export default PatchCallCidTable;