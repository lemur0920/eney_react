import React, {Fragment, useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {apiTokenList,genToken,initialize} from "../../../modules/user/mypage/mypage";
import {withRouter} from "react-router-dom";
import qs from "qs";
import Pagination from "../../../components/util/Pagination";
import ApiTokenTable from "../../../components/mypage/apiToken/ApiTokenTable";
import styled from "styled-components";
import Button from "../../../components/common/Button";
import TokenGenModal from "../../../components/mypage/apiToken/TokenGenModal";
import CouponRegisterModal from "../../../components/mypage/point/CouponRegisterModal";
import Loading from "../../../components/util/Loading";

const CustomButton = styled(Button)`
  padding: 4px 5px;
  float:right;
  margin: 20px;
  padding: 5px 10px;
`;


const ApiTokenManagement = ({location, history}) => {

    const dispatch = useDispatch();

    const [isShowGen, setIsShowGen] = useState(false);

    const {tokenList, apiTokenPage, listLoading,isSuccess,error} = useSelector(({mypage, loading}) => ({
        tokenList: mypage.apiTokenList,
        apiTokenPage: mypage.apiTokenPage,
        error: mypage.genToken.error,
        isSuccess: mypage.genToken.isSuccess,
        listLoading: loading['mypage/API_TOKEN_LIST'],

    }))

    // useEffect(() => {
    //     const {page = 1} = qs.parse(location.search, {
    //         ignoreQueryPrefix: true,
    //     });
    //
    //     if (listLoading) {
    //         return;
    //     }
    //
    //     // history.push(`${location.pathname}?page=1`)
    //
    // }, [])

    useEffect(() => {
        const {page} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if (listLoading) {
            return;
        }

        // console.log("토큰 화면")

        dispatch(apiTokenList({page}))

    },[location.search])

    // useEffect(() => {
    //
    //     const {page = 1} = qs.parse(location.search,{
    //         ignoreQueryPrefix:true,
    //     });
    //
    //     if(isSuccess){
    //         dispatch(apiTokenList({page}))
    //     }
    //
    // },[isSuccess])

    const onPageChanged = data => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const query = `?page=${data}`;
        history.push(`${location.pathname}${query}`)

    };

    const showGanModal = () =>{
        dispatch(initialize('genToken'));

        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        setIsShowGen(!isShowGen);

        if(isSuccess){

            dispatch(apiTokenList({page}))
        }

    }

    const onTokenGen = data => {
        dispatch(genToken(data))
    };

    return (
        <Fragment>
            {!listLoading && tokenList !== null ? (
                <Fragment>
                    <CustomButton eney onClick={showGanModal}>토큰키 생성</CustomButton>
                    {isShowGen && (
                        <TokenGenModal open={isShowGen} onClose={showGanModal} onSubmit={onTokenGen} isSuccess={isSuccess} error={error}/>
                    )}
                    <ApiTokenTable list={tokenList}/>
                    <Pagination
                        totalRecords={apiTokenPage.totalCount}
                        pageLimit={apiTokenPage.pageSize}
                        pageNeighbours={1}
                        currentPage={apiTokenPage.pageNo}
                        onPageChanged={onPageChanged}
                    />
                </Fragment>
            ):(
                <Loading/>
            )}
        </Fragment>
    )
};

export default withRouter(ApiTokenManagement);