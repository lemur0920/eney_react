import React, {useEffect, useState} from 'react';
import Loading from "../../../util/Loading";
import Chart from "chart.js";
import ChartDataLabels from 'chartjs-plugin-datalabels';
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";
Chart.plugins.unregister(ChartDataLabels);


const ReturnVisitGraph = ({cx,chartData,isLoading}) => {
    const chartRef = React.createRef();
    const [returnVisitChart, setReturnVisitChart] = useState(null);

    useEffect(() => {

        if(!isLoading && chartData.new_visit !== 0 && chartData.new_visit !== null){
            const returnVisitChartRef = chartRef.current.getContext("2d");

            let new_visit = chartData.new_visit;
            let revisit_visit = chartData.revisit_visit;
            let visit_total = new_visit+revisit_visit;

            const revisit_per = (revisit_visit/visit_total) * 100;
            const newvisit_per = 100 - revisit_per;

            const chart = new Chart(returnVisitChartRef, {
                type: 'pie',
                data: {
                    datasets: [{
                        data: [newvisit_per.toFixed(2),revisit_per.toFixed(2)],
                        backgroundColor: [
                            '#37464a','#f5c80f'],
                        label: '',
                        borderWidth: 0,
                        datalabels: {
                            color: '#000000',
                            font:{
                                weight:'bold'
                            }
                        }
                    }],
                    labels: [
                        '1회 방문율','재방문율'
                    ]
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

            setReturnVisitChart(chart);
        }
    },[chartData,isLoading])


    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>재방문율 통계<DashBoardInfoTooltip text="재방문율" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
            </div>
            <div className={cx("graph_area")}>
                <div style={{height:"200px",textAlign:"center",lineHeight:"200px"}}>
                    {isLoading ? <Loading/> : (
                        <canvas
                            hidden={returnVisitChart === null}
                            id="returnVisitChart"
                            ref={chartRef}
                            height={200}
                        />
                    )}
                    {returnVisitChart === null && !isLoading && (
                        <p>시간대별 콜 통계를 추가하세요</p>
                    )}
                </div>
            </div>
        </>
    );
};

export default ReturnVisitGraph;