import React from 'react';

const FullLoading = () => {
    return (
        <div style={{position:"absolute", width:"100%",height:"100%",backgroundColor: "rgba( 211, 211, 211, 0.6 )"}}>
            <img src={require("../../asset/image/animation.gif")} style={{ position:"absolute" ,top:"50%" ,left:"50%", marginTop:-25 ,marginLeft:-25}}/>
        </div>
    );
};

export default FullLoading;