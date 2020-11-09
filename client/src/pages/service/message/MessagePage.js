import React from 'react';
import {Switch, Route,Redirect} from 'react-router-dom';
import style from '../service.module.css';
import classnames from "classnames/bind";
import MessageSend from "../../../container/service/message/MessageSend";
import PatchCallManagementDetail from "../../../container/service/patchcall/PatchCallManagementDetail";
import MessageTemplate from "../../../container/service/message/MessageTemplate";
import MessageTemplateDetail from "../../../container/service/message/MessageTemplateDetail";
import MessageTemplateCreate from "../../../container/service/message/MessageTemplateCreate";
import MessageResult from "../../../container/service/message/MessageResult";
import KakaoTemplateCreate from "../../../container/service/message/kakao/KakaoTemplateCreate";
import KakaoTemplateListPage from "../../../container/service/message/kakao/KakaoTemplateListPage";
import KakaoSenderProfilePage from "../../../container/service/message/kakao/KakaoSenderProfilePage";
import KakaoTemplateDetail from "../../../container/service/message/kakao/KakaoTemplateDetail";

const cx = classnames.bind(style);

const MessagePage = () => {
    return (
        <section className={cx("cont")}>
            <Switch>
                <Route exact path="/service/message/send" render={(props) => <MessageSend {...props} cx={cx} />}/>
                <Route exact path="/service/message/template" render={(props) => <MessageTemplate {...props} cx={cx} />}/>
                <Route exact path="/service/message/template/detail" render={(props) => <MessageTemplateDetail {...props} cx={cx} />}/>
                <Route exact path="/service/message/template/create" render={(props) => <MessageTemplateCreate {...props} cx={cx} />}/>
                <Route exact path="/service/message/result" render={(props) => <MessageResult {...props} cx={cx} />}/>


                <Route exact path="/service/message/kakao/alimtalk/create" render={(props) => <KakaoTemplateCreate {...props} cx={cx} />}/>
                <Route exact path="/service/message/kakao/alimtalk/detail" render={(props) => <KakaoTemplateDetail {...props} cx={cx} />}/>


                <Route exact path="/service/message/kakao/alimtalk" render={(props) => <KakaoTemplateListPage {...props} cx={cx} />}/>
                <Route exact path="/service/message/kakao/profile/list" render={(props) => <KakaoSenderProfilePage {...props} cx={cx} />}/>





                <Route
                    exact
                    path="/service/message"
                    render={() => <Redirect to="/service/message/send" />}
                />
            </Switch>
        </section>
    );
};

export default MessagePage;