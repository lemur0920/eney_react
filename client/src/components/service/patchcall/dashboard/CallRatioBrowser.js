import React, {useEffect, useState} from 'react';
import Loading from "../../../util/Loading";
import * as randomColor from "../../../../lib/util/random_color";
import Chart from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";

const CallRatioBrowser = ({cx,isLoading,chartData}) => {
    const chartRef = React.createRef();
    const [browserChart, setBrowserChart] = useState(null);

    useEffect(() => {

        if(!isLoading && chartData !== null && chartData.countList.length > 0){
            const browserChartRef = chartRef.current.getContext("2d");

            let colorList = [];
            for (let i in chartData.countList) {
                colorList.push(randomColor.getRandomColor())
            }

            const chart = new Chart(browserChartRef, {
                type: 'pie',
                data: {
                    datasets: [{
                        data: chartData.countList,
                        backgroundColor: colorList,
                        label: '',
                        borderWidth: 0,
                        datalabels: {
                            color: '#000000',
                            font:{
                                weight:'bold'
                            }
                        }
                    }],
                    labels: chartData.browserList
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

            setBrowserChart(chart);
        }
    },[chartData,isLoading])

    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>패치콜 Browser<DashBoardInfoTooltip text="콜 유입 브라우저" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
            </div>
            <div className={cx("graph_area")}>
                <div style={{height:"250px",textAlign:"center",lineHeight:"200px"}}>
                    {isLoading ? <Loading/> : (
                        <canvas
                            hidden={browserChart === null}
                            id="browserChart"
                            ref={chartRef}
                            height={250}
                        />
                    )}
                    {browserChart === null && !isLoading && (
                        <p>데이터가 없습니다</p>
                    )}
                </div>
            </div>
        </>
    );
};

export default CallRatioBrowser;