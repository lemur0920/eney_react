import React, {Fragment, useCallback, useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from "react-router-dom";
import Button from "../../../components/common/Button";
import CustomAlertModal from "../../../components/common/CustomAlertModal";
import MessageSendTextInput from "../../../components/service/message/MessageSendTextInput";
import MessagePhone from "../../../components/service/message/MessagePhone";
import {editTemplateValue, setTemplateContent} from "../../../modules/service/template";
import styled from "styled-components";
import MessageSendFormCid from "../../../components/service/message/MessageSendFormCid";
import qs from "qs";
import {getAllCidList, getCidList, initialize} from "../../../modules/service/patchcall";
import {
    getCustomerGroupList,
    editCustomerGroup,
    getCustomerGroupListCount,
    insertSMS,
    message_initialize, editCallback
} from "../../../modules/service/message";
import {deleteLicense, myinfo} from "../../../modules/user/mypage/mypage";
import PatchCallLogTable from "../../../components/service/patchcall/calllog/PatchCallLogTable";
import Pagination from "../../../components/util/Pagination";
import Loading from "../../../components/util/Loading";
import MessageSendFormSelectCustomerGroup from "../../../components/service/message/MessageSendFormSelectCustomerGroup";
import MessageSendFormShowEstimate from "../../../components/service/message/MessageSendFormShowEstimate";
import CustomerGroupMemberManagementModal from "../customer/group/CustomerGroupMemberManagementModal";
import MessageTemplateListModal from "./send/MessageTemplateListModal";
import {getByte} from "../../../lib/validation/validation";
import {formatDate} from "../../../lib/util/formatter";
import Flatpickr from "react-flatpickr";
import {Korean} from "flatpickr/dist/l10n/ko.js"
import FormControl from "@material-ui/core/FormControl";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Radio from "@material-ui/core/Radio";
import coloringInfo from "../../../components/coloring_apply/ColoringData";
import AlertModal from "../../../components/common/AlertModal";
import MessageSendResultModal from "./send/MessageSendResultModal";
import MessageMsgTypeRadio from "../../../components/service/message/MessageMsgTypeRadio";
import swal from "sweetalert";


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

const MessageSend = ({cx, location, history}) => {

        const option = {
            mode: "single",
            // maxDate: "today",
            minDate: "today",
            locale: Korean,
            dateFormat: "Y-m-d H:i",
            altFormat: "F j, Y",
            enableTime: true,
        };

        const dispatch = useDispatch();
        const [isOpen, setIsOpen] = useState(false);
        const [showModal, setShowModal] = useState(false);
        const [showTemplateListModal, setShowTemplateListModal] = useState(false);
        const [showSendResultModal, setShowSendResultModal] = useState(false);
        const [msgType, setMsgType] = useState('1');

        const [fileloc1, setFileloc1] = useState("");


        const [res_type, setRes_type] = useState('0');
        const [request_time, setRequest_time] = useState(null);

        const setDate = (e) => {
            setRequest_time(formatDate(e[0]));
        };

        const {templateEdit, cGroupList, selectedCustomerGroup, selectedCallback, epoint, cGroupCount, sendResult, templateList, cGroupCustomerList, templateEditResult, templateEditError, cid, loading} = useSelector(({patchcall, mypage, message, template, loading}) => ({
            templateEdit: template.templateEdit,
            templateEditResult: template.templateEditResult,
            templateEditError: template.templateEditError,
            cid: patchcall.cid,
            epoint: mypage.user.epoint,
            cGroupList: message.cGroupList,
            cGroupCustomerList: message.cGroupCustomerList,
            selectedCustomerGroup: message.selectedCustomerGroup,
            selectedCallback: message.selectedCallback,
            cGroupCount: message.cGroupCount,
            sendResult: message.sendResult,
            // loading: loading['patchcall/GET_ALL_CID_LIST'],
            loading: loading['message/GET_CUSTOMER_GROUP_LIST'],
        }));

        // TODO : WEB SMS 예약발송 추가 (완료)
        // TODO : WEB SMS 발송 후 발송 완료건 결과표시 추가 O
        // TODO : WEB SMS 보유캐시 부족시 발송불가알람 추가
        // TODO : WEB SMS 묶음발송 값 추가 O
        // TODO : WEB SMS 묶음발송 값 추가 후 페이징 수정/추가

        useEffect(() => {
            dispatch(getAllCidList());


            return () => {
                dispatch(initialize());
            }

        }, []);

        useEffect(() => {
            dispatch(myinfo());

        }, []);

        useEffect(() => {
            dispatch(getCustomerGroupList());

            return () => {
                dispatch(message_initialize());
            }
        }, []);

        useEffect(() => {
            console.log('selectedCustomerGroup');
            console.log(selectedCustomerGroup);
            dispatch(getCustomerGroupListCount(
                selectedCustomerGroup
            ))
        }, [selectedCustomerGroup]);

        useEffect(() => {
            if (sendResult != null) {
                // setShowSendResultModal(true);
                swal("발송에 성공하였습니다").then(() => {
                    // history.push("/service/message/send");
                    window.location.replace("/service/message/send");
                })
            }
        }, [sendResult]);

        // failed: 0
        // point: 22
        // success: 1


        // 선택그룹 변경
        const changeCustomerGroup = e => {
            const {value, name} = e.target;

            dispatch(
                editCustomerGroup(value)
            )
        };

        // 선택 발신번호 변경
        const changeCallback = e => {
            const {value, name} = e.target;

            dispatch(
                editCallback(value)
            )
        };


        // 입력폼 변경시 미리보기 보여줌

        const [viewText, setViewText] = useState('');
        const [viewSubject, setViewSubject] = useState('');
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

        const sendResultModalCloseHandle = e => {
            history.push(`/service/message/result`)
        }


        const changeMsg_type = e => {
            const {value, name} = e.target;
            if (value == 1 && (getByte(viewText)) > 90) {
                swal('작성한 내용이 90byte를 초과하여 변경할 수 없습니다');

            } else {
                setMsgType(value);
            }

        };

// 템플릿 리스트 MODAL
        const showTemplateModal = e => {
            setShowTemplateListModal(true);
        };


        const sendSMS = () => {

            const data = {
                subject: viewSubject,
                text: viewText,
                // msg_type: '3',
                msg_type: msgType,
                callback: selectedCallback,
                group_idx: selectedCustomerGroup,
            };

            if (res_type == '1') {
                if (request_time == null) {
                    swal("예약발송 시간을 지정해 주세요");
                    return;
                } else {
                    data.request_time = request_time; //예약발송 시간지정
                }
            } else {
                data.request_time = 'IMMEDIATE'; //예약발송 시간지정
            }

            // if ()
            console.log(data);
            if (fileloc1 != "") data.fileloc1 = fileloc1;

            if (viewSubject == null || viewSubject == '') {
                swal("제목을 입력해 주세요");
                return;
            }

            if (viewText == null || viewText == '') {
                swal("내용을 입력해 주세요");
                return;
            }
            // cGroupCount
            // epoint

            dispatch(insertSMS(data));
        }

// 템플릿 선택시 입력값 지정 함수
        const handleSelectTemplate = (value) => {
            const subject = value.subject;
            const text = value.text;
            const msg_type = value.msg_type;
            console.log(value);

            swal({
                text: "삭제하시겠습니까?",
                buttons: ["취소", "확인"],
                closeOnConfirm: false,
            })
                .then((confirm) => {
                    console.log(confirm);
                    if (confirm) {

                        setViewSubject(subject);
                        setViewText(text);
                        setMsgType(msg_type);

                        setShowTemplateListModal(false);

                    }
                });


            /*dispatch(
                setTemplateContent({
                    subject: subject,
                    text: text,
                })
            );*/
        };

        const changeSelectedItem = (e) => {
            const {name, value} = e.target;

            // onChange={(e) => setCouponNum(e.target.value)}

            setRes_type(value);

            // if (value == 1) console.log(name);
        }

        const request_option = [
            {label: '즉시', value: '0'},
            {label: '예약', value: '1'},
        ];

        const [imageFile, setImageFile] = useState(null);
        const [previewURL, setPreviewURL] = useState(null);

        const file = (e) => {
            console.log(e.target.files[0]);
            let file = e.target.files[0];
            let reader = new FileReader();

            if (file == undefined) {

                setFileloc1("");
                setPreviewURL("");


            } else {
                setFileloc1(file);

                e.preventDefault();

                reader.onloadend = () => {
                    setPreviewURL(reader.result);
                }

                reader.readAsDataURL(file);
            }


            // fileReader.readAsDataURL(e.target.files[0]);

        };

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
                    <div className={cx("sms_step", "message_target", "message_setup")}>

                        <div className={cx('title_area')}>
                            <h1 className={cx('top_title')}><span>문자</span>발송</h1>
                            <div className={cx('right_btn')}>
                                <button onClick={showTemplateModal}>템플릿 가져오기</button>
                            </div>
                        </div>
                        {cid.list != null && cGroupList != null && !loading ? (
                            <div className={cx('message_write', 'clfx')}>
                                <div className={cx("info_message")}>
                                    <MessageMsgTypeRadio cx={cx} msg_type={msgType} changeMsg_type={changeMsg_type}/>
                                    <MessageSendTextInput cx={cx} changeTemplateForm={changeTemplateForm}
                                                          text={viewText} msgType={msgType}
                                                          subject={viewSubject}/>

                                    <Fragment>
                                        <div className={cx('upload_g_wrap')}>
                                            <div className={cx('upload_g')}>
                                                {msgType == 3 ?
                                                    (
                                                        <div>
                                                            <input type="file" className={cx('tf_g')} onChange={file}/>
                                                        </div>
                                                    )
                                                    : ''}

                                            </div>
                                        </div>
                                    </Fragment>
                                    {/*{cid.list != null && cGroupList != null && !loading ? (*/}
                                    <Fragment>
                                        <MessageSendFormCid cx={cx} cidList={cid.list} onChange={changeCallback}/>
                                    </Fragment>
                                    {/*) : <Loading/>}*/}


                                    <div>
                                        <div className={cx('section_g', 'section_time')}>
                                            <div className={cx('section_g', 'section_time')}>
                                                <strong className={cx('tit_message')}>발송 날짜/시간 설정</strong>
                                                <div className={cx('time_start')}>
                                                    <strong className={cx('tit_end', 'tit_start')}>발송 시작 일시</strong>
                                                    <div className={cx('date_select', 'clfx')}>
                                                        {/*<div className={cx('opt_time')}>*/}
                                                        <div>
                                                            <FormControl>
                                                                <RadioGroup required={true} className={cx("radio_group")}
                                                                            aria-label="type" name="send_res"
                                                                            value={res_type}
                                                                            onChange={e => {
                                                                                changeSelectedItem(e)
                                                                            }}>
                                                                    <ul className={cx("clfx")}>

                                                                        {request_option.map((item, index) => (
                                                                            <Fragment key={index}>
                                                                                <FormControlLabel value={item.value}
                                                                                                  control={<Radio
                                                                                                      required={true}
                                                                                                      color="primary"/>}
                                                                                                  label={item.label}/>
                                                                            </Fragment>
                                                                        ))}
                                                                    </ul>
                                                                </RadioGroup>
                                                            </FormControl>
                                                        </div>
                                                        {/*</div>*/}
                                                        <div className={cx('box_tf', 'box_date')}>
                                                            {res_type == '1' ? (<Flatpickr options={option}
                                                                                           disabled={false}
                                                                                           placeholder="날짜를 선택하세요"
                                                                                           onClose={(e) => {
                                                                                               setDate(e)
                                                                                           }}
                                                            />) : ''}

                                                        </div>
                                                    </div>
                                                    <p className={cx('txt_etc')}>문자에 대한 수신의 결과 값은 48시간이 지나야 확인 할 수 있습니다.</p>
                                                </div>
                                            </div>

                                            {/*{cGroupList != null && !loading ? (*/}
                                            <Fragment>
                                                <MessageSendFormSelectCustomerGroup cx={cx} list={cGroupList.list}
                                                                                    groupCount={cGroupCount}
                                                                                    onChange={changeCustomerGroup}/>
                                            </Fragment>
                                            {/*) : <Loading/>}*/}

                                            <MessageSendFormShowEstimate cx={cx} groupCount={cGroupCount} msgType={msgType}
                                                                         epoint={epoint} fileloc1={fileloc1}/>
                                        </div>
                                    </div>


                                </div>
                                <MessagePhone cx={cx} text={viewText} subject={viewSubject} img={previewURL}/>
                            </div>
                        ) : <Loading/>}

                        <div className={cx("wrap_btn")}>
                            <Button eney className={cx("submit_btn")} onClick={e => {
                                sendSMS()
                            }}>전송</Button>
                            <CustomCancelButton type="button" className={cx("submit_btn")}
                                                onClick={() => history.goBack()}>취소</CustomCancelButton>
                        </div>


                    </div>
                </div>
                <CustomAlertModal open={isOpen} onClose={() => setIsOpen(!isOpen)} text="설정이 변경되었습니다"
                                  callBack={() => history.goBack()}/>

                <MessageTemplateListModal open={showTemplateListModal}
                                          onClose={() => setShowTemplateListModal(false)}
                                          cx={cx}
                                          onSelect={handleSelectTemplate}
                />
                {/*history.push(`${location.pathname}/detail?vno=${vno}`)*/}
                {sendResult != null ? (<MessageSendResultModal open={showSendResultModal}
                                                               onClose={sendResultModalCloseHandle}
                                                               cx={cx}
                                                               result={sendResult}
                                                               onSelect={handleSelectTemplate}/>) : ''}

            </Fragment>


        );
    }
;

export default withRouter(MessageSend);