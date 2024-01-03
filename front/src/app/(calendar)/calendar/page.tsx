import { Metadata } from 'next';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import CalendarMain from '@/app/(calendar)/calendar/CalendarMain';

export const metadata: Metadata = {
  title: '캘린더 리스트',
  description: '캘린더 - 메인 컨텐츠',
};

const CalendarMainWrap = () => {
  return <CalendarMain />;
};

export default CalendarMainWrap;
