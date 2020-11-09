import React from 'react';
import {TableCell, TableRow,Collapse} from "@material-ui/core";
import Button from "@material-ui/core/Button";

const UserItem = ({user,handleInfoModal}) => {
    return (
        <>
            <TableRow>
                <TableCell className="pl-3 fw-normal" align="center">{user.member_kind}</TableCell>
                <TableCell align="center">{user.userid}</TableCell>
                <TableCell align="center">{user.name}</TableCell>
                <TableCell align="center">{user.phone_number}</TableCell>
                <TableCell align="center">서비스 내역</TableCell>
                <TableCell align="center">포인트</TableCell>
                {/*<TableCell>홈텍스</TableCell>*/}
                {/*<TableCell>티켓관리</TableCell>*/}
                <TableCell align="center">비고</TableCell>
                <TableCell align="center">{user.reg_date}</TableCell>
                <TableCell align="center">{user.purpose}</TableCell>
                <TableCell align="center">{user.last_login}</TableCell>
                <TableCell align="center"><Button onClick={() => {handleInfoModal(user.idx)}}>관리</Button></TableCell>
                {/*<TableCell>{user.identification_code}</TableCell>*/}
            </TableRow>
        </>
    );
};

export default UserItem;