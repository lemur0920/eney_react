import React from 'react';
import {Switch, Route,Redirect} from 'react-router-dom';
import style from '../service.module.css';
import classnames from "classnames/bind";
import MessageStep1 from "../../../container/service/message/MessageSend";
import PatchCallManagementDetail from "../../../container/service/patchcall/PatchCallManagementDetail";
import PatchCallLog from "../../../container/service/patchcall/PatchCallLog";
import Swagger from "../../../components/service/api_doc/swagger";

const cx = classnames.bind(style);

const MessagePage = () => {
    return (
        <section className={cx("cont")}>
            <Swagger />
        </section>
    );
};

export default MessagePage;