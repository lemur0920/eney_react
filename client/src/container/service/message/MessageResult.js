import React, {Fragment, useEffect, useState} from 'react';
import Pagination from "../../../components/util/Pagination";
import Loading from "../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import qs from "qs";
import {withRouter} from 'react-router-dom';
import {formatDateYYMMDD} from "../../../lib/util/formatter";


import MessageTemplateTable from "../../../components/service/message/MessageTemplateTable";
import {getResultList} from "../../../modules/service/message_result";
import Flatpickr from "react-flatpickr";
import MessageResultSearchBox from "../../../components/service/message/MessageResultSearchBox";
import MessageResultTable from "../../../components/service/message/MessageResultTable";
import {getSenderProfileList} from "../../../modules/service/kakao";
// import {getCidList} from "../../../modules/service/patchcall";

const MessageResult = ({cx, location, history}) => {
    const dispatch = useDispatch();

    const {resultList, resultTableList, resultSearch, loading} = useSelector(({message_result, loading}) => ({
        resultList: message_result.resultList,
        resultSearch: message_result.resultSearch,

        loading: loading['message/GET_RESULT_LIST'],
    }));


    let beginDate;
    let currentDate;

    const [date, setDate] = useState(null);
    const [initDate, setInitDate] = useState(false);

    const handleFickr = (e) => {
        setDate(e);
    }

    const handleSearch = (e) => {
        let data = {
            page: 1,
            year: resultSearch.year,
            month: resultSearch.month,
        }

        if (!(date == null || date.length == 0)) {

            if (date.length == 2) {
                data.startYear = date[0].getFullYear();
                data.startMonth = date[0].getMonth() + 1;
                data.startDay = date[0].getDate();
                data.endYear = date[1].getFullYear();
                data.endMonth = date[1].getMonth() + 1;
                data.endDay = date[1].getDate();

                history.push(`${location.pathname}?page=${1}&sy=${data.startYear}&sm=${data.startMonth}&sd=${data.startDay}&ey=${data.endYear}&em=${data.endMonth}&ed=${data.endDay}`)

            }


        }
        dispatch(getResultList(data));


    }
    useEffect(() => {


        let pickrDate = new Date();
        const {page = 1, sy = null, sm = null, sd = null, ey = null, em = null, ed = null} = qs.parse(location.search, {
            ignoreQueryPrefix: true,
        });

        if (sy != null) {
            resultSearch.beginDate = new Date(sy, sm - 1, sd);
            resultSearch.currentDate = new Date(ey, em - 1, ed);
        } else if (sy == null) {
            let _beginDate = new Date(pickrDate.getFullYear(), pickrDate.getMonth(), 1);
            let _currentDate = new Date(pickrDate.getFullYear(), pickrDate.getMonth(), pickrDate.getDate());
            resultSearch.beginDate = _beginDate;
            resultSearch.currentDate = _currentDate;

        }
        setInitDate(true);

        let data = {
            page: page,
        }
        data.startYear = resultSearch.beginDate.getFullYear();
        data.startMonth = resultSearch.beginDate.getMonth() + 1;
        data.startDay = resultSearch.beginDate.getDate();
        data.endYear = resultSearch.currentDate.getFullYear();
        data.endMonth = resultSearch.currentDate.getMonth() + 1;
        data.endDay = resultSearch.currentDate.getDate();

        dispatch(getResultList(data));

        // }, []);
    }, [dispatch, location.search]);


    const pageChange = page => {
        // history.push(`${location.pathname}?page=${page}`)
        const {sy = null, sm = null, sd = null, ey = null, em = null, ed = null} = qs.parse(location.search, {
            ignoreQueryPrefix: true,
        });
        history.push(`${location.pathname}?page=${page}&sy=${sy}&sm=${sm}&sd=${sd}&ey=${ey}&em=${em}&ed=${ed}`)


    };


    return (

        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>메세지</li>
                    <li>결과조회</li>
                </ul>
            </div>

            <div className={cx("box_cont", "patchcall_management_content")}>
                <div className="">
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>결과</span>조회</h1>
                    </div>

                    <div className={cx("tb_style_1")}>
                        <div>
                            {initDate == true ? (
                                <MessageResultSearchBox cx={cx} searchUse={true} handleFickr={handleFickr}
                                                        handleSearch={handleSearch} beginDate={resultSearch.beginDate}
                                                        currentDate={resultSearch.currentDate}
                                />
                            ) : 'ss'}


                        </div>

                        {resultList !== null && !loading ? (
                            <Fragment>
                                <MessageResultTable list={resultList.list} cx={cx}/>
                                <Pagination
                                    totalRecords={resultList.page.totalCount}
                                    pageLimit={resultList.page.pageSize}
                                    pageNeighbours={1}
                                    currentPage={resultList.page.pageNo}
                                    onPageChanged={pageChange}/>
                            </Fragment>
                        ) : <Loading/>}

                    </div>
                </div>
            </div>
        </Fragment>

    );
};

export default withRouter(MessageResult);