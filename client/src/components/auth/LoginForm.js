import React from 'react';
import styled from 'styled-components'
import palette from '../../lib/styles/palette'
import Button from '../common/Button'
import {Link} from 'react-router-dom'
import {login} from "../../modules/auth";
import {TextField, Box, makeStyles} from '@material-ui/core';


const useStyles = makeStyles(theme => ({
    input: {
        width:"90%",
        margin:10
    }
}));


const LoginFormBlock = styled.div`
  text-align: center;
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

const UserId = styled(TextField)({
    width:"80%",
    margin:5

});

const UserPw = styled(TextField)({
    width:"80%",
});


const ButtonWidthMarginTop = styled(Button)`
    margin-top:2rem;
    width:80%;
`;

const ErrorMessage = styled.div`
  color:red;
  text-align: center;
  font-sie:0.875rem;
  margin-top:1rem;
`;
const LoginForm = ({type, form, onChange, onSubmit, error,children}) => {

    const classes = useStyles();

    return (
        <LoginFormBlock>
            <form onSubmit={onSubmit}>
                <UserId className={classes.input} label="아이디" autoComplete="userId"  name="userId"  type="text" onChange={onChange} value={form.userId}/>
                <UserPw className={classes.input} label="비밀번호" autoComplete="new-password"  name="password"  type="password"  onChange={onChange} value={form.password}/>
                {/*<StyledInput autoComplete="userId"  name="userId" placeholder="아이디" type="text" onChange={onChange} value={form.userId}/>*/}
                {/*<StyledInput autoComplete="new-password"  name="password" placeholder="비밀번호" type="password"  onChange={onChange} value={form.password}/>*/}
                {error && <ErrorMessage>{error}</ErrorMessage>}
                <ButtonWidthMarginTop  eney fullWidth >
                    로그인
                </ButtonWidthMarginTop>
            </form>
            {children}
        </LoginFormBlock>
    );
};

export default React.memo(LoginForm);