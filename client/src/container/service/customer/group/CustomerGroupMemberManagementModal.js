import React, {useEffect,useState} from 'react';
import CustomerEventTable from "../../../../components/service/customer/CustomerEventTable";
import {getGroupByCustomerList,updateCustomerGroupMember, initialize} from "../../../../modules/service/customer";
import CustomModal from "../../../../components/common/CustomModal";
import {useDispatch, useSelector} from "react-redux";
import CustomerGroupMemberTransfer from "../../../../components/service/customer/group/CustomerGroupMemberTransfer";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import Checkbox from "@material-ui/core/Checkbox";
import ListItemText from "@material-ui/core/ListItemText";
import Loading from "../../../../components/util/Loading";

const CustomerGroupMemberManagementModal = ({open, onClose, cx, group_idx,group_name}) => {
    const title = "";
    const subTitle = "";

    const [show, setShow] = useState(false)

    const dispatch = useDispatch();

    const {usedGroup,unusedGroup,error,loading,groupUpdateResult,groupUpdateError,groupUpdateLoading} = useSelector(({customer,loading}) =>({
        usedGroup:customer.groupByList.usedGroup,
        unusedGroup:customer.groupByList.unusedGroup,
        error:customer.error,
        groupUpdateResult:customer.groupUpdateResult,
        groupUpdateError:customer.groupUpdateError,
        loading: loading['customer/GET_GROUP_BY_CUSTOMER_LIST'],
        groupUpdateLoading:loading['customer/UPDATE_CUSTOMER_GROUP_MEMBER']
    }))

    useEffect(() => {
        return () => {
            dispatch(initialize("groupByList"))
            dispatch(initialize("groupUpdateError"))
            dispatch(initialize("groupUpdateResult"))
            dispatch(initialize("groupUpdateLoading"))

        }

    },[])

    useEffect(() => {
        if(group_idx !== null && group_idx !== undefined){
            const data = {
                group_idx:group_idx
            }
            dispatch(getGroupByCustomerList(data))
        }

    },[group_idx])

    useEffect(()=> {
        if(usedGroup !== null && unusedGroup !== null){
            setShow(true)
        }
    },[usedGroup,unusedGroup])

    useEffect(()=> {
        if( !groupUpdateLoading && groupUpdateError === null && groupUpdateResult){
            alert("그룹 설정이 완료되었습니다")
            onClose();
        }
    },[groupUpdateError,groupUpdateResult, groupUpdateLoading])

    const handleSave = (useArray, unusedArray) => {
        let useCustomerIdxList = useArray.map((value) => {return value.idx})
        let unusedCustomerIdxList = unusedArray.map((value) => {return value.idx})

        const data = {
            group_idx : group_idx,
            use_customer_idx_list:useCustomerIdxList,
            unused_customer_idx_list:unusedCustomerIdxList
        }
        dispatch(updateCustomerGroupMember(data))
    }


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <div className={cx("popup","group_member_popup")}>
                <div className={cx("popup-content")}>
                    <h1>그룹 멤버 관리 ({group_name})</h1>
                    <div className={cx("inner")}>
                        <div className={cx("tb_scroll")}>
                            <div className={cx("tb_style_2")}>
                                {loading && <Loading/>}
                                {show &&(
                                    <CustomerGroupMemberTransfer cx={cx} usedGroup={usedGroup} unusedGroup={unusedGroup} onClose={onClose} handleSave={handleSave} loading={loading}/>
                                )}
                            </div>
                        </div>
                        <div className={cx("popup_close")}>
                            <button onClick={() => onClose()}><span></span></button>
                        </div>
                    </div>
                </div>
            </div>
        </CustomModal>
    );
};

export default CustomerGroupMemberManagementModal;