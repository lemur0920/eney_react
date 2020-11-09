import React, {Fragment, useEffect, useState} from 'react';
import PaginationWithAllItem from "../../../util/PaginationWithAllItem";
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";

const AgentRankingTable = ({cx,chartDate}) => {

    const [items,setItems] = useState({
        allItem:[],
        pageOfItems:[]
    });

    useEffect(() => {
        if(chartDate.agentList.length > 0){
            setItems({
                ...items,
                allItem:chartDate.agentList
            })
        }

    },[chartDate])

    const onChangePage = (pageOfItems) => {
        setItems({
            ...items,
            pageOfItems:pageOfItems
        })

    }

    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>관리 가맹점 콜수를 기반한 Ranking<DashBoardInfoTooltip text="번호설정에서 설정한 가맹점 정보 통계" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
            </div>
            <div className={cx("graph_area")}>
                <div className={cx("tb_style_1")}>
                    <table className={cx("agent_ranking_tb")}>
                        <thead>
                        <tr>
                            <th scope="col">순위</th>
                            <th scope="col">가맹점</th>
                            <th scope="col">CALL</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            items.pageOfItems.map((item,index) => {
                                if(index > 14){
                                    return;
                                }

                                return <tr><td>{item.rownum}</td><td>{item.agent_name}</td><td>{item.count}</td></tr>

                            })
                        }

                        </tbody>
                    </table>
                    <PaginationWithAllItem items={items.allItem} onChangePage={(e) => {onChangePage(e)}} pageSize={1}/>
                </div>
            </div>
        </>
    );
};

export default AgentRankingTable;