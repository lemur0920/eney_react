import React, {useEffect, useState} from 'react';
import Flatpickr from "react-flatpickr";
import Loading from "../../../util/Loading";
import * as randomColor from "../../../../lib/util/random_color";
import Chart from "chart.js";
import ChartDataLabels from 'chartjs-plugin-datalabels';
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";

const OutGoingTypeGraph = ({cx,chartData,isLoading}) => {
    const chartRef = React.createRef();
    const [outGoingTypeChart, setOutGoingTypeChart] = useState(null);

    useEffect(() => {

        if(!isLoading && chartData.total_ani !== 0 && chartData.total_ani !== null){
            const timeAvgChartRef = chartRef.current.getContext("2d");

            let ectCall = chartData.total_ani - (chartData.mobile_ani + chartData.area_ani +chartData.virtual_ani);

            const chart = new Chart(timeAvgChartRef, {
                type: 'pie',
                data: {
                    labels: [
                        '이동 전화',
                        '유선 전화',
                        '가상 번호',
                        '기타'
                    ],
                    datasets: [{
                        data: [chartData.mobile_ani,chartData.area_ani,chartData.virtual_ani,ectCall],
                        backgroundColor: [
                            '#196F96','#E85469','#3CAFE3','#B0AA1D'
                        ],
                        borderWidth: 0,
                        datalabels: {
                            color: '#000000',
                            font:{
                                weight:'bold'
                            }
                        },
                    }],
                },
                fill : true,
                plugins: [ChartDataLabels],
                options: {
                    plugins:{
                        datalabels: {
                            formatter: function(value, context) {
                                if(value === 0){
                                    return ''
                                }else{
                                    return value;
                                }
                            }
                        }
                    },
                    layout: {
                        padding: {
                            left: 0,
                            right: 0,
                            top: 0,
                            bottom: 0
                        }
                    },
                    legend: {
                        position: 'bottom',
                        labels: {
                            usePointStyle: true,
                            paddingTop: 10
                        }},
                    spanGaps: false,
                }
            });

            setOutGoingTypeChart(chart);
        }
    },[chartData,isLoading])

    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>발신 전화 유형 통계<DashBoardInfoTooltip text="발신 전화 유형 통계" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
            </div>
            <div className={cx("graph_area")}>
                <div style={{height:"200px",textAlign:"center",lineHeight:"200px"}}>
                    {isLoading ? <Loading/> : (
                        <canvas
                            hidden={outGoingTypeChart === null}
                            id="outGoingTypeChart"
                            ref={chartRef}
                            height={200}
                        />
                    )}
                    {outGoingTypeChart === null && !isLoading && (
                        <p>시간대별 콜 통계를 추가하세요</p>
                    )}
                </div>
            </div>
        </>
    );
};

export default OutGoingTypeGraph;