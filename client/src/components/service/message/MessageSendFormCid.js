import React, {useEffect} from 'react';
import Select from "@material-ui/core/Select";

const MessageSendFormCid = ({cx, cidList, onChange}) => {

    useEffect(() => {
        if(cidList.length > 0 ){
            const e = {
                target:{
                    name:"customerGroup",
                    value:cidList[0].callback
                }
            };
            onChange(e)
        }
    }, [cidList]);

    return (
        <div className={cx('section_type')}>
            <ul>
                <li>
                    <span className={cx('tit_message')}>발신번호</span>
                    <div className={cx('right_area_wrap')}>
                        {/*{cateList.map((item, index) => (*/}

                        <select name="callback_no" onChange={onChange}>
                            {cidList.map((item, index) => (
                                <option key={index} value={item.callback}>{item.callback}</option>
                            ))}
                        </select>


                        {/*<select value={cidList} name="callback_no">
                            {cidList.map((item,index) => (
                                <option key={index} value={item}>{item}</option>
                            ))}
                        </select>*/}
                    </div>
                </li>
                {/*<li>
                    <span className={cx('tit_message')}>080번호삽입</span>
                    <div className={cx('right_area_wrap')}>
                        <input type="text"/>
                        <div className={cx('right_area')}>
                            <input type="checkbox"/>
                        </div>
                    </div>
                </li>*/}
            </ul>
        </div>
    );
};

export default MessageSendFormCid;