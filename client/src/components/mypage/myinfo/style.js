import { makeStyles } from "@material-ui/core/styles";
import palette from "../../../lib/styles/palette";

export default makeStyles(theme => ({
    tabContent: {
        margin:"0 auto",
        width:"79.5%",
        ['@media (max-width:765px)']: {
            width: '100%'
        }
    },
    division: {
        borderBottom:"1px solid lightgray",
        textAlign: "left",
        paddingTop:40,
        paddingBottom:40,
        '& .MuiBox-root': {
            // height:58
        },
        '&:last-child': {
            borderBottom:0,
        },
        ['@media (max-width:765px)']: {
            marginTop:20
        },

    },
    subTitle: {
        textAlign:"center",
        fontSize:24,
        padding:40
    },
    label: {
        width:"20%",
        fontSize:12,
        display:"inline-block",
        padding:10,
        float:"left",
        textAlign:"left",
        ['@media (max-width:514px)']: {
            width: '100%'
        }
    },
    detailLabel:{
        clear:"both",
        width:"20%",
        fontSize:12,
        display:"inline-block",
        padding:10,
        float:"left",
        textAlign:"left",
        ['@media (max-width:514px)']: {
            width: '100%'
        }
    },
    postLabel:{
        // textAlign:"center",
        width:"12%",
        textAlign:'left',
        ['@media (max-width:765px)']: {
            width: '100%',
        }
    },
    modalLabel: {
        width:"15%",
        fontSize:12,
        display:"inline-block",
        padding:10,
        float:"left",
        textAlign:"center",
        ['@media (max-width:514px)']: {
            width: '100%'
        }
    },
    fullInput: {
        width:"80%",
        height:44,
        display:"inline-block",
        padding:6,
        marginBottom:5,
        '& .MuiOutlinedInput-input': {
            padding: "10px 10px"
        },
        '& fieldset':{
            backgroundColor: "lightgray",
            opacity: 0.3,
        },
        ['@media (max-width:514px)']: {
            width: '100%'
        }
    },
    divisionInput: {
        width:"61%",
        padding:6,
        float:"left",
        '& .MuiOutlinedInput-input': {
            padding: "10px 10px"
        },
        '& fieldset':{
            backgroundColor: "lightgray",
            opacity: 0.3,
        },
        ['@media (max-width:514px)']: {
            width: '100%'
        }
    },
    detailAddrInput: {
        width:"61%",
        height:44,
        padding:6,
        float:"left",
        '& .MuiOutlinedInput-input': {
            padding: "10px 10px"
        },
        '& fieldset':{
            backgroundColor: "lightgray",
            opacity: 0.3,
        },
        ['@media (max-width:514px)']: {
            marginLeft:0,
            width: '100%'
        }
    },
    modalDetailAddrInput:{
        width:"61.5%",
        height:44,
        display:"inline-block",
        padding:6,
        float:"left",
        marginLeft:"20%",
        '& .MuiOutlinedInput-input': {
            padding: "10px 10px"
        },
        ['@media (max-width:514px)']: {
            marginLeft:0,
            width: '100%'
        }
    },
    changeButton:{
        width:"18%",
        padding:10,
        marginLeft:5,
        color:"white",
        ['@media (max-width:514px)']: {
            width: '98%',
            marginTop:20
        },
    },
    businessLicenseAddBtn:{
        width:"18%",
        padding:10,
        color:"white",
        marginLeft: 19,
        ['@media (max-width:514px)']: {
            width: '98%',
            marginTop:20,
            marginLeft:5
        },
    },
    fileInputBox:{
        width:"59.4%",
        display:"inline-block",
        paddingLeft:6,
        ['@media (max-width:514px)']: {
            width: '98%',
        },

    },
    fileInput:{
        borderRadius: 3,
        opacity: 0.3,
        backgroundColor: 'lightgray',
        paddingLeft: 8,
        borderColor: "rgba(0, 0, 0, 0.23)",
        font: "inherit",
        width: "98.5%",
        height: 26,
        margin: 0,
        padding: 6,
        minWidth: 0,
        boxSizing: "content-box",
        ['@media (max-width:514px)']: {
            width: '98%',
        },
    },
    licenseBox:{
        marginLeft:"20%",
        width: "80%",
        '& p':{
            verticalAlign: "middle",
            width: "72.9%",
            display: "inline-block",
            ['@media (max-width:514px)']: {
                marginTop:5,
                width: '96%',
            },
        },
        '& button':{
            marginTop: 5,
            marginLeft: 12.4,
            width: "10.3%",
            ['@media (max-width:514px)']: {
                marginLeft:0,
                width: '100%',
            },
        },
        "&:last-child": {
            marginBottom:10
        },
        ['@media (max-width:514px)']: {
            marginLeft:0,
            width: '98%',
        },

    },
    searchButton:{
        width:"15%",
        padding:5,
        marginLeft:8,
        marginTop:8,
        color:"white",
        backgroundColor:"#37afe5",
        '&:hover': {
            fontWeight: "bold",
            backgroundColor:palette.cyan[1]
        },
        ['@media (max-width:514px)']: {
            width: '98%',
            marginTop:20
        },
    },
    textField:{
        width:"100%",
        padding:"5px",
    },
    btn:{
        float:"right",
        margin:"24px 13px 8px 13px",
        backgroundColor: palette.cyan[0],
        color:"white",
        '&:hover': {
            fontWeight: "bold",
            backgroundColor:palette.cyan[1]
        }
    },
    num:{
        width:"90%",
        margin:"5px"
    },
    domainSelect:{
        minWidth:120,
        margin:5,
        height:33
    },
    email:{
        width:"100px",
        margin:"5px",
        '& input':{
            height:20,
        }
},
    postCodeInput:{
        width:"18%",
        height:44,
        marginLeft:4,
        display:"inline-block",
        padding:6,
        float:"left",
        '& .MuiOutlinedInput-input': {
            padding: "10px 10px"
        },
        '& fieldset':{
            backgroundColor: "lightgray",
            opacity: 0.3,
        },
        ['@media (max-width:514px)']: {
            marginLeft:0,
                width: '100%'
        }
    },
    addrInput: {
        width:"48.5%",
        float:"left",
        padding:6,
        '& .MuiOutlinedInput-input': {
            padding: "10px 10px"
        },
        '& fieldset':{
            backgroundColor: "lightgray",
            opacity: 0.3,
        },
        ['@media (max-width:514px)']: {
            marginLeft:0,
            width: '100%'
        }
    },
    modalBtnBox:{
        textAlign:"center",
        marginTop:20
    },
    addrBox:{
        // height:55
    },
    at:{
        lineHeight:3
    },
    select:{
        width:"100%"
    }

}));
