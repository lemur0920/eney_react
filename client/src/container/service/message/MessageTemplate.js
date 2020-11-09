import React, {Fragment, useEffect, useState} from 'react';
import Pagination from "../../../components/util/Pagination";
import Loading from "../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import qs from "qs";
import {deleteTemplate, getTemplateList, initialize} from "../../../modules/service/template";
import {withRouter} from 'react-router-dom';
import PatchCallCidTable from "../../../components/service/patchcall/cid/PatchCallCidTable";
import MessageTemplateTable from "../../../components/service/message/MessageTemplateTable";
import swal from "sweetalert";
// import {getCidList} from "../../../modules/service/patchcall";

const MessageTemplate = ({cx, location, history}) => {
    const dispatch = useDispatch();

    const {list, page, deleteResult, loading} = useSelector(({template, loading}) => ({
        list: template.templateList.list,
        page: template.templateList.page,
        deleteResult: template.deleteResult,
        loading: loading['template/GET_TEMPLATE_LIST'],
    }));

    useEffect(() => {

        const {page = 1} = qs.parse(location.search, {
            ignoreQueryPrefix: true,
        });
        dispatch(getTemplateList(page));

        return () => {
            dispatch(initialize());
        }
    }, []);

    useEffect(() => {
        const {page = 1} = qs.parse(location.search, {
            ignoreQueryPrefix: true,
        });

        if (!loading) {
            dispatch(getTemplateList(page))
        }

    }, [location.search]);

    useEffect(() => {
        if (deleteResult > 0) {
            swal("삭제되었습니다")
                .then((value) => {
                    const {page = 1} = qs.parse(location.search, {
                        ignoreQueryPrefix: true,
                    });

                    dispatch(getTemplateList(page));
                });
        }
    }, [deleteResult])

    const pageChange = page => {
        history.push(`${location.pathname}?page=${page}`)
    };

    const moveTemplateCreatePage = () => {
        history.push(`${location.pathname}/create`);
    };


    const handleDeleteTemplate = (idx) => {
        dispatch(deleteTemplate(idx));
    };

    return (
        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>메세지</li>
                    <li>문자 템플릿</li>
                </ul>
            </div>
            <div className={cx("box_cont")}>
                <div className="">
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>문자</span>템플릿</h1>
                    </div>
                    <div className={cx("tb_style_1")}>

                        {list !== null && page !== null && !loading ? (
                            <Fragment>
                                <button className={cx("btn_add")} onClick={moveTemplateCreatePage}>
                                    <span>템플릿 작성</span></button>
                                <MessageTemplateTable list={list}
                                                      handleDeleteTemplate={handleDeleteTemplate} cx={cx}/>
                                <Pagination
                                    totalRecords={page.totalCount}
                                    pageLimit={page.pageSize}
                                    pageNeighbours={1}
                                    currentPage={page.pageNo}
                                    onPageChanged={pageChange}/>
                            </Fragment>
                        ) : <Loading/>}

                    </div>
                </div>
            </div>
            {/*<PatchCallCidAdd cx={cx} open={showCidModal} onClose={() => setShowCidModal(!showCidModal)}/>*/}
        </Fragment>
    );
};

export default withRouter(MessageTemplate);