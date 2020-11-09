import React, {useEffect, useState} from 'react';
import Flatpickr from "react-flatpickr";
import * as randomColor from "../../../../lib/util/random_color";
import Chart from "chart.js";
import * as moment from 'moment'
import Loading from "../../../util/Loading";
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";

const TimeByCallDataGraph = ({cx,chartData, isLoading}) => {
    const chartRef = React.createRef();

    const [searchDate, setSearchDate] = useState(null)

    const [timeChart, setTimeChart] = useState(null);

    const [data , setData] = useState({
        labels:null,
        tmpLabels:[],
        datasets:[],
    });

    useEffect(() => {

    },[])
    useEffect(() => {
        if(chartData.time.length > 0 ){
            const ranColor = randomColor.getRandomColor();
            const value = {
                label: `${moment(chartData.date, 'YYYYMMDD').format('YYYY-MM-DD')}`,
                data: chartData.count,
                fill: false,
                borderColor: ranColor,
                borderWidth: 2,
                pointBorderColor: ranColor,
                pointBackgroundColor: ranColor,
                lineTension: 0.1,
            }

            let labelArray = chartData.time
            let dataArray = data.datasets.concat([value]);

            setData({
                labels:labelArray,
                tmpLabels: data.tmpLabels.concat([chartData.time]),
                datasets:dataArray
            })
        }


    },[chartData])

    useEffect(() => {

        if(data.labels !== null){
            const timeByChartRef = chartRef.current.getContext("2d");

            const chart = new Chart(timeByChartRef, {
                type: "line",
                data: {
                    //Bring in data
                    labels: data.labels,
                    datasets:data.datasets
                },
                fill : false,
                options: {
                    legend: {
                        labels: {
                            usePointStyle: true,
                            padding: 10
                        }
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                autoSkip: false,
                                stepSize: 1,
                                beginAtZero: true,
                                padding: 20,
                            },
                            gridLines: {
                                tickMarkLength: 40,
                                display: true,
                                drawTicks: false,
                                drawOnChartArea: true,
                            },
                        }],
                        xAxes: [{
                            ticks: {
                                autoSkip: false,
                                beginAtZero:true,
                                padding: 7,
                                labelOffset: 5,
                            },
                            gridLines: {
                                tickMarkLength: 40,
                                display: true,
                                drawTicks: false,
                                drawOnChartArea: true,
                            },
                        }],
                    },
                    tooltips: {
                        callbacks: {
                            title: function() {
                                return '';
                            },
                            label: function(item, d) {
                                return data.tmpLabels[item.datasetIndex][item.index] + " : " + data.datasets[item.datasetIndex].data[item.index]
                            },
                        }
                    },
                }
            });

            setTimeChart(chart);
        }


    },[data])

    const onChartReset = () => {
        if(timeChart !== null){
            timeChart.destroy();
            setData({
                labels:null,
                tmpLabels:[],
                datasets:[],
            })

            setTimeChart(null);
        }
    }


    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>시간별 그래프<DashBoardInfoTooltip text="시간 기준 콜 수" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
                <div className={cx("right_btn")}>
                    <button className={cx("btn_refresh")} onClick={() => {onChartReset()}}><span></span></button>
                </div>
            </div>
            <div className={cx("graph_area")}>
                <div style={{height:"260px",textAlign:"center",lineHeight:"260px"}}>
                    {isLoading ? <Loading/> : (
                        <canvas
                            hidden={timeChart === null}
                            id="timeChart"
                            ref={chartRef}
                            height={"55vh"}
                        />
                    )}
                    {timeChart === null && !isLoading && (
                        <p>날짜 별 그래프에서 날짜를 선택하세요</p>
                    )}

                </div>
            </div>
        </>
    );
};

export default TimeByCallDataGraph;