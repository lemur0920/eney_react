import React from 'react';
import CustomerCaseList from "../customer_case/CustomerCaseList";
import CloudServiceApplyList from "./CloudServiceApplyList";
import TableRow from "@material-ui/core/TableRow";
import TableHead from "@material-ui/core/TableHead";

const CloudServiceApplyListTable = ({cx,list,handleCreate,handleRemove}) => {
    return (
        <table className={cx("service_apply","cloud_table")}>
            <thead>
                <tr>
                <th scope="col">번호</th>
                <th scope="col">신청자</th>
                <th scope="col">서비스 종류</th>
                <th scope="col">요금</th>
                <th scope="col">결제 방법</th>
                <th scope="col">신청일</th>
                <th scope="col">결제상태</th>
                <th scope="col">상태</th>
                <th scope="col">관리</th>
                </tr>
            </thead>
            <CloudServiceApplyList list={list} cx={cx} handleCreate={handleCreate} handleRemove={handleRemove}/>
        </table>
    );
};

export default CloudServiceApplyListTable;