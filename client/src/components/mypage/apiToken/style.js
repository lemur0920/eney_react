import { makeStyles } from "@material-ui/core/styles";
import palette from "../../../lib/styles/palette";

export default makeStyles(theme => ({
    box:{
        height:100
    },
    table:{
        maxWidth: 500,
        display:'inline-block',
        ['@media (max-width:768px)']: {
            // width: '100%',
            marginLeft:0,
        }
    },
    th:{
        width:'30%',
        height:50,
        textAlign:'left',
        padding: "0 30px",
        ['@media (max-width:768px)']: {
            padding:0
        }
    },
    td:{
        width:'400px',
        textAlign:'left',
        padding: "0 50px",
        ['@media (max-width:768px)']: {
            padding: "0 20px",
        }
    },
    inputLabel:{
        width:'150px'
    },
    allocationIP:{
        width:70,
        marginRight:8,
        ['@media (max-width:768px)']: {
            width:30,
        }
    }
    // tabContent: {
    //     margin:"0 auto",
    //     width:"79.5%"
    // },
    // division: {
    //     borderBottom:"1px solid black",
    //     textAlign: "left",
    //     paddingTop:40,
    //     paddingBottom:40,
    //     '& .MuiBox-root': {
    //         // height:58
    //     },
    // },
    // subTitle: {
    //     textAlign:"center",
    //     fontSize:24,
    //     padding:40
    // },
    // label: {
    //     width:"20%",
    //     fontSize:12,
    //     display:"inline-block",
    //     padding:10,
    //     float:"left",
    //     textAlign:"left",
    //     ['@media (max-width:514px)']: {
    //         width: '100%'
    //     }
    // },
    // detailLabel:{
    //     clear:"both",
    //     width:"20%",
    //     fontSize:12,
    //     display:"inline-block",
    //     padding:10,
    //     float:"left",
    //     textAlign:"left",
    //     ['@media (max-width:514px)']: {
    //         width: '100%'
    //     }
    // },
    // postLabel:{
    //     textAlign:"center",
    //     width:"12%"
    // },
    // modalLabel: {
    //     width:"15%",
    //     fontSize:12,
    //     display:"inline-block",
    //     padding:10,
    //     float:"left",
    //     textAlign:"center",
    //     ['@media (max-width:514px)']: {
    //         width: '100%'
    //     }
    // },
    // fullInput: {
    //     width:"80%",
    //     height:44,
    //     display:"inline-block",
    //     padding:6,
    //     marginBottom:5,
    //     '& .MuiOutlinedInput-input': {
    //         padding: "10px 10px"
    //     },
    //     '& fieldset':{
    //         backgroundColor: "lightgray",
    //         opacity: 0.3,
    //     },
    //     ['@media (max-width:514px)']: {
    //         width: '100%'
    //     }
    // },
    // divisionInput: {
    //     width:"61%",
    //     padding:6,
    //     float:"left",
    //     '& .MuiOutlinedInput-input': {
    //         padding: "10px 10px"
    //     },
    //     '& fieldset':{
    //         backgroundColor: "lightgray",
    //         opacity: 0.3,
    //     },
    //     ['@media (max-width:514px)']: {
    //         width: '100%'
    //     }
    // },
    // detailAddrInput: {
    //     width:"61%",
    //     height:44,
    //     padding:6,
    //     float:"left",
    //     '& .MuiOutlinedInput-input': {
    //         padding: "10px 10px"
    //     },
    //     '& fieldset':{
    //         backgroundColor: "lightgray",
    //         opacity: 0.3,
    //     },
    //     ['@media (max-width:514px)']: {
    //         marginLeft:0,
    //         width: '100%'
    //     }
    // },
    // modalDetailAddrInput:{
    //     width:"61.5%",
    //     height:44,
    //     display:"inline-block",
    //     padding:6,
    //     float:"left",
    //     marginLeft:"20%",
    //     '& .MuiOutlinedInput-input': {
    //         padding: "10px 10px"
    //     },
    //     ['@media (max-width:514px)']: {
    //         marginLeft:0,
    //         width: '100%'
    //     }
    // },
    // changeButton:{
    //     width:"18%",
    //     padding:10,
    //     marginLeft:5,
    //     color:"white",
    //     ['@media (max-width:514px)']: {
    //         width: '98%'
    //     },
    // },
    // textField:{
    //     width:"100%",
    //     padding:"5px",
    // },
    // btn:{
    //     float:"right",
    //     margin:"24px 13px 8px 13px",
    //     backgroundColor: palette.cyan[0],
    //     color:"white",
    //     '&:hover': {
    //         fontWeight: "bold",
    //         backgroundColor:palette.cyan[1]
    //     }
    // },
    // num:{
    //     width:"90%",
    //     margin:"5px"
    // },
    // domainSelect:{
    //     minWidth:120,
    //     margin:5,
    //     height:33
    // },
    // email:{
    //     width:"100px",
    //     margin:"5px",
    //     '& input':{
    //         height:20,
    //     }
    // },
    // postCodeInput:{
    //     width:"19%",
    //     height:44,
    //     display:"inline-block",
    //     padding:6,
    //     float:"left",
    //     '& .MuiOutlinedInput-input': {
    //         padding: "10px 10px"
    //     },
    //     '& fieldset':{
    //         backgroundColor: "lightgray",
    //         opacity: 0.3,
    //     },
    //     ['@media (max-width:514px)']: {
    //         marginLeft:0,
    //         width: '100%'
    //     }
    // },
    // addrInput: {
    //     width:"48.5%",
    //     float:"left",
    //     padding:6,
    //     '& .MuiOutlinedInput-input': {
    //         padding: "10px 10px"
    //     },
    //     '& fieldset':{
    //         backgroundColor: "lightgray",
    //         opacity: 0.3,
    //     },
    //     ['@media (max-width:514px)']: {
    //         marginLeft:0,
    //         width: '100%'
    //     }
    // },
    // modalBtnBox:{
    //     textAlign:"center",
    //     marginTop:20
    // },
    // addrBox:{
    //     height:55
    // },
    // at:{
    //     lineHeight:3
    // },
    // select:{
    //     width:"100%"
    // }

}));
