import React from 'react';
import styled from 'styled-components'
import palette from '../../lib/styles/palette'
import LoginForm from '../../components/auth/LoginForm'
import eneyLogoLarge from '../../asset/image/eney_logo_large.png';
import {Link} from 'react-router-dom'

const LoginContainerBlock = styled.div`
    position: absolute;
    left:0;
    top:0;
    bottom:0;
    right:0;
    background: ${palette.gray[2]};
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

const WhiteBox = styled.div`
  .logo-area{
      display:block;padding-bottom: 2rem;
      text-align: center;
      font-weight: bold;
      letter-spacing: 2px;
      a{
        img{
          width: 50%;
        }
      }
  }
  
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.255);
  padding: 2rem;
  width:450px;
  background: white;
  border-radius: 2px;
  @media (max-width: 768px) {
    width:95%;
  }
`;

const LoginTemplate = ({children}) => {
    return (
        <LoginContainerBlock>
            <WhiteBox>
                <div className="logo-area">
                    <Link to="/">
                        <img src={eneyLogoLarge}/>
                    </Link>
                </div>
                {children}
            </WhiteBox>
        </LoginContainerBlock>
    );
};

export default LoginTemplate;