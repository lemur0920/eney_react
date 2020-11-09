import React, {useEffect,Fragment} from 'react';
import {makeStyles, Box,Typography,AppBar,Tabs,Tab} from "@material-ui/core";
import palette from "../../../lib/styles/palette";
import {useDispatch, useSelector} from "react-redux";
import {Link} from 'react-router-dom';
import qs from "qs";
import {usePreloader} from "../../../lib/PreloaderContext";
import {myinfo, initialize,pointChargeLogList, pointPaymentLogList, registerCoupon} from "../../../modules/user/mypage/mypage";
import PointLogTable from "../../../components/mypage/point/PointLogTable";
import TabPanel from "../../../components/common/TabPanel";
import Pagination from "../../../components/util/Pagination";
import {withRouter} from "react-router-dom";
import PaymentTable from "../../../components/mypage/point/PaymentLogTable";
import PointChargeInfo from "./PointChargeInfo";
import PointPaymentInfo from "./PointPaymentInfo";
import Button from "../../../components/common/Button";
import styled from "styled-components";
import CouponRegisterModal from "../../../components/mypage/point/CouponRegisterModal";

const useStyles = makeStyles({
    root: {
        flexGrow: 1,
        // backgroundColor: theme.palette.background.paper,
    },
    content: {
        wid: 650,
    },
    pointInfoBox: {
        width:"100%",
        border: "1px solid rgba(224, 224, 224, 1)",
        marginBottom:20,
        ['@media (max-width:768px)']: {
            textAlign:"center"
        },
    },
    txt01:{
        display:"inline-block",
        padding:"50px 30px 50px 100px",
        ['@media (max-width:768px)']: {
            display:"block",
            padding:"30px",
        },
    },
    point:{
        fontSize:25,
    },
    couponBtn:{
        padding:20,
        backgroundColor:palette.cyan[0],
        color:"white",
        float:"right",
        marginTop:30,
        marginRight:30,
        '&:hover': {
            fontWeight: "bold",
            backgroundColor:palette.cyan[0]
        }
    },
    pointTab:{
        backgroundColor:"white",
        color:"black",
        margin: "0 auto",
        boxShadow: "none",
        // borderBottom: "0.5px solid lightgray",
    },
    indicator:{
        backgroundColor:palette.cyan[0],
    },
    btnBox:{
      display:'inline',
        ['@media (max-width:768px)']: {
            display:'block',
        },
    },
    tab:{
        padding:0
    }

});

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

const CustomButton = styled(Button)`
    display: inline-block;
    margin:0;
    float:right;
    margin-top:30px;
    margin-right:30px;
    padding:20px;
    @media (max-width: 768px) {
    float:none;
    margin-right:0;
    width:46%;
    margin: 2%;
  }
    
`;


const PointInfo = ({location,history}) => {
    const classes = useStyles();
    const [value, setValue] = React.useState(0);
    const [pageNum, setpageNum] = React.useState(0);
    const [isShowModal, setIsShowModal] = React.useState(false);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const dispatch = useDispatch();

    const {chargeLogList, chargePageInfo, chargeLoading, paymentLogList, paymentPageInfo, paymentLoading,epoint,couponResult, couponError} = useSelector(({mypage,loading,auth}) =>({
        chargeLogList: mypage.pointChargeLogList,
        chargePageInfo: mypage.pointChargeLogPage,
        chargeLoading: loading['mypage/POINT_CHARGE_LOG_LIST'],
        paymentLogList: mypage.pointPaymentLogList,
        paymentPageInfo: mypage.pointPaymentLogPage,
        paymentLoading: loading['mypage/POINT_PAYMENT_LOG_LIST'],
        epoint: mypage.user.epoint,
        couponResult: mypage.coupon.registerResult,
        couponError: mypage.coupon.error,
    }))

    useEffect(() => {
        if(couponResult === true && couponError === null){
            dispatch(myinfo())
        }
    },[couponResult])

    const onPaymentPageChanged = data => {
            const {chargePage = 1} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        history.push(`${location.pathname}?chargePage=${chargePage}&paymentPage=${data}`)
    };

    const showRegisterModal = () =>{
        setIsShowModal(!isShowModal);
        dispatch(initialize('coupon'))
    }

    const onRegisterCoupon = (couponNum) => {
        dispatch(registerCoupon(couponNum));
    }

    const moveChage = () =>{
        history.push('/pointCharge')
    }

    return (
        <Box className={classes.content}>
            <Box className={classes.pointInfoBox}>
                <Typography className={classes.txt01} component="span">
                    사용가능한 포인트
                </Typography>
                <Typography className={classes.point} component="span">
                    {epoint}
                </Typography>
                <Typography component="span">
                    &nbsp;&nbsp;point
                </Typography>
                <div className={classes.btnBox}>
                    <CustomButton eney onClick={showRegisterModal}>
                        포인트 쿠폰 등록
                    </CustomButton>
                    <CustomButton eney onClick={moveChage}>
                        포인트 충전
                    </CustomButton>
                </div>
            </Box>
            <AppBar className={classes.pointTab} position="static">
                <Tabs value={value} onChange={handleChange}
                      classes={{
                          indicator: classes.indicator
                      }}>
                    <Tab label="포인트 사용 내역" {...a11yProps(0)} />
                    <Tab label="포인트 충전 내역" {...a11yProps(1)} />
                </Tabs>
                <TabPanel value={value} index={0} className={classes.tab}>
                    {value === 0 && (
                        <PointChargeInfo/>
                    )}
                </TabPanel>
                <TabPanel value={value} index={1} className={classes.tab}>
                    {value === 1 && (
                        <PointPaymentInfo/>
                    )}
                </TabPanel>
            </AppBar>
            {isShowModal && (
                <CouponRegisterModal open={isShowModal} onClose={showRegisterModal} onSubmit={onRegisterCoupon} error={couponError} couponResult={couponResult}/>
            )}
        </Box>
    );
};

export default withRouter(PointInfo);