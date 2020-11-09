import React from 'react';
import CloudManagementList from "./CloudManagementList";

const CloudManagementTable = ({list, cx,start, stop, restart}) => {
    return (
        <table>
            <colgroup>
                <col style={{width:"6.9%"}}/>
                <col style={{width:"9%"}}/>
                <col style={{width:"20.76%"}}/>
                <col style={{width:"12.76%"}}/>
                <col style={{width:"11.7%"}}/>
                <col style={{width:"15.2%"}}/>
                <col style={{width:"15.2%"}}/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">No</th>
                <th scope="col">인스턴스 이름</th>
                <th scope="col">인스턴스 ID</th>
                <th scope="col">STATUS</th>
                <th scope="col">전원 상태</th>
                <th scope="col">생성일</th>
                <th scope="col">작업</th>
            </tr>
            </thead>
            <CloudManagementList list={list} cx={cx} start={start} stop={stop} restart={restart}/>
        </table>
    );
};

export default CloudManagementTable;