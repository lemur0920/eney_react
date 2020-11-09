import React from 'react';
import MessageResultList from "./MessageResultList";

const MessageResultTable = ({cx, list}) => {
    console.log(list);
    return (
        <table>
            <colgroup>
                <col style={{width:"15%"}}/>
                <col/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">제목</th>
                <th scope="col">발송요청</th>
                <th scope="col">방식</th>
                {/*<th scope="col">발송건수</th>*/}
                <th scope="col">성공</th>
                <th scope="col">실패</th>
            </tr>
            </thead>
            <MessageResultList list={list} cx={cx}/>
        </table>
    );
};

export default MessageResultTable;