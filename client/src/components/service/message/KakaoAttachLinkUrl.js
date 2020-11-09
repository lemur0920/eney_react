import React, {Fragment} from 'react';
import KakaoAttachLink from "./KakaoAttachLink";

const KakaoAttachLinkUrl = ({cx, linkType, item, changeValue}) => {
    console.log(linkType);
    return (

        <div className={cx('k_link')}>
            {
                (function () {
                    if (linkType == 'WL') return (<Fragment>
                        <KakaoAttachLink cx={cx} type={'Mobile'} isWebLink={true} required={true} name={'linkMo'} changeValue={changeValue} item={item}/>
                        <KakaoAttachLink cx={cx} type={'PC (선택)'} isWebLink={true} name={'linkPc'} changeValue={changeValue} item={item}/>
                    </Fragment>);
                    if (linkType == 'AL') return (<Fragment>
                        <KakaoAttachLink cx={cx} type={'Mobile'} isWebLink={true} name={'linkMo'} changeValue={changeValue} item={item}/>
                        <KakaoAttachLink cx={cx} type={'PC (선택)'} isWebLink={true} name={'linkPc'} changeValue={changeValue} item={item}/>
                        <KakaoAttachLink cx={cx} type={'iOS'} name={'linkIos'} changeValue={changeValue} item={item}/>
                        <KakaoAttachLink cx={cx} type={'Android'} name={'linkAnd'} changeValue={changeValue} item={item}/>
                        <div>※ Mobile, iOS, Android중 2개 이상 필수 입력 대상입니다.</div>
                    </Fragment>);
                })()
            }
        </div>
    );
};

export default KakaoAttachLinkUrl;