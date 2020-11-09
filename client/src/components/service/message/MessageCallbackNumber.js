import React, {Component} from 'react';

const MessageCallbackNumber = ({cx}) => {
    return (
        <div className={cx("section_type")}>
            <ul>
                <li>
                    <span className={cx("tit_message")}>발신번호</span>
                    <div className={cx("right_area_wrap")}>
                        <input type="text"/>
                        <div className={cx("right_area")}>
                            <button className={cx("btn")}>발신번호등록</button>
                        </div>
                    </div>
                </li>
                <li>
                    <span className={cx("tit_message")}>080번호삽입</span>
                    <div className={cx("right_area_wrap")}>
                        <input type="text"/>
                        <div className={cx("right_area")}>
                            <input type="checkbox"/>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    );
}

export default MessageCallbackNumber;