import React from 'react';
import MessageTemplateItem from "./MessageTemplateItem";


const MessageTemplateList = ({list, handleDeleteTemplate, cx}) => {


    return (
        <tbody>
        {list.map((item) => (
            <MessageTemplateItem key={item.idx} handleDeleteTemplate={handleDeleteTemplate} cx={cx} item={item}/>
        ))}
        </tbody>
    );
};

export default MessageTemplateList;