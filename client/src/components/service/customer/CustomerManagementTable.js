import React from 'react';
import CustomerManagementList from "./CustomerManagementList";

const CustomerManagementTable = ({cx,list,showModal,handleShowInfoEditModal}) => {
    return (
        <table>
            <colgroup>
                <col style={{width:"6.9%"}}/>
                <col style={{width:"9%"}}/>
                <col style={{width:"12.76%"}}/>
                <col style={{width:"5%"}}/>
                <col style={{width:"11.7%"}}/>
                <col style={{width:"20%"}}/>
                <col style={{width:"15.2%"}}/>
                <col style={{width:"8%"}}/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">No.</th>
                <th scope="col">이름</th>
                <th scope="col">전화번호</th>
                <th scope="col">성별</th>
                <th scope="col">소속</th>
                <th scope="col">지역</th>
                <th scope="col">이메일</th>
                <th scope="col">등록일</th>
                {/*<th scope="col">메모하기</th>*/}
            </tr>
            </thead>
            <tbody>
                <CustomerManagementList cx={cx} list={list} showModal={showModal} handleShowInfoEditModal={handleShowInfoEditModal}/>
            </tbody>
        </table>
    );
};

export default CustomerManagementTable;