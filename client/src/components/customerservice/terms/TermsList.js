import React from 'react';
import TermsItem from "./TermsItem";

const TermsList = ({boardList, group, condition}) => {
    return (
        <tbody>
        {boardList.map((item) => (
            <TermsItem item={item} group={group} condition={condition} key={item.content_id}/>
        ))}
        </tbody>
    );
};

export default TermsList;