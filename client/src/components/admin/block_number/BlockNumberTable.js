import React from 'react';
import ThirdPartServiceApplyList from "../third_part/ThirdPartServiceApplyList";
import BlockNumberList from "./BlockNumberList";

const BlockNumberTable = ({cx, list}) => {
    return (
        <table className={cx("service_apply")}>
            <thead>
            <tr>
                <th scope="col">번호</th>
                <th scope="col">신청자</th>
                <th scope="col">서비스종류</th>
                <th scope="col">요금</th>
                <th scope="col">결제 방법</th>
                <th scope="col">신청일</th>
                <th scope="col">결제상태</th>
                <th scope="col">상태</th>
                <th scope="col">번호 적용</th>
                <th scope="col">차단번호</th>
                <th scope="col">관리</th>
            </tr>
            </thead>
            <BlockNumberList list={list} cx={cx} />
        </table>
    );
};

export default BlockNumberTable;