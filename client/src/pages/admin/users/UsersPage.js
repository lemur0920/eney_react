import React from 'react';
import Users from '../../../container/admin/users/Users'
import style from '../../../asset/style/admin/user.module.css';
import classnames from "classnames/bind";

const cx = classnames.bind(style);

const UsersPage = () => {
    return (
        <Users cx={cx} clsssName={cx("user_list")}/>
    );
};

export default UsersPage;