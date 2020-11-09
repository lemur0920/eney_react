import React, {useState, useEffect, Fragment} from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import qs from "qs";
import * as moment from 'moment'
import {withRouter} from 'react-router-dom';
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Radio from "@material-ui/core/Radio";
import {Korean} from "flatpickr/dist/l10n/ko.js"
import "../../../asset/style/flatpickr.css";
import Flatpickr from "react-flatpickr";
import {formatDateYYMMDD} from "../../../lib/util/formatter";


const MessageResultSearchBox = ({cx, location, handleSearch, handleFickr, beginDate, currentDate}) => {

    let pickrDate = new Date();
    let _beginDate = new Date(2020, 7, 1);
    let _currentDate = new Date(2020, 7, 10);

    console.group("날짜들어옴");
    console.log(beginDate);
    console.log(currentDate);

    console.log(_beginDate);
    console.log(_currentDate);
    console.groupEnd();

    const option = {
        mode: "range",
        maxDate: "today",
        minDate: new Date().setDate(-90),
        locale: Korean,
        dateFormat: "Y-m-d",
        defaultDate: [beginDate, currentDate],
        onReady: [
            function (e) {
                handleFickr(e)
            }
        ]
    }

    return (
        <div className={cx("search_box")}>

            <Flatpickr options={option}
                       onClose={(e) => {
                           handleFickr(e)
                       }}
                // onChange={(e) => {changeValue(e.target.name, e.target.checked ? "Y" : "N")}}

                       disabled={false}
                       placeholder="날짜를 선택하세요"
            />

            <button onClick={handleSearch}><img src={require('../../../asset/image/service/top_btn_search.gif')}
                                                alt="검색"/></button>
        </div>
    );
};

export default withRouter(MessageResultSearchBox);