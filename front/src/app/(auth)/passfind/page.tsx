import PassFind from './Passfind';
import { Metadata } from 'next/types';
export const metadata: Metadata = {
  title: '다가치 비밀번호 찾기',
  description: 'password find',
};
const PassFindWrap = () => {
  return <PassFind />;
};

export default PassFindWrap;
