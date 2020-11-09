import React,{useState} from 'react';
import { Link} from 'react-router-dom';

const CompanyIntroduceTab = ({page}) => {

    const [index, setIndex] = useState(0);


    return (
        <div className="company_lnb">
            <ul className="clfx">
                <li className={page === "company" ? "on" : ""} ><Link to="/introduce/company"><span><b>회사소개</b></span></Link></li>
                <li className={page === "history" ? "on" : ""} ><Link to="/introduce/history"><span><b>회사연혁</b></span></Link></li>
                <li className={page === "ci" ? "on" : ""}><Link to="/introduce/ci"><span><b>CI소개</b></span></Link></li>
                <li className={page === "map" ? "on" : ""}><Link to="/introduce/map"><span><b>오시는길</b></span></Link></li>
            </ul>
        </div>
    );
};

export default CompanyIntroduceTab;