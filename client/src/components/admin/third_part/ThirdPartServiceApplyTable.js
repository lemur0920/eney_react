import React from 'react';
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import ThirdPartServiceApplyList from "./ThirdPartServiceApplyList";

const ThirdPartServiceApplyTable = ({cx,list,updateStatus}) => {
    return (
        <table className={cx("service_apply")}>
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
            <ThirdPartServiceApplyList list={list} cx={cx} updateStatus={updateStatus}/>
        </table>
    );
};

export default ThirdPartServiceApplyTable;