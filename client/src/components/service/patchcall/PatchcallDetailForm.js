import React,{useState,useEffect,Fragment} from 'react';
import {useDispatch} from "react-redux";
import Button from "../../common/Button";
import Dialog from "@material-ui/core/Dialog";
import IconButton from "@material-ui/core/IconButton";
import styled from "styled-components";
import Postcode from "../../util/Postcode";
import CustomModal from "../../common/CustomModal";
import * as clientLib from '../../../lib/api/client';

import swal from 'sweetalert';


const RemoveButton01 = styled(Button)`
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

const RemoveButton02 = styled(Button)`
    padding: 2px 8px;
    font-size: 0.875rem;
    margin-left: 5px;
    background-color: white;
    color:#37afe5;
    border:1px solid #37afe5;
    font-weight: 500;
    line-height: 1.75;
    &:hover{
       background: none;
       }
`;

const PatchcallDetailForm = ({cx, agent,address,getAddress,callbackCheck,messagingList,onChangeAgentValue,onChangeAgentAddr,onDisableCallback}) => {
    const dispatch = useDispatch();

    const [callbackDisable, setCallbackDisable] = useState(false)
    const [showMap, setShowMap] = useState(false)
    const [isCallbackUse, setIsCallbackUse] = useState("")
    const [isOpen, setIsOpen] = useState(false);

    useEffect(() => {

        if(agent.vno.substring(0,4) === '0506' || callbackCheck === null){
            setIsCallbackUse("N")
            setCallbackDisable(true);
        }else {
            if (agent.smsYn === 'Y' || agent.smsYn === 'M') {
                setIsCallbackUse("Y")
            }
            if (agent.outSmsYn === 'Y') {
                setIsCallbackUse("Y")
            }

            if(agent.smsYn ==='N' && agent.outSmsYn ==='N') {
                setIsCallbackUse("N")
            }
        }

    },[])

    const changeCallbackUse = (e) => {
        if(e.target.value === "N"){
            onDisableCallback();
        }
        setIsCallbackUse(e.target.value)
    }

    const changeValue = (name, value) =>{
        const data = {
            key:name,
            value:value
        }
        onChangeAgentValue(data)
    }

    const changeFile = (e) => {
        if(e.target.files[0].size > 20000){
            alert("MMS 이미지 파일은 20KB 미만입니다");
            e.target.value = null;
            return
        };
        changeValue(e.target.name, e.target.files !== null && e.target.files !== undefined ? e.target.files : null)
    }

    const onChangeAddress = (e) => {
        const {value, name} = e.target;

        setAddress({
            ...address,
            [name]:value
        })

    }

    const openTumbnail = () => {
        setIsOpen(!isOpen)
    }

    const removeFile = (e) => {
        swal({
            text: "이미지를 삭제하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
            .then((willDelete) => {
                if (willDelete) {
                    changeValue(e.target.name, null)
                }
            });
    }

        return (
            <>
        <table className={cx("agent_detail_tb")}>
            <colgroup>
                <col style={{width:"20%"}}/>
                <col style={{width:"80%"}}/>
                {/*<col style={{width:"12.76%"}}/>*/}
                {/*<col style={{width:"11.7%"}}/>*/}
                {/*<col style={{width:"15.2%"}}/>*/}
                {/*<col style={{width:"8.5%"}}/>*/}
            </colgroup>
            <thead>
            {/*<tr>*/}
            {/*    <th scope="col">가맹점명</th>*/}
            {/*    <th scope="col">발신지명</th>*/}
            {/*    <th scope="col">050번호</th>*/}
            {/*    <th scope="col">착신번호</th>*/}
            {/*    <th scope="col">등록일</th>*/}
            {/*    <th scope="col">만기일</th>*/}
            {/*</tr>*/}
            </thead>
            <tbody>
                <tr>
                    <th>
                        050번호
                    </th>
                    <td>
                        <input type="text" value={agent.vno} readOnly={true} disabled={true}/>
                    </td>
                </tr>
                <tr>
                    <th>
                        실착신번호
                    </th>
                    <td>
                        <input type="text" name="rcvNo" value={agent.rcvNo} onChange={(e) => {changeValue(e.target.name, e.target.value)}}/>
                    </td>
                </tr>
                <tr>
                    <th>
                        가맹점명
                    </th>
                    <td>
                        <input type="text" name="name" value={agent.name} onChange={(e) => {changeValue(e.target.name, e.target.value)}}/>
                    </td>
                </tr>
                <tr>
                    <th>
                        주소
                    </th>
                    <td>
                        <label htmlFor="address">주소</label>
                        <input type="text" className={cx("addr_input")} id="address" value={address.address} onClick={() => setShowMap(true)} name="address"  readOnly={true}/>

                        <label htmlFor="zonecode">우편번호</label>
                        <input  type="text" id="zonecode" className={cx("addr_input")} name="zonecode" onClick={() => setShowMap(true)}  value={address.zonecode}readOnly={true}/>

                        <div className={cx("addr_box")}>
                            <label htmlFor="detailAddress">상세주소</label>
                            <input  type="text" id="detailAddress"  className={cx("addr_input","detailAddress")} name="detailAddress" value={address.detailAddress} onChange={(e) => onChangeAgentAddr(e)} />
                        <button className={cx("btn_g","addr_search")} onClick={() => setShowMap(true)}>주소 검색</button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>
                        발신지명
                    </th>
                    <td>
                        <input type="text" name="dongName" value={agent.dongName} onChange={(e) => {changeValue(e.target.name, e.target.value)}} readOnly={agent.dongYn !== "Y"} disabled={agent.dongYn !== "Y"}/>
                        <select  defaultValue={agent.dongYn} name="dongYn" onChange={(e) => {changeValue(e.target.name, e.target.value)}}>
                            <option value="Y">사용</option>
                            <option value="N">미사용</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>
                        SMS Call Back 사용 유무
                    </th>
                    <td>
                        <select  name="isCallbackUse" value={isCallbackUse} disabled={callbackDisable} onChange={(e) => changeCallbackUse(e)}>
                            <option value="Y">사용</option>
                            <option value="N">미사용</option>
                        </select>
                    </td>
                </tr>
                {isCallbackUse === "Y" &&(
                    <Fragment>
                    <tr>
                        <th>
                            메세지 전송 방법
                        </th>
                        <td>
                            <input type='checkbox'
                                checked={agent.smsYn === "Y" || agent.smsYn === "M"}
                                   onChange={(e) => {changeValue(e.target.name, e.target.checked ? "Y" : "N")}}
                                   name='smsYn'
                                   color="primary"
                                   id="all_chk"
                            />
                            <label htmlFor="all_chk">통화 시 전체 이벤트 메시지 발송</label>
                            <input type='checkbox'
                                   name='outSmsYn'
                                   checked={agent.outSmsYn === "Y"}
                                   onChange={(e) => {changeValue(e.target.name, e.target.checked ? "Y" : "N")}}
                                   color="primary"
                                   id="out_chk"
                            />
                            <label htmlFor="out_chk">부재중 통화 발생 시 메시지 발송</label>
                            <br/>
                            <br/>
                            <p className="mt-10">통화 시 전체 이벤트 메시지 발송 : 통화가 걸려온 모든 사용자에게 메시지를 전송할 수 있습니다.<br/>
                                부재중 통화 발생 시 메시지 발송 : 부재중 통화가 발생 시 가맹점에 메시지를 전송할 수 있습니다.
                            </p>
                        </td>
                    </tr>
                    </Fragment>
                )}
                {isCallbackUse === "Y" && (agent.smsYn === "Y" || agent.smsYn === "M")&& (
                    <Fragment>
                        <tr>
                            <th>메세지 발신번호 선택</th>
                            <td>
                                <select value={agent.callback_no} name="callback_no" onChange={(e) => {changeValue(e.target.name, e.target.value)}}>
                                    {messagingList.map((item,index) => (
                                        <option key={index} value={item.callback}>{item.callback}</option>
                                    ))}
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>통화 시 전체 이벤트<br/>메세지 내용</th>
                            <td>
                                <div>
                                    <label className="mt-10">
                                        <input type="checkbox" name='smsYn' checked={agent.smsYn === "M"} onChange={(e) => {changeValue(e.target.name, e.target.checked ? "M" : "Y")}} />
                                        mms 이용
                                    </label>
                                </div>
                                <textarea value={agent.sms === null ? "" : agent.sms} name="sms" onChange={(e) => {changeValue(e.target.name, e.target.value)}}></textarea>
                                    {agent.smsYn === "M" && agent.mmsFile !== null && (
                                        <div>
                                            <Button eney onClick={() => {openTumbnail()}}>이미지 확인</Button>
                                            <RemoveButton01 name="mmsFile" onClick={(e) => removeFile(e)}>삭제</RemoveButton01>
                                            {isOpen && (
                                                <Dialog open={isOpen} onClose={() => openTumbnail()}>
                                                    <IconButton  className={cx("modal_close_btn")} color="inherit"  onClick={() => openTumbnail()} aria-label="close">
                                                        {/*<CloseIcon />*/}
                                                        닫기
                                                    </IconButton>
                                                    <img
                                                        className={cx("image")}
                                                        src={`${clientLib.getUrl()}/file/mms/${agent.mmsFile}`}
                                                        alt="no image"
                                                    />
                                                </Dialog>
                                            )}
                                        </div>
                                    )}

                                {agent.smsYn === "M" && agent.mmsFile === null &&
                                (
                                    <div>
                                        <div className="file_input">
                                            <input type="file" name="files" onChange={(e) => {changeFile(e)}} accept=".jpg,.jpeg" />
                                            {agent.files !== null && (
                                                <RemoveButton02 name="files" onClick={(e) => removeFile(e)}>삭제</RemoveButton02>
                                            )}
                                            <p className="mt-10">
                                                파일 확장자 : jpeg / 권장 사이즈 : 176 * 144px , 20KB 미만 / 최대 허용 사이즈 : 1000 * 1000px ,
                                                1MB 미만(휴대폰에 따라 실패할 수 있음)
                                            </p>
                                        </div>
                                    </div>
                                )}
                            </td>
                        </tr>
                    </Fragment>
                )}
                {isCallbackUse === "Y" && agent.outSmsYn === "Y" && (
                    <Fragment>
                        <tr>
                            <th>부재중 메세지<br/>착신 번호</th>
                            <td>
                                <input type = "text" name="out_sms_phone" value={agent.out_sms_phone === null ? "" : agent.out_sms_phone} onChange={(e) => {changeValue(e.target.name, e.target.value)}}/>
                            </td>
                        </tr>
                        <tr>
                            <th>부재중 통화 발생 시<br/>메시지 내용</th>
                            <td>
                                <textarea value={agent.out_sms === null ? "" : agent.out_sms} name="out_sms" onChange={(e) => {changeValue(e.target.name, e.target.value)}}></textarea>
                            </td>
                        </tr>
                    </Fragment>
                )}
            </tbody>
        </table>
        <CustomModal open={showMap}>
            <Postcode getAddress={getAddress} onClose={() => setShowMap(false)}/>
        </CustomModal>
    </>
    );
};

export default PatchcallDetailForm;