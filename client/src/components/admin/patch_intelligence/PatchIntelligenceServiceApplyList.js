import React from 'react';
import CloudServiceApplyItem from "../cloud/CloudServiceApplyItem";
import TableBody from "@material-ui/core/TableBody";
import PatchIntelligenceServiceApplyItem from "./PatchIntelligenceServiceApplyItem";

const PatchIntelligenceServiceApplyList = ({list, cx,updateStatus}) => {
    return (
        <TableBody>
            {list.map((item) => (
                <PatchIntelligenceServiceApplyItem item={item}  key={item.idx} cx={cx} updateStatus={updateStatus}/>
            ))}
        </TableBody>
    );
};

export default PatchIntelligenceServiceApplyList;