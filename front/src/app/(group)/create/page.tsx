import { Metadata } from 'next/types';
import Create from './Create';

export const metadata: Metadata = {
  title: '다가치 그룹 생성하기',
  description: '다가치 그룹 생성 페이지 입니다.',
};

const CreateWrap = () => {
  return <Create />;
};

export default CreateWrap;
