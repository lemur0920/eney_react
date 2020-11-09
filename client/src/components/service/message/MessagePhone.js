import React, {Component, Fragment, useState} from 'react';
import TextareaAutosize from "@material-ui/core/TextareaAutosize";
import KakaoAttachItem from "./KakaoAttachItem";

const subectStyle = {
    display: 'block',
    color: ' rgba(60, 60, 60, 1)',
    fontWeight: '500'
}

const textStyle = {
    overflow: 'hidden',
    wordWrap: 'break-word'
}

const MessagePhone = ({cx, text, subject, buttons, img}) => {
    return (
        <div className={cx("view_message_phone")}>
            <div className={cx("wrap_ico")}>
                <span className={cx("ico_round")}></span>&nbsp;
                <span className={cx("ico_circle")}></span>
            </div>
            <div className={cx("wrap_message")}>
                <div className={cx("ad_top")}>
                    <strong className={cx("tit_name")}>에네이</strong>
                    <span className={cx("txt_mail")}>010-1234-1234</span>
                </div>
                <div className={cx("fake_scroll")} style={{height: '482px'}}>
                    <div style={{position: 'relative', top: '0px'}}>
                        <div className={cx("comp_message")}>
                            <div className={cx("message_profile")}>
                                <div className={cx("wrap_thumb")} style={{opacity: '1'}}>
                                    <img
                                        src="http://img1.daumcdn.net/thumb/C24x24.mplusfriend/?fname=http://k.kakaocdn.net/dn/bkXHpZ/btqCgOWkD2T/6CMOFo4rqwxsiTVFhjw3jK/img_s.jpg"
                                        className="thumb_img" alt=""></img>
                                </div>
                                <div className={cx("wrap_cont")}><span className={cx("txt_profile")}>에네이</span></div>
                            </div>
                            <div className={cx("box_message")}>
                                <div className={cx("area_message")}>
                                    <div className={cx("inner_message")}>
                                        <div className={cx("desc_message", "inp_on")}>
                                            <span style={subectStyle}>{subject == '' ? "메세지 제목" : subject}</span>
                                            {img != null ? (<img className='profile_preview' src={img}></img>) : ''}
                                            {/*<textarea style={textStyle} value={text}>*/}
                                            {/*</textarea>*/}
                                            <TextareaAutosize aria-label="minimum height" cols="30" rowsMin={1}
                                                              value={text} placeholder="입력한 내용이 출력됩니다."/>
                                            {
                                                buttons != null ? buttons.map((item, index) => (
                                                    <Fragment key={index}>
                                                        <div className="message_btn" style={{
                                                            overflow: 'hidden',
                                                            margin: '4px 0 0',
                                                            border: 'none'
                                                        }}>
                                                        <span className="link_name" style={{
                                                            display: 'block',
                                                            padding: '5px',
                                                            textAlign: 'center',
                                                            textDecoration: 'none',
                                                            backgroundColor: '#f1f2f4',
                                                            color: 'rgb(153,153,153)',
                                                            borderRadius: '3px'
                                                        }}>
                                                            <span className="txt_btn" style={{
                                                                color: '#191919'
                                                            }}>{!item.name != true ? item.name : '버튼명 입력'}</span>
                                                        </span>
                                                        </div>
                                                    </Fragment>
                                                )) : ''
                                            }
                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div className={cx("scroll")}>
                        <span className={cx("top")}></span>
                        <span className={cx("bottom")}></span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default MessagePhone;