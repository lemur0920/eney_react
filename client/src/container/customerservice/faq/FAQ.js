import React, {useEffect,Fragment} from 'react';
import NoticeTable from "../../../components/customerservice/notice/NoticeTable";
import {useDispatch, useSelector} from "react-redux";
import {faqBoardList} from "../../../modules/customerservice/customerservice";
import {withRouter} from 'react-router-dom';
import qs from "qs";
import Pagination from "../../../components/util/Pagination";
import FAQTable from "../../../components/customerservice/faq/FAQTable";
// import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core";
import Button from "../../../components/common/Button";
import styled from "styled-components";
const CustomButton = styled(Button)`
  padding: 4px 5px;
  margin:2px;
  float:right;
  margin:10px 0 0 0;
`;

const FAQ = ({location, history,match}) => {

    const dispatch = useDispatch();
    // const classes = useStyles();

    const {list, loading, boardPage, group, condition,user} = useSelector(({customerservice,loading,auth}) =>({
        list: customerservice.faqBoardList,
        boardPage: customerservice.faqPage,
        loading:loading['customerservice/FAQ_BOARD_LIST'],
        group: customerservice.boardInfo.group,
        condition: customerservice.boardInfo.condition,
        user:auth.user
    }))

    // useEffect(() => {
    //
    //     const {type, page} = qs.parse(location.search,{
    //         ignoreQueryPrefix:true,
    //     });
    //
    //     if(loading || type ==="notice"){
    //         return;
    //     }
    //     dispatch(faqBoardList({type,page}))
    //
    // },[])

    useEffect(() => {

        const {type, page} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(loading || type ==="notice"){
            return;
        }

        dispatch(faqBoardList({type,page}))

    },[dispatch, location.search])

    const PageChange = data => {
        const {type, page} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });
        history.push(`${location.pathname}?type=${type}&page=${data}`)
    };

    const moveWrite = () => {
        const {type} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });
        history.push(`${match.url}/write?type=${type}`)
    };


    return (
        <Fragment>
        {!loading && list && boardPage &&(
            <Fragment>
            <div className="notice_list">
                <FAQTable boardList={list} group={group} condition={condition}/>
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

export default withRouter(FAQ);