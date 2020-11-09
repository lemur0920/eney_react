import React, {useEffect, useState, Fragment} from 'react';
import CustomModal from "../../../../components/common/CustomModal";
import {useDispatch, useSelector} from "react-redux";

import MessageTemplateModal from "../../../../components/service/message/MessageTemplateModal";
import {getTemplateList} from "../../../../modules/service/template";

const MessageTemplateListModal = ({open, onClose, cx, group_idx, group_name, list, onSelect}) => {
    const title = "";
    const subTitle = "";

    const [show, setShow] = useState(false)

    const dispatch = useDispatch();

    const {templateList} = useSelector(({template, loading}) => ({
        templateList: template.templateList,
        groupUpdateLoading: loading['customer/UPDATE_CUSTOMER_GROUP_MEMBER']
    }));

    useEffect(() => {
        dispatch(getTemplateList(1));
    }, []);


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <div className={cx("popup", "template_popup")}>
                <div className={cx("popup-content")}>
                    {/*<div className={cx('popup', 'template_popup', 'template_area')} id="template_popup">*/}
                    <h1>템플릿 가져오기</h1>
                    <div className={cx('inner')}>
                        <div className={cx("tb_scroll")}>
                            <div className={cx('answering_message_wrap')}>

                                <div className={cx('answering_message', 'clfx')}>


                                        {templateList.list != null ? (
                                            templateList.list.map((item, index) =>
                                                <MessageTemplateModal cx={cx} list={item} key={index} onSelect={onSelect}/>)
                                        ) : 'loading'}

                                </div>
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

export default MessageTemplateListModal;