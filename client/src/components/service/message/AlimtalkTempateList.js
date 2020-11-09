import React from 'react';
import MessageResultItem from "./MessageResultItem";
import KakaoSenderProfileItem from "./KakaoSenderProfileItem";
import AlimtalkTemplateItem from "./AlimtalkTemplateItem";


const AlimtalkTemplateList = ({list, cx, deleteAction, history}) => {


    return (
        <table>
            <colgroup>
                <col/>
                <col/>
                <col/>
                <col style={{width: "20%"}}/>
                <col/>
                <col/>
                <col/>
                <col style={{width: "13%"}}/>

            </colgroup>
            <thead>
            <tr>
                <th>카카오톡 채널 ID</th>
                <th>채널이름</th>
                <th>템플릿명</th>
                <th>발신프로필키</th>
                <th>상태</th>
                <th>검수상태</th>
                <th>등록일</th>
                <th>비고</th>

            </tr>
            </thead>
            <tbody>
            {list.map((item, index) => (
                <AlimtalkTemplateItem key={index} cx={cx} item={item} deleteAction={deleteAction} history={history}/>
            ))}
            </tbody>
        </table>

    );
};

export default AlimtalkTemplateList;