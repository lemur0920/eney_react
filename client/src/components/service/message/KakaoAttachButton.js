import React from 'react';
import styled from "styled-components";
import Button from "../../common/Button";


const CustomCancelButton = styled(Button)`
    padding: 6px 16px;
    font-size: 0.875rem;
    margin: 8px;
    background-color: white;
    color: #1a1a1a;
    border: 1px solid #949398;
    font-weight: 500;
    line-height: 1.75;
    &:hover{
       background: #f2f2f2; color: #1a1a1a;
       }
`;
const KakaoAttachButton = ({cx, typeList,item,changeValue,onRemove, linkType}) => {
    return (
        <table className={cx('kakao_btn')}>
            <colgroup>
                <col style={{width: "30%"}}/>
                <col style={{width: "50%"}}/>
                <col style={{width: "30%"}}/>

            </colgroup>
            <thead>
            <tr>
                <th>버튼타입<span className={cx('color-d-red')}>*</span></th>
                <th>버튼명</th>
                <th>삭제</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <select name="linkType" onChange={(e) => changeValue(item.key,e)} value={item.linkType}>
                        {typeList.map((item, index) => (
                            <option key={index} value={item.value}>{item.type}</option>
                        ))}
                    </select>
                </td>

                <td>
                    <input type="text" name="name" onChange={(e) => changeValue(item.key,e)}  value={item.name} placeholder={'버튼명'}/>
                </td>
                <td>
                    <CustomCancelButton type="button" className={cx("submit_btn")} onClick={()=> onRemove(item.key)}>삭제</CustomCancelButton>
                </td>
            </tr>
            </tbody>
        </table>
    );
};

export default KakaoAttachButton;