import React from 'react';
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";

const TopCallData = ({cx, compareData}) => {

    const insertSign = (value) =>{
        if(value > 0 ){
            return "+"+value;
        }else{
            return value;
        }
    }

    const getSecondsToHMS = (seconds) =>{
        var hour = parseInt(seconds/3600);
        var min = parseInt((seconds%3600)/60);
        var sec = seconds%60;
        return min+"m : " + sec+"s";
    }

    return (
        <div className={cx("cont_2")}>
            <ul>
                <li className={cx("bg_1")}>
                    <div className={cx("txt_area")}>
                        <strong>{compareData.conversion_rate}%</strong>
                        <span className={cx("change_rate_txt")}>{compareData.conversion_rate_change_rate === "Infinity" || compareData.conversion_rate_change_rate === 'NaN' ? "측정불가" : `${insertSign(compareData.conversion_rate_change_rate)}%`}</span>
                        <span>전환율<DashBoardInfoTooltip text="2일전 대비 어제 전환율" style={{marginLeft:5,width:17}}/></span>
                    </div>
                </li>
                <li className={cx("bg_2")}>
                    <div className={cx("txt_area")}>
                        <strong>{compareData.result_call}</strong>
                        <span className={cx("change_rate_txt")}>{compareData.result_call_change_rate === "Infinity"  || compareData.result_call_change_rate === 'NaN' ? "측정불가" : `${insertSign(compareData.result_call_change_rate)}%`}</span>
                        <span>성공콜<DashBoardInfoTooltip text="2일전 대비 어제 성공콜" style={{marginLeft:5,width:17}}/></span>
                    </div>
                </li>
                <li className={cx("bg_3")}>
                    <div className={cx("txt_area")}>
                        <strong>{compareData.total_call}</strong>
                        <span className={cx("change_rate_txt")}>{compareData.total_call_change_rate === "Infinity"  || compareData.total_call_change_rate === 'NaN' ? "측정불가" : `${insertSign(compareData.total_call_change_rate)}%`}</span>
                        <span>전체콜<DashBoardInfoTooltip text="2일전 대비 어제 전체콜" style={{marginLeft:5,width:17}}/></span>
                    </div>
                </li>
                <li className={cx("bg_4")}>
                    <div className={cx("txt_area")}>
                        <strong>{getSecondsToHMS(compareData.svc_duration)}</strong>
                        <span className={cx("change_rate_txt")}>{compareData.svc_duration_change_rate === "Infinity"  || compareData.svc_duration_change_rate === 'NaN' ? "측정불가" : `${insertSign(compareData.svc_duration_change_rate)}%`}</span>
                        <span>평균통화시간<DashBoardInfoTooltip text="2일전 대비 어제 통화시간" style={{marginLeft:5,width:17}}/></span>
                    </div>
                </li>
            </ul>
        </div>
    );
};

export default TopCallData;