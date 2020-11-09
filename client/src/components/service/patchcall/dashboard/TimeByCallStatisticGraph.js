import React, {useEffect, useState} from 'react';
import Loading from "../../../util/Loading";
import { Korean } from "flatpickr/dist/l10n/ko.js"
import Flatpickr from "react-flatpickr";
import * as randomColor from "../../../../lib/util/random_color";
import * as moment from 'moment'
import Chart from "chart.js";
import ChartDataLabels from 'chartjs-plugin-datalabels';
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";
Chart.plugins.unregister(ChartDataLabels);

const TimeByCallStatisticGraph = ({cx,chartData,addTimeAvgChart,isLoading}) => {
    const chartRef = React.createRef();
    const isBrowser = process.env.APP_ENV === 'browser';
    let option = null;

    const [timeAvgChart, setTimeAvgChart] = useState(null);

    const [data , setData] = useState({
        labels:null,
        tmpLabels:[],
        datasets:[],
    });

    if(isBrowser){
        option = {
            mode: "range",
            maxDate: "today",
            locale:Korean,
            dateFormat: "Y-m-d",
            // enableTime: true,
            onReady: function (dateObj, dateStr, instance) {
                let $cal = $(instance.calendarContainer);
                if ($cal.find('.flatpickr-clear').length < 1) {
                    $cal.append('<button class="flatpickr-clear graph_add_btn" id="time_avg_graph_add_btn">그래프 추가</button>');
                    $cal.find('#time_avg_graph_add_btn').on('click', function (e) {
                        const selectDate = instance.selectedDates
                        addChart(moment(selectDate[0]).format("YYYYMMDD"),moment(selectDate[1]).format("YYYYMMDD"));
                        instance.close()
                    });
                }
            }
        }
    }



    useEffect(() => {
        if(chartData.timeList.length > 0 ){
            const ranColor = randomColor.getRandomColor();
            const value = {
                label: `${moment(chartData.startDate, 'YYYYMMDD').format('YYYY-MM-DD')} ~ ${moment(chartData.endDate, 'YYYYMMDD').format('YYYY-MM-DD')}`,
                data: chartData.countList,
                fill: true,
                backgroundColor: ranColor,
                borderColor: ranColor,
                borderWidth: 2,
                pointBorderColor: ranColor,
                pointBackgroundColor: ranColor,
                lineTension: 0.1,
            }

            let dataArray = data.datasets.concat([value]);

            setData({
                labels:chartData.timeList,
                tmpLabels: data.tmpLabels.concat([chartData.timeList]),
                datasets:dataArray
            })
        }


    },[chartData])

    useEffect(() => {

        if(data.labels !== null){
            const timeAvgChartRef = chartRef.current.getContext("2d");

            const chart = new Chart(timeAvgChartRef, {
                type: "bar",
                data: {
                    //Bring in data
                    labels: data.labels,
                    datasets:data.datasets
                },
                fill : true,
                options: {
                    layout: {
                        padding: {
                            left: 0,
                            right: 0,
                            top: 0,
                            bottom: 0
                        }
                    },
                    legend: {
                        labels: {
                            usePointStyle: true,
                            padding: 10
                        }
                    },
                    spanGaps: false,
                    scales: {
                        yAxes: [{
                            ticks: {
                                stepSize: 1,
                                beginAtZero: true,
                                padding: 5,
                            },
                            gridLines: {
                                tickMarkLength: 40,
                                offsetGridLines: true,
                                display: true,
                                drawTicks: false,
                                drawOnChartArea: true,
                            },
                        }],
                        xAxes: [{
                            gridLines: {
                                tickMarkLength: 40,
                                offsetGridLines: true,
                                display: true,
                                drawTicks: false,
                                drawOnChartArea: true,
                            },

                        }]
                    },
                    hover:{
                        events: ['mousemove', 'click'],
                        onHover: (event, chartElement) => {
                            event.target.style.cursor = chartElement[0] ? 'pointer' : 'default';
                        },
                    },
                    tooltips: {
                        callbacks: {
                            title: function() {
                                return '';
                            },
                            label: function(item, d) {
                                return data.tmpLabels[item.datasetIndex][item.index] + "시 : " + data.datasets[item.datasetIndex].data[item.index]
                            },
                        }
                    },
                }
            });

            setTimeAvgChart(chart);
        }
    },[data])

    const addChart = (startDate, endDate) => {
        if(startDate !== null && endDate !== null){
            setTimeAvgChart(null);
            addTimeAvgChart(startDate, endDate)
            // setSearchDate({
            //     startDate:null,
            //     endDate:null,
            // })
        }
    }

    const onChartReset = () => {
        if(timeAvgChart !== null){
            timeAvgChart.destroy();
            setData({
                labels:null,
                tmpLabels:[],
                datasets:[],
            })
            setTimeAvgChart(null);
        }
    }


    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>시간대별 콜 통계 그래프<DashBoardInfoTooltip text="시간대 별 콜 통계 그래프" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
                <div className={cx("right_btn")}>
                    {isBrowser && (
                        <Flatpickr
                            className={cx("graph_date_picker")}
                            options={option}
                            placeholder="날짜를 선택하세요"
                        />
                    )}
                    <button className={cx("btn_refresh")} onClick={() => {onChartReset()}}><span></span></button>
                </div>
            </div>
            <div className={cx("graph_area")}>
                <div style={{height:"200px",textAlign:"center",lineHeight:"200px"}}>
                    {isLoading ? <Loading/> : (
                        <canvas
                            hidden={timeAvgChart === null}
                            id="timeAvgChart"
                            ref={chartRef}
                            height={"90vh"}
                        />
                    )}
                    {timeAvgChart === null && !isLoading && (
                        <p>기간을 선택하세요</p>
                    )}
                </div>
            </div>
        </>
    );
};

export default TimeByCallStatisticGraph;