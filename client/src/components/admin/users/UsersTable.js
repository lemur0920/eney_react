import React from "react";
import {
    Table,
    TableRow,
    TableHead,
    TableCell,
} from "@material-ui/core";

import UserList from "./UserList";
import UserTableHeaderList from "./UserTableHeaderList";
const UsersTable = ({users, loading,handleInfoModal}) => {

    const columns = ["구분","아이디","이름","휴대폰","서비스내역","포인트","비고","가입일","가입목적","최근접속","관리"]

    return (
        <Table className="mb-0 user_list">
            <colgroup>
                <col style={{width:"6.9%"}}/>
                <col style={{width:"9%"}}/>
                <col style={{width:"12.76%"}}/>
                <col style={{width:"11.7%"}}/>
                <col style={{width:"15.2%"}}/>
                <col style={{width:"8.5%"}}/>
                <col style={{width:"6.9%"}}/>
                <col style={{width:"9%"}}/>
                <col style={{width:"12.76%"}}/>
                <col style={{width:"11.7%"}}/>
                <col style={{width:"15.2%"}}/>
            </colgroup>
            <TableHead>
                <TableRow>
                    <UserTableHeaderList columns={columns}/>
                    {/*{columns.map(key => (*/}
                    {/*    <TableCell key={key}>{key}</TableCell>*/}
                    {/*))}*/}
                </TableRow>
            </TableHead>
            <UserList users={users} loading={loading} handleInfoModal={handleInfoModal}/>
        </Table>
    );
};

export default UsersTable;