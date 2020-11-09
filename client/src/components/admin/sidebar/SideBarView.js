import React from 'react';
import {
    Drawer,
    IconButton,
    List,
    withStyles } from "@material-ui/core";

import HomeIcon from "@material-ui/icons/Home";
import NotificationsIcon from "@material-ui/icons/NotificationsNone";
import PeoPleIcon from "@material-ui/icons/People";
import UIElementsIcon from "@material-ui/icons/FilterNone";
import TableIcon from "@material-ui/icons/BorderAll";
import SupportIcon from "@material-ui/icons/QuestionAnswer";
import LibraryIcon from "@material-ui/icons/LibraryBooks";
import FAQIcon from "@material-ui/icons/HelpOutline";
import ArrowBackIcon from "@material-ui/icons/ArrowBack";


import classNames from 'classnames';

import SidebarLink from './components/SidebarLink/SidebarLinkContainer';
import Dot from './components/Dot';

const structure = [
    { id: 0, label: 'Dashboard', link: '/app/dashboard', icon: <HomeIcon /> },
    { id: 1, label: 'Users', link: '/admin/users', icon: <PeopleIcon /> },
    { id: 2, label: 'Tables', link: '/app/tables', icon: <TableIcon /> },
    { id: 3, label: 'Notifications', link: '/app/notifications', icon: <NotificationsIcon />},
    {
        id: 4,
        label: 'UI Elements',
        link: '/app/ui',
        icon: <UIElementsIcon />,
        children: [
            { label: 'Icons', link: '/app/ui/icons' },
            { label: 'Charts', link: '/app/ui/charts' },
            { label: 'Maps', link: '/app/ui/maps' },
        ],
    },
    { id: 5, type: 'divider' },
    { id: 6, type: 'title', label: 'HELP' },
    { id: 7, label: 'Library', link: '', icon: <LibraryIcon /> },
    { id: 8, label: 'Support', link: '', icon: <SupportIcon /> },
    { id: 9, label: 'FAQ', link: '', icon: <FAQIcon />},
    { id: 10, label: '쿠폰', link: '/admin/coupon', icon: <PeopleIcon /> },
    { id: 11, type: 'divider' },
    { id: 12, type: 'title', label: 'PROJECTS' },
    { id: 13, label: 'My recent', link: '', icon: <Dot size="large" color="secondary" /> },
    { id: 14, label: 'Starred', link: '', icon: <Dot size="large" color="primary" /> },
    { id: 15, label: 'Background', link: '', icon: <Dot size="large" color="secondary" /> },
];

const SidebarView = ({ classes, theme, toggleSidebar, isSidebarOpened, isPermanent, location }) => {
    return (
        <Drawer
            variant={isPermanent ? 'permanent' : 'temporary'}
            className={classNames(classes.drawer, {
                [classes.drawerOpen]: isSidebarOpened,
                [classes.drawerClose]: !isSidebarOpened,
            })}
            classes={{
                paper: classNames(classes.drawer, {
                    [classes.drawerOpen]: isSidebarOpened,
                    [classes.drawerClose]: !isSidebarOpened,
                }),
            }}
            open={isSidebarOpened}
        >
            <div className={classes.mobileBackButton}>
                <IconButton
                    onClick={toggleSidebar}
                >
                    <ArrowBackIcon classes={{ root: classNames(classes.headerIcon, classes.headerIconCollapse) }} />
                </IconButton>
            </div>
            <List className={classes.sidebarList}>
                {structure.map(link => <SidebarLink key={link.id} location={location} isSidebarOpened={isSidebarOpened} {...link} />)}
            </List>
        </Drawer>
    );
}

const drawerWidth = 240;

const styles = theme => ({
    menuButton: {
        marginLeft: 12,
        marginRight: 36,
    },
    hide: {
        display: 'none',
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap',
        top: theme.spacing.unit * 8,
        [theme.breakpoints.down("sm")]: {
            top: 0,
        }
    },
    drawerOpen: {
        width: drawerWidth,
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    drawerClose: {
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        overflowX: 'hidden',
        width: theme.spacing.unit * 7 + 40,
        [theme.breakpoints.down("sm")]: {
            width: drawerWidth,
        }
    },
    toolbar: {
        ...theme.mixins.toolbar,
        [theme.breakpoints.down("sm")]: {
            display: 'none',
        }
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3,
    },
    mobileBackButton: {
        marginTop: theme.spacing.unit * .5,
        marginLeft: theme.spacing.unit * 3,
        [theme.breakpoints.only("sm")]: {
            marginTop: theme.spacing.unit * .625,
        },
        [theme.breakpoints.up("md")]: {
            display: 'none',
        }
    }
});

export default withStyles(styles, { withTheme: true })(SidebarView);
