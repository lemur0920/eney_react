import React, {Component, Fragment} from 'react';

import {getByte} from '../../../lib/validation/validation';


const MessageSendTextInput = ({cx, subject, text, msgType, isKakao, changeTemplateForm}) => {

    return (
        <Fragment>
            <div className={cx("section_type")}>
                <ul>
                    <li>
                        <span className={cx("tit_message")}>메시지 제목</span>
                        <div className={cx("btn_name")}>
                            <input type="text" onChange={changeTemplateForm} name="subject" value={subject}/>
                            <span>{subject.length}/20자</span>
                        </div>
                    </li>
                </ul>
            </div>
            <div className={cx("bundle_message")}>
                <em className={cx("txt_ad")}>(광고)</em>
                <div className={cx("box_tf", "box_tf3", "box_error")}>
                                    <textarea name="text" initialvalue="" error="필수 입력 사항입니다." id="messageWrite"
                                              className={cx("tf_g")} maxLength="2000"
                                              placeholder="메시지 내용을 입력해주세요."
                                              onChange={changeTemplateForm}
                                              value={text}
                                    ></textarea>
                    <span className={cx("txt_byte", "false")}>
                        {
                            isKakao == true ? text.length : getByte(text)
                        }
                        /
                        {
                            isKakao == true ? '1000자' : msgType == 1 ? '90byte' : '2000byte'
                        }
                                    </span>
                </div>
                <span className={cx("txt_ad")}>수신거부 : 홈 &gt; 채널차단</span>
                <p className={cx("desc_error")}>필수 입력 사항입니다.</p>
            </div>
        </Fragment>
    );
}

export default MessageSendTextInput;