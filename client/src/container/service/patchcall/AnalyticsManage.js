import React, {Fragment, useEffect,useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import qs from "qs";
import {getViewIdList,editViewId,deleteViewId,addViewId, initialize} from "../../../modules/service/analytics";
import AnalyticsManageTable from "../../../components/service/patchcall/analytics_manage/AnalyticsManageTable";
import Loading from "../../../components/util/Loading";
import Pagination from "../../../components/util/Pagination";
import AnalyticsViewIdAddModal from "./AnalyticsViewIdAddModal";
import swal from 'sweetalert';
import PatchCallCidAdd from "./PatchCallCidAdd";

const AnalyticsManage = ({cx,location,history}) => {
    const dispatch = useDispatch();

    const [showAddModal, setShowAddModal] = useState(false)

    const {viewIdList,addResult,editResult,deleteResult, error, loading,addLoading,editLoading,deleteLoading} = useSelector(({analytics,loading}) =>({
        viewIdList: analytics.viewId,
        addResult: analytics.addResult,
        editResult: analytics.editResult,
        deleteResult: analytics.deleteResult,
        loading: loading['analytics/GET_VIEW_ID_LIST'],
        addLoading: loading['analytics/ADD_VIEW_ID'],
        editLoading: loading['analytics/EDIT_VIEW_ID'],
        deleteLoading: loading['analytics/DELETE_VIEW_ID'],
        error: analytics.error
    }))

    useEffect(() => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        dispatch(getViewIdList(page))

        return () => {
            dispatch(initialize());
        }
    },[])

    useEffect(() => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(!loading){
            dispatch(getViewIdList(page))
        }

    },[location.search,dispatch])

    useEffect(() => {
        if(!addLoading && addResult){
            const {page = 1} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            if(!loading){
                dispatch(getViewIdList(page))
                setShowAddModal(false)
            }
        }

    },[addResult,dispatch,addLoading])

    useEffect(() => {
        if(!editLoading && editResult){
            const {page = 1} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            if(!loading){
                dispatch(getViewIdList(page))
                swal("수정이 완료되었습니다");
            }
        }

    },[editResult,dispatch,editLoading])

    useEffect(() => {
        if(!deleteLoading && deleteResult){
            const {page = 1} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            if(!loading){
                dispatch(getViewIdList(page))
                swal("삭제가 완료되었습니다");
            }
        }

    },[deleteResult,dispatch,deleteLoading])

    const movePage = (page) => {
        history.push(`${location.pathname}?page=${page}`)
    }

    const onAddViewId = (data) => {
        dispatch(addViewId(data))
    }

    const onEditViewId = (data) => {
        swal({
            text: "수정하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(editViewId(data))
            }
        });
    }

    const onDeleteViewId = (data) => {

        swal({
            text: "삭제하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
            .then((willDelete) => {
                if (willDelete) {
                    dispatch(deleteViewId(data))
                }
            });
    }

    return (
        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>패치콜</li>
                    <li>애널리틱스 관리</li>
                </ul>
            </div>

            <div className={cx("box_cont","analytics_content")}>
                <div className="">
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>애널리틱스</span>관리</h1>
                    </div>
                    <div className={cx("tb_style_1")}>
                        <button className={cx("btn_add")} onClick={() => setShowAddModal(true)}><span>View ID 등록</span></button>
                        {viewIdList.list !== null && !loading ? (
                            <Fragment>
                                <AnalyticsManageTable list={viewIdList.list} cx={cx} onDelete={onDeleteViewId} onEdit={onEditViewId}/>
                                <Pagination
                                    totalRecords={viewIdList.page.totalCount}
                                    pageLimit={viewIdList.page.pageSize}
                                    pageNeighbours={1}
                                    currentPage={viewIdList.page.pageNo}
                                    onPageChanged={movePage}/>
                            </Fragment>
                        ) : <Loading/>}
                    </div>
                </div>
            </div>
            <AnalyticsViewIdAddModal cx={cx} open={showAddModal} onClose={() => setShowAddModal(!showAddModal)} loading={addLoading} result={addResult} error={error} onAddViewId={onAddViewId}/>
        </Fragment>
    );
};

export default withRouter(AnalyticsManage);