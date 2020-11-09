import React, {useEffect,Fragment} from 'react';
import Moment from "react-moment";

const CustomerCaseViewContent = ({item,cx}) => {
    let quill;
    let Quill;
    let quillBetterTable;

    const isBrowser = process.env.APP_ENV === 'browser';


    useEffect( () => {
        if(isBrowser) {

            Quill = require('quill');
            quillBetterTable = require('quill-better-table');

            quill = new Quill('#viewerSection', {
                readOnly:true
            });

            quill.clipboard.dangerouslyPasteHTML(0, item.content);
        }

    },[])

    return (
        <Fragment>
        <div className={cx("project_view_top")}>
            <h1> {item.project}</h1>
            <div className={cx("info","clfx")}>
                <div className={cx("client")}>
                    <h2>가사명</h2>
                    {item.clients}
                </div>
                <div className={cx("release")}>
                    <h2>RELEASE DATE</h2>
                    <Moment format="YYYY-MM-DD">{item.release_date}</Moment>
                </div>
            </div>
            <p className={cx("txt")}>
                {item.description}</p>
            {/*<div className={cx("btn_area")}>*/}
            {/*    <a href={`${item.link}`} target="_blank"><span>사이트 바로가기</span></a>*/}
            {/*</div>*/}
        </div>
            <div className={cx("view_cont")}>
                <div id="viewerSection"></div>
            </div>
        </Fragment>
    );
};

export default CustomerCaseViewContent;