import React from 'react';

const KakaoAttachLink = ({cx, type, required, isWebLink,changeValue, item, name}) => {
    return (
        <div className={cx('kakao_button')}>
            <label htmlFor="">{type}<span className={cx('color-d-red')}>{required == true ? '*' : ''}</span></label>
            <input name={name} className="input input-link right" type="text" onChange={(e) => changeValue(item.key,e)} value={item[name]}
                   placeholder={isWebLink == true ? 'http:// 또는 https:// 를 포함해서 입력해주세요' : ''}></input>
        </div>
    );
};

export default KakaoAttachLink;