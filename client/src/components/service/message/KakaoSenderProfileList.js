import React from 'react';
import MessageResultItem from "./MessageResultItem";
import KakaoSenderProfileItem from "./KakaoSenderProfileItem";


const KakaoSenderProfileList = ({list, cx, deleteAction}) => {


    return (
        <table>
            <colgroup>
                <col/>
                <col/>
                <col/>
                <col/>
                <col/>
                <col style={{width: "15%"}}/>

            </colgroup>
            <thead>
            <tr>
                <th>발송 ID</th>
                <th>카카오톡 채널 이름</th>
                <th>카카오톡 채널 ID</th>
                <th className="text-center">발신프로필 키</th>
                <th>등록일</th>
                <th>비고</th>

            </tr>
            </thead>
            <tbody>
            {list.map((item, index) => (
                <KakaoSenderProfileItem key={index} cx={cx} item={item} deleteAction={deleteAction}/>
            ))}
            </tbody>
        </table>

    );
};

export default KakaoSenderProfileList;