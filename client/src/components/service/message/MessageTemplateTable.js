import React from 'react';
import PatchcallManagementList from "../patchcall/PatchcallManagementList";
import MessageTemplateList from "./MessageTemplateList";

const MessageTemplateTable = ({cx, handleDeleteTemplate, list}) => {
    return (
        <table>
            <colgroup>
                <col style={{width:"15%"}}/>
                <col/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">메세지 타입</th>
                <th scope="col">제목</th>
                <th scope="col">관리</th>
            </tr>
            </thead>
            <MessageTemplateList list={list} handleDeleteTemplate={handleDeleteTemplate} cx={cx}/>
        </table>
    );
};

export default MessageTemplateTable;