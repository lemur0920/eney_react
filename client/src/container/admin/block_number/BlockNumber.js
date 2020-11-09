import React, {Fragment, useEffect} from 'react';
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import Grid from "@material-ui/core/Grid";
import style from "../../../asset/style/admin/customer_case.module.css";
import styled from "styled-components";
import Button from "../../../components/common/Button";
import classnames from "classnames/bind";
import {useDispatch, useSelector} from "react-redux";
import {getBlockNumber} from "../../../modules/admin/block_number/block_number";
import BlockNumberTable from "../../../components/admin/block_number/BlockNumberTable";


const cx = classnames.bind(style);

const CustomerAddBtn = styled(Button)`
  float: right;
  margin: 30px;
`;

const BlockNumber = ({history, location, cx}) => {
    const dispatch = useDispatch();

    const {list, page, loading} = useSelector(({block_number, loading}) => ({
        list: block_number.blockNumber.list,
        page: block_number.blockNumber.page,
        loading: loading['adminBlockNumber/GET_BLOCK_NUMBER'],
    }))

    useEffect(() => {
        const data = {
            page: 1,
        }
        dispatch(getBlockNumber(data))

        if(!loading && list !== null){

        }
    }, [])


    return (
        <Fragment>
            <PageTitle title="080 차단번호 지정"/>
            <div className={cx("tb_style_1")}>
                <Grid container spacing={4}>
                    <Grid item xs={12}>
                        <form>
                            <CustomerAddBtn onClick={() => console.log('dd')} eney>080 차단번호 추가</CustomerAddBtn>
                        </form>
                        {list !== null && !loading && (
                            <Fragment>
                                <BlockNumberTable cx={cx} list={list}/>
                            </Fragment>

                        )}
                    </Grid>
                </Grid>
            </div>

        </Fragment>
    );
};

export default BlockNumber;