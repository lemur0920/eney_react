import React from 'react';
import FAQList from "./FAQList";
import NoticeTable from "../notice/NoticeTable";

const FAQTable = ({boardList, group, condition}) => {
    return (
        <table>
            <colgroup>
                <col style={{width:"10%"}}/>
                <col style={{width:"80%"}}/>
                <col style={{width:"10%"}}/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">번호</th>
                <th scope="col">제목</th>
                <th scope="col">분류</th>
                {/*<th scope="col">등록일</th>*/}
            </tr>
            </thead>
            <FAQList boardList={boardList} group={group} condition={condition}/>
        </table>
    );
};

export default FAQTable;