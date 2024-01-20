import { Metadata } from 'next/types';
import GroupDetail from '@/app/(group)/group/detail/[groupId]/GroupDetail';

export const metadata: Metadata = {
  title: '다가치 디테일 페이지',
  description: '다가치 디테일 페이지',
};

const GroupDetailWrap = () => {
  return <GroupDetail />;
};

export default GroupDetailWrap;
