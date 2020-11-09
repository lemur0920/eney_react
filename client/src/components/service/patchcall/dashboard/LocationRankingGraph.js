import React, {useEffect, useState} from 'react';
import Loading from "../../../util/Loading";
import Chart from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";
import * as randomColor from "../../../../lib/util/random_color";
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";
Chart.plugins.unregister(ChartDataLabels);

const LocationRankingGraph = ({cx,chartData,isLoading}) => {
    const chartRef = React.createRef();
    const [locationRankingChart, setLocationRankingChart] = useState(null);

    useEffect(() => {

        if(!isLoading && chartData.countList.length > 0){
            const locationRankingChartRef = chartRef.current.getContext("2d");

            const ranColor = randomColor.getRandomColor();
            const value = {
                label: `${chartData.startDate} ~ ${chartData.endDate}`,
                data: chartData.countList,
                fill: true,
                backgroundColor: ranColor,
                borderColor: ranColor,
                borderWidth: 2,
                pointBorderColor: ranColor,
                pointBackgroundColor: ranColor,
                lineTension: 0.1,
                datalabels: {
                    labels: {
                        value: {
                            color: '#000000'
                        }
                    },
                    font:{
                        weight:'bold'
                    },
                }
            }

            const chart = new Chart(locationRankingChartRef, {
                type: 'horizontalBar',
                data: {
                    labels: chartData.sidoList ,
                    datasets: [value],
                },
                fill : true,
                plugins: [ChartDataLabels],
                options: {
                    legend: {
                        labels: {
                            usePointStyle: true,
                            padding: 10,
                            display: true
                        }
                    },
                    scales: {
                        xAxes: [{
                            gridLines: {
                                display: false,
                                drawBorder: false
                            },
                            ticks: {
                                display: false,
                                beginAtZero: true
                            },

                        }],
                        yAxes: [{
                            barThickness: 15,
                            gridLines: {
                                display: false,
                                drawBorder: false,
                            },
                        }]
                    },
                    plugins:{
                        datalabels: {
                                anchor: 'center',
                                font: {
                                    color:"white",
                                    weight: 'bold'
                                },
                            opacity:1,
                                formatter: function(value, context) {
                                    return value;
                                },
                            padding: {
                                left: 32
                            },
                            textAlign:"left"
                        }
                    },
                    layout: {
                    },
                }
            });

            setLocationRankingChart(chart);
        }
    },[chartData,isLoading])
    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>국내 지역별 통화 현황<DashBoardInfoTooltip text="번호설정에서 설정한 지역 정보 통계" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
            </div>
            <div className={cx("graph_area")}>
                <div style={{height:"50px",textAlign:"center"}}>
                    {isLoading ? <Loading/> : (
                        <canvas
                            hidden={locationRankingChart === null}
                            id="locationRankingChart"
                            height={"auto"}
                            ref={chartRef}
                        />
                    )}
                    {locationRankingChart === null && !isLoading && (
                        <p>시간대별 콜 통계를 추가하세요</p>
                    )}
                </div>
            </div>
        </>
    );
};

export default LocationRankingGraph;