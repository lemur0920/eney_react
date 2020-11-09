import React, {useEffect,Fragment} from 'react';
import {withRouter} from 'react-router-dom';
import {useDispatch, useSelector} from "react-redux";
import {getCustomerCaseList,getCustomerCaseCate,subscribeCheck, deleteCustomerCase, initialize} from "../../modules/admin/customer_case/customer_case";
import CustomerCaseList from "../../components/customer_case/CustomerCaseList";
import CustomerCaseTab from "../../components/customer_case/CustomerCaseTab";
import qs from "qs";
import Pagination from "../../components/util/Pagination";
import swal from 'sweetalert';

const CustomerCase = ({cx,location,history}) => {
    const dispatch = useDispatch();

    const {cate = null} = qs.parse(location.search,{
        ignoreQueryPrefix:true,
    });

    const {customerCase,customerCaseCate,subscribeAuth,checkLoading,loading} = useSelector(({customer_case,loading}) =>({
        subscribeAuth:customer_case.subscribeCheck,
        customerCase: customer_case.customerCase,
        customerCaseCate: customer_case.customerCaseCate,
        deleteResult: customer_case.deleteResult,
        error: customer_case.error,
        loading: loading['customerCase/GET_CUSTOMER_CASE_LIST'],
        checkLoading: loading['customerCase/SUBSCRIBE_CHECK'],
    }))

    useEffect(() => {

        dispatch(subscribeCheck());

        return () => {
            dispatch(initialize());
        }

    },[])

    useEffect(() => {
        if(!checkLoading && subscribeAuth !== null && !subscribeAuth){
            swal("교육/컨설팅 아카데미 구독 신청을 해주세요1");
            history.goBack();
        }

        if(checkLoading !== undefined && !checkLoading && subscribeAuth){
            const {cate = null, page = 1} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            const data = {
                page: page,
                cate : cate
            }
            dispatch(getCustomerCaseCate())
            dispatch(getCustomerCaseList(data))
        }

    },[dispatch])


    useEffect(() => {

        const {cate = null, page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            page: page,
            cate : cate
        }
        dispatch(getCustomerCaseList(data))

    },[location.search])

    useEffect(() => {
        if(!loading){
            window.scroll(0,0);
        }

    },[loading])

    const movePage = (page) => {
        const {cate = ""} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        history.push(`${location.pathname}?cate=${cate}&page=${page}`)
    }

    const viewContent = (idx) =>{
        if(checkLoading !== undefined && !checkLoading && !subscribeAuth){
            alert("교육/컨설팅 아카데미 구독 신청을 해주세요")
        }else{
            history.push(`/customer_case/view?idx=${idx}`)
        }
    }

    return (
        <Fragment>
                <div className="title_cont">
                    <h1 className="title_style_5">교육/컨설팅 아카데미</h1>
                    <p className="txt_style_1">에네이클라우드 기반 웹프로젝트 구축을 위한 온라인 교육 및 컨설팅을 제공 합니다
                    </p>
                </div>

                <section className={cx("cont2")} >
                    <Fragment>
                        {customerCaseCate !== null && (
                            <div className={cx("tab")}>
                                <CustomerCaseTab tabList={customerCaseCate} cate={cate} cx={cx}/>
                            </div>
                        )}
                        {customerCase.list !== null && (
                            <div>
                            <CustomerCaseList list={customerCase.list} cx={cx} viewContent={viewContent}/>
                            </div>
                        )}
                        {!loading && customerCase.page !== null &&(
                            <div className={cx("pagination")}>
                            <Pagination
                                totalRecords={customerCase.page.totalCount}
                                pageLimit={customerCase.page.pageSize}
                                pageNeighbours={1}
                                currentPage={customerCase.page.pageNo}
                                onPageChanged={movePage}/>
                            </div>
                        )}
                    </Fragment>
                </section>

        </Fragment>
    );
};

export default withRouter(CustomerCase);