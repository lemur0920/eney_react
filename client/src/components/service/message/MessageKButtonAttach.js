import React, {Component, Fragment, useState} from 'react';

import {getByte} from '../../../lib/validation/validation';
import KakaoAttachLinkUrl from "./KakaoAttachLinkUrl";
import Button from "../../common/Button";
import styled from "styled-components";
import KakaoAttachButton from "./KakaoAttachButton";
import ColoringItem from "../../admin/coloring/ColoringItem";
import TableBody from "@material-ui/core/TableBody";
import KakaoAttachLink from "./KakaoAttachLink";
import PatchcallManagementItem from "../patchcall/PatchcallManagementItem";
import KakaoAttachItem from "./KakaoAttachItem";

// 1: 배송조회(DS) : 택배 배송조회 시
// 2: 웹릿크(WL) : 모바일 또는 PC 웹릿크
// 3. 앱릿크(AL) : iOS 또는 Android 앱릿크
// 4. 봇키워드(BK) : 선택한 버튺이름을 젂달
// 5. 메시지젂달(MD) : 버튺이름 & 메시지내용 젂달
const k_button_type = [
    {value: 'DS', type: '배송조회'},
    {value: 'WL', type: '웹링크'},
    {value: 'AL', type: '앱링크'},
    {value: 'BK', type: '봇키워드'},
    {value: 'MD', type: '메세지전달'},
]

const CustomCancelButton = styled(Button)`
    padding: 10px;
    font-size: 0.875rem;
    margin: 8px;
    //background-color: white;
    background: #2442a0;
    color: white;
    border: none;
    font-weight: 500;
    line-height: 1.75;
    width: 170px;
    &:hover{
       background: #192a5f; color: white;
       }
`;


// const [type, setType] = useState(0);

const inp = e => {
    console.log(e.target.value);
}

const MessageKButtonAttach = ({cx, addAttach, handleAttach,changeValue,onRemove, list}) => {
    return (
        <Fragment>
            {list.map((item, index) => (
                <Fragment key={index}>
                    <div className={cx('section_type', 'bd_top')}>
                        <ul>
                            <KakaoAttachItem cx={cx} typeList={k_button_type} index={index} item={item} list={list} handleAttach={handleAttach} changeValue={changeValue} onRemove={onRemove}/>
                        </ul>
                    </div>
                </Fragment>
            ))}
            <div style={{'textAlign': 'center'}}>
                <CustomCancelButton type="button" className={cx("submit_btn")} onClick={addAttach}>버튼추가</CustomCancelButton>

            </div>
        </Fragment>


    );
}

export default MessageKButtonAttach;