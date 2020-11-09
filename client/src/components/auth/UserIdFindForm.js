import React from 'react';
import styled from "styled-components";
import palette from "../../lib/styles/palette";
import Button from "../common/Button";
import {Link} from "react-router-dom";

const IdFindFormBlock = styled.div`
  h3{
      margin:0;
      color:${palette.gray[8]};
      margin-bottom: 1rem;
  }
`;

const StyledInput = styled.input`
  font-size:1rem;
  border:none;
  border-bottom: 1px solid ${palette.gray[5]};
  padding-bottom: 0.5rem;
  outline: none;
  width:100%;
  &:focus{
    color:$oc-teal-7;
    border-bottom: 1px solid ${palette.gray[7]};
  }
  & +&{
    margin-top:1.3rem;
  }

`;

const FindFormFooter = styled.div`
    margin-top:2rem;
    text-align:center;
    a{
      margin:1rem;
      color:${palette.gray[6]};
      text-decoration: underline;
      &:hover{
        color: ${palette.gray[9]}
      }
    }
`;

const ButtonWidthMarginTop = styled(Button)`
    margin-top:2rem;
`;

const ErrorMessage = styled.div`
  color:red;
  text-align: center;
  font-sie:0.875rem;
  margin-top:1rem;
`;


const UserIdFindForm = () => {
    return (
        <IdFondFormBlock>
            <form onSubmit={onSubmit}>
                <StyledInput autoComplete="userId"  name="userId" placeholder="아이디" type="text" />
                <StyledInput autoComplete="new-password"  name="password" placeholder="비밀번호" type="password" />
                {error && <ErrorMessage>{error}</ErrorMessage>}
                <ButtonWidthMarginTop  eney fullWidth >
                    로그인
                </ButtonWidthMarginTop>
            </form>
            <IdFindFormFooter>
                <Link to="/auth/signup">아이디 찾기</Link>
                <Link to="/auth/signup">비밀번호 찾기</Link>
                <Link to="/auth/signup">회원가입</Link>
            </IdFindFormFooter>
        </IdFondFormBlock>
    );
};

export default UserIdFindForm;