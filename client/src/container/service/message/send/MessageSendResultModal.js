import React, {useEffect, useState, Fragment} from 'react';
import CustomModal from "../../../../components/common/CustomModal";
import {useDispatch, useSelector} from "react-redux";

import MessageTemplateModal from "../../../../components/service/message/MessageTemplateModal";
import {getTemplateList} from "../../../../modules/service/template";
import Loading from "../../../../components/util/Loading";
import CustomerGroupMemberTransfer from "../../../../components/service/customer/group/CustomerGroupMemberTransfer";

const MessageSendResultModal = ({open, onClose, cx, result, list, onSelect}) => {
    const title = "";
    const subTitle = "";

    const [show, setShow] = useState(false)

    const dispatch = useDispatch();


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <div className={cx("popup","group_member_popup")}>
                <div className={cx("popup-content")}>
                    <h1>발송결과</h1>
                    <div className={cx("inner")}>


                        {/*<div className={cx("tb_scroll")}>
                            <div className={cx("tb_style_2")}>
                                ㅇㅇㅇ
                            </div>
                        </div>*/}

                        <ul>
                            <li>차감 포인트 : {result.point}</li>
                            <li>성공 : {result.success}건</li>
                            <li>실패 : {result.failed}건</li>
                        </ul>
                        <div className={cx("popup_close")}>
                            <button onClick={() => onClose()}><span></span></button>
                        </div>
                    </div>
                </div>
            </div>
        </CustomModal>
    );
};

export default MessageSendResultModal;