import React,{Fragment} from 'react';
import BoardView from "../../../container/customerservice/common/BoardView";
import Notice from "../../../container/customerservice/notice/Notice";
import BoardEdit from "../../../container/customerservice/common/BoardEdit";
import {Route, withRouter} from 'react-router-dom';
import FAQ from "../../../container/customerservice/faq/FAQ";
import Terms from "../../../container/customerservice/terms/Terms";

const TermsTemplate = ({children,id, type, match}) => {

    if(type === "notice"){
        return;
    }

    return (
        <Fragment>
            <div className="sub_title_area clfx mb_20">
                <div className="txt_area">
                    <h1 className="title_style_2">이용약관</h1>
                    <p className="txt_style_2">
                        에네이 클라우드 및 제반 서비스 이용과 관련하여 필요한 사항을 규정합니다.
                    </p>
                </div>
            </div>
            <Route exact path={match.url} render={()=>!id ?
                (<Terms/>) : (<BoardView/>)
            }/>
            <Route exact path={`${match.url}/:edit(edit)`}  render={()=> (<BoardEdit type="edit" />)
            } />
            <Route exact path={`${match.url}/:write(write)`}  render={()=> (<BoardEdit type="write"/>)
            } />
        </Fragment>
    );
};

export default withRouter(TermsTemplate);