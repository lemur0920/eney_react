import React,{useEffect} from 'react';
import SwaggerUI from 'swagger-ui-react'
import "swagger-ui-react/swagger-ui.css"
import * as clientLib from '../../../lib/api/client';
import classnames from "classnames/bind";
import style from "../../../pages/service/service.module.css";
import './test.css'

const cx = classnames.bind(style);

const Swagger = () => {


    return (
        <section className={cx("cont")}>
            <div className={cx("navi")}>
                <ul className={cx("clfx")}>
                    <li>개발자센터</li>
                    <li>API DOCUMENT</li>
                </ul>
            </div>

            <SwaggerUI url={`${clientLib.getUrl()}/v2/api-docs`}/>
        </section>
    );
};

export default Swagger;