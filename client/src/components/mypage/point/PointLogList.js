import React from 'react';
import {TableBody} from "@material-ui/core";
import PointLogItem from "./PointLogItem";

const PointLogList = ({logList}) => {
    return (
        <TableBody>
            {logList.map((pointLog) => (
                    <PointLogItem pointLog={pointLog} key={pointLog.epoint_log_id}/>
                ))}
        </TableBody>
    );
};

export default PointLogList;