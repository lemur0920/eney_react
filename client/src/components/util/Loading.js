import React from 'react';

const Loading = () => {
    return (
        <div>
            <img src={require("../../asset/image/spinner.gif")} style={{ margin: "0 auto", display: "block",clear:"both"}} />
        </div>
    );
};

export default Loading;