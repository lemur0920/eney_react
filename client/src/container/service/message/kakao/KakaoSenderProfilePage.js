import React, {Fragment, useCallback, useEffect, useState} from 'react';
import PatchcallManagementTable from "../../../../components/service/patchcall/PatchcallManagementTable";
import Pagination from "../../../../components/util/Pagination";
import Loading from "../../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from "react-router-dom";
import qs from "qs";
import styled from "styled-components";
import Button from "../../../../components/common/Button";
import MessageSendTextInput from "../../../../components/service/message/MessageSendTextInput";
import MessagePhone from "../../../../components/service/message/MessagePhone";
import MessageMsgTypeRadio from "../../../../components/service/message/MessageMsgTypeRadio";
import MessageKButtonAttach from "../../../../components/service/message/MessageKButtonAttach";
import MessageResultSearchBox from "../../../../components/service/message/MessageResultSearchBox";
import MessageResultTable from "../../../../components/service/message/MessageResultTable";
import PatchCallCidList from "../../../../components/service/patchcall/cid/PatchCallCidList";
import PatchCallCidAdd from "../../patchcall/PatchCallCidAdd";

import kakao, {
    getCategoryList,
    getKakaoAuthToken,
    createPlusFriend,
    initialize,
    getSenderProfileList, deletePlusFriend
} from "../../../../modules/service/kakao";

import PlusFriendAddModal from "../send/PlusFriendAddModal";
import MessageResultList from "../../../../components/service/message/MessageResultList";
import KakaoSenderProfileList from "../../../../components/service/message/KakaoSenderProfileList";
import swal from "sweetalert";
import {getServiceApplyList} from "../../../../modules/admin/service_apply/service_apply_manage";

const CustomCancelButton = styled(Button)`
    padding: 6px 16px;
    font-size: 0.875rem;
    margin: 8px;
    background-color: white;
    color:#37afe5;
    border:1px solid #37afe5;
    font-weight: 500;
    line-height: 1.75;
    &:hover{
       background: none;
       }
`;

