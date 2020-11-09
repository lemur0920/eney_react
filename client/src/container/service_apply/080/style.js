import {makeStyles} from "@material-ui/styles";
import palette from "../../../lib/styles/palette";
import arrowWhite from "../../../asset/image/arrow_white.png";

export default makeStyles(theme => ({
    radioGroup:{
        display:"inline-block",
        "& label":{
            "& span":{
                fontSize:13
            }
        }
    },
    radio:{
        "&$checked": {
            color: palette.cyan[0],
        }
    },
    th:{
        fontWeight: 400,
        padding: "10px 0px",
        fontSize: 16,
        width: "20%",
        textAlign:"center",
        border: "1px solid #c2c2c2",
        borderLeft: "none"
    },
    td:{
        padding: "10px 10px",
        fontSize: 16,
        width: "75%",
        border: "1px solid #c2c2c2",
        borderRight: "none",
        height:60,
        "& input[type='file']":{
            border:"none"
        }
    },
    registInfo:{
        width: "72%",
        display: "inline-block",
        verticalAlign: "top",
        ['@media (max-width:1070px)']: {
            width: '100%'
        }
    },
    pointPayBox:{
    "& font":{
        color: palette.cyan[0]
    }
    },
    registPay:{
        width: "26%",
        paddingLeft:15,
        display: "inline-block",
        verticalAlign: "top",
        ['@media (max-width:1070px)']: {
            width: '100%',
            paddingLeft:0,
        },
        "& .pay_div":{
            border: "1px solid lightgray",
            margin: "0 0 0 auto",
            ['@media (max-width:1070px)']: {
                border:"none"
            },
        },
        "& .pay_div h1":{
            margin: 0,
            fontSize: 18,
            textAlign: "center",
            fontWeight: 400,
            paddingTop: 25,
        },
        "& .pay_div .txt_style_1":{
            padding:10
        },
        "& .pay_div .amount_list":{
            padding:15
        },
        "& .pay_div .amount_list table":{
            textAlign: "center",
            width: "100%",
            marginBottom: 120,
            ['@media (max-width:768px)']: {
                marginBottom: 30,
            },
        },
        "& table tr":{
            borderTop: "1px solid lightgray",
            padding: "10px 0",
        },
        "& table tr:first-child":{
            borderTop: "1px solid #c2c2c2",
            borderBottom: "1px solid #c2c2c2",
            padding: "10px 0",
        },
        "& table th":{
            background: "none",
            border: "none",
            fontWeight:400,
            fontSize:17,
            borderBottom: "2px solid #c2c2c2",
            padding: "12px 0",
        },
        "& table td":{
            width: "50%",
            padding: "12px 10px",
            color:"gray",
            fontWeight: 500,
        },
        "& .pay_div .amount_list #payment":{
            padding:30
        },
        "& .pay_div .amount_list #payment span:first-child":{
            // textAlign: "right",
            border: "none",
            color: palette.cyan[0],
            fontWeight: 500,
        },
        "& .pay_div .amount_list .amount":{
            border: "none",
            padding: 0,
            margin: "5px 0",
            fontWeight: 400,
            fontSize: 24,
            // color: palette.cyan[0]
        },
        "& .service_reg_next":{
            display: "block",
            textAlign: "center",
            cursor: "pointer",
            margin: "5px 0",
            borderRadius: 3,
            padding: "15px 0",
            background: `url(${arrowWhite}) no-repeat 70% center #37afe5`,
            fontSize: 16,
            color: "white"
        }

    },
    // root: {
    //     backgroundColor: theme.palette.background.paper,
    //     maxWidth: 1140,
    //     margin: "0 auto",
    //     // flexGrow: 1,"auth"
    // },
    // appBar: {
    //     backgroundColor: "white",
    //     boxShadow: "none",
    //     marginTop: 10,
    //     // maxWidth:640,
    //     margin: "0 auto",
    //     padding: "0 24px",
    // },
    // subTitle: {
    //     textAlign: "center",
    //     fontSize: 24,
    //     padding: 40
    // },
    // indicator: {
    //     height: 3,
    //     backgroundColor: palette.cyan[0],
    //     transition: "none",
    //     display: "flex",
    //     justifyContent: "center",
    //     // backgroundColor: "transparent",
    //     "& > div": {
    //         maxWidth: 40,
    //         width: 40,
    //         backgroundColor: "#635ee7"
    //     }
    // },
    // labelColor: {
    //     color: palette.cyan[0],
    // },
    // label: {
    //     fontSize: 15,
    //     fontWeight: 400,
    //     color: "#626262",
    //     opacity: 1,
    //     border: "1px solid #ced0da",
    //     "&:nth-child(2)": {
    //         'border-left': '0px',
    //         'border-right': '0px',
    //     },
    //     '&.MuiTab-textColorInherit.Mui-selected': {
    //         color: "black",
    //         fontWeight: 500
    //     }
    // },
    // tabPannel: {
    //     padding: "30px 0px",
    //     minHeight: 950
    // }
    // ['@media (min-width:780px)']: { // eslint-disable-line no-useless-computed-key
    //     width: '80%'
    // }
}));
