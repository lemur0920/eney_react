import React from 'react';
import CustomerEventList from "./CustomerEventList";

const CustomerEventTable = ({eventList,cx,audioDownload}) => {
    return (
        <table>
            <colgroup>
                <col style={{width:65}}/>
                <col style={{width:95}}/>
                <col style={{width:88}}/>
                <col style={{width:70}}/>
                <col style={{width:165}}/>
                <col style={{width:75}}/>
                <col style={{width:85}}/>
                <col style={{width:90}}/>
                <col style={{width:55}}/>
                <col style={{width:70}}/>
                <col style={{width:75}}/>
                <col style={{width:80}}/>
                <col style={{width:120}}/>
                <col style={{width:225}}/>
                {/*<col style={{width:100}}/>*/}
            </colgroup>
            <thead>
            <tr>
                <th scope="col" rowSpan="2" className={cx("sky_btm")}>No.</th>
                <th scope="col" rowSpan="2" className={cx("sky_btm")}>날짜/시간</th>
                <th scope="col" rowSpan="2" className={cx("sky_btm")}>유형</th>
                <th scope="col" colSpan="14">이벤트 상세</th>
            </tr>
            <tr>
                <th scope="col" className={cx("sky_btm")}>도달유무</th>
                <th scope="col" className={cx("sky_btm")}>목적지</th>
                <th scope="col" className={cx("sky_btm")}>수신자</th>
                <th scope="col" className={cx("sky_btm")}>수신자지역</th>
                <th scope="col" className={cx("sky_btm")}>음원파일</th>
                <th scope="col" className={cx("sky_btm")}>통화 시간</th>
                <th scope="col" className={cx("sky_btm")}>통화 시작시간</th>
                <th scope="col" className={cx("sky_btm")}>통화 종료시간</th>
                <th scope="col" className={cx("sky_btm")}>유입 키워드</th>
                <th scope="col" className={cx("sky_btm")}>브라우저</th>
                <th scope="col" className={cx("sky_btm")}>접속기기</th>
                <th scope="col" className={cx("sky_btm")}>접속환경</th>
                <th scope="col" className={cx("sky_btm")}>지역</th>
                <th scope="col" className={cx("sky_btm")}>referer</th>
                {/*<th scope="col" className={cx("sky_btm")}>메모하기</th>*/}
            </tr>
            </thead>
            <tbody>
            {eventList !== null && (
                <CustomerEventList eventList={eventList} cx={cx} audioDownload={audioDownload}/>
            )}
            </tbody>
        </table>
    );
};

export default CustomerEventTable;