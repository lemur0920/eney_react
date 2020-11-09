import React,{useEffect} from 'react';
import { withRouter } from 'react-router-dom';
import qs from "qs";

const ScrollTop = ({children, location}) => {
    useEffect(() => {

        const {target} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(target !== "colorring_area"){
            window.scrollTo(0, 0)
        }

    },[location.pathname, location.search])
    return (
        children
    );
};

export default withRouter(ScrollTop);