import React, {useState} from 'react';
import KakaoAttachButton from "./KakaoAttachButton";
import KakaoAttachLinkUrl from "./KakaoAttachLinkUrl";

// const [linkType, setLinkType] = useState('1');


const KakaoAttachItem = ({cx, typeList, list, item, handleAttach, changeValue, index, onRemove}) => {
    // console.group('list')
    // console.log(list);
    // console.log(index);
    // console.groupEnd();
    return (

        <div>
            <KakaoAttachButton cx={cx} typeList={typeList} item={item} changeValue={changeValue} onRemove={onRemove}/>
            <KakaoAttachLinkUrl linkType={list[index].linkType} item={item} cx={cx} changeValue={changeValue}/>
        </div>
    );
};

export default KakaoAttachItem;