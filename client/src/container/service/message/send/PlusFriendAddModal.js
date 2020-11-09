import React from 'react';
import {Box} from "@material-ui/core";
import PatchCallCidNumCheck from "../../../../components/service/patchcall/cid/PatchCallCidNumCheck";
import PatchCallACS from "../../../../components/service/patchcall/cid/PatchCallACS";
import CustomModal from "../../../../components/common/CustomModal";
import MessageTemplateModal from "../../../../components/service/message/MessageTemplateModal";

const PlusFriendAddModal = ({cx, open, categoryList, getAuth, yellowId, phoneNumber, categoryCode, token, createFriend, phoneNumberState, onClose}) => {
    // const PatchCallCidAdd = ({open, onClose,cx}) => {
    const title = '카카오톡 채널 등록';
    const subTitle = '';

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <div className={cx("popup", "channel_registration_popup")}>
                <div className={cx("popup-content")}>
                    {/*<div className={cx('popup', 'template_popup', 'template_area')} id="template_popup">*/}
                    <h1>그룹 멤버 관리</h1>
                    <div className={cx('inner')}>
                        <ul>
                            <li>
                                <div className={cx('title')}>카카오톡 채널등록 <span className={cx('ico_dot')}>필수입력</span></div>
                                <input type="text" onChange={yellowId}></input>
                                <p>@표시로 시작하는 카카오톡 채널 검색용 아이디</p>
                            </li>
                            <li>
                                <div className={cx('title')}>핸드폰번호 <span className={cx('ico_dot')}>필수입력</span></div>
                                <div className={cx('hp_btn_area')}>
                                    <input type="text" value={phoneNumberState} onChange={phoneNumber}></input>
                                    <button onClick={getAuth}>인증요청</button>
                                </div>
                                <p>* 카카오톡 채널관리자로 등록된 핸드폰번호</p>
                            </li>
                            <li>
                                <div className={cx('title')}>인증토큰 <span className={cx('ico_dot')}>필수입력</span></div>
                                <input type="text" className={cx('w_50')} onChange={token}></input>
                                <p>카카오톡 채널 관리자의 카카오톡으로 전송된 숫자로된 인증번호</p>
                            </li>
                            <li>
                                <div className={cx('title')}>카테고리 <span className={cx('ico_dot')}>필수입력</span></div>
                                <select name="" id="" onChange={categoryCode}>
                                    <option value="#">카테고리선택</option>
                                    {categoryList.data != null ? categoryList.data.map((item, index) => (
                                        <option key={index} value={item.code}>({item.code}){item.name}</option>
                                    )) : ''}
                                </select>
                            </li>
                        </ul>
                        <div className={cx('popup_close')}>
                            <button onClick={() => onClose()}><span></span></button>
                        </div>
                        <button type="submit" onClick={createFriend}  className={cx('btn_g' ,'btn_g2')}>등록</button>
                    </div>
                </div>
            </div>
        </CustomModal>
    );
};

export default PlusFriendAddModal;