import React, {useEffect,useCallback, Fragment} from 'react';
import NoticeTable from "../../../components/customerservice/notice/NoticeTable";
import {useDispatch, useSelector} from "react-redux";
import {boardList, faqBoardList} from "../../../modules/customerservice/customerservice";
import {withRouter} from 'react-router-dom';
import qs from "qs";
import Pagination from "../../../components/util/Pagination";
import {makeStyles} from "@material-ui/core";
import Button from "../../../components/common/Button";
import styled from "styled-components";
const CustomButton = styled(Button)`
  padding: 4px 5px;
  margin:2px;
  float:right;
  margin:10px 0 0 0;
`;



const Notice = ({location, history,match}) => {

    const dispatch = useDispatch();
    // const classes = useStyles();

    const {list, loading, boardPage, group, condition,user} = useSelector(({customerservice,loading,auth}) =>({
        list: customerservice.boardList,
        boardPage: customerservice.page,
        loading:loading['customerservice/BOARD_LIST'],
        group: customerservice.boardInfo.group,
        condition: customerservice.boardInfo.condition,
        user:auth.user
    }))



    /*useEffect(() => {

        const {type, page} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(loading || type ==="help"){
            return;
        }
        dispatch(boardList({type,page}))

        console.log("인잇")

        console.log(loading)

    },[])*/

    useEffect(() => {

        const {type, page} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(loading || type ==="help"){
            return;
        }

        dispatch(boardList({type,page}))

    },[dispatch, location.search])

    const PageChange = data => {
        const {type, page} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });
        history.push(`${match.url}?type=${type}&page=${data}`)
    };

    const moveWrite = useCallback(() => {
        const {type} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });
        history.push(`${match.url}/write?type=${type}`)
    },[location.search]);


    return (
        <Fragment>
        {!loading && list && boardPage && condition &&(
            <Fragment>
            <div className="notice_list">
                <NoticeTable boardList={list} group={group} condition={condition}/>
                {(user && user.role === "ADMIN") && (
                    <CustomButton eney onClick={moveWrite}>
                        글쓰기
                    </CustomButton>
                )}
            </div>
                <Pagination
                    totalRecords={boardPage.totalCount}
                    pageLimit={boardPage.pageSize}
                    pageNeighbours={1}
                    currentPage={boardPage.pageNo}
                    onPageChanged={PageChange}/>
            </Fragment>

    )}
        </Fragment>
    );
};

export default withRouter(Notice);