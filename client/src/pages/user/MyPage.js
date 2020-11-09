import React,{useEffect,memo} from 'react';
import PropTypes from 'prop-types';
import {withRouter} from 'react-router-dom';
import SwipeableViews from 'react-swipeable-views';
import {makeStyles, useTheme, withStyles} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import MyInfo from "../../container/user/mypage/MyInfo";
import palette from "../../lib/styles/palette";
import ServiceInfo from "../../container/user/mypage/ServiceInfo";
import PointInfo from "../../container/user/mypage/PointInfo";
import TabPanel from "../../components/common/TabPanel";
import {useSelector} from "react-redux";
import IamPortScript from "../../components/util/IamPortScript";
import ApiTokenManagement from "../../container/user/mypage/ApiTokenManagement";

function a11yProps(index) {
    return {
        id: `full-width-tab-${index}`,
        'aria-controls': `full-width-tabpanel-${index}`,
    };
}

const useStyles = makeStyles(theme => ({
    root: {
        backgroundColor: theme.palette.background.paper,
        maxWidth: 1140,
        margin: "0 auto",
        // flexGrow: 1,"auth"
    },
    appBar: {
        backgroundColor: "white",
        boxShadow: "none",
        marginTop: 10,
        // maxWidth:640,
        margin: "0 auto",
    },
    subTitle: {
        textAlign: "center",
        fontSize: 24,
        padding: 40
    },
    indicator: {
        height: 3,
        backgroundColor: palette.cyan[0],
        transition: "none",
        display: "flex",
        justifyContent: "center",
        // backgroundColor: "transparent",
        "& > div": {
            maxWidth: 40,
            width: 40,
            backgroundColor: "#635ee7"
        }
    },
    labelColor: {
        color: palette.cyan[0],
    },
    label: {
        fontSize: 15,
        fontWeight: 400,
        color: "#626262",
        opacity: 1,
        border: "1px solid #ced0da",
        "&:nth-child(3)": {
            'border-left': '0px',
            'border-right': '0px',
        },
        '&.MuiTab-textColorInherit.Mui-selected': {
            color: "black",
            fontWeight: 500
        }
    },
    tabPannel: {
        padding: "30px 0px",
        minHeight: 950
    }
}));

const StyledTabs = withStyles(theme => ({
    indicator: {
        height:3,
        backgroundColor:"white",
        transition:"none",
        display: "flex",
        justifyContent: "center",
        "& > div": {
            // maxWidth: 200,
            width: "100%",
            backgroundColor: palette.cyan[0]
        }
    }
}))(props => <Tabs {...props} TabIndicatorProps={{ children: <div /> }} />);


const MyPage = ({location,history}) => {

    const {user = null} = useSelector(({auth}) => ({user:auth.user}))

    useEffect(() => {
        if(user === null){
            history.push("/auth/login")
        }
    },[])


    const classes = useStyles();
    const theme = useTheme();
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);

        if(location.search !== ""){
            history.push(`${location.pathname}`)

        }
    };

    const handleChangeIndex = index => {
        setValue(index);
    };

    // useEffect(() => {
    //
    // },location.pathname)


    return (
        <section className={`${classes.subContatiner} sub_container`}>
            <div className="title_cont">
                <h1 className="title_style_5">마이페이지</h1>
            </div>
            <div className={classes.root}>
                <AppBar position="static" className={classes.appBar} color="default">
                    <StyledTabs
                        value={value}
                        onChange={handleChange}
                        // indicatorColor="black"
                        // textColor="primary"
                        variant="fullWidth"
                        aria-label="full width tabs example"
                    >
                        <Tab classes={{ root: classes.label }} label="회원정보" {...a11yProps(0)} />
                        <Tab classes={{ root: classes.label }} label="서비스 정보" {...a11yProps(1)} />
                        <Tab classes={{ root: classes.label }} label="포인트" {...a11yProps(2)} />
                        <Tab classes={{ root: classes.label }} label="API 토큰관리" {...a11yProps(3)} />
                    </StyledTabs>
                </AppBar>
                {value === 0 && (
                    <TabPanel value={value} index={0} dir={theme.direction} className={classes.tabPannel}>
                        <Box className={classes.subTitleBox}>
                            <h1 className="title_style_2">회원 정보</h1>
                            {/*<p className="txt_style_2">*/}
                            {/*    에네이 클라우드 플랫폼 사용을 위한 가이드 입니다.*/}
                            {/*</p>*/}
                        </Box>
                            <MyInfo/>
                            <IamPortScript/>
                    </TabPanel>
                )}
                {value === 1 && (
                    <TabPanel value={value} index={1} dir={theme.direction} className={classes.tabPannel}>
                        <Box className={classes.subTitleBox}>
                            <h1 className="title_style_2">서비스 정보</h1>
                            <p className="txt_style_2">
                                이곳에서 고객님의 서비스 이용현황을 확인 할 수 있습니다.
                            </p>
                            {/*<Typography component="h1">서비스 정보</Typography>*/}
                            {/*<Typography component="p">이곳에서 고객님의 서비스 이용현황을 확인 할 수 있습니다.</Typography>*/}
                        </Box>
                        {value === 1 && (
                            <ServiceInfo/>
                        )}
                    </TabPanel>
                )}
                    <TabPanel value={value} index={2} dir={theme.direction} className={classes.tabPannel}>
                        <Box className={classes.subTitleBox}>
                            <h1 className="title_style_2">포인트 정보</h1>
                            <p className="txt_style_2">
                                이곳에서 고객님의 충전 및 사용 포인트 정보를 보실 수 있습니다.
                            </p>
                            {/*<Typography component="h1">포인트 정보</Typography>*/}
                            {/*<Typography component="p">이곳에서 고객님의 충전 및 사용 포인트 정보를 보실 수 있습니다.</Typography>*/}
                        </Box>
                        {value === 2 && (
                            <PointInfo location={location}/>
                        )}
                    </TabPanel>
                    <TabPanel value={value} index={3} dir={theme.direction} className={classes.tabPannel}>
                        <h1 className="title_style_2">API 토큰관리</h1>
                        <p className="txt_style_2">
                            이곳에서 고객님의 충전 및 사용 포인트 정보를 보실 수 있습니다.
                        </p>
                        {value === 3 && (
                            <ApiTokenManagement location={location}/>
                        )}
                    </TabPanel>
            </div>
        </section>
    );
};

export default React.memo(withRouter(MyPage));