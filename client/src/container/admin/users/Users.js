import React,{useEffect,useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import {userList} from "../../../modules/admin/users";
import  Grid  from "@material-ui/core/Grid";
import MUIDataTable from "mui-datatables";
import qs from 'qs';

// components
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import Widget from "../../../components/admin/widget/Widget";

// data
import UsersTable from "../../../components/admin/users/UsersTable";
import {usePreloader} from "../../../lib/PreloaderContext";
import UserInfoModal from "./UserInfoModal";
import CustomerGroupMemberManagementModal from "../../service/customer/group/CustomerGroupMemberManagementModal";
import Pagination from "../../../components/util/Pagination";
import * as moment from "moment";
import PatchCallSearchBox from "../../../components/service/patchcall/PatchCallSearchBox";


const Users = ({location,history,cx}) => {

    const searchList = [
        {name:"선택",value:""},
        {name:"아이디",value:"userid"},
        {name:"이름",value:"name"},
        {name:"사업자등록번호",value:"corporate_number"},
        {name:"이메일",value:"email"},
        {name:"전화번호",value:"phone_number"},

    ]


    const dispatch = useDispatch();

    const [isInfoModal, setIsInfoModal] = useState(false);
    const [selectUser, setSelectUser] = useState(null);

    const {users, error, loading, pageInfo} = useSelector(({users,loading}) =>({
        users: users.users,
        error: users.error,
        loading: loading['users/USER_LIST'],
        pageInfo: users.pageInfo
    }))


    usePreloader(() => dispatch(userList({page})))

    useEffect(() => {

        if(loading){
            return;
        }

        const {page = 1,search_filed = null, search = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const data = {
            page: page,
            search_filed : search_filed,
            search : search,
        }

        dispatch(userList(data))

    },[dispatch,location.search])

    const handleInfoModal = (idx) =>{
        setSelectUser(idx)
        setIsInfoModal(true);

    }

    const movePage = (page) => {

        const {search_filed = null, search = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });
        history.push(`${location.pathname}?page=${page}${`${search_filed !== null ? (`&search_filed=${search_filed}`) : ""}`}${`${search !== null ?
            (`&search=${search}`) : ""}`}`)
    }


    const moveUserSearch = (searchForm) => {
        const {page = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        const {search_filed = null, search = null} = searchForm;

        history.push(`${location.pathname}?page=${page}${`${search_filed !== null && search_filed !== "" ? (`&search_filed=${search_filed}`) : ""}`}${`${search !== null  && search !== ""  ?
            (`&search=${search}`) : ""}`}`)

    }


    return (
        <>
            <PageTitle title="사용자" />
            <Grid container spacing={4}>
                <Grid item xs={12}>
                    {!loading && users.list !== null && (
                        <>
                            <PatchCallSearchBox className={cx("search_box")} cx={cx} onSearch={moveUserSearch} searchUse={true} dateUse={false} searchList={searchList} excelDownload={null}/>
                            <UsersTable users={users} loading={loading} handleInfoModal={handleInfoModal}/>
                            <Pagination
                                totalRecords={users.page.totalCount}
                                pageLimit={users.page.pageSize}
                                pageNeighbours={1}
                                currentPage={users.page.pageNo}
                                onPageChanged={movePage}/>
                                </>
                        )
                    }
                </Grid>
            </Grid>
            {isInfoModal &&
                <UserInfoModal cx={cx} userIdx={selectUser} open={isInfoModal} onClose={ () => setIsInfoModal(false)}/>
            }

        </>
    );
}

export default withRouter(Users);
