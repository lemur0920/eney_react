import React, {Component, useEffect, useState} from 'react';
import { withRouter} from "react-router-dom";
import properties from "../../properties";
import ChannelService from '../../lib/channel/ChannelService';
import qs from "qs";
import {userList} from "../../modules/admin/users";

// const ChannelTalk = ({location}) => {

class ChannelTalk extends Component{

    constructor(props) {
        super(props)
    }


    componentDidMount(){

        const exclusionArray = [
            '/admin',
            '/admin/dashboard',
            '/admin/users',
        ]

        if(exclusionArray.indexOf(location.pathname) !== -1){
            ChannelService.hide();
            return false;
        }else{
            ChannelService.boot({
                "pluginKey": properties.CHANNEL_PLUGIN_KEY
            });
        }
    }

    // console.log("트랙")
    // const [load, setLoad] = useState(false);
    //
    // const exclusionArray = [
    //     '/admin',
    //     '/admin/dashboard',
    //     '/admin/users',
    // ]
    //
    // useEffect(() => {
    //
    //     if(exclusionArray.indexOf(location.pathname) !== -1){
    //         console.log("채널톡")
    //         ChannelService.hide();
    //         return false;
    //     }else{
    //         ChannelService.boot({
    //             "pluginKey": properties.CHANNEL_PLUGIN_KEY
    //         });
    //     }
    //
    // },[])

    // if(load){
    //     if(exclusionArray.indexOf(location.pathname) !== -1){
    //         console.log("채널톡")
    //         ChannelService.hide();
    //         return false;
    //     }else{
    //         ChannelService.boot({
    //             "pluginKey": properties.CHANNEL_PLUGIN_KEY
    //         });
    //     }
    // }

    // return (
    //     null
    // );
    render() {
        return(
            null
        )
    }
};

export default withRouter(ChannelTalk);