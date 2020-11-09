import React, {Fragment, useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {
    getInstanceList,
    instanceRestart,
    instanceStart,
    instanceStop
} from "../../../modules/admin/service_apply/cloud";
import Loading from "../../../components/util/Loading";
import CloudManagementTable from "../../../components/service/cloud/CloudManagementTable";
import swal from 'sweetalert';

const CloudManagement = ({cx}) => {
    const dispatch = useDispatch();

        const {list,loading,manage,startLoading,stopLoading,restartLoading} = useSelector(({cloud,loading}) =>({
            list: cloud.instance.list,
            error: cloud.error,
            startResult:cloud.startResult,
            stopResult:cloud.stopResult,
            restartResult:cloud.restartResult,
            startError:cloud.startError,
            stopError:cloud.stopError,
            manage:cloud.manage,
            loading: loading['adminCloud/GET_INSTANCE_LIST'],
            startLoading: loading['adminCloud/INSTANCE_START'],
            stopLoading: loading['adminCloud/INSTANCE_STOP'],
            restartLoading: loading['adminCloud/INSTANCE_RESTART'],
        }))

    useEffect(() => {

        dispatch(getInstanceList())

    },[])

    useEffect(() => {

        if(!startLoading && !stopLoading && !restartLoading){
            dispatch(getInstanceList())
        }

    },[manage,dispatch,startLoading,stopLoading,restartLoading])

    const handleInstanceStart = (id) => {
        swal({
            text: "인스턴스를 시작하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(instanceStart(id));
            }
        });
    }

    const handleInstanceStop = (id) => {
        swal({
            text: "인스턴스를 정지하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(instanceStop(id));
            }
        });
    }

    const handleInstanceReStart = (id) => {
        swal({
            text: "인스턴스를 재시작하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(instanceRestart(id));
            }
        });
    }

    return (
        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>CLOUD</li>
                    <li>클라우드 관리</li>
                </ul>
            </div>

            <div className={cx("box_cont","cloud_content")}>
                <div className="">
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>클라우드</span>관리</h1>
                    </div>
                    <div className={cx("tb_style_1")}>
                        {!loading  && list !== null&& (
                            <>
                                <button className={cx("btn_refresh")} onClick={() => dispatch(getInstanceList())}><span>새로고침</span></button>
                                <CloudManagementTable list={list} cx={cx} start={handleInstanceStart} stop={handleInstanceStop} restart={handleInstanceReStart}/>
                            </>
                        )}
                        {loading && <Loading/>}
                        {!loading && list === null && ("인스턴스가 존재하지않습니다")}
                    </div>
                </div>
            </div>
        </Fragment>
    );
};

export default CloudManagement;