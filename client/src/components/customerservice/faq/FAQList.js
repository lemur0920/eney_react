import React from 'react';
import FAQItem from "./FAQItem";
import NoticeItem from "../notice/NoticeItem";

const FAQList = ({boardList, group, condition}) => {
    return (
        <tbody>
        {boardList.map((item) => (
            <FAQItem item={item} group={group} condition={condition} key={item.content_id}/>
        ))}
        </tbody>
    );
};

export default FAQList;