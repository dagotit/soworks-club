import { Metadata } from 'next/types';
import Join from './Join';

export const metadata: Metadata = {
  title: '다가치 회원가입',
  description: '다가치 회원가입 페이지 입니다.',
};

const JoinWrap = () => {
  return <Join />;
};

export default JoinWrap;