const KakaoSenderProfilePage = ({cx, history}) => {


    const dispatch = useDispatch();
    const [isOpen, setIsOpen] = useState(false);
    const [showPlusFriendModal, setShowPlusFriendModal] = useState(false);

    const [yellowId, setYellowId] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [categoryCode, setCategoryCode] = useState('#');
    const [token, setToken] = useState('');

    const {category_list, code_create_friend, code_auth, list, page, code_delete_profile, loading} = useSelector(({kakao, loading}) => ({
        category_list: kakao.category_list,
        code_create_friend: kakao.code_create_friend,
        list: kakao.sender_list.list,
        page: kakao.sender_list.page,
        code_delete_profile: kakao.code_delete_profile,
        code_auth: kakao.code_auth,
        loading: loading['kakao/GET_SENDER_PROFILE_LIST'],
    }));


    const getAuthToken = e => {
        console.log(yellowId);
        console.log(phoneNumber);
        let data = {
            yellowId: yellowId,
            phoneNumber: phoneNumber,
        }
        dispatch(getKakaoAuthToken(data));
    };

    const phoneNumberChange = e => {
        console.log(e.target.value);
        const re = /^[0-9\b]+$/;

        // if value is not blank, then test the regex

        if (e.target.value == '' || re.test(e.target.value)) {
            console.log('true')
            setPhoneNumber(e.target.value)
        }
    }

    const handleDeleteProfile = (senderKey) => {
        console.log("senderKey :: " + senderKey);
        swal(senderKey + "\r\n정말 삭제할까요?", {
            buttons: ["취소", true],
        }).then((value) => {
            if (value == true) {
                dispatch(deletePlusFriend(senderKey));
            }
        });
    }

    /*    function handleDeleteProfile(senderKey) {
            console.log("senderKey :: " + senderKey);

        }*/

    const createFriend = e => {
        let data = {
            yellowId: yellowId,
            phoneNumber: phoneNumber,
            categoryCode: categoryCode,
            token: token,
        }

        console.log(data);
        if (yellowId == '' || yellowId == null) {
            swal("카카오톡 채널ID를 입력해 주세요.");
            return;

        }
        if (phoneNumber == '' || phoneNumber == null) {
            swal("휴대폰번호를 입력해 주세요.");
            return;
        }
        if (categoryCode == '#' || categoryCode == null) {
            swal("카테고리를 선택해 주세요.");
            return;

        }
        if (token == '' || token == null) {
            swal("인증토큰을 입력해 주세요.");
            return;

        }
        dispatch(createPlusFriend(data));

    }

    useEffect(() => {
        dispatch(getCategoryList());

        return () => {
            dispatch(initialize());
        }

    }, []);


    // code_auth (인증번호요청)에 따른 message 출력
    useEffect(() => { // 인증번호요청, 등록요청 alert
        if (code_auth.code != '200' && code_auth.code != null) swal(code_auth.message)
    }, [code_auth]);

    // code_create_friend (생성성공)에 따른 message 출력
    useEffect(() => { // 인증번호요청, 등록요청 alert
        if (code_create_friend.code != '200' && code_create_friend.code != null) {
            swal(code_create_friend.message)
        } else if (code_create_friend.code == '200') {
            swal("발신프로필 등록에 성공하였습니다.").then(() => {
                history.go(0);
            });

        }
    }, [code_create_friend]);


    // code_delete_profile (삭제 결과값)에 따른 message 출력
    useEffect(() => {
        if (code_delete_profile.code != null) swal(code_delete_profile.message);
    }, [code_delete_profile]);


    useEffect(() => {
        if (!loading && list !== null) {
            dispatch(getSenderProfileList(1))
        }


    }, []);


    useEffect(() => {

        const {page = 1} = qs.parse(location.search, {
            ignoreQueryPrefix: true,
        });

        const data = {
            page: page,
        }

        dispatch(getSenderProfileList(data))
        // getSenderProfileList
        // getSenderProfileList
    }, [dispatch, location.search])


    const pageChange = page => {
        history.push(`${location.pathname}?page=${page}`)
    };


    /*useEffect(() => {
        console.log(templateEditResult);
        console.log(templateEditError);

        if (templateEditResult === true && templateEditError === null) {
            setIsOpen(true)
        }
    }, [templateEditResult]);*/

    return (

        <Fragment>
            <div className={cx("navi")}>
                <ul className={cx("clfx")}>
                    <li>Home</li>
                    <li>서비스</li>
                    <li>카카오 발신프로필</li>
                </ul>
            </div>
            <div className={cx("box_cont")}>
                <div className="">
                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}><span>카카오</span>발신프로필</h1>
                    </div>
                    <div className={cx("tb_style_1")}>
                        <Fragment>
                            <button className={cx("btn_add")} onClick={() => setShowPlusFriendModal(true)}>
                                <span>카카오 발신프로필 등록</span></button>


                            {list != null && !loading ?
                                <KakaoSenderProfileList list={list} deleteAction={handleDeleteProfile}
                                                        cx={cx}/> : <Loading/>}
                            {!loading && page !== null ? (
                                <Pagination
                                    totalRecords={page.totalCount}
                                    pageLimit={page.pageSize}
                                    pageNeighbours={1}
                                    currentPage={page.pageNo}
                                    onPageChanged={pageChange}/>
                            ) : ''}


                        </Fragment>

                    </div>

                </div>
            </div>
            <PlusFriendAddModal cx={cx} open={showPlusFriendModal}
                                onClose={() => setShowPlusFriendModal(!showPlusFriendModal)}
                                categoryList={category_list} getAuth={getAuthToken}
                                yellowId={(e) => setYellowId(e.target.value)}
                                phoneNumber={phoneNumberChange}
                                token={(e) => setToken(e.target.value)}
                                categoryCode={(e) => setCategoryCode(e.target.value)}
                                createFriend={createFriend}
                                phoneNumberState={phoneNumber}
            />

        </Fragment>


    );
};

export default withRouter(KakaoSenderProfilePage);