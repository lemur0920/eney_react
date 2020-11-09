import React,{useEffect,useState} from 'react';
import Chart from "chart.js";
import * as moment from 'moment'
import "../../../../asset/style/flatpickr.css";
import { Korean } from "flatpickr/dist/l10n/ko.js"
import Flatpickr from "react-flatpickr";
import { Line } from 'react-chartjs-2';
import * as randomColor from '../../../../lib/util/random_color'
import Loading from "../../../util/Loading";
import {getInstanceList} from "../../../../modules/admin/service_apply/cloud";
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";


const DateByCallDataGraph = ({cx,chartData,addDateByChart,addTimeByChart,isLoading}) => {
    const chartRef = React.createRef();
    const isBrowser = process.env.APP_ENV === 'browser';
    let option = null;

    const [dateChart, setDateChart] = useState(null);

    const [data , setData] = useState({
        labels:null,
        tmpLabels:[],
        datasets:[],
    });

    useEffect(() => {

    },[])

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
                    $cal.append('<button class="flatpickr-clear graph_add_btn" id="graph_add_btn">그래프 추가</button>');
                    $cal.find('#graph_add_btn').on('click', function (e) {
                        const selectDate = instance.selectedDates
                        addChart(moment(selectDate[0]).format("YYYYMMDD"),moment(selectDate[1]).format("YYYYMMDD"));
                        instance.close()
                    });
                }
            }
        }
    }

    useEffect(() => {
        if(chartData.date.length > 0 ){
            const ranColor = randomColor.getRandomColor();
            const value = {
                label: `${chartData.date[0]} ~ ${chartData.date[chartData.date.length-1]}`,
                data: chartData.count,
                fill: false,
                borderColor: ranColor,
                borderWidth: 2,
                pointBorderColor: ranColor,
                pointBackgroundColor: ranColor,
                lineTension: 0.1,
            }

            let labelArray = [];
            let dataArray = data.datasets.concat([value]);

            //원래 있던 labels보다 새로 가져온 labels가 더 클 경우
            if(data.labels !== null && data.labels.length < chartData.date.length){
                labelArray = data.labels
                var count = Math.abs(data.labels.length - chartData.date.length);
                for(var i=1; i <= count; i++) {
                    const date = moment(labelArray[labelArray.length-1], 'YYYY-MM-DD').add(+1,'days').format('YYYY-MM-DD');
                    labelArray = labelArray.concat(date);
                }

            }else{
                labelArray = chartData.date
            }

            setData({
                labels:labelArray,
                tmpLabels: data.tmpLabels.concat([chartData.date]),
                datasets:dataArray
            })
        }


    },[chartData])

    useEffect(() => {

        if(data.labels !== null){
            const dateByChartRef = chartRef.current.getContext("2d");

            const chart = new Chart(dateByChartRef, {
                type: "line",
                data: {
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
                                beginAtZero:true,
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
                                padding: 5,
                            },
                            gridLines: {
                                tickMarkLength: 40,
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
                                return data.tmpLabels[item.datasetIndex][item.index] + " : " + data.datasets[item.datasetIndex].data[item.index]
                            },
                        }
                    },
                }
            });

            chartRef.current.onclick = function (evt) {
                let activePoints = chart.getElementsAtEvent(evt);
                if (activePoints.length) {
                    let mouse_position = Chart.helpers.getRelativePosition(evt, chart.chart);

                    activePoints = $.grep(activePoints, function (activePoint, index) {
                        let leftX = activePoint._model.x - 5,
                            rightX = activePoint._model.x + 5,
                            topY = activePoint._model.y + 5,
                            bottomY = activePoint._model.y - 5;

                        return mouse_position.x >= leftX && mouse_position.x <= rightX && mouse_position.y >= bottomY && mouse_position.y <= topY;
                    });
                    let date = moment(data.tmpLabels[activePoints[0]._datasetIndex][activePoints[0]._index],"YYYY-MM-DD").format("YYYYMMDD")
                    addTimeByChart(date)
                }
            };

            setDateChart(chart);
        }


    },[data])

    const addChart = (startDate, endDate) => {
        if(startDate !== null && endDate !== null){
            setDateChart(null);
            addDateByChart(startDate, endDate)
            // setSearchDate({
            //     startDate:null,
            //     endDate:null,
            // })
        }
    }

    const onChartReset = () => {
        if(dateChart !== null){
            dateChart.destroy();
            setData({
                labels:null,
                tmpLabels:[],
                datasets:[],
            })
            setDateChart(null);
        }
    }

    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>날짜별 그래프<DashBoardInfoTooltip text="날짜 기준 콜 수" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
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
                <div style={{height:"260px",textAlign:"center",lineHeight:"260px"}}>
                    {isLoading ? <Loading/> : (
                        <canvas
                            hidden={dateChart === null}
                            id="dateChart"
                            ref={chartRef}
                            height={"55vh"}
                        />
                    )}
                    {dateChart === null && !isLoading && (
                        <p>기간을 선택하세요</p>
                    )}
                </div>
            </div>
        </>
    );
};

export default DateByCallDataGraph;