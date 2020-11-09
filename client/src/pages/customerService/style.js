import {makeStyles} from "@material-ui/core/styles";
import palette from "../../lib/styles/palette";

export default makeStyles(theme => ({
    root: {
        backgroundColor: "white",
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
        padding: "0 24px",
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
        "&:nth-child(2)": {
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
