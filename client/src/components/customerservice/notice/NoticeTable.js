import React from 'react';
import NoticeList from "./NoticeList";

const NoticeTable = ({boardList,group, condition}) => {
    return (
        <table>
            <colgroup>
                <col style={{width:"10%"}}/>
                <col style={{width:"60%"}}/>
                <col style={{width:"10%"}}/>
                <col style={{width:"10%"}}/>
                <col style={{width:"10%"}}/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col">번호</th>
                <th scope="col">제목</th>
                <th scope="col" align="center">분류</th>
                <th scope="col" align="center">상태</th>
                <th scope="col" align="center">등록일</th>
            </tr>
            </thead>
            <NoticeList boardList={boardList} group={group} condition={condition}/>
            {/*<tbody>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner complete">완료</span>*/}
            {/*        완료*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner ing">진행</span>*/}
            {/*        진행중*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner complete">완료</span>*/}
            {/*        완료*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner ing">진행</span>*/}
            {/*        진행중*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner complete">완료</span>*/}
            {/*        완료*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner ing">진행</span>*/}
            {/*        진행중*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner complete">완료</span>*/}
            {/*        완료*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner ing">진행</span>*/}
            {/*        진행중*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner complete">완료</span>*/}
            {/*        완료*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*<tr>*/}
            {/*    <td>10</td>*/}
            {/*    <td className="txt_l"><a href="#">금융보안원의 '금융 클라우드 서비스 가이드 라인'을 준수하여 안정적인...</a></td>*/}
            {/*    <td>모바일 운영</td>*/}
            {/*    <td className="banner_line">*/}
            {/*        <span className="banner ing">진행</span>*/}
            {/*        진행중*/}
            {/*    </td>*/}
            {/*    <td>2019.10.08</td>*/}
            {/*</tr>*/}
            {/*</tbody>*/}
        </table>
    );
};

export default NoticeTable;