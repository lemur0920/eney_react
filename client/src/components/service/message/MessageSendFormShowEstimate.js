import React, {useEffect, Fragment} from 'react';

const msgCostTable = {
    1: 15,
    3: 45,
    4: 140,
}

const MessageSendFormShowEstimate = ({cx, groupCount, msgType, epoint, fileloc1}) => {
    return (
        <div className={cx('message_total_price')}>
            <h3 className={cx('tit_end')}>예상 발송 비용</h3>

            <div className={cx('tb_1')}>
                <table>
                    <colgroup>
                        <col style={{width: "50%"}}/>
                        <col/>
                    </colgroup>
                    <tbody>
                    <tr className={cx('bg_1')}>
                        {/*<th>15원 X {groupCount}(건)+VAT =</th>*/}
                        {
                            fileloc1 == '' || fileloc1 == undefined ? (
                                <Fragment>
                                    <th>{msgCostTable[msgType]}원 X {groupCount}(건) =</th>
                                    <td className={cx('emph_g')}>{msgCostTable[msgType] * groupCount}원</td>

                                </Fragment>
                            ) : (
                                <Fragment>
                                    <th>{msgCostTable[4]}원 X {groupCount}(건) =</th>
                                    <td className={cx('emph_g')}>{msgCostTable[4] * groupCount}원</td>
                                </Fragment>
                            )
                        }

                    </tr>
                    <tr>
                        <th>유료 발송수</th>
                        <td>{groupCount}건</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <h3 className={cx('tit_end')}>잔액정보</h3>
            <div className={cx('tb_2')}>
                <table>
                    <colgroup>
                        <col style={{width: "20%"}}/>
                        <col style={{width: "30%"}}/>
                        <col style={{width: "20%"}}/>
                        <col style={{width: "30%"}}/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row">현재캐시</th>
                        <td>{epoint}원</td>
                        <th scope="row">발송 후 예상 잔여캐시</th>
                        <td>
                            {/*<button className={cx('btn')}>충전하기</button>*/}
                            {epoint - (msgCostTable[msgType] * groupCount)}원
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <p className={cx('txt_etc')}>메시지 발송등록시 과금된 비용과 실발송 후의 차액은 익월에 돌려드립니다.</p>
        </div>
    );
};

export default MessageSendFormShowEstimate;