import React from 'react';
import BlockNumberItem from "./BlockNumberItem";

const BlockNumberList = ({cx, list}) => {
    return (
        <tbody>
        {list.map((item, idx) => (
            <BlockNumberItem cx={cx} item={item} key={idx}/>
        ))}
        </tbody>
    );
};

export default BlockNumberList;