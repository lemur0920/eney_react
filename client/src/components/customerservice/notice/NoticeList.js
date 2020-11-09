import React from 'react';
import NoticeItem from "./NoticeItem";
import NoticeTable from "./NoticeTable";

const NoticeList = ({boardList, group, condition}) => {
    return (
        <tbody>
        {boardList.map((item) => (
            <NoticeItem item={item} group={group} condition={condition} key={item.content_id}/>
        ))}
        </tbody>
    );
};

export default NoticeList;