import React, {Fragment, useEffect,useState} from 'react';
import Loading from "../../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import qs from "qs";
import Pagination from "../../../../components/util/Pagination";
import {getCustomerGroupList,deleteCustomerGroup, initialize} from "../../../../modules/service/customer";
import CustomerGroupManagementTable from "../../../../components/service/customer/group/CustomerGroupManagementTable";
import CustomerGroupAddModal from "./CustomerGroupAddModal";
import CustomerGroupEditModal from "./CustomerGroupEditModal";
import CustomerGroupMemberManagementModal from "./CustomerGroupMemberManagementModal";
import swal from 'sweetalert';

const CustomerGroupManagement = ({cx,location,history}) => {
    const dispatch = useDispatch();

    const [showAddModal, setShowAddModal] = useState(false)
    const [showEditModal, setShowEditModal] = useState(false)
    const [showEditMemberModal, setShowEditMemberModal] = useState(false)
    const [editGroup, setEditGroup] = useState({
        idx:null,
        group_name:null
    })
    const [editGroupMember, setEditGroupMember] = useState(null)

    const {group,deleteResult,updateResult,loading,deleteLoading,updateLoading,deleteError,updateError, } = useSelector(({customer,loading}) =>({
        group: customer.group,
        addResult:customer.addResult,
        deleteResult:customer.deleteResult,
        updateResult:customer.updateResult,
        addError:customer.addError,
        deleteError:customer.deleteError,
        updateError:customer.updateError,
        loading: loading['customer/GET_CUSTOMER_GROUP_LIST'],
        addLoading: loading['customer/ADD_CUSTOMER_GROUP'],
        deleteLoading: loading['customer/DELETE_CUSTOMER_GROUP'],
        updateLoading: loading['customer/UPDATE_CUSTOMER_GROUP'],
    }))

    useEffect(() => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            page: page,
        }

        dispatch(getCustomerGroupList(data))

        return () => {
            dispatch(initialize('group'));
        }

    },[])

    useEffect(() => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            page: page
        }

        dispatch(getCustomerGroupList(data))

    },[location.search])

    useEffect(() => {
        if(!loading){
            window.scroll(0,0);
        }
    },[loading])

    useEffect(() => {

        if(!deleteLoading && deleteResult){
            swal("삭제가 완료되었습니다");

            const {page = 1} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            const data = {
                page: page
            }

            dispatch(getCustomerGroupList(data))
        }

        if(!deleteLoading && !deleteResult && deleteError !== null){
            swal("오류가 발생되었습니다");
        }

    },[deleteLoading, deleteResult,deleteError])


    const movePage = (page) => {

        history.push(`${location.pathname}?page=${page}`)

    }

    const handleDeleteCustomerGroup = (idx) => {
        swal({
            text: "그룹을 삭제하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
            .then((willDelete) => {
                if (willDelete) {
                    dispatch(deleteCustomerGroup(idx));
                }
            });
    }

    const handleUpdateCustomerGroup = (idx, group_name) => {
        setEditGroup({
            idx:idx,
            group_name:group_name
        })

        setShowEditModal(true);
    }

    const handleGroupMemberModal = (idx,name) => {

        setEditGroupMember({
            idx:idx,
            name:name
        })

        setShowEditMemberModal(true)
    }

    return (
        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>패치AI</li>
                    <li>그룹관리</li>
                </ul>
            </div>

            <div className={cx("box_cont")}>
                <div className={cx("customer_group_list")}>
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>그룹</span>관리</h1>
                    </div>
                    <div className={cx("txt_r","mb_20")}>
                        <button className={cx("btn_add")} onClick={() => setShowAddModal(true)}><span>추가하기</span></button>
                    </div>
                    <div className={cx("tb_style_1")}>
                        <span className={cx("guide_txt_01")}>
                        </span>
                        {group.list !== null && !loading ? (
                            <Fragment>
                                <CustomerGroupManagementTable cx={cx} list={group.list} handleDeleteCustomerGroup={handleDeleteCustomerGroup} handleUpdateCustomerGroup={handleUpdateCustomerGroup} handleGroupMemberModal={handleGroupMemberModal}/>
                                <Pagination
                                    totalRecords={group.page.totalCount}
                                    pageLimit={group.page.pageSize}
                                    pageNeighbours={1}
                                    currentPage={group.page.pageNo}
                                    onPageChanged={movePage}/>
                            </Fragment>
                        ) : <Loading/>}
                    </div>

                </div>
            </div>
            <CustomerGroupAddModal cx={cx} open={showAddModal} onClose={() => setShowAddModal(!showAddModal)} />
            <CustomerGroupEditModal cx={cx} open={showEditModal} onClose={() => setShowEditModal(!showEditModal)} editGroup={editGroup}/>
            {showEditMemberModal &&
            <CustomerGroupMemberManagementModal open={showEditMemberModal} onClose={ () => setShowEditMemberModal(false)} cx={cx} group_idx={editGroupMember.idx} group_name={editGroupMember.name}/>
            }
        </Fragment>
    );
};

export default withRouter(CustomerGroupManagement);