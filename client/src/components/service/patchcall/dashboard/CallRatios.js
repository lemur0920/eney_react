import React,{useEffect} from 'react';
import CallRatioKeyword from "./CallRatioKeyword";
import CallRatioSource from "./CallRatioSource";
import CallRatioOs from "./CallRatioOs";
import CallRatioBrowser from "./CallRatioBrowser";
import Tooltip from "@material-ui/core/Tooltip/Tooltip";
import HelpOutlineIcon from '@material-ui/icons/HelpOutline';

const CallRatios = ({cx,callRatio,isLoading}) => {
    useEffect(()=> {
    },[])
    return (
        <div className={cx("cont_3","clfx")}>
            <div className={cx("box")}>
                <CallRatioKeyword cx={cx} list={callRatio.keyword.length > 0 ? callRatio.keyword : null}/>
            </div>
            <div className={cx("box")}>
                <CallRatioSource cx={cx} chartData={callRatio.source.countList.length > 0 ? callRatio.source : null}/>
            </div>
            <div className={cx("box")}>
                <CallRatioOs cx={cx} chartData={callRatio.os.countList.length > 0 ? callRatio.os : null}/>
            </div>
            <div className={cx("box")}>
                <CallRatioBrowser cx={cx} chartData={callRatio.browser.countList.length > 0 ? callRatio.browser : null}/>
            </div>
        </div>
    );
};

export default CallRatios;