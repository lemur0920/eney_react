import React from 'react';
import AnalyticsManageList from "./AnalyticsManageList";

const AnalyticsManageTable = ({cx, list, onDelete, onEdit}) => {
    return (
        <table>
            <colgroup>
                <col style={{width:"6.9%"}}/>
                <col style={{width:"9%"}}/>
                <col style={{width:"12.76%"}}/>
                <col style={{width:"11.7%"}}/>
                <col style={{width:"15.2%"}}/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">No.1</th>
                <th scope="col">VIEW_ID</th>
                <th scope="col">Comments</th>
                <th scope="col">등록일</th>
                <th scope="col">관리</th>
            </tr>
            </thead>
            <AnalyticsManageList list={list} cx={cx} onDelete={onDelete} onEdit={onEdit}/>
        </table>
    );
};

export default AnalyticsManageTable;