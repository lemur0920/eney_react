import React, {Fragment, useEffect,useState} from 'react';
import Pagination from "../../../components/util/Pagination";
import Loading from "../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import qs from "qs";
import {getCidList, initialize} from "../../../modules/service/patchcall";
import {withRouter} from 'react-router-dom';
import PatchCallCidTable from "../../../components/service/patchcall/cid/PatchCallCidTable";
import PatchCallCidAdd from "./PatchCallCidAdd";

const PatchCallCid = ({cx,location,history}) => {
    const dispatch = useDispatch();

    const [showCidModal, setShowCidModal] = useState(false)

    const {cid, loading, acsCheck, acsError} = useSelector(({patchcall,loading, acs}) =>({
        cid: patchcall.cid,
        acsCheck:acs.check,
        acsError:acs.error,
        loading: loading['patchcall/GET_CID_LIST'],
    }))

    useEffect(() => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        dispatch(getCidList(page))

        return () => {
            dispatch(initialize());
        }

    },[])

    useEffect(() => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(!loading){
            dispatch(getCidList(page))
        }

    },[location.search])

    useEffect(() => {
        if(!loading){
            window.scroll(0,0);
        }
    },[loading])

    const movePage = (page) => {
        history.push(`${location.pathname}?page=${page}`)
    }



    return (
        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>패치콜</li>
                    <li>발신번호리스트</li>
                </ul>
            </div>

            <div className={cx("box_cont")}>
                <div className="">
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>발신번호</span>리스트</h1>
                    </div>
                    <div className={cx("tb_style_1")}>
                        {cid.list !== null && !loading ? (
                            <Fragment>
                                <button className={cx("btn_add")} onClick={() => setShowCidModal(true)}><span>발신번호 등록</span></button>
                                <PatchCallCidTable list={cid.list} cx={cx}/>
                                <Pagination
                                    totalRecords={cid.page.totalCount}
                                    pageLimit={cid.page.pageSize}
                                    pageNeighbours={1}
                                    currentPage={cid.page.pageNo}
                                    onPageChanged={movePage}/>
                            </Fragment>
                        ) : <Loading/>}
                    </div>
                </div>
            </div>
            <PatchCallCidAdd cx={cx} open={showCidModal} onClose={() => {dispatch(initialize());setShowCidModal(!showCidModal);}}/>
        </Fragment>
    );
};

export default withRouter(PatchCallCid);