import React,{useEffect,useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom';
import {toggleSidebar} from "../../../modules/admin/layout";
import {
    AppBar,
    Toolbar,
    IconButton,
} from "@material-ui/core/index";
import ArrowBackIcon from "@material-ui/icons/ArrowBack";
import classNames from "classnames";

// styles
import useStyles from "./styles";

// // components
import { Badge, Typography } from "../wrappers/Wrappers";
// import Notification from "../Notification/Notification";
// import UserAvatar from "../UserAvatar/UserAvatar";

// context

// import {
//     useLayoutState,
//     useLayoutDispatch,
//     toggleSidebar,
// } from "../../context/LayoutContext";
// import { useUserDispatch, signOut } from "../../context/UserContext";

const messages = [
    {
        id: 0,
        variant: "warning",
        name: "Jane Hew",
        message: "Hey! How is it going?",
        time: "9:32",
    },
    {
        id: 1,
        variant: "success",
        name: "Lloyd Brown",
        message: "Check out my new Dashboard",
        time: "9:18",
    },
    {
        id: 2,
        variant: "primary",
        name: "Mark Winstein",
        message: "I want rearrange the appointment",
        time: "9:15",
    },
    {
        id: 3,
        variant: "secondary",
        name: "Liana Dutti",
        message: "Good news from sale department",
        time: "9:09",
    },
];

const notifications = [
    { id: 0, color: "warning", message: "Check out this awesome ticket" },
    {
        id: 1,
        color: "success",
        type: "info",
        message: "What is the best way to get ...",
    },
    {
        id: 2,
        color: "secondary",
        type: "notification",
        message: "This is just a simple notification",
    },
    {
        id: 3,
        color: "primary",
        type: "e-commerce",
        message: "12 new orders has arrived today",
    },
];

const AdminHeader = () => {
    const dispatch = useDispatch();

    const {isSidebarOpened} = useSelector(({layout}) =>({
        isSidebarOpened : layout.isSidebarOpened
    }))


    var classes = useStyles();

    // global
    // var layoutState = useLayoutState();
    // var layoutDispatch = useLayoutDispatch();
    // var userDispatch = useUserDispatch();

    // local
    var [mailMenu, setMailMenu] = useState(null);
    var [isMailsUnread, setIsMailsUnread] = useState(true);
    var [notificationsMenu, setNotificationsMenu] = useState(null);
    var [isNotificationsUnread, setIsNotificationsUnread] = useState(true);
    var [profileMenu, setProfileMenu] = useState(null);
    var [isSearchOpen, setSearchOpen] = useState(false);

    return (
        <AppBar position="fixed" className={classes.appBar}>
            <Toolbar className={classes.toolbar}>
                <IconButton
                    color="inherit"
                    onClick={() => dispatch(toggleSidebar())}
                    className={classNames(
                        classes.headerMenuButton,
                        classes.headerMenuButtonCollapse,
                    )}
                >
                    <ArrowBackIcon
                            classes={{
                                root: classNames(
                                    classes.headerIcon,
                                    classes.headerIconCollapse,
                                ),
                            }}
                        />
                    {/*{layoutState.isSidebarOpened ? (*/}
                    {/*    <ArrowBackIcon*/}
                    {/*        classes={{*/}
                    {/*            root: classNames(*/}
                    {/*                classes.headerIcon,*/}
                    {/*                classes.headerIconCollapse,*/}
                    {/*            ),*/}
                    {/*        }}*/}
                    {/*    />*/}
                    {/*) : (*/}
                    {/*    <MenuIcon*/}
                    {/*        classes={{*/}
                    {/*            root: classNames(*/}
                    {/*                classes.headerIcon,*/}
                    {/*                classes.headerIconCollapse,*/}
                    {/*            ),*/}
                    {/*        }}*/}
                    {/*    />*/}
                    {/*)}*/}
                </IconButton>
                <Typography variant="h6" weight="medium" className={classes.logotype}>
                    React Material Admin
                </Typography>
                <div className={classes.grow} />
                {/*<Menu*/}
                {/*    id="mail-menu"*/}
                {/*    open={Boolean(mailMenu)}*/}
                {/*    anchorEl={mailMenu}*/}
                {/*    onClose={() => setMailMenu(null)}*/}
                {/*    MenuListProps={{ className: classes.headerMenuList }}*/}
                {/*    className={classes.headerMenu}*/}
                {/*    classes={{ paper: classes.profileMenu }}*/}
                {/*    disableAutoFocusItem*/}
                {/*>*/}
                {/*    <div className={classes.profileMenuUser}>*/}
                {/*        <Typography variant="h4" weight="medium">*/}
                {/*            New Messages*/}
                {/*        </Typography>*/}
                {/*        <Typography*/}
                {/*            className={classes.profileMenuLink}*/}
                {/*            component="a"*/}
                {/*            color="secondary"*/}
                {/*        >*/}
                {/*            {messages.length} New Messages*/}
                {/*        </Typography>*/}
                {/*    </div>*/}
                {/*    {messages.map(message => (*/}
                {/*        <MenuItem key={message.id} className={classes.messageNotification}>*/}
                {/*            <div className={classes.messageNotificationSide}>*/}
                {/*                <UserAvatar color={message.variant} name={message.name} />*/}
                {/*                <Typography size="sm" color="text" colorBrightness="secondary">*/}
                {/*                    {message.time}*/}
                {/*                </Typography>*/}
                {/*            </div>*/}
                {/*            <div*/}
                {/*                className={classNames(*/}
                {/*                    classes.messageNotificationSide,*/}
                {/*                    classes.messageNotificationBodySide,*/}
                {/*                )}*/}
                {/*            >*/}
                {/*                <Typography weight="medium" gutterBottom>*/}
                {/*                    {message.name}*/}
                {/*                </Typography>*/}
                {/*                <Typography color="text" colorBrightness="secondary">*/}
                {/*                    {message.message}*/}
                {/*                </Typography>*/}
                {/*            </div>*/}
                {/*        </MenuItem>*/}
                {/*    ))}*/}
                {/*</Menu>*/}
            </Toolbar>
        </AppBar>
    );
}


export default withRouter(AdminHeader);