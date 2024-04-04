import { Metadata } from 'next/types';
import Login from './Login';

export const metadata: Metadata = {
  title: '다가치 로그인',
  description: 'login page',
};

const LoginWrap = () => {
  return <Login />;
};

export default LoginWrap;
