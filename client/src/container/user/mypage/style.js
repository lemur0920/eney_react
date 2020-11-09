import {makeStyles} from "@material-ui/styles";
import palette from "../../../lib/styles/palette";
import arrowWhite from "../../../asset/image/arrow_white.png";

export default makeStyles(theme => ({
    radioGroup:{
        display:"inline-block",
        "& label":{
            marginLeft:1
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
        borderRight: "none"
    },
    registInfo:{
        width: "72%",
        display: "inline-block",
        verticalAlign: "top",
        ['@media (max-width:768px)']: {
            width:"100%",
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
        "& .pay_div":{
            border: "1px solid lightgray",
            margin: "0 0 0 auto",
            ['@media (max-width:768px)']: {
                border: "none",
            }
        },
        "& .pay_div h1":{
            margin: 0,
            fontSize: 18,
            textAlign: "center",
            fontWeight: 400,
            paddingTop: 25,
            // background: "#c2c2c2"
        },
        "& .pay_div .amount_list":{
            padding:15,
            ['@media (max-width:768px)']: {
                padding:0,
                paddingTop:15
            }
        },
        "& .pay_div .amount_list table":{
            textAlign: "center",
            width: "100%",
            marginBottom: 120,
            ['@media (max-width:768px)']: {
                marginBottom: 30,
            }
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
            background: `url(${arrowWhite}) no-repeat 220px center #37afe5`,
            fontSize: 16,
            color: "white"
        },
        ['@media (max-width:768px)']: {
            width:"100%",
            padding:0
        }

    },
}));
