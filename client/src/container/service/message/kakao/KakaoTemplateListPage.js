import React, {Fragment, useCallback, useEffect, useState} from 'react';
import PatchcallManagementTable from "../../../../components/service/patchcall/PatchcallManagementTable";
import Pagination from "../../../../components/util/Pagination";
import Loading from "../../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from "react-router-dom";
import qs from "qs";
import styled from "styled-components";
import Button from "../../../../components/common/Button";

import kakao, {
    getCategoryList,
    getKakaoAuthToken,
    createPlusFriend,
    initialize,
    getSenderProfileList, getKakaoTemplateList, deletePlusFriend, deleteKakaoTemplate, getKakaoTemplate
} from "../../../../modules/service/kakao";

import PlusFriendAddModal from "../send/PlusFriendAddModal";
import MessageResultList from "../../../../components/service/message/MessageResultList";
import KakaoSenderProfileList from "../../../../components/service/message/KakaoSenderProfileList";
import AlimtalkTemplateList from "../../../../components/service/message/AlimtalkTempateList";
import swal from "sweetalert";

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

const KakaoTemplateListPage = ({cx, history}) => {


        const dispatch = useDispatch();
        const [isOpen, setIsOpen] = useState(false);

        const [yellowId, setYellowId] = useState('');
        const [phoneNumber, setPhoneNumber] = useState('');

        const {create_friend, get_auth, list, page, code_delete_template, loading} = useSelector(({kakao, loading}) => ({
            create_friend: kakao.create_friend,
            code_delete_template: kakao.code_delete_template,
            sender_list: kakao.sender_list,
            list: kakao.template_list.list,
            page: kakao.template_list.page,
            get_auth: kakao.get_auth,
            loading: loading['kakao/GET_KAKAO_TEMPLATE_LIST'],
        }));

        const registAlimtalkTemplate = () => {
            console.log('dddd');
            // history.push(`${location.pathname}/detail?vno=${vno}`)
            history.push(`${location.pathname}/create`)

        }

        useEffect(() => { // 인증번호요청, 등록요청 alert
            if (create_friend != null) {
                alert(create_friend.message);
            }
            if (get_auth != null) alert(get_auth.message);

        }, [create_friend, get_auth]);


        /*useEffect(() => {
            dispatch(getKakaoTemplateList());

            return () => {
                dispatch(initialize());
            }

        }, []);*/


        useEffect(() => {
            if (!loading && list !== null) {
                console.log("here ");
                dispatch(getKakaoTemplateList(1))
            }


        }, []);


        useEffect(() => {

            const {page = 1} = qs.parse(location.search, {
                ignoreQueryPrefix: true,
            });

            const data = {
                page: page,
            }

            dispatch(getKakaoTemplateList(data))
            // getSenderProfileList
            // getSenderProfileList
        }, [dispatch, location.search]);


        const pageChange = page => {
            history.push(`${location.pathname}?page=${page}`)
        };

        const handleDeleteProfile = (senderKey, templateCode) => {
            // {senderKey, templateCode}
            let data = {
                senderKey: senderKey,
                templateCode: templateCode,
            };
            console.log(data);
            swal(templateCode + "\r\n정말 삭제할까요?", {
                buttons: ["취소", true],
            }).then((value) => {
                if (value == true) {
                    dispatch(deleteKakaoTemplate(data));
                }
            });
        };

        useEffect(() => {
            //                                    {list !== null && page !== null && !loading ? (

            console.log("::::::::::::");
            console.log(list);
            console.log(page);
            console.log(loading);
        })

        return (

            <Fragment>
                <div className={cx("navi")}>
                    <ul className={cx("clfx")}>
                        <li>Home</li>
                        <li>서비스</li>
                        <li>카카오 템플릿</li>
                    </ul>
                </div>
                <div className={cx("box_cont")}>
                    <div className="">
                        <div className={cx("title_area")}>
                            <h1 className={cx("title_style_2")}><span>카카오</span>알림톡 템플릿</h1>
                        </div>
                        <div className={cx("tb_style_1")}>
                            <Fragment>
                                <button className={cx("btn_add")}
                                        onClick={() => registAlimtalkTemplate()}>

                                    <span>알림톡 템플릿 등록</span></button>
                                {/*{customer.list !== null && !loading ? (*/}
                                {loading}
                                {/*{customer.list !== null && !loading ? (*/}

                                {list !== null && page !== null &&!loading ? (
                                    <Fragment>


                                        <AlimtalkTemplateList list={list} cx={cx}
                                                              deleteAction={handleDeleteProfile}
                                                              history={history}
                                        />
                                        <Pagination
                                            totalRecords={page.totalCount}
                                            pageLimit={page.pageSize}
                                            pageNeighbours={1}
                                            currentPage={page.pageNo}
                                            onPageChanged={pageChange}/>
                                    </Fragment>
                                ) : <Loading/>}

                            </Fragment>

                        </div>

                    </div>
                </div>

            </Fragment>


        );
    }
;

export default withRouter(KakaoTemplateListPage);