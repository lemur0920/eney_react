import React, { useState, useEffect } from "react";
import { Drawer, IconButton, List } from "@material-ui/core";

import HomeIcon from "@material-ui/icons/Home";
import NotificationsIcon from "@material-ui/icons/NotificationsNone";
import PeoPleIcon from "@material-ui/icons/People";
import UIElementsIcon from "@material-ui/icons/FilterNone";
import TableIcon from "@material-ui/icons/BorderAll";
import SupportIcon from "@material-ui/icons/QuestionAnswer";
import LibraryIcon from "@material-ui/icons/LibraryBooks";
import FAQIcon from "@material-ui/icons/HelpOutline";
import ArrowBackIcon from "@material-ui/icons/ArrowBack";
import Block from "@material-ui/icons/Block";



import { useTheme } from "@material-ui/styles";
import { withRouter } from "react-router-dom";
import classNames from "classnames";

// styles
import useStyles from "./styles";

// components
import SidebarLink from "./components/sidebarLink/SidebarLink";
import Dot from "./components/Dot";
import {useSelector} from "react-redux";
import {CodeBlock} from "quill/modules/syntax";

// context
// import {
//     useLayoutState,
//     useLayoutDispatch,
//     toggleSidebar,
// } from "../../context/LayoutContext";

const structure = [
    { id: 0, label: "Dashboard", link: "/app/dashboard", icon: <HomeIcon /> },
    {
        id: 1,
        label: "Users",
        link: "/admin/users",
        icon: <PeoPleIcon />,
    },
    { id: 2, label: "Tables", link: "/app/tables", icon: <TableIcon /> },
    {
        id: 3,
        label: "Notifications",
        link: "/app/notifications",
        icon: <NotificationsIcon />,
    },
    {
        id: 4,
        label: "UI Elements",
        link: "/app/ui",
        icon: <UIElementsIcon />,
        children: [
            { label: "Icons", link: "/app/ui/icons" },
            { label: "Charts", link: "/app/ui/charts" },
            { label: "Maps", link: "/app/ui/maps" },
        ],
    },
    { id: 5, label: "콜로그 조회", link: "/admin/call_log", icon: <LibraryIcon /> },
    { id: 6, label: "패치콜 신청", link: "/admin/patch_call", icon: <LibraryIcon /> },
    { id: 7, label: "패치 Intelligence 신청", link: "/admin/patch_intelligence", icon: <LibraryIcon /> },
    { id: 8, label: "3rd Part 신청", link: "/admin/third_part", icon: <LibraryIcon /> },
    { id: 9, label: "클라우드 신청", link: "/admin/cloud", icon: <LibraryIcon /> },
    { id: 10, label: "컬러링 신청", link: "/admin/coloring", icon: <LibraryIcon /> },
    { id: 11, label: "파일 업로드", link: "/admin/sound_upload", icon: <LibraryIcon /> },
    { id: 12, label: "교육/컨설팅 관리", link: "/admin/customer_case", icon: <SupportIcon /> },
    { id: 13, label: "카운트 수정", link: "/admin/customUserCount", icon: <FAQIcon /> },
    { id: 14, label: "쿠폰", link: "/admin/coupon", icon: <PeoPleIcon />},
    { id: 15, label: "080 차단번호 지정", link: "/admin/block-number", icon: <Block /> },
];

function Sidebar({ location }) {
    var classes = useStyles();
    var theme = useTheme();

    const {isSidebarOpened} = useSelector(({layout}) =>({
        isSidebarOpened : layout.isSidebarOpened
    }))


    // global
    // var { isSidebarOpened } = useLayoutState();
    // var layoutDispatch = useLayoutDispatch();

    // local
    const [isPermanent, setPermanent] = useState(true);

    useEffect(function() {
        window.addEventListener("resize", handleWindowWidthChange);
        handleWindowWidthChange();
        return function cleanup() {
            window.removeEventListener("resize", handleWindowWidthChange);
        };
    });

    return (
        <Drawer
            variant={isPermanent ? "permanent" : "temporary"}
            className={classNames(classes.drawer, {
                [classes.drawerOpen]: isSidebarOpened,
                [classes.drawerClose]: !isSidebarOpened,
            })}
            classes={{
                paper: classNames({
                    [classes.drawerOpen]: isSidebarOpened,
                    [classes.drawerClose]: !isSidebarOpened,
                }),
            }}
            open={isSidebarOpened}
        >
            <div className={classes.toolbar} />
            <div className={classes.mobileBackButton}>
                <IconButton >
                    <ArrowBackIcon
                        classes={{
                            root: classNames(classes.headerIcon, classes.headerIconCollapse),
                        }}
                    />
                </IconButton>
            </div>
            <List className={classes.sidebarList}>
                {structure.map(link => (
                    <SidebarLink
                        key={link.id}
                        location={location}
                        isSidebarOpened={isSidebarOpened}
                        {...link}
                    />
                ))}
            </List>
        </Drawer>
    );

    // ##################################################################
    function handleWindowWidthChange() {
        var windowWidth = window.innerWidth;
        var breakpointWidth = theme.breakpoints.values.md;
        var isSmallScreen = windowWidth < breakpointWidth;

        if (isSmallScreen && isPermanent) {
            setPermanent(false);
        } else if (!isSmallScreen && !isPermanent) {
            setPermanent(true);
        }
    }
}

export default withRouter(Sidebar);
