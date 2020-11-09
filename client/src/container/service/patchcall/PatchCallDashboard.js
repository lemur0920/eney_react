import React, {Fragment,useEffect,useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {getCompareData,getCallRatios,getDateByCallData,getTimeByCallData, getTimeAvgCallData,initialize} from "../../../modules/service/patchcall_dashboard";
import TopCallData from "../../../components/service/patchcall/dashboard/TopCallData";
import DateByCallDataGraph from "../../../components/service/patchcall/dashboard/DateByCallDataGraph";
import TimeByCallDataGraph from "../../../components/service/patchcall/dashboard/TimeByCallDataGraph";
import Loading from "../../../components/util/Loading";
import TimeByCallStatisticGraph from "../../../components/service/patchcall/dashboard/TimeByCallStatisticGraph";
import OutGoingTypeGraph from "../../../components/service/patchcall/dashboard/OutGoingTypeGraph";
import ReturnVisitGraph from "../../../components/service/patchcall/dashboard/ReturnVisitGraph";
import LocationRankingGraph from "../../../components/service/patchcall/dashboard/LocationRankingGraph";
import AgentRankingTable from "../../../components/service/patchcall/dashboard/AgentRankingTable";
import CallRatios from "../../../components/service/patchcall/dashboard/CallRatios";
import Fab from '@material-ui/core/Fab';
import Tooltip from '@material-ui/core/Tooltip';


const PatchCallDashboard = ({cx}) => {

    const dispatch = useDispatch();

    const [dateGraphData, setDateGraphData] = useState({
        date:[],
        count:[],
    });

    const [timeGraphData, setTimeGraphData] = useState({
        time:[],
        count:[],
        date:""
    });

    const {compareData,callRatio,dateByCallData,timeByCallData,timeAvgCallData,visitCallData,locationCallData,agentCallData,  compareLoading,dateByCallDataLoading,timeByCallDataLoading,timeAvgCallDataLoading,callRatioLoading,dateDataError} = useSelector(({patchcall_dashboard,loading}) =>({
        compareData: patchcall_dashboard.compareData,
        callRatio: patchcall_dashboard.callRatio,
        dateByCallData:patchcall_dashboard.dateByCallData,
        timeByCallData:patchcall_dashboard.timeByCallData,

        timeAvgCallData:patchcall_dashboard.timeAvg,
        visitCallData:patchcall_dashboard.visitType,
        locationCallData:patchcall_dashboard.location,
        agentCallData:patchcall_dashboard.agent,
        dateDataError:patchcall_dashboard.dateDataError,
        compareLoading: loading['patchcallDashboard/GET_COMPARE_DATA'],
        dateByCallDataLoading: loading['patchcallDashboard/GET_DATE_BY_CALL_DATA'],
        timeByCallDataLoading: loading['patchcallDashboard/GET_TIME_BY_CALL_DATA'],
        timeAvgCallDataLoading: loading['patchcallDashboard/GET_TIME_AVG_CALL_DATA'],
        callRatioLoading: loading['patchcallDashboard/GET_CALL_RATIO'],
    }))


    useEffect(() => {
        dispatch(getCompareData());
        dispatch(getCallRatios());
    },[])


    useEffect(() => {
        if(!dateByCallDataLoading && dateByCallData.dateList.length > 0 && dateByCallData.countList.length > 0 ){
            setDateGraphData({
                date : dateByCallData.dateList,
                count : dateByCallData.countList,
            })
        }
    },[dispatch,dateByCallData,dateByCallDataLoading])

    useEffect(() => {
        if(!timeByCallDataLoading && timeByCallData.timeList.length > 0 && timeByCallData.countList.length > 0 ){
            setTimeGraphData({
                time : timeByCallData.timeList,
                count : timeByCallData.countList,
                date:timeByCallData.date,
            })
        }
    },[dispatch,timeByCallData,timeByCallDataLoading])

    const addDateByChart = (startDate,endDate) => {
        const date = {
            startDate:startDate,
            endDate:endDate
        }
        dispatch(getDateByCallData(date))
        setDateGraphData({
            date : [],
            count : []
        })

    }

    const addTimeByChart = (date) => {
        dispatch(getTimeByCallData(date))
        setTimeGraphData({
            time : [],
            count : [],
            date:""
        })
    }

    const addTimeAvgChart = (startDate,endDate) => {
        const date = {
            startDate:startDate,
            endDate:endDate
        }
        dispatch(getTimeAvgCallData(date))

    }


    return (
        <Fragment>
            <section className={cx("patch_call_dashboard")}>
                <div className={cx("navi")}>
                    <ul className={cx("clfx")}>
                        <li>패치콜</li>
                        <li>대시보드</li>
                    </ul>
                </div>
                <TopCallData cx={cx} compareData={compareData}/>
                {!callRatioLoading && callRatio !== null && (
                    <CallRatios cx={cx} callRatio={callRatio} isLoading={callRatioLoading}/>
                )}

                    <div className={cx("cont_4")}>
                        <div className={cx("box")}>
                            <DateByCallDataGraph cx={cx} chartData={dateGraphData} addDateByChart={addDateByChart} addTimeByChart={addTimeByChart} isLoading={dateByCallDataLoading}/>
                        </div>
                        <div className={cx("box")}>
                            <TimeByCallDataGraph cx={cx} chartData={timeGraphData} isLoading={timeByCallDataLoading}/>
                        </div>
                    </div>

                    <div className={cx("cont_5","clfx")}>
                        <div className={cx("left_area")}>
                            <div className={cx("top_area","box")}>
                                <TimeByCallStatisticGraph cx={cx} addTimeAvgChart={addTimeAvgChart} chartData={timeAvgCallData} isLoading={timeAvgCallDataLoading}/>
                            </div>

                            <div className={cx("btm_area")}>
                                <div className={cx("box")}>
                                    <OutGoingTypeGraph cx={cx} chartData={visitCallData} isLoading={timeAvgCallDataLoading}/>
                                </div>

                                <div className={cx("box")}>
                                    <ReturnVisitGraph cx={cx} chartData={visitCallData} isLoading={timeAvgCallDataLoading}/>
                                </div>
                            </div>
                        </div>

                        <div className={cx("right_area")}>
                            <div className={cx("box")}>
                                <LocationRankingGraph cx={cx} chartData={locationCallData} isLoading={timeAvgCallDataLoading}/>
                            </div>

                            <div className={cx("box")}>
                                <AgentRankingTable cx={cx} chartDate={agentCallData}/>
                            </div>
                        </div>
                    </div>
            </section>
        </Fragment>
    );
};

export default PatchCallDashboard;