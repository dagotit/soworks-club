'use client';

// import styles from './ListItem.module.css';
import { memo } from 'react';
import { useParams } from 'next/navigation';
import { useGetGroupInfo } from '@/hooks/useGroup';

// 불필요한 리렌더링 막기
const GroupDetail = memo(() => {
  const pathname: any = useParams();
  const apiGroupInfo = useGetGroupInfo(Number(pathname.groupId));

  console.log('apiGroupInfo:', apiGroupInfo);

  return <div>{pathname.groupId}</div>;
});
export default GroupDetail;
