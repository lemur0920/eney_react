import React from 'react';
import JoinTemplate from "../../components/auth/JoinTemplate";
import Join from "../../container/auth/Join";
import IamPortScript from "../../components/util/IamPortScript";

const JoinPage = () => {
    return (
        <JoinTemplate>
            <Join/>
            <IamPortScript/>
        </JoinTemplate>
    );
};

export default JoinPage;