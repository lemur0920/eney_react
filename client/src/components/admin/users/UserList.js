import React from 'react';
import TableBody from "@material-ui/core/TableBody";
import UserItem from "./UserItem";

const UserList = ({users,loading,handleInfoModal}) => {

    return (
        <TableBody>
            {!loading && users && (
                users.list.map(user =>
                    <UserItem key={user.idx} user={user} handleInfoModal={handleInfoModal}/>
                )
            )}
        </TableBody>
    );
};

export default UserList;