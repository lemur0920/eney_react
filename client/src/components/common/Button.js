import React from 'react';
import styled,{css} from 'styled-components'
import palette from '../../lib/styles/palette'

const StyledButton = styled.button`
  border:none;
  border-radius: 4px;
  font-size:1rem;
  //font-weight: bold;
  padding:0.25rem 1rem;
  color:white;
  outline: none;
  cursor: pointer;
  min-width: 64px;
  
  background: ${palette.gray[8]};
  &:hover{
  background: ${palette.gray[6]};
  }
  
  ${props =>
    props.fullWidth &&
    css`
      padding-top : 0.75rem;
      padding-bottom: 0.75rem;
      width:100%;
      font-size:1.125rem;
    `}
  
  ${props =>
    props.eney &&
    css`
    background: ${palette.cyan[0]};
    padding: 6px 16px;
    font-size: 0.875rem;
    margin:8px;
    font-family: "noto";
    font-weight: 500;
    line-height: 1.75;
    &:hover{
      //font-weight: bold;
      //background: ${palette.cyan[1]};
      background: #009EFF;
    }  
  `}
    
`;

const Button = props => <StyledButton {...props}/>

export default Button;