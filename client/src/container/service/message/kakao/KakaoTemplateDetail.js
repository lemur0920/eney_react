import React, {Fragment, useCallback, useEffect, useState} from 'react';
import PatchcallManagementTable from "../../../../components/service/patchcall/PatchcallManagementTable";
import Pagination from "../../../../components/util/Pagination";
import Loading from "../../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import {withRouter, Redirect} from "react-router-dom";
import qs from "qs";
import template, {
    editTemplateValue,
    createTemplate,
    initialize
} from "../../../../modules/service/template";
import styled from "styled-components";
import Button from "../../../../components/common/Button";
import CustomAlertModal from "../../../../components/common/CustomAlertModal";
import MessageSendTextInput from "../../../../components/service/message/MessageSendTextInput";
import MessagePhone from "../../../../components/service/message/MessagePhone";
import MessageMsgTypeRadio from "../../../../components/service/message/MessageMsgTypeRadio";
import MessageKButtonAttach from "../../../../components/service/message/MessageKButtonAttach";
import {
    createKakaoTemplate,
    getKakaoTemplate,
    getSenderProfileList,
    updateKakaoTemplate
} from "../../../../modules/service/kakao";
import {editCallback} from "../../../../modules/service/message";
import swal from "sweetalert";
import AlimtalkTemplateList from "../../../../components/service/message/AlimtalkTempateList";

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
const KakaoTemplateDetail = ({cx, history}) => {


    const dispatch = useDispatch();
    const [isOpen, setIsOpen] = useState(false);

    const [senderProfile, setSenderProfile] = useState(null);

    const [kakaoAttach, setKakaoAttach] = useState([]);


    const {templateEdit, templateEditResult, templateEditError, code_create_template, detail_kakao_template, loading} = useSelector(({template, kakao, loading}) => ({
        templateEdit: template.templateEdit,
        templateEditResult: template.templateEditResult,
        templateEditError: template.templateEditError,

        detail_kakao_template: kakao.detail_kakao_template,
        code_create_template: kakao.code_create_template,

        loading: loading['template/CREATE_TEMPLATE'],
    }));

    const changeValue = (index, e) => {
        const {name, value} = e.target;

        console.log(name);
        console.log(value);
        console.log(index);

        setKakaoAttach(
            kakaoAttach.map(kakao =>
                kakao.key === index ? {...kakao, [name]: value} : kakao
            )
        );

    }

    const onRemove = key => {
        setKakaoAttach(kakaoAttach.filter(kakao => kakao.key !== key));
    };

    const addKakaoAttachButton = useCallback(
        text => {
            console.log(kakaoAttach.length);

            if (kakaoAttach.length > 4) {
                swal('버튼 타입은 5개까지 등록 가능합니다. ');
                return;
            } else {
                let attach = {
                    key: kakaoAttach.length === 0 ? 1 : kakaoAttach[kakaoAttach.length - 1].key + 1,
                    name: '',
                    linkType: 'DS',
                    ordering: kakaoAttach.length === 0 ? 1 : kakaoAttach[kakaoAttach.length - 1].key + 1,
                    linkMo: '',
                    linkPc: '',
                    linkAnd: '',
                    linkIos: '',
                };
                setKakaoAttach(kakaoAttach.concat(attach))
            }

        }, [kakaoAttach]
    )

    const [viewText, setViewText] = useState(null);
    const [viewSubject, setViewSubject] = useState(null);

    const changeTemplateForm = e => {
        const {value, name} = e.target;
        // console.log(value);
        console.log(name);

        switch (name) {
            case 'subject':
                setViewSubject(value);
                break;

            case 'text':
                console.log('text임');
                setViewText(value);

                break;
        }


        /*
                dispatch(
                    editTemplateValue({
                        key: name,
                        value,
                    })
                )
        */
    };


    const onCreateTemplate = () => {
        console.log(senderProfile);
        /*if (senderProfile == null || senderProfile == '#') {
            swal("수정 action 추가 작성필요");
            return;
        }*/
        // {"idx":18,"userid":"eney1982","sender_key":"16a6fdc7eecfa5bb6949c5123ebecdbbb8b04cf4","uuid":"@mzy5ztawotruzxk","name":"eney test","created_at":"2020-07-02 15:01:50.0"}

        let data = {
            // senderKey: 'db25460eaa8facc9c3bfe94c837b252f15e2416e',
            senderKey: detail_kakao_template.data.senderKey,
            templateCode: detail_kakao_template.data.templateCode,
            templateName: viewSubject,
            templateContent: viewText,
            // buttons: JSON.stringify(kakaoAttach),
            buttons: kakaoAttach,
        }
        console.log(data);
        // dispatch(createKakaoTemplate(data));
        dispatch(updateKakaoTemplate(data));

    };

    const handleAttach = e => {
        console.log(e);
    }

    const changeSender = e => {
        const {value, name} = e.target;
        console.log(value);
        if (value != '#') {
            setSenderProfile(JSON.parse(value));
        } else {
            setSenderProfile(null);
        }

    };


    useEffect(() => {

        const {senderkey, templatecode} = qs.parse(location.search, {
            ignoreQueryPrefix: true,
        });

        let data = {
            senderKey: senderkey,
            templateCode: templatecode,
        }
        dispatch(getKakaoTemplate(data));


    }, []);

    useEffect(() => {

        if (detail_kakao_template.code == '200') {

            setViewText(detail_kakao_template.data.templateContent);
            setViewSubject(detail_kakao_template.data.templateName);

            setKakaoAttach(detail_kakao_template.data.buttons);
        }

    }, [detail_kakao_template]);


    useEffect(() => { // 템플릿 작성 완료시


        console.log(code_create_template);

        if (code_create_template.code != null) {
            if (code_create_template.code == '200') {
                swal(code_create_template.message)
                    .then((value) => {
                        history.push("/service/message/kakao/alimtalk");
                    });
                console.log('200');
            } else {
                swal(code_create_template.message);
                console.log('!200');

            }
        }


    }, [code_create_template]);


    useEffect(() => {
        console.log(templateEditResult);
        console.log(templateEditError);

        if (templateEditResult === true && templateEditError === null) {
            setIsOpen(true)
        }
    }, [templateEditResult]);

    return (
        <Fragment>
            {detail_kakao_template.code != null && viewSubject != null && viewText != null ? (
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
                                    <h1 className={cx('top_title')}><span>카카오</span>템플릿 상세</h1>
                                </div>

                                <div className={cx('message_write', 'clfx')}>
                                    <div className={cx('section_type')}>
                                        <ul>
                                            <li>
                                                <span className={cx('tit_message')}>발신 프로필</span>
                                                <div className={cx('right_area_wrap')}>
                                                    <input type="text" readOnly={true}
                                                           value={detail_kakao_template.data.senderKey}/>
                                                </div>
                                            </li>
                                            {/*<li>
                    <span className={cx('tit_message')}>080번호삽입</span>
                    <div className={cx('right_area_wrap')}>
                        <input type="text"/>
                        <div className={cx('right_area')}>
                            <input type="checkbox"/>
                        </div>
                    </div>
                </li>*/}
                                        </ul>
                                    </div>
                                    {/*templateName.  templateContent*/}
                                    <div className={cx("info_message")}>
                                        <MessageSendTextInput cx={cx} changeTemplateForm={changeTemplateForm}
                                                              text={viewText}
                                                              subject={viewSubject}/>
                                        <MessageKButtonAttach cx={cx} addAttach={addKakaoAttachButton} list={kakaoAttach}
                                                              handleAttach={handleAttach} changeValue={changeValue}
                                                              onRemove={onRemove}/>
                                    </div>
                                    <MessagePhone cx={cx} text={viewText} subject={viewSubject} buttons={kakaoAttach}/>
                                </div>

                                <div className={cx("wrap_btn")}>
                                    {detail_kakao_template.data.inspectionStatus == 'SRJ' || detail_kakao_template.data.inspectionStatus == 'REJ' ? (
                                        <Button eney className={cx("submit_btn")} onClick={e => {
                                            onCreateTemplate()
                                        }}>수정</Button>) : ''}

                                    <CustomCancelButton type="button" className={cx("submit_btn")}
                                                        onClick={() => history.goBack()}>취소</CustomCancelButton>
                                </div>


                            </div>
                        </div>
                    </Fragment>
                ) :
                detail_kakao_template.code != '200' ? '템플릿 정보를 조회할 수 없습니다 (이미 삭제된 발신프로필이거나 템플릿)' : <Loading/>}

            <CustomAlertModal open={isOpen} onClose={() => setIsOpen(!isOpen)} text="작성이 변경되었습니다"
                              callBack={() => history.goBack()}/>
        </Fragment>


    );
};

export default withRouter(KakaoTemplateDetail);