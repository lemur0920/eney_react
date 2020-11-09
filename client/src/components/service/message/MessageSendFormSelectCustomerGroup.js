import React, {Fragment, useEffect, useState} from 'react';
import Select from "@material-ui/core/Select";

const MessageSendFormSelectCustomerGroup = ({cx, list, groupCount, onChange}) => {

    useEffect(() => {
        console.log(list);
        console.log(groupCount);
    }, []);

    useEffect(() => {
        if (list.length > 0) {
            const e = {
                target: {
                    name: "customerGroup",
                    value: list[0].idx
                }
            };
            onChange(e)
        }
    }, [list]);

    return (
        <Fragment>

            <div>
                <h3 className={cx('tit_end')}>메세지 타겟 설정</h3>
                <div className={cx('info_setup')}>
                    <div className={cx('info_bundle')}>
                        <strong className={cx('tit_bundle')}>선택된 그룹</strong>
                        <div className={cx('cont_bundle')}>

                            <select name="customerGroup" onChange={onChange} defaultValue={0}>
                                {list.map((item, index) => (
                                    <option key={index} value={item.idx}>{item.group_name}</option>
                                ))}
                            </select>

                        </div>
                    </div>
                    <p className={cx('txt_total')}>총 예상 발송 대상&nbsp;<span
                        className={cx('emph_g')}><span>{groupCount}</span>명</span>
                    </p>
                    {groupCount == 0 ? (<p className={cx('desc_warn')}>메시지 발송 대상이 0명이어서 메시지를 발송할 수 없습니다.</p>) : ''}

                </div>
            </div>
        </Fragment>
    );
};

export default MessageSendFormSelectCustomerGroup;