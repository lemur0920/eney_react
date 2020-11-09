import React from 'react';

const PatchCallLogItem = ({cx, item,audioDownload}) => {
    return (
        <tr className={cx("active_tr")}>
            <td>
                {item.agentName}
            </td>
            <td>
                {item.dongName}
            </td>
            <td>
                {item.aniNo}
            </td>
            <td>
                {item.rcvNo}
            </td>
            <td>
                {item.vno}
            </td>
            <td>
                {item.csDate}
            </td>
            <td>
                {item.csTime}
            </td>
            <td>
                {item.ssTime}
            </td>
            <td>
                {item.seTime}
            </td>
            <td>
                {item.svc_duration_text}
            </td>
            <td>
                {item.call_result_text}({item.callResult})
            </td>
            <td>
                    {item.voiceFile !== "" && (
                        <button style={{width:20}} onClick={() => audioDownload(item.csDate.substring(4,6),item.voiceFile)}><img src={require("../../../../asset/image/download_icon.png")}/></button>
                                /*<audio src={`${item.csDate.substring(4,6)}/${item.voiceFile}`} type="audio/wav" controls preload="auto" loop=""></audio>*/


                    )}
            </td>
        </tr>
    );
};

export default PatchCallLogItem;