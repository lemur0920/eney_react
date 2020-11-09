import {makeStyles} from "@material-ui/styles";
import palette from "../../lib/styles/palette";
// import palette from "../../../lib/styles/palette";

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
    box:{
        backgroundColor:"#fafafa",
        padding:0

    },
    table:{
        border:"1px solid lightgray",
        backgroundColor:"white",

    },
    th:{
        fontWeight: 400,
        padding: "10px 0px",
        fontSize: 16,
        width: "25%",
        textAlign:"center",
        border: "1px solid #c2c2c2",
        borderLeft: "none",
        height:60,
    },
    td:{
        padding: "10px 10px",
        fontSize: 16,
        width: "75%",
        border: "1px solid #c2c2c2",
        borderRight: "none"
    },
    downloadBtn:{
        border: "1px solid #c2c2c2",
        background: "#fff",
        cursor: "pointer",
        padding: "5px 10px",
        borderRadius: 3
    },
    applyBtnBox:{
        textAlign:"center",
    }
}));
