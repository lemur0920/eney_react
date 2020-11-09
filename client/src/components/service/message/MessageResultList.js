import React from 'react';
import MessageResultItem from "./MessageResultItem";


const MessageResultList = ({list,cx}) => {


    return (
        <tbody>
        {list.map((item, index) => (
            <MessageResultItem key={index} cx={cx} item={item}/>
        ))}
        </tbody>
    );
};

export default MessageResultList;