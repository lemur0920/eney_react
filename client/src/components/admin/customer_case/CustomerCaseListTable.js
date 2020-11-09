import React from 'react';
import CustomerCaseList from "./CustomerCaseList";
import style from '../../../asset/style/admin/customer_case.module.css';
import classnames from "classnames/bind";
import styled from "styled-components";

const cx = classnames.bind(style);

const CustomerCaseListTable = ({list,moveEdit,handleDelete }) => {
    return (
        <table className={cx("customer_case_table")}>
            <thead>
            <tr>
                <th scope="col">번호</th>
                <th scope="col">Link</th>
                <th scope="col">Clients</th>
                <th scope="col">Project</th>
                <th scope="col">ReleaseDate</th>
                <th scope="col">Category</th>
                <th scope="col">Tumbnail</th>
                <th scope="col">관리</th>
            </tr>
            </thead>
            <CustomerCaseList list={list} moveEdit={moveEdit} handleDelete={handleDelete} cx={cx}/>
        </table>
    );
};

export default CustomerCaseListTable;