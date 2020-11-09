import React from 'react';
import PatchCallLogList from "./PatchCallLogList";

const PatchCallLogTable = ({list, cx,audioDownload}) => {
    return (
        <table>
            <thead>
            <tr>
                <th scope="col">가맹점명</th>
                <th scope="col">발신지명</th>
                <th scope="col">발신번호</th>
                <th scope="col">착신번호</th>
                <th scope="col">050번호</th>
                <th scope="col">통화날짜</th>
                <th scope="col">통화발신시간</th>
                <th scope="col">통화수신시간</th>
                <th scope="col">통화종료시간</th>
                <th scope="col">통화시간</th>
                <th scope="col">통화결과</th>
                <th scope="col">녹취</th>
            </tr>
            </thead>
            <PatchCallLogList list={list} cx={cx} audioDownload={audioDownload}/>
        </table>
    );
};

export default PatchCallLogTable;