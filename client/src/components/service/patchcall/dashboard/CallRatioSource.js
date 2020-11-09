import React, {useEffect, useState} from 'react';
import Loading from "../../../util/Loading";
import Chart from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";
import * as randomColor from "../../../../lib/util/random_color";
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";

const CallRatioSource = ({cx,isLoading,chartData}) => {
    const chartRef = React.createRef();
    const [sourceChart, setSourceChart] = useState(null);

    useEffect(() => {


        if(!isLoading && chartData !== null && chartData.countList.length > 0){
            const returnVisitChartRef = chartRef.current.getContext("2d");

            const ranColor = randomColor.getRandomColor();

            let colorList = [];
            for (let i in chartData.countList) {
                colorList.push(randomColor.getRandomColor())
            }

            const chart = new Chart(returnVisitChartRef, {
                type: 'pie',
                data: {
                    datasets: [{
                        data: chartData.countList,
                        backgroundColor: colorList,
                        borderWidth: 0,
                        label: '',
                        datalabels: {
                            color: '#000000',
                            font:{
                                weight:'bold'
                            }
                        }
                    }],
                    labels: chartData.sourceList
                },
                fill : true,
                plugins: [ChartDataLabels],
                options: {
                    plugins:{
                        datalabels: {
                            formatter: function(value, context) {
                                return value + '%';
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
                    tooltips: {
                        enabled: true,
                        callbacks: {
                            label: function(tooltipItem, data) {
                                //get the concerned dataset
                                var dataset = data.datasets[tooltipItem.datasetIndex];
                                var currentValue = dataset.data[tooltipItem.index];
                                return currentValue + "%";
                            }
                        }
                    }
                }
            });

            setSourceChart(chart);
        }
    },[chartData,isLoading])

    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>패치콜 Source<DashBoardInfoTooltip text="콜 유입 채널" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
            </div>
            <div className={cx("graph_area")}>
                <div style={{height:"250px",textAlign:"center",lineHeight:"200px"}}>
                    {isLoading ? <Loading/> : (
                        <canvas
                            hidden={sourceChart === null}
                            id="returnVisitChart"
                            ref={chartRef}
                            height={250}
                        />
                    )}
                    {sourceChart === null && !isLoading && (
                        <p>데이터가 없습니다</p>
                    )}
                </div>
            </div>
        </>
    );
};

export default CallRatioSource;