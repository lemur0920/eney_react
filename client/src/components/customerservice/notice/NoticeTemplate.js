import React,{Fragment} from 'react';
import BoardView from "../../../container/customerservice/common/BoardView";
import {Route, withRouter,Switch} from 'react-router-dom';
import Notice from "../../../container/customerservice/notice/Notice";
import BoardEdit from "../../../container/customerservice/common/BoardEdit";

const NoticeTemplate = ({children, id, type,  match}) => {


    if(type === "help"){
        return;
    }

    return (
        <Fragment>
            <div className="sub_title_area clfx mb_20">
                <div className="txt_area">
                    <h1 className="title_style_2">공지사항 </h1>
                    <p className="txt_style_2">
                        ENEY의 공지 사항 및 신규 서비스에 관련된 소식을 전해 드립니다.
                    </p>
                </div>
            </div>
            <Route exact path={match.url} render={()=>!id ?
                (<Notice/>) : (<BoardView/>)
            }/>
            <Route exact path={`${match.url}/:edit(edit)`}  render={()=> (<BoardEdit type="edit" />)
            } />
            <Route exact path={`${match.url}/:write(write)`}  render={()=> (<BoardEdit type="write"/>)
            } />
        </Fragment>
    );
};

export default withRouter(NoticeTemplate);