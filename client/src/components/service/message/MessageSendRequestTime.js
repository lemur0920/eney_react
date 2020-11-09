import React, {Component} from 'react';

const MessageSendRequestTime = ({cx}) => {
    return (
        <div className={cx("section_g", "section_time")}>
            <div className={cx("section_g", "section_time")}>
                <strong className={cx("tit_message")}>발송 날짜/시간 설정</strong>
                <div className={cx("time_start")}>
                    <strong className={cx("tit_end", "tit_start")}>발송 시작 일시</strong>
                    <div className={cx("date_select", "clfx")}>
                        <div className={cx("box_tf", "box_date")}>
                            <input className={cx("tf_g")} type="text" value="2020.02.25"></input>
                        </div>
                        <div className={cx("opt_time")}>
                            <select name="" id="">
                                <option value="#">3시</option>
                            </select>
                        </div>
                        <div className={cx("opt_time")}>
                            <select name="" id="">
                                <option value="#">54분</option>
                            </select>
                        </div>
                    </div>
                    <p className={cx("txt_etc")}>문자에 대한 수신의 결과 값은 48시간이 지나야 확인 할 수 있습니다.</p>
                </div>
            </div>

            <div className={cx("message_total_price")}>
                <h2 className={cx("tit_message")}>총 발송 비용</h2>
                <div className={cx("tb_1")}>
                    <table>
                        <colgroup>
                            <col style={{width: '50%'}}/>
                            <col/>
                        </colgroup>
                        <tbody>
                        <tr className="bg_1">
                            <th>15원 X 4(건)+VAT =</th>
                            <td className={cx("emph_g")}>0원</td>
                        </tr>
                        <tr>
                            <th>유료 발송수</th>
                            <td>4건</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div className={cx("expectation")}>
                    <h3 className={cx("tit_end")}>메시지별 예상비용</h3>
                    <span className={cx("vat_txt")}>VAT 10%</span>
                    <div className={cx("box")}>
                        <span className={cx("title")}>일반</span>
                        <div>15원 X 유료 발송수</div>
                    </div>
                </div>
                <h3 className={cx("tit_end")}>잔액정보</h3>
                <div className={cx("tb_2")}>
                    <table>
                        <colgroup>
                            <col style={{width: '20%;'}}/>
                            <col style={{width: '30%;'}}/>
                            <col style={{width: '20%;'}}/>
                            <col style={{width: '30%;'}}/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row">현재캐시</th>
                            <td>0원</td>
                            <th scope="row">발송 후 캐시</th>
                            <td>
                                <button className={cx("btn")}>충전하기</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <p className={cx("txt_etc")}>메시지 발송등록시 과금된 비용과 실발송 후의 차액은 메시지 발송종료 후에 돌려드립니다.</p>
            </div>
        </div>
    );
}

export default MessageSendRequestTime;