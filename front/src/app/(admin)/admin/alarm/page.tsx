import { Metadata } from 'next/types';
import { Fragment } from 'react';
import Header from '@/components/Header';
import UserList from '@/components/UserList';

export const metadata: Metadata = {
  title: '다가치 관리자 페이지 - 알람전송',
  description: '사용자 알림 보내기',
};


const AdminAlarmWrap = () => {
  return (
    <Fragment>
      <Header isBackBtn={true} />
      <UserList />
    </Fragment>
  )
}

export default AdminAlarmWrap;