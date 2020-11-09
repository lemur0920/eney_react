import React, {Fragment} from 'react';

const a = (e) => {
    console.log(e);
}

const MessageTemplateModal = ({cx, list, onSelect}) => {
    console.log(list);
    return (
        <Fragment>
            <div onClick={() => {onSelect(list)}} className={cx("dd")}>
                <div className={cx('response_message_area')}>
                    <div className={cx('response_message')}>
                        <div className={cx('top_area', 'clfx')}>
                            <span>{list.subject}</span>
                        </div>
                        <textarea name="" id="" cols="30" rows="10"
                                  value={list.text} readOnly={true}/>
                    </div>
                </div>
            </div>
        </Fragment>
    );
};

export default MessageTemplateModal;