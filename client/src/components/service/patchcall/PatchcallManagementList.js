import React from 'react';
import PatchcallManagementItem from "./PatchcallManagementItem";


const PatchcallManagementList = ({list,cx}) => {


    return (
        <tbody>
            {list.map((item) => (
                <PatchcallManagementItem key={item.vno} cx={cx} item={item}/>
            ))}
        </tbody>
    );
};

export default PatchcallManagementList;