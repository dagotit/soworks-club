import { Metadata } from 'next/types';
import { Fragment } from 'react';
import Header from '@/components/Header';
import News from '@/app/(system)/news/News';
import { apiGetAlarmList } from '@/services/userService';

export const metadata: Metadata = {
  title: '다가치 알림',
  description: '알림/공지',
};
const NewsWrap = async () => {

  return (
    <Fragment>
      <Header />
      <News />
    </Fragment>
  );
};

export default NewsWrap;