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
import {createKakaoTemplate, getSenderProfileList} from "../../../../modules/service/kakao";
import {editCallback} from "../../../../modules/service/message";
import swal from "sweetalert";
import {getByte, isUrl} from "../../../../lib/validation/validation";

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

const KakaoTemplateCreate = ({cx, history}) => {


    const dispatch = useDispatch();
    const [isOpen, setIsOpen] = useState(false);

    const [senderProfile, setSenderProfile] = useState(null);

    const [kakaoAttach, setKakaoAttach] = useState([]);


    const {templateEdit, templateEditResult, templateEditError, code_create_template, sender_list, loading} = useSelector(({template, kakao, loading}) => ({
        templateEdit: template.templateEdit,
        templateEditResult: template.templateEditResult,
        templateEditError: template.templateEditError,
        sender_list: kakao.sender_list.list,
        code_create_template: kakao.code_create_template,

        loading: loading['kakao/GET_SENDER_PROFILE_LIST'],
    }));

    const changeValue = (index, e) => {
        const {name, value} = e.target;


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

    /*const changeTemplateForm = e => {
        const {value, name} = e.target;
        console.log(value);
        console.log(name);
        dispatch(
            editTemplateValue({
                key: name,
                value,
            })
        )
    };*/
    const [viewText, setViewText] = useState('');
    const [viewSubject, setViewSubject] = useState('');

    const changeTemplateForm = e => {
        // console.log(templateEdit);
        const {value, name} = e.target;
        if (name == 'subject' && (value.length > 20)) {
            console.log('subj   ect가 20byte보다 큼');
            swal("제목은 20자를 넘을 수 없습니다.");
            // console.log(e.preventDefault());
        } else if (name == 'text' && (value.length > 1000)) {
            swal("내용은 1000자를 넘을 수 없습니다.");
        } else {

            if (name == 'text') setViewText(value);
            if (name == 'subject') setViewSubject(value);
        }
    }


    const onCreateTemplate = () => {
        console.log(senderProfile);
        if (senderProfile == null || senderProfile == '#') {
            swal("발신 프로필을 선택해 주세요");
            return;
        }
        console.log(kakaoAttach);
        kakaoAttach.map((item, index) => {
            console.log(item);
            console.log(index);
            // isUrl(item.linkMo, "url 주소를 확인해 주세요.");
            if (item.linkType == 'WL') {
                isUrl(item.linkMo, "url 주소를 확인해 주세요.");
            }

            if (item.linkType == 'AL') {
                // isUrl(item.linkMo, "url 주소를 확인해 주세요.");
                alert("※ Mobile, iOS, Android중 2개 이상 필수 입력 대상입니다.");
            }
        });


        // {"idx":18,"userid":"eney1982","sender_key":"16a6fdc7eecfa5bb6949c5123ebecdbbb8b04cf4","uuid":"@mzy5ztawotruzxk","name":"eney test","created_at":"2020-07-02 15:01:50.0"}
        let data = {
            // senderKey: 'db25460eaa8facc9c3bfe94c837b252f15e2416e',
            senderKey: senderProfile.sender_key,
            uuid: senderProfile.uuid,
            name: senderProfile.name,
            templateName: viewSubject,
            templateContent: viewText,
            // buttons: JSON.stringify(kakaoAttach),
            buttons: kakaoAttach,
        }
        dispatch(createKakaoTemplate(data));

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
        dispatch(getSenderProfileList({page: -1}));
    }, []);


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
                        <h1 className={cx('top_title')}><span>카카오</span>템플릿 작성</h1>
                    </div>

                    <div className={cx('message_write', 'clfx')}>
                        <div className={cx('section_type')}>
                            <ul>
                                <li>
                                    <span className={cx('tit_message')}>발신 프로필</span>
                                    <div className={cx('right_area_wrap')}>
                                        {/*{cateList.map((item, index) => (*/}

                                        {sender_list != null ?
                                            <select name="senderProfile" onChange={changeSender}>
                                                <option value={"#"}>발신프로필 선택</option>
                                                {sender_list.map((item, index) => (
                                                    <option key={index}
                                                            value={JSON.stringify(item)}>{item.name}</option>
                                                ))}
                                            </select> : ''}


                                        {/*<select value={cidList} name="callback_no">
                            {cidList.map((item,index) => (
                                <option key={index} value={item}>{item}</option>
                            ))}
                        </select>*/}
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

                        <div className={cx("info_message")}>
                            <MessageSendTextInput cx={cx} changeTemplateForm={changeTemplateForm}
                                                  text={viewText}
                                                  subject={viewSubject}
                                                  isKakao={true}
                            />
                            <MessageKButtonAttach cx={cx} addAttach={addKakaoAttachButton} list={kakaoAttach}
                                                  handleAttach={handleAttach} changeValue={changeValue}
                                                  onRemove={onRemove}/>
                        </div>
                        <MessagePhone cx={cx} text={viewText} subject={viewSubject} buttons={kakaoAttach}/>
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

export default withRouter(KakaoTemplateCreate);