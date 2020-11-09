import React from 'react';
import PatchcallManagementList from "./PatchcallManagementList";

const PatchcallManagementTable = ({list, cx}) => {
    return (
        <table>
            <colgroup>
                <col style={{width:"6.9%"}}/>
                <col style={{width:"9%"}}/>
                <col style={{width:"12.76%"}}/>
                <col style={{width:"11.7%"}}/>
                <col style={{width:"15.2%"}}/>
                <col style={{width:"8.5%"}}/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">가맹점명</th>
                <th scope="col">발신지명</th>
                <th scope="col">050번호</th>
                <th scope="col">착신번호</th>
                <th scope="col">등록일</th>
                <th scope="col">만기일</th>
            </tr>
            </thead>
            <PatchcallManagementList list={list} cx={cx}/>
        </table>
    );
};

export default PatchcallManagementTable;