import React, {Fragment, useCallback, useEffect, useState} from 'react';
import PatchcallManagementTable from "../../../components/service/patchcall/PatchcallManagementTable";
import Pagination from "../../../components/util/Pagination";
import Loading from "../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from "react-router-dom";
import qs from "qs";
import template, {
    editTemplateValue,
    createTemplate,
    initialize
} from "../../../modules/service/template";
import styled from "styled-components";
import Button from "../../../components/common/Button";
import CustomAlertModal from "../../../components/common/CustomAlertModal";
import MessageSendTextInput from "../../../components/service/message/MessageSendTextInput";
import MessagePhone from "../../../components/service/message/MessagePhone";
import MessageMsgTypeRadio from "../../../components/service/message/MessageMsgTypeRadio";
import swal from "sweetalert";
import {getByte} from "../../../lib/validation/validation";

const CustomCancelButton = styled(Button)`
    padding: 6px 16px;
    font-size: 0.875rem;
    margin: 8px;
    background-color: white;
    color:#37afe5;
    border:1px solid #37afe5;
    font-weight: 500;
    line-height: 1.75;
    &:hover{
       background: none;
       }
`;

const MessageTemplateCreate = ({cx, history}) => {


    const dispatch = useDispatch();
    const [isOpen, setIsOpen] = useState(false);

    const {templateEditResult, templateEditError, loading} = useSelector(({template, loading}) => ({
        templateEditResult: template.templateEditResult,
        templateEditError: template.templateEditError,
        loading: loading['template/CREATE_TEMPLATE'],
    }));

    const [viewText, setViewText] = useState('');
    const [viewSubject, setViewSubject] = useState('');
    const [msgType, setMsgType] = useState('1');

    const changeTemplateForm = e => {
        const {value, name} = e.target;
        if (name == 'subject' && (value.length > 20)) {
            console.log('subject가 20byte보다 큼');
            swal("제목은 20자를 넘을 수 없습니다.");
            // console.log(e.preventDefault());
        } else if (name == 'text' && (getByte(value) > 90) && msgType == 1) {

            swal("내용은 90byte를 넘을 수 없습니다.");


        } else if (name == 'text' && (getByte(value) > 2000) && msgType == 3) {

            swal("내용은 2000byte를 넘을 수 없습니다.");


        } else {

            if (name == 'text') setViewText(value);
            if (name == 'subject') setViewSubject(value);
        }
    }

    const changeMsg_type = e => {
        const {value, name} = e.target;
        if (value == 1 && (getByte(viewText)) > 90  ){
            swal('작성한 템플릿 내용이 90byte를 초과하여 변경할 수 없습니다');

        } else {
            setMsgType(value);
        }
    };

    const onCreateTemplate = () => {
        const data = {
            subject: viewSubject,
            text: viewText,
            msg_type: msgType
        }

        dispatch(createTemplate(data))
    };

    useEffect(() => {

        if (templateEditResult === true && templateEditError === null) {
            swal("작성에 성공했습니다").then(() => {
                history.push("/service/message/template")
            })
        }

    }, [templateEditResult]);

    useEffect(() => {
        return () => {
            dispatch(initialize());
        }
    }, [])

    return (

        <Fragment>
            <div className={cx("navi")}>
                <ul className={cx("clfx")}>
                    <li>Home</li>
                    <li>서비스</li>
                    <li>문자메세징 작성</li>
                </ul>
            </div>
            <div className={cx("box_cont")}>
                <div className={cx("sms_step")}>

                    <div className={cx('title_area')}>
                        <h1 className={cx('top_title')}><span>문자</span>템플릿 작성</h1>
                    </div>

                    <div className={cx('message_write', 'clfx')}>
                        <div className={cx("info_message")}>
                            <MessageMsgTypeRadio cx={cx} msg_type={msgType} changeMsg_type={changeMsg_type}/>
                            <MessageSendTextInput cx={cx} changeTemplateForm={changeTemplateForm} text={viewText} msgType={msgType}
                                                  subject={viewSubject}/>
                        </div>
                        <MessagePhone cx={cx} text={viewText} subject={viewSubject}/>
                    </div>

                    <div className={cx("wrap_btn")}>
                        <Button eney className={cx("submit_btn")} onClick={e => {
                            onCreateTemplate()
                        }}>작성</Button>
                        <CustomCancelButton type="button" className={cx("submit_btn")}
                                            onClick={() => history.goBack()}>취소</CustomCancelButton>
                    </div>

                </div>
            </div>
        </Fragment>


    );
};

export default withRouter(MessageTemplateCreate);