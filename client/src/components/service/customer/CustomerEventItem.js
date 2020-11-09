import React from 'react';
import moment from 'moment';

const CustomerEventItem = ({item,cx,audioDownload}) => {
    return (
        <tr>
            <td>{item.rownum}</td>
            <td>{moment(item.event_date).year()} <br/>{moment(item.event_date).format('MM-DD HH:mm:ss a')}</td>
            <td>{item.event_type}</td>
            <td>
                {item.call_result === "0" ? "(성공)" : "(실패)"}
            </td>
            <td><a href="http://www.eney.co.kr/" target="_blank"
                   className={cx("c-sky")}>http://www.eney.co.kr/</a></td>
            <td>빙글리타이</td>
            <td>{item.receiver_location}</td>
            <td>
                {item.voice_file !== "" && item.voice_file !== null && (
                    <button style={{width:20}} onClick={() => audioDownload(item.event_date.substring(5,7),item.voice_file)}><img src={require("../../../asset/image/download_icon.png")}/></button>
                    /*<audio src={`${item.csDate.substring(4,6)}/${item.voiceFile}`} type="audio/wav" controls preload="auto" loop=""></audio>*/
                )}
            </td>
                <td>{item.call_duration}</td>
                <td>{item.ss_date !== null && moment(item.ss_date).format('YYYY-MM-DD HH:mm:ss a')}</td>
                <td>{item.se_date !== null && moment(item.se_date).format('YYYY-MM-DD HH:mm:ss a')}</td>
            <td>{item.keyword}</td>
            <td>{item.browser}</td>
            <td>{item.os}</td>
            <td>{item.deviceCategory}</td>
            <td>{item.city}</td>
            <td><a href="#" className={cx("f-s-bd")}>https://blog.naver.com/?Redirect</a></td>
            {/*<td></td>*/}
        </tr>
    );
};

export default CustomerEventItem;