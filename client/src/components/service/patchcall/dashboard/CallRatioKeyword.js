import React, {useEffect, useState} from 'react';
import PaginationWithAllItem from "../../../util/PaginationWithAllItem";
import DashBoardInfoTooltip from "../../../util/DashBoardInfoTooltip";

const CallRatioKeyword = ({cx,list}) => {
    const [items,setItems] = useState({
        allItem:[],
        pageOfItems:[]
    });

    useEffect(() => {
        if(list !== null && list.length > 0){
            setItems({
                ...items,
                allItem:list
            })
        }

    },[list])

    const onChangePage = (pageOfItems) => {
        setItems({
            ...items,
            pageOfItems:pageOfItems
        })

    }

    return (
        <>
            <div className={cx("po_r")}>
                <h1 className={cx("title_style_1")}>패치콜 키워드<DashBoardInfoTooltip text="콜 유입 키워드" style={{marginLeft:5,marginBottom:3,width:17}}/></h1>
            </div>
            <div className={cx("graph_area")}>
                <div className={cx("tb_style_1")}>
                    <table className={cx("agent_ranking_tb")}>
                        <thead>
                        <tr>
                            <th scope="col">순위</th>
                            <th scope="col">키워드</th>
                            <th scope="col">CALL</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            items.pageOfItems.map((item,index) => {
                                return <tr key={item.rownum}><td>{item.rownum}</td><td>{item.keyword}</td><td>{item.count}</td></tr>

                            })
                        }

                        </tbody>
                    </table>
                    <PaginationWithAllItem items={items.allItem} onChangePage={(e) => {onChangePage(e)}} pageSize={5} />
                </div>
            </div>
        </>
    );
};

export default CallRatioKeyword;