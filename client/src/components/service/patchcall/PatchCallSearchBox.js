import React,{useState,useEffect,Fragment} from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import qs from "qs";
import * as moment from 'moment'
import {withRouter} from 'react-router-dom';
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Radio from "@material-ui/core/Radio";
import { Korean } from "flatpickr/dist/l10n/ko.js"
import Flatpickr from "react-flatpickr";
import "../../../asset/style/flatpickr.css";


const PatchCallSearchBox = ({cx,location,onSearch,searchUse,dateUse,searchList}) => {
    const option = {
        mode:"range",
        maxDate: "today",
        locale:Korean,
        dateFormat: "Y-m-d",
    }

    const [searchForm, setSearchFrom] = useState({
        search_filed:"",
        search:"",
        startDate:null,
        endDate:null
    })

    useEffect(() => {
        const {search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });


        setSearchFrom({
            ...searchForm,
            search_filed: search_filed !== null ? search_filed : "",
            search: search !== null ? search : "",
            startDate: startDate !== null ? moment(startDate, "yyyymmdd").format("yyyy-mm-mm") : null,
            endDate: endDate !== null ? moment(endDate, "yyyymmdd").format("yyyy-mm-mm") : null,
        })
    },[])

    const onChangeSearchValue = (e) => {
        const {name, value} = e.target
        setSearchFrom({
            ...searchForm,
            [name] : value
        })
    }

    const setDate = (e) => {
        setSearchFrom({
            ...searchForm,
            // startDate: moment(e[0]).format("YYYYMMDD"),
            // endDate: moment(e[1]).format("YYYYMMDD")
            startDate: e[0],
            endDate: e[1],
        })
    }

    return (
        <div className={cx("search_box")}>
            {searchUse && (
                <>
                <label htmlFor="search_filed">검색 조건</label>
                <select
                name='search_filed'
                color="primary"
                id="search_filed"
                className={cx("search_input")}
                onChange={(e) => onChangeSearchValue(e)}
                value={searchForm.search_filed}
                >
                    {searchList !== null && searchList !== undefined && (
                        searchList.map((item) => (
                                <option key={item.name} value={item.value}>{item.name}</option>
                            )
                        )
                    )}
                </select>
                <input className={cx("search_input")} onChange={(e) => onChangeSearchValue(e)} value={searchForm.search} type="text" name='search' color="primary"/>
                </>

            )}
                {dateUse && (
                    <Fragment>
                        <label htmlFor="startDate">검색 기간</label>
                        <Flatpickr options={option}
                                   onClose={(e)=>  {setDate(e)}}
                                   disabled={false}
                                   placeholder="날짜를 선택하세요"
                        />
                    </Fragment>
                )}
            <button onClick={() => {onSearch(searchForm)}}><img src={require('../../../asset/image/service/top_btn_search.gif')} alt="검색"/></button>
        </div>
    );
};

export default withRouter(PatchCallSearchBox);