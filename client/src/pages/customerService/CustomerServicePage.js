import React, {useEffect, useState} from 'react';
import {Link, withRouter} from 'react-router-dom';
import NoticeTemplate from "../../components/customerservice/notice/NoticeTemplate";
import Notice from "../../container/customerservice/notice/Notice";
import qs from "qs";
import FAQTemplate from "../../components/customerservice/faq/FAQTemplate";
import FAQ from "../../container/customerservice/faq/FAQ";
import AppBar from "@material-ui/core/AppBar/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import TabPanel from "../../components/common/TabPanel";
import palette from "../../lib/styles/palette";
import {useTheme, withStyles} from '@material-ui/core/styles';
import TermsTemplate from "../../components/customerservice/terms/TermsTemplate";
import Terms from "../../container/customerservice/terms/Terms";


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

function a11yProps(index) {
    return {
        id: `full-width-tab-${index}`,
        'aria-controls': `full-width-tabpanel-${index}`,
    };
}


const CustomerServicePage = ({location, history,match}) => {

    const [type, setType] = useState("");
    const [id, setId] = useState();
    const [value, setValue] = React.useState(0);
    const theme = useTheme();

    const setBoardType = index => {
        switch (index){
            case 0:
                return "notice";
            case 1:
                return "help";
            case 2:
                return "terms";
        }
    };


    const handleChange = (event, newValue) => {
        history.push(`${match.url}?type=${setBoardType(newValue)}`)

        setValue(newValue);
    };

    const handleChangeIndex = index => {
        history.push(`${match.url}?type=${setBoardType(index)}`)

        setValue(index);
    };
    useEffect(() => {

        const {type} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(type === "notice"){
            setValue(0);
        }else if(type === "help"){
            setValue(1);
        }else if(type === "terms"){
            setValue(2);
        }else{
            history.push(`${location.pathname}?type=notice&page=1`)
            return;
        }

    },[])



    useEffect(() => {

        const {type,id} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(!type){
            history.push(`${location.pathname}?type=notice&page=1`)
            return;
        }

        if(id){
            setId(id)
        }else{
            setId(null)
        }

        setType(type);

        if(type === "notice"){
            setValue(0)
        }


    },[location.search])

    return (
        <section className="sub_container customer_container">
            <div className="title_cont">
                <h1 className="title_style_5">고객센터</h1>
            </div>
            <div className="root">
                <AppBar position="static" className="appBar" color="default">
                    <StyledTabs
                        value={value}
                        onChange={handleChange}
                        variant="fullWidth"
                        aria-label="full width tabs example"
                        >
                        <Tab classes={{ root: 'label' }} label="공지사항" {...a11yProps(0)} />
                        <Tab classes={{ root: 'label' }} label="이용가이드" {...a11yProps(1)} />
                        <Tab classes={{ root: 'label' }} label="이용약관" {...a11yProps(2)} />
                    </StyledTabs>
                </AppBar>
                    <TabPanel value={value} index={0} dir={theme.direction} className='tabPannel'>
                        {type === "notice" &&(
                            <NoticeTemplate id={id} type={type}>
                                <Notice/>
                            </NoticeTemplate>
                        )}
                    </TabPanel>
                    <TabPanel value={value} index={1} dir={theme.direction} className='tabPannel'>
                        {type === "help" &&(
                            <FAQTemplate id={id} type={type}>
                                <FAQ/>
                            </FAQTemplate>
                        )}
                    </TabPanel>
                    <TabPanel value={value} index={2} dir={theme.direction} className='tabPannel'>
                        {type === "terms" &&(
                            <TermsTemplate id={id} type={type}>
                                <Terms/>
                            </TermsTemplate>
                        )}
                    </TabPanel>
            </div>
        </section>
    )
};

export default React.memo(withRouter(CustomerServicePage));